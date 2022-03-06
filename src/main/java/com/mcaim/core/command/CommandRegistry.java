package com.mcaim.core.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.Field;
import java.util.Map;

public class CommandRegistry {
    private static CommandMap COMMAND_MAP;

    public static void registerCommand(Command command) {
        COMMAND_MAP.register("", command);
    }

    public static void unRegister(String command) {
        unregisterCommand(command);
    }

    @SuppressWarnings("unchecked")
    private static void unregisterCommand(String command) {
        Field commandMap;
        Field knownCommands;
        try {
            commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMap.setAccessible(true);
            knownCommands = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommands.setAccessible(true);
            ((Map<String, Command>) knownCommands.get(commandMap.get(Bukkit.getServer()))).remove(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            COMMAND_MAP = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        } catch (Exception e) {
            System.err.println("Couldn't access command map");
        }
    }
}
