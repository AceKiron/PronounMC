package dev.mxace.pronounmc.api;

import dev.mxace.pronounmc.PronounMC;
import dev.mxace.pronounmc.api.pronounssets.PronounsSet;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class PronounsDatabase {
    public final static PronounsDatabase instance = new PronounsDatabase();

    private File file;
    private YamlConfiguration config;

    private PronounsDatabase() {
        file = new File(PronounMC.instance.getDataFolder(), "database_pronouns.yml");

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

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PronounsSetApprovementStatus getApprovementStatus(Player player, PronounsSet pronounsSet) {
        if (!config.contains(player.getUniqueId().toString())) return PronounsSetApprovementStatus.ASK;
        if (!config.getConfigurationSection(player.getUniqueId().toString()).contains(pronounsSet.getShortName())) return PronounsSetApprovementStatus.ASK;

        return PronounsSetApprovementStatus.values()[config.getConfigurationSection(player.getUniqueId().toString()).getInt(pronounsSet.getShortName())];
    }

    public void setApprovementStatus(Player player, PronounsSet pronounsSet, PronounsSetApprovementStatus approvementStatus) {
        if (!config.contains(player.getUniqueId().toString())) config.createSection(player.getUniqueId().toString());

        for (PronounsEventListener listener : PronounAPI.instance.getListeners()) {
            listener.onPronounsSetApprovementStatusChanged(player, pronounsSet, getApprovementStatus(player, pronounsSet), approvementStatus);
        }

        config.getConfigurationSection(player.getUniqueId().toString()).set(pronounsSet.getShortName(), approvementStatus.ordinal());

        save();
    }
}
