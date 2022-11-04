package io.github.acekironcommunity.pronounmc;

import io.github.acekironcommunity.pronounmc.commands.AddPronounCommand;
import io.github.acekironcommunity.pronounmc.commands.GetPronounsCommand;
import io.github.acekironcommunity.pronounmc.commands.RemovePronounCommand;
import io.github.acekironcommunity.pronounmc.commands.UnusedCommand;
import io.github.acekironcommunity.pronounmc.handlers.ChatHandler;
import io.github.acekironcommunity.pronounmc.tabcompleters.PronounsTabCompleter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Initialize the config
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);

        Utils.SetPlugin(this);

        Utils.log("Starting up", true);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Utils.log("Hooking into PlaceholderAPI", false);
            new PronounMCPlaceholder().register();
        }

        if (getConfig().getBoolean("handle-chat")) new ChatHandler(this);

        new PronounAPI(this);

        PronounsTabCompleter pronounsTabCompleter = new PronounsTabCompleter();

        boolean pronounOverrideEnabled = Utils.getPronounOverrideEnabled();

        getCommand("getpronouns").setExecutor(new GetPronounsCommand());

        if (pronounOverrideEnabled) {
            UnusedCommand cmdHandler = new UnusedCommand();
            
            getCommand("addpronoun").setExecutor(cmdHandler);
            getCommand("removepronoun").setExecutor(cmdHandler);
        } else {
            getCommand("addpronoun").setExecutor(new AddPronounCommand());
            getCommand("addpronoun").setTabCompleter(pronounsTabCompleter);

            getCommand("removepronoun").setExecutor(new RemovePronounCommand());
            getCommand("removepronoun").setTabCompleter(pronounsTabCompleter);
        }
    }

    @Override
    public void onDisable() {
        Utils.log("Shutting down", true);
    }
}
