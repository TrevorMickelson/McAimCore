package com.mcaim.core.util;

import java.awt.Color;
import net.md_5.bungee.api.ChatColor;


public class ChatPrefix {
    public static final String BROWN = ChatColor.of(new Color(130, 67, 20)).toString();
    public static final String ORANGE = ChatColor.of(new Color(255, 140, 0)).toString();
    public static final String CRIMSON = ChatColor.of(new Color(220, 20, 60)).toString();
    public static final String PINK = ChatColor.of(new Color(255, 153, 255)).toString();
    public static final String DEEP_PINK = ChatColor.of(new Color(255, 20, 147)).toString();
    public static final String INDIGO = ChatColor.of(new Color(75, 0, 130)).toString();
    public static final String FOREST_GREEN = ChatColor.of(new Color(12, 144, 36)).toString();

    // Symbols
    public static final char COMMAND_SYMBOL = '●';
    public static final char INFO = '*';
    public static final char WARN_SYMBOL = '✘';

    // Prefixes for easier messaging
    public static final String FAIL = ChatColor.DARK_GRAY + "[" + CRIMSON + COMMAND_SYMBOL + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
    public static final String SUCCESS = ChatColor.DARK_GRAY + "[" + FOREST_GREEN + COMMAND_SYMBOL + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
    public static final String INFORM = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "> " + ChatColor.GRAY;
    public static final String WARN = ChatColor.DARK_RED + "" + WARN_SYMBOL + " " + ChatColor.RED;
}
