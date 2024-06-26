package dev.mxace.pronounmc.pronouns;

import dev.mxace.pronounmc.PronounMC;

import dev.mxace.pronounmc.config.SimpleConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Pronouns {

    private final Map<String, LocalePronouns> localePronouns;

    private Pronouns(Map<String, LocalePronouns> localePronouns) {
        this.localePronouns = localePronouns;
    }

    public static Pronouns parse(ConfigurationSection section) {
        Map<String, LocalePronouns> localePronouns = new HashMap<String, LocalePronouns>();

        for (String locale : section.getKeys(false)) {
            if (PronounMC.pluginInstance.getSimpleConfig().localeExists(locale)) {
                localePronouns.put(locale, LocalePronouns.parse(Objects.requireNonNull(section.getConfigurationSection(locale))));
            } else {
                Bukkit.getLogger().warning("Unknown locale: " + locale + ".");
            }
        }

        return new Pronouns(localePronouns);
    }

    public LocalePronouns getLocalePronouns(String locale) {
        SimpleConfig config = PronounMC.pluginInstance.getSimpleConfig();
        return config.localeExists(locale) ? localePronouns.get(locale) : localePronouns.get(config.defaultLocale);
    }

}