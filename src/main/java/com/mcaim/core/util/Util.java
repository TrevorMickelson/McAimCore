package com.mcaim.core.util;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * This is sort of a misc utility
 * class, which just means, I couldn't
 * find a home for these methods so
 * so they're just gonna go here
 */
public class Util {
    /**
     * This method formats the time
     * until a specific time/date
     *
     * Makes it human readable for in-game
     */
    public static String formatTime(long millis) {
        long totalSeconds = millis / 1000;

        // Setting up times
        long days = totalSeconds / 86400;
        long hours = (totalSeconds / 3600) - (days * 24);
        long minutes = (totalSeconds / 60) - (days * 1440) - (hours * 60);
        long seconds = totalSeconds - (days * 86400) - (hours * 3600) - (minutes * 60);

        // Adding values to string
        String formatted = (days <= 0 ? "" : days + "d ") + (hours <= 0 ? "" : hours + "h ") +
                           (minutes <= 0 ? "" : minutes + "m ") + (seconds <= 0 ? "" : seconds + "s");

        return formatted.isEmpty() ? "0s" : formatted;
    }

    /**
     * Makes a large amount of seconds readable
     */
    public static String formatSeconds(long seconds) { return formatTime(seconds * 1000); }

    public static int getRandom(int min, int max) {
        Random random = new Random();
        int randIndex = random.nextInt(2);
        int randomValue = random.nextInt((max - min)) + 1;

        // I'm determining if I want to
        // subtract from max, or add from
        // min, this makes it more of a
        // "random" experience, because each
        // one is "random" but different
        return randIndex == 0 ? min + randomValue : max - randomValue;
    }

    /**
     * Random percent chance for
     * something to possibly happen
     */
    public static boolean percentChance(int percent) { return new Random().nextInt(100) < percent; }

    /**
     * Converts an integer to a roman numeral
     */
    public static String intToRomanNumeral(int value) {
        StringBuilder builder = new StringBuilder();

        int[] num = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
        String[] sym = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
        int i = 12;

        while (value > 0) {
            int div = value / num[i];
            value = value % num[i];

            while (div-- > 0)
                builder.append(sym[i]);

            i--;
        }

        return builder.toString();
    }

    /**
     * Rounds double to (x) places
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Converts of a list of enums to a string list
     */
    public static <E extends Enum<E>> List<String> enumsToStrings(Class<E> enumData) {
        List<String> stringList = new ArrayList<>();

        for (Enum<E> enumVal : enumData.getEnumConstants())
            stringList.add(enumVal.name().toLowerCase());

        return stringList;
    }

    public static String formatNumber(double value) {
        return value == 0 ? "0.0" : new DecimalFormat("#,###.00").format(value);
    }

    public static String format(String s) {
        if (s.contains("_"))
            s = s.replaceAll("_", " ");

        return WordUtils.capitalizeFully(s);
    }

    public static String trans(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
