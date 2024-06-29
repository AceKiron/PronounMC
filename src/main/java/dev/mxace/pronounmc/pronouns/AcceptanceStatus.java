package dev.mxace.pronounmc.pronouns;

import dev.mxace.pronounmc.PronounMC;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AcceptanceStatus {

    private final Map<String, String> localeAcceptanceStatuses;
    private final boolean positive;

    private AcceptanceStatus(Map<String, String> localeAcceptanceStatuses, boolean positive) {
        this.localeAcceptanceStatuses = localeAcceptanceStatuses;
        this.positive = positive;
    }

    public static AcceptanceStatus parse(ConfigurationSection section) {
        ConfigurationSection localesSection = Objects.requireNonNull(section.getConfigurationSection("locales"));
        Map<String, String> localeAcceptanceStatuses = new HashMap<String, String>();

        for (String locale : localesSection.getKeys(false)) {
            if (PronounMC.pluginInstance.getSimpleConfig().localeExists(locale)) {
                localeAcceptanceStatuses.put(locale, localesSection.getString(locale));
            } else {
                Bukkit.getLogger().warning("Unknown locale: " + locale + ".");
            }
        }

        return new AcceptanceStatus(localeAcceptanceStatuses, section.getBoolean("positive", true));
    }

    public String getLocaleString(String locale) {
        return localeAcceptanceStatuses.get(locale);
    }

    public boolean isPositive() {
        return positive;
    }

}
