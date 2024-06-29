package dev.mxace.pronounmc;

import dev.mxace.pronounmc.commands.MyPronounsCommand;
import dev.mxace.pronounmc.commands.PronounsInfoCommand;
import dev.mxace.pronounmc.config.AcceptanceStatusesConfig;
import dev.mxace.pronounmc.config.PronounsConfig;
import dev.mxace.pronounmc.config.SimpleConfig;
import dev.mxace.pronounmc.config.TextsConfig;
import dev.mxace.pronounmc.handlers.ChatHandler;
import dev.mxace.pronounmc.handlers.JoinHandler;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

public final class PronounMC extends JavaPlugin {

    public static PronounMC pluginInstance;

    private PronounMCDatabase database;

    private SimpleConfig config;
    private PronounsConfig pronounsConfig;
    private TextsConfig textsConfig;
    private AcceptanceStatusesConfig acceptanceStatusesConfig;

    private void registerCommand(Class<?> c, String commandName) {
        PluginCommand pc = getCommand(commandName);
        if (pc == null) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not find /" + commandName + " command.");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            try {
                Object commandInstance = c.getDeclaredField("commandInstance").get(null);

                pc.setExecutor((CommandExecutor) commandInstance);
                pc.setTabCompleter((TabCompleter) commandInstance);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                Bukkit.getLogger().log(Level.SEVERE, ex.getMessage());
                Bukkit.getPluginManager().disablePlugin(this);
            }
        }
    }

    @Override
    public void onEnable() {
        pluginInstance = this;

        config = new SimpleConfig();
        pronounsConfig = new PronounsConfig();
        textsConfig = new TextsConfig();
        acceptanceStatusesConfig = new AcceptanceStatusesConfig();

        if (!getDataFolder().exists()) {
            if (!getDataFolder().mkdirs()) {
                Bukkit.getLogger().warning("Data folder could not be created.");
            }
        }

        try {
            database = new PronounMCDatabase("pronouns.db");
        } catch (SQLException | IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, ex.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        }

        registerCommand(PronounsInfoCommand.class, "pronounsinfo");
        registerCommand(MyPronounsCommand.class, "mypronouns");

        try {
            JoinHandler joinHandler = new JoinHandler(pronounsConfig.getAllIdentifiers());
            getServer().getPluginManager().registerEvents(joinHandler, this);

            for (Player p : getServer().getOnlinePlayers()) {
                try {
                    joinHandler.registerDefaultPronounsAcceptanceStatuses(p.getUniqueId());
                } catch (SQLException ex) {
                    Bukkit.getLogger().log(Level.SEVERE, ex.getMessage());
                }
            }
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, ex.getMessage());
        }

        getServer().getPluginManager().registerEvents(new ChatHandler(), this);
    }

    @Override
    public void onDisable() {
        try {
            database.closeConnection();
        } catch (SQLException ex) {
            Bukkit.getLogger().log(Level.WARNING, ex.getMessage());
        }
    }

    public PronounMCDatabase getDatabase() {
        return database;
    }

    public AcceptanceStatusesConfig getAcceptanceStatusesConfig() {
        return acceptanceStatusesConfig;
    }

    public SimpleConfig getSimpleConfig() {
        return config;
    }

    public PronounsConfig getPronounsConfig() {
        return pronounsConfig;
    }

    public TextsConfig getTextsConfig() {
        return textsConfig;
    }

}
