package com.mcaim.core.command;

import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class QuickCommand {
    private final Map<String, Consumer<CommandSender>> subCommands = new HashMap<>();
    private Consumer<CommandSender> command;
    private String permission;
    private boolean requiredOp;
    private boolean player;
    private boolean console;

    Map<String, Consumer<CommandSender>> getSubCommands() { return subCommands; }
    Consumer<CommandSender> getCommand() { return command; }
    boolean isRequiredOp() { return requiredOp; }
    boolean isPlayer() { return player; }
    boolean isConsole() { return console; }

    public QuickCommand assertArgument(String argument, Consumer<CommandSender> sender) {
        subCommands.put(argument.toLowerCase(), sender);
        return this;
    }

    public QuickCommand assertPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public QuickCommand assertOp() {
        requiredOp = true;
        return this;
    }

    public QuickCommand assertPlayer() {
        player = true;
        return this;
    }

    public QuickCommand assertConsole() {
        console = true;
        return this;
    }

    public void register(String name, Consumer<CommandSender> command) {
        this.command = command;
        Command newCommand = new Command(name, this);

        if (permission != null)
            newCommand.setPermission(permission);

        CommandRegistry.registerCommand(newCommand);
    }

    public static QuickCommand create() {
        return new QuickCommand();
    }
}
