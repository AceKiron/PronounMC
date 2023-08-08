package dev.mxace.pronounmc;

import dev.mxace.pronounmc.api.PronounAPI;
import dev.mxace.pronounmc.api.PronounsDatabase;
import dev.mxace.pronounmc.commandexecutors.PronounmcCommandExecutor;
import dev.mxace.pronounmc.commandexecutors.ViewpronounsCommandExecutor;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * Main class for PronounMC.
 * @see org.bukkit.plugin.java.JavaPlugin
 * @author AceKiron
 * @version 2.3
 */
public final class PronounMC extends JavaPlugin {
    /**
     * Singleton instance of the PronounMC plugin.
     * @see org.bukkit.plugin.java.JavaPlugin
     */
    public static PronounMC instance;

    /**
     * Make constructor private.
     */
    private PronounMC() {

    }

    /**
     * Called on plugin enable.
     * Replaces the instance singleton, loads the default pronouns sets and sets command executors.
     * @throws RuntimeException Gets thrown if the pronouns sets couldn't be loaded.
     * @see dev.mxace.pronounmc.api.pronounssets
     */
    @Override
    public void onEnable() {
        instance = this;

        // Load all pronouns
        try {
            PronounAPI.instance.loadPronounsSetsInPackage(getClassLoader(), "dev.mxace.pronounmc.api.pronounssets");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        getCommand("pronounmc").setExecutor(PronounmcCommandExecutor.instance);
        getCommand("viewpronouns").setExecutor(ViewpronounsCommandExecutor.instance);
    }

    /**
     * Called on plugin disable.
     * Saves any unsaved changes to the database.
     * @see dev.mxace.pronounmc.api.PronounsDatabase
     */
    @Override
    public void onDisable() {
        PronounsDatabase.instance.save();
    }
}
