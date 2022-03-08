package io.github.acekiron.pronounmc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class PMCPlugin extends JavaPlugin {

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

        Bukkit.getPluginCommand("addpronouns").setTabCompleter(new PronounsTabCompleter());
        Bukkit.getPluginCommand("removepronouns").setTabCompleter(new PronounsTabCompleter());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        if (command.getName().equalsIgnoreCase("getpronouns")) {
            if (!sender.hasPermission("pronounmc.get")) {
                sender.sendMessage(formatMessage("Missing the pronounmc.get permission.", true));
                return true;
            }

            commandGetPronouns((Player) sender, args);
            return true;
        }

        if (command.getName().equalsIgnoreCase("addpronouns")) {
            if (!sender.hasPermission("pronounmc.modify")) {
                sender.sendMessage(formatMessage("Missing the pronounmc.modify permission.", true));
                return true;
            }

            commandAddPronouns((Player) sender, args);
            return true;
        }

        if (command.getName().equalsIgnoreCase("removepronouns")) {
            if (!sender.hasPermission("pronounmc.modify")) {
                sender.sendMessage(formatMessage("Missing the pronounmc.modify permission.", true));
                return true;
            }

            commandRemovePronouns((Player) sender, args);
            return true;
        }

        return false;
    }

    private void commandGetPronouns(Player sender, String[] args) {
        if (args.length == 0) { // Get your own pronouns
            sender.sendMessage(formatMessage(PMCAPI.getPronouns(sender.getUniqueId()) + ".", false));
            return;
        }

        Player player = Bukkit.getPlayer(args[0]); // Get someone else's pronouns

        if (player == null) {
            sender.sendMessage(formatMessage("Could not find " + args[0] + ".", true));
            return;
        }

        sender.sendMessage(formatMessage(PMCAPI.getPronouns(player.getUniqueId()) + ".", false));
    }

    private void commandAddPronouns(Player sender, String[] args) {
        switch (args.length) {
            case 0:
                sender.sendMessage(formatMessage("Missing a set of pronouns.", true));
                return;

            case 1:
                // Modify own pronouns
                if (PMCAPI.addPronouns(sender.getUniqueId(), args[0])) {
                    sender.sendMessage(formatMessage("Updated pronouns.", false));
                    return;
                }

                sender.sendMessage(formatMessage("Could not update pronouns.", true));
                return;

            case 2:
                // Modify someone else's pronouns
                if (!sender.hasPermission("pronounmc.modify.other")) {
                    sender.sendMessage(formatMessage("Missing the pronounmc.modify.other permission.", true));
                    return;
                }

                Player player = Bukkit.getPlayer(args[1]);

                if (player == null) {
                    sender.sendMessage(formatMessage("Could not find " + args[0] + ".", true));
                    return;
                }

                if (PMCAPI.addPronouns(player.getUniqueId(), args[0])) {
                    sender.sendMessage(formatMessage("Updated pronouns.", false));
                    return;
                }

                sender.sendMessage(formatMessage("Could not update pronouns.", true));
                return;

            default:
                sender.sendMessage(formatMessage("Too many arguments.", true));
                return;
        }
    }

    private void commandRemovePronouns(Player sender, String[] args) {
        switch (args.length) {
            case 0:
                sender.sendMessage(formatMessage("Missing a set of pronouns.", true));
                return;

            case 1:
                // Modify own pronouns
                if (PMCAPI.removePronouns(sender.getUniqueId(), args[0])) {
                    sender.sendMessage(formatMessage("Updated pronouns.", false));
                    return;
                }

                sender.sendMessage(formatMessage("Could not update pronouns.", true));
                return;

            case 2:
                // Modify someone else's pronouns
                if (!sender.hasPermission("pronounmc.modify.other")) {
                    sender.sendMessage(formatMessage("Missing the pronounmc.modify.other permission.", true));
                    return;
                }

                Player player = Bukkit.getPlayer(args[1]);

                if (player == null) {
                    sender.sendMessage(formatMessage("Could not find " + args[0] + ".", true));
                    return;
                }

                if (PMCAPI.addPronouns(player.getUniqueId(), args[0])) {
                    sender.sendMessage(formatMessage("Updated pronouns.", false));
                    return;
                }

                sender.sendMessage(formatMessage("Could not update pronouns.", true));
                return;

            default:
                sender.sendMessage(formatMessage("Too many arguments.", true));
                return;
        }
    }

}
