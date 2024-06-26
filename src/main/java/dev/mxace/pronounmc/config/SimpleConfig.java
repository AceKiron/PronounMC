package dev.mxace.pronounmc.config;

import dev.mxace.pronounmc.PronounMC;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class SimpleConfig extends BaseConfig {

    private final List<String> locales;

    public final String defaultLocale, defaultAcceptanceStatus;
    public final boolean hideNegativePronounsAcceptance;

    public SimpleConfig() {
        super("config.yml");

        locales = config.getStringList("locales");

        defaultLocale = config.getString("default_locale");
        defaultAcceptanceStatus = config.getString("default_acceptance_status");

        hideNegativePronounsAcceptance = config.getBoolean("hide_negative_pronouns_acceptance", true);

        if (!localeExists(defaultLocale)) {
            Bukkit.getLogger().warning("Default locale is not part of the locales list.");
        }
    }

    public boolean localeExists(String locale) {
        return locales.contains(locale);
    }

    public List<String> getAllLocales() {
        return locales;
    }

    public String getPreferredLocale(UUID uuid) {
        try {
            return PronounMC.pluginInstance.getDatabase().getPreferredLocale(new HashMap<String, String>() {{
                put("player_uuid", uuid.toString());
            }});
        } catch (SQLException ex) {
            Bukkit.getLogger().log(Level.SEVERE, ex.getMessage());
            return defaultLocale;
        }
    }

    public void setPreferredLocale(UUID uuid, String locale) {
        if (!locales.contains(locale)) return;

        try {
            PronounMC.pluginInstance.getDatabase().setPreferredLocale(new HashMap<String, String>() {{
                put("player_uuid", uuid.toString());
                put("locale", locale);
            }});
        } catch (SQLException ex) {
            Bukkit.getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }

}
