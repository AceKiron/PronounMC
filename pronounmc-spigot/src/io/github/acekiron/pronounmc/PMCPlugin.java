package io.github.acekiron.pronounmc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PMCPlugin extends JavaPlugin {
	
	private static String formatMessage(String message, boolean red) {
		if (red) return ChatColor.RED + "PronounMC: " + message;
		return ChatColor.GREEN + "PronounMC: " + message;
	}
	
    @Override
    public void onEnable() {
    	PMCAPI.InitPMCAPI(this);
    	
        // Initialize the config
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        
        Bukkit.getPluginCommand("setpronouns").setTabCompleter(new PronounsTabCompleter());
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (!(sender instanceof Player)) {
            return false;
        }
    	
    	if (command.getName().equalsIgnoreCase("getpronouns")) {
    		commandGetPronouns((Player) sender, args);
    		return true;
    	}
    	
    	if (command.getName().equalsIgnoreCase("setpronouns")) {
    		commandSetPronouns((Player) sender, args);
    		return true;
    	}
    	
    	return false;
    }
    
    private void commandGetPronouns(Player sender, String[] args) {
    	if (!sender.hasPermission("pronounmc.get")) {
    		sender.sendMessage(formatMessage("Missing the pronounmc.get permission.", true));
    		return;
    	}
    	
    	if (args.length == 0) {
    		sender.sendMessage(formatMessage(PMCAPI.getString(PMCAPI.getPronouns(sender.getUniqueId())), false));
    		return;
    	}
    	
    	Player player = Bukkit.getPlayer(args[0]);
    	
    	if (player == null) {
    		sender.sendMessage(formatMessage("Could not find " + args[0] + ".", true));
    		return;
    	}
    	
    	sender.sendMessage(formatMessage(PMCAPI.getString(PMCAPI.getPronouns(player.getUniqueId())), false));
    }
    
    private void commandSetPronouns(Player sender, String[] args) {
    	if (!sender.hasPermission("pronounmc.set")) {
    		sender.sendMessage(formatMessage("Missing the pronounmc.set permission.", true));
    		return;
    	}
    	
    	if (args.length == 0) {
    		sender.sendMessage(formatMessage("Missing a set of pronouns", true));
    		return;
    	}
    	
    	if (PMCAPI.setPronouns(sender.getUniqueId(), args[0])) {
    		sender.sendMessage(formatMessage("Success setting pronouns to " + args[0] + ".", false));
    	} else {
    		sender.sendMessage(formatMessage("Could not set pronouns.", true));
    	}
    }
    
}
