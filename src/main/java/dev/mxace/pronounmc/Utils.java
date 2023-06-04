package dev.mxace.pronounmc;

import org.bukkit.ChatColor;

public class Utils {
    public final static Utils instance = new Utils();
    private Utils() {

    }

    public String formatString(String string, boolean ok) {
        return (ok ? ChatColor.GREEN : ChatColor.RED) + string;
    }

    public String capitalizeString(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
