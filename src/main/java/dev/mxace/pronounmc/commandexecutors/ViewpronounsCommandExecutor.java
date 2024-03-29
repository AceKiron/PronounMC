package dev.mxace.pronounmc.commandexecutors;

import dev.mxace.pronounmc.Utils;
import dev.mxace.pronounmc.api.PronounAPI;
import dev.mxace.pronounmc.api.PronounsDatabase;
import dev.mxace.pronounmc.api.PronounsSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The command executor for the /viewpronouns command.
 * Lists a player's pronouns to the sender.
 * @see org.bukkit.command.CommandExecutor
 * @author AceKiron
 * @version 2.2
 */
public class ViewpronounsCommandExecutor implements CommandExecutor {
    /**
     * Singleton instance of /viewpronouns's command executor.
     * @see org.bukkit.command.CommandExecutor
     */
    public final static ViewpronounsCommandExecutor instance = new ViewpronounsCommandExecutor();

    /**
     * Make constructor private.
     */
    private ViewpronounsCommandExecutor() {

    }

    /**
     * Handles the command.
     * @param sender Sender of the command.
     * @param command Command which was executed.
     * @param label Alias of the command which was used.
     * @param args Passed command arguments.
     * @return True if the command was successfully executed, otherwise returns false.
     * @see org.bukkit.command.CommandSender
     * @see org.bukkit.command.Command
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;

        if (args.length > 0) {
            if (!sender.hasPermission("pronounmc.view.other")) {
                sender.sendMessage(Utils.instance.formatString("You need the 'pronounmc.view.other' permission to do this.", false));
                return true;
            }

            player = Bukkit.getServer().getPlayer(args[0]);
        } else {
            if (!sender.hasPermission("pronounmc.view")) {
                sender.sendMessage(Utils.instance.formatString("You need the 'pronounmc.view' permission to do this.", false));
                return true;
            }

            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.instance.formatString("Only players can use this command without an argument.", false));
                return true;
            }

            player = (Player) sender;
        }

        String message = "";

        for (PronounsSet pronounsSet : PronounAPI.instance.getRegisteredPronouns()) {
            message += ChatColor.AQUA
                    + pronounsSet.getFullName()
                    + ": "
                    + ChatColor.BLUE
                    + PronounAPI.instance.approvementStatusToString(PronounsDatabase.instance.getApprovementStatus(player, pronounsSet))
                    + "\n";
        }

        sender.sendMessage(message);
        return true;
    }
}
