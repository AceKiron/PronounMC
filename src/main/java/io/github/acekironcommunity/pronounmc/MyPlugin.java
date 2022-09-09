package io.github.acekironcommunity.pronounmc;

import io.github.acekironcommunity.pronounmc.commands.AddPronounCommand;
import io.github.acekironcommunity.pronounmc.commands.GetPronounsCommand;
import io.github.acekironcommunity.pronounmc.commands.RemovePronounCommand;
import io.github.acekironcommunity.pronounmc.handlers.ChatHandler;

import io.github.acekironcommunity.pronounmc.tabcompleters.PronounsTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        //Bukkit.getLogger().info("Starting up");
        Utils.log("Starting up", true);

        Utils.SetPlugin(this);

        // Initialize the config
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            //System.out.println("Hooking into PlaceholderAPI");
            Utils.log("Hooking into PlaceholderAPI", false);
            new PronounMCPlaceholder().register();
        }

        if (getConfig().getBoolean("handle-chat")) new ChatHandler(this);

        new PronounAPI(this);

        PronounsTabCompleter pronounsTabCompleter = new PronounsTabCompleter();

        getCommand("addpronoun").setExecutor(new AddPronounCommand());
        getCommand("addpronoun").setTabCompleter(pronounsTabCompleter);

        getCommand("removepronoun").setExecutor(new RemovePronounCommand());
        getCommand("removepronoun").setTabCompleter(pronounsTabCompleter);

        getCommand("getpronouns").setExecutor(new GetPronounsCommand());
    }

    @Override
    public void onDisable() {
        //Bukkit.getLogger().info("Shutting down");
        Utils.log("Shutting down", true);
    }
}
