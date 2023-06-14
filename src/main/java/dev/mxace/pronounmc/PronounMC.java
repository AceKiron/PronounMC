package dev.mxace.pronounmc;

import dev.mxace.pronounmc.api.PronounAPI;
import dev.mxace.pronounmc.api.PronounsDatabase;
import dev.mxace.pronounmc.commandexecutors.PronounmcCommandExecutor;
import dev.mxace.pronounmc.commandexecutors.ViewpronounsCommandExecutor;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class PronounMC extends JavaPlugin {
    public static PronounMC instance;

    @Override
    public void onEnable() {
        instance = this;

        // Load all pronouns
        try {
            PronounAPI.instance.loadPronounsSetsInPackage(getClassLoader(), "dev.mxace.pronounmc.api.pronounssets");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        getCommand("pronounmc").setExecutor(PronounmcCommandExecutor.instance);
        getCommand("viewpronouns").setExecutor(ViewpronounsCommandExecutor.instance);
    }

    @Override
    public void onDisable() {
        PronounsDatabase.instance.save();
    }
}
