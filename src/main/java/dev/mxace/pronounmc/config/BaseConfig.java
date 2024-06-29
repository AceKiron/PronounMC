package dev.mxace.pronounmc.config;

import dev.mxace.pronounmc.PronounMC;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class BaseConfig {

    private final String path;
    private final File file;

    protected YamlConfiguration config;

    protected BaseConfig(String path) {
        this.path = path;

        file = new File(PronounMC.pluginInstance.getDataFolder(), path);

        if (!file.exists()) {
            PronounMC.pluginInstance.saveResource(path, false);
            Bukkit.getLogger().info("Created configuration file '" + path + "'.");
        }

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException ex) {
            Bukkit.getLogger().warning("Configuration file '" + path + "' could not be loaded.");
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException ex) {
            Bukkit.getLogger().warning("Configuration file '" + path + "' could not be saved.");
        }
    }

    public void set(String key, Object value) {
        config.set(key, value);

        save();
    }

}
