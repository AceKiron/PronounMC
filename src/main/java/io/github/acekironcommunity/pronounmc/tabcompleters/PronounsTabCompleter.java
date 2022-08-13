package io.github.acekironcommunity.pronounmc.tabcompleters;

import io.github.acekironcommunity.pronounmc.PronounAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PronounsTabCompleter implements TabCompleter {

    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 2) {
            List<String> players = new ArrayList<String>();

            for (Object player : Bukkit.getServer().getOnlinePlayers().toArray()) {
                String name = ((Player) player).getName();

                if (name.toLowerCase().startsWith(args[1].toLowerCase()))
                    players.add(name);
            }

            return players;
        }

        List<String> list = PronounAPI.getAllCodes();

        if (args.length == 1) {
            // Don't even ask
            List<String> itemsToRemove = new ArrayList<>();

            for (String s : list) {
                if (!s.startsWith(args[0].toLowerCase())) {
                    itemsToRemove.add(s);
                }
            }

            list.removeAll(itemsToRemove);
        }

        return list;
    }

}
