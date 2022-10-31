package io.github.acekironcommunity.pronounmc.handlers;

import io.github.acekironcommunity.pronounmc.MyPlugin;

import io.github.acekironcommunity.pronounmc.PronounAPI;
import io.github.acekironcommunity.pronounmc.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatHandler implements Listener {

    public ChatHandler(MyPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) {
            Utils.log("Another plugin already handled the chat event", true);
            return;
        }

        event.setCancelled(true);

        String message = "<" + event.getPlayer().getDisplayName() + ">[" + PronounAPI.getPronouns(event.getPlayer().getUniqueId()) + "] " + event.getMessage();

        for (Player player : event.getRecipients()) {
            player.sendMessage(message);
        }

        System.out.println(message);
    }

}
