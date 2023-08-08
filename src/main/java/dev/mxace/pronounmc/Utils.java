package dev.mxace.pronounmc;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

/**
 * Utilities class.
 * @author AceKiron
 * @version 2.3
 */
public class Utils {
    /**
     * Singleton instance of the Utils class.
     */
    public final static Utils instance = new Utils();

    /**
     * Make constructor private.
     */
    private Utils() {

    }

    /**
     * Formats a string to have red or green text.
     * @param string String to be formatted
     * @param ok Makes the text green if true, otherwise it makes the text red.
     * @return Formatted string.
     */
    public String formatString(@NotNull String string, boolean ok) {
        return (ok ? ChatColor.GREEN : ChatColor.RED) + string;
    }

    /**
     * Capitalizes the first character of a string.
     * @param string The string to be capitalized.
     * @return The capitalized string.
     */
    public String capitalizeString(@NotNull String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
