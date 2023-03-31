package dev.mxace.pronounmc.api;

import dev.mxace.pronounmc.PronounMC;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PronounsDatabase {

    private File file;

    protected YamlConfiguration config;

    public PronounsDatabase() {
        PronounMC plugin = PronounMC.GetInstance();
        file = new File(plugin.getDataFolder(), "database_pronouns.yml");

        if (!file.exists()) {
            file.getParentFile().mkdirs();

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = new YamlConfiguration();

        try {
            config.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public List<Pronoun> Get(UUID playerUuid) {
        return config.getStringList(playerUuid.toString()).stream().map(identifier -> PronounAPI.GetPronounFromIdentifier(identifier)).collect(Collectors.toList());
    }

    public void Set(UUID playerUuid, List<Pronoun> pronouns) {
        config.set(playerUuid.toString(), pronouns.stream().map(pronoun -> pronoun.GetFullName()).collect(Collectors.toList()));
        Save();
    }

    public void Save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
