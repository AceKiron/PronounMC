package dev.mxace.pronounmc.api;

import dev.mxace.pronounmc.PronounMC;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

/**
 * Main database class.
 * @author AceKiron
 * @version 2.3
 */
public class PronounsDatabase {
    /**
     * Singleton instance of the PronounsDatabase class.
     */
    public final static PronounsDatabase instance;

    static {
        try {
            instance = new PronounsDatabase();
        } catch (IllegalStateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * File where the database is stored.
     */
    private final File file;

    /**
     * Yaml config where the database is stored.
     */
    private final YamlConfiguration config;

    /**
     * Make constructor private.
     * @throws IOException Gets thrown if the file couldn't be created.
     * @throws IllegalStateException Gets thrown if the file exists and doesn't exist at the same time.
     */
    @SuppressWarnings("CallToPrintStackTrace")
    private PronounsDatabase() throws IOException, IllegalStateException {
        file = new File(PronounMC.instance.getDataFolder(), "database_pronouns.yml");

        if (!file.exists()) {
            if (!file.getParentFile().mkdirs()) throw new IOException("Couldn't create parent directory.");

            try {
                if (!file.createNewFile()) throw new IllegalStateException("File both exists and doesn't exist.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = new YamlConfiguration();

        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save any unsaved changes to the database.
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve pronouns set approvement status of a player and pronouns set.
     * @param player Player whose pronouns set approvement status the API should get.
     * @param pronounsSet Pronouns set of which the API should get the approvement status.
     * @return The pronouns set approvement status of a player and pronouns set.
     * @see org.bukkit.entity.Player
     * @see dev.mxace.pronounmc.api.PronounsSet
     */
    public PronounsSetApprovementStatus getApprovementStatus(@NotNull Player player, @NotNull PronounsSet pronounsSet) {
        if (!config.contains(player.getUniqueId().toString())) return PronounsSetApprovementStatus.ASK;
        if (!config.getConfigurationSection(player.getUniqueId().toString()).contains(pronounsSet.getShortName())) return PronounsSetApprovementStatus.ASK;

        return PronounsSetApprovementStatus.values()[config.getConfigurationSection(player.getUniqueId().toString()).getInt(pronounsSet.getShortName())];
    }

    /**
     * Set the pronouns set approvement status of a player and pronouns set.
     * @param player Player whose pronouns set approvement status the API should change.
     * @param pronounsSet The pronouns set of which the API should change the approvement status.
     * @param approvementStatus The new approvement status.
     * @see org.bukkit.entity.Player
     * @see dev.mxace.pronounmc.api.PronounsSet
     * @see dev.mxace.pronounmc.api.PronounsSetApprovementStatus
     */
    public void setApprovementStatus(@NotNull Player player, @NotNull PronounsSet pronounsSet, @NotNull PronounsSetApprovementStatus approvementStatus) {
        if (!config.contains(player.getUniqueId().toString())) config.createSection(player.getUniqueId().toString());

        for (PronounsEventListener listener : PronounAPI.instance.getListeners()) {
            listener.onPronounsSetApprovementStatusChanged(player, pronounsSet, getApprovementStatus(player, pronounsSet), approvementStatus);
        }

        config.getConfigurationSection(player.getUniqueId().toString()).set(pronounsSet.getShortName(), approvementStatus.ordinal());

        save();
    }
}
