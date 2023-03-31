package dev.mxace.pronounmc;

import dev.mxace.pronounmc.api.Pronoun;
import dev.mxace.pronounmc.api.PronounAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public final class PronounMC extends JavaPlugin {

    private static PronounMC instance;
    public static PronounMC GetInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;

        // Initialize the config
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);

        PronounAPI.Initialize();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) new Placeholder().register();

        if (getConfig().getBoolean("handle-chat")) Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onPlayerChat(AsyncPlayerChatEvent event) {
                if (event.isCancelled()) return;
                event.setCancelled(true);

                String message = "<" + event.getPlayer().getDisplayName() + ">[" + PronounAPI.AsString(PronounAPI.GetDatabase().Get(event.getPlayer().getUniqueId())) + "] " + event.getMessage();
                System.out.println(message);
                for (Player player : event.getRecipients()) player.sendMessage(message);
            }
        }, this);

        for (Map<?, ?> pronouns : getConfig().getMapList("pronouns")) {
            new Pronoun() {
                @Override
                public String GetSubjectPronoun() {
                    return (String) pronouns.get("subjectPronoun");
                }

                @Override
                public String GetObjectPronoun() {
                    return (String) pronouns.get("objectPronoun");
                }

                @Override
                public String GetPossessiveAdjective() {
                    return (String) pronouns.get("possessiveAdjective");
                }

                @Override
                public String GetPossessivePronoun() {
                    return (String) pronouns.get("possessivePronoun");
                }

                @Override
                public String GetReflexivePronoun() {
                    return (String) pronouns.get("reflexivePronoun");
                }
            };
        }

        getCommand("pronounmc").setExecutor(new PronounMCCommandExecutor());
    }

}
