package io.github.acekironcommunity.pronounmc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Utils {

    private static String messagePrefix;
    private static boolean verboseLogging;
    private static boolean pronounOverrideEnabled;
    private static String pronounOverride;

    public static void SetPlugin(MyPlugin plugin) {
        messagePrefix = plugin.getConfig().getString("message-prefix");
        verboseLogging = plugin.getConfig().getBoolean("verbose-logging");
        pronounOverrideEnabled = plugin.getConfig().getBoolean("enable-third-party-override");
        pronounOverride = plugin.getConfig().getString("third-party-override");
    }

    public static boolean getPronounOverrideEnabled() {
        return pronounOverrideEnabled;
    }

    public static String getPronounOverride() {
        return pronounOverride;
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
