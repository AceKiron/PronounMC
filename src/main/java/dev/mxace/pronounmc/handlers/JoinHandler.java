package dev.mxace.pronounmc.handlers;

import dev.mxace.pronounmc.PronounMC;
import dev.mxace.pronounmc.utils.FSUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class JoinHandler implements Listener {

    private List<String> identifiers;
    private List<String> queries;

    public JoinHandler(List<String> identifiers) throws IOException {
        this.identifiers = identifiers;
        queries = FSUtils.readResourceDirectory("sql/onJoin").stream().map(e -> {
            try {
                return FSUtils.readResource(e);
            } catch (IOException ex) {
                Bukkit.getLogger().log(Level.SEVERE, ex.getMessage());
                return null;
            }
        }).toList();
    }

    public void registerDefaultPronounsAcceptanceStatuses(UUID uuid) throws SQLException {
        String uuidString = uuid.toString();

        for (String identifier : identifiers) {
            Map<String, String> map = new HashMap<String, String>() {{
                put("pronoun_identifier", identifier);
                put("player_uuid", uuidString);
                put("default_acceptance_status", Integer.toString(PronounMC.pluginInstance.getAcceptanceStatusesConfig().defaultAcceptanceStatusIndex));
                put("default_locale", PronounMC.pluginInstance.getSimpleConfig().defaultLocale);
            }};

            for (String query : queries) {
                PronounMC.pluginInstance.getDatabase().executeQuery(query, map);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        try {
            registerDefaultPronounsAcceptanceStatuses(event.getPlayer().getUniqueId());
        } catch (SQLException ex) {
            Bukkit.getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }

}
