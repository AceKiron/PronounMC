package io.github.acekironcommunity.pronounmc;

import org.bukkit.ChatColor;

public class Utils {

    public static String formatMessage(String message, boolean ok) {
        if (ok) return formatMessage(message, ChatColor.GREEN);
        else return formatMessage(message, ChatColor.RED);
    }

    public static String formatMessage(String message, ChatColor color) {
        return color + formatMessage(message);
    }

    public static String formatMessage(String message) {
        return "PronounMC: " + message;
    }

}
