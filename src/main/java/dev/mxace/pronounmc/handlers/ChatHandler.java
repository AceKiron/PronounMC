package dev.mxace.pronounmc.handlers;

import dev.mxace.pronounmc.PronounMC;

import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;

public class ChatHandler implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        try {
            if (event.getPlayer().hasPermission("pronounmc.pronounsinchat")) {
                for (Player recipient : event.getRecipients()) {
                    TextComponent message = new TextComponent(String.format(event.getFormat(), event.getPlayer().getDisplayName(), event.getMessage()));

                    message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(PronounMC.pluginInstance.getDatabase().getAllPronounsAcceptanceStatusesAsString(PronounMC.pluginInstance.getSimpleConfig().getPreferredLocale(recipient.getUniqueId()), new HashMap<String, String>() {{
                        put("player_uuid", event.getPlayer().getUniqueId().toString());
                    }}))));

                    recipient.spigot().sendMessage(message);
                }
            }

            Bukkit.getLogger().info(String.format(event.getFormat(), event.getPlayer().getDisplayName(), event.getMessage()));

            event.setCancelled(true);
        } catch (SQLException ex) {
            Bukkit.getLogger().log(Level.SEVERE, ex.getMessage());
            Bukkit.getPluginManager().disablePlugin(PronounMC.pluginInstance);
        }
    }

}
