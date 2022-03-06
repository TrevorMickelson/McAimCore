package com.mcaim.core.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class Command extends org.bukkit.command.Command {
    private final Map<String, Consumer<CommandSender>> subCommands;
    private final Consumer<CommandSender> command;
    private final boolean requiredOp;
    private final boolean player;
    private final boolean console;

    public Command(String name, QuickCommand quickCommand) {
        super(name);
        subCommands = quickCommand.getSubCommands();
        command = quickCommand.getCommand();
        requiredOp = quickCommand.isRequiredOp();
        player = quickCommand.isPlayer();
        console = quickCommand.isConsole();
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        List<String> displayedArguments = new LinkedList<>();
        int length = args.length;

        if (length == 1) {
            String argument = args[0].toLowerCase();

            for (String key : subCommands.keySet()) {
                if (key.startsWith(argument))
                    displayedArguments.add(key);
            }
        }

        return displayedArguments;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        try {
            String permission = getPermission();
            boolean hasPermission = permission == null || permission.isEmpty() || sender.hasPermission(permission);

            if (!hasPermission) {
                sender.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command");
                return false;
            }

            // Checking for if required op
            if (requiredOp && !sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "Only operators can perform this command");
                return false;
            }

            if (player && sender instanceof ConsoleCommandSender) {
                sender.sendMessage(ChatColor.RED + "Only players can perform this command");
                return false;
            }

            if (console && sender instanceof Player) {
                sender.sendMessage(ChatColor.RED + "This command can only be executed by the console");
                return false;
            }

            // I need to determine if there's sub commands
            // and then determine if there's an argument to check for
            boolean isSubCommandAndArgument = !subCommands.isEmpty() && args.length > 0;

            if (isSubCommandAndArgument) {
                String argument = args[0].toLowerCase();
                Consumer<CommandSender> senderConsumer = subCommands.get(argument);

                if (senderConsumer == null) {
                    sender.sendMessage(ChatColor.RED + "That argument does not exist!");
                    return false;
                }

                senderConsumer.accept(sender);
                return true;
            }

            command.accept(sender);
            return true;
        } catch(Exception e){
            e.printStackTrace();
            sender.sendMessage(getUsage());
        }

        return false;
    }
}
