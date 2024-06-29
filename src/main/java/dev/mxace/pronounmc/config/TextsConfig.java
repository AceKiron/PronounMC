package dev.mxace.pronounmc.config;

import dev.mxace.pronounmc.PronounMC;
import org.bukkit.configuration.ConfigurationSection;

public class TextsConfig extends BaseConfig {

    public TextsConfig() {
        super("texts.yml");
    }

    public String getLocaleText(String key, String locale) {
        ConfigurationSection section = config.getConfigurationSection(key);
        if (section == null) {
            return null;
        }

        return section.getString(locale);
    }

}
