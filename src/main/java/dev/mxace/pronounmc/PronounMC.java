package dev.mxace.pronounmc;

import com.google.common.reflect.ClassPath;

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
            ClassPath path = ClassPath.from(getClassLoader());
            for (ClassPath.ClassInfo info : path.getTopLevelClasses("dev.mxace.pronounmc.api.pronounssets")) {
                Class.forName(info.getName(), true, getClassLoader());
            }
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
