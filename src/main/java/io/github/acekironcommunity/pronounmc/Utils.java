package io.github.acekironcommunity.pronounmc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Utils {

    private static String messagePrefix;
    private static boolean verboseLogging;

    public static void SetPlugin(MyPlugin plugin) {
        messagePrefix = plugin.getConfig().getString("message-prefix");
        verboseLogging = plugin.getConfig().getBoolean("verbose-logging");
    }

    public static String formatMessage(String message, boolean ok) {
        if (ok) return formatMessage(message, ChatColor.GREEN);
        else return formatMessage(message, ChatColor.RED);
    }

    public static String formatMessage(String message, ChatColor color) {
        return color + formatMessage(message);
    }

    public static String formatMessage(String message) {
        return messagePrefix + message;
    }

    public static void log(String message, boolean verboseOnly) {
        if (verboseOnly) {
            if (verboseLogging) {
                Bukkit.getLogger().info("[PronounMC] " + message);
            }
        } else {
            Bukkit.getLogger().info("[PronounMC] " + message);
        }
    }

}
