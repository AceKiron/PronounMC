package io.github.acekiron.pronounmc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class PronounsTabCompleter implements TabCompleter {
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
        	
        	List<String> list = PMCAPI.getAllCodes();
        	
        	if (args.length > 0) {
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

        return null;
    }

}
