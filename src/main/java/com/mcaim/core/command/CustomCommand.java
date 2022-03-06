package com.mcaim.core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class CustomCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender var1, Command var2, String var3, String[] var4) {
        return false;
    }
}
