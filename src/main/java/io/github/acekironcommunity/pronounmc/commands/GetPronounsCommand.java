package io.github.acekironcommunity.pronounmc.commands;

import io.github.acekironcommunity.pronounmc.PronounAPI;
import io.github.acekironcommunity.pronounmc.Utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetPronounsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 0:
                // Get own pronouns
                if (!(sender instanceof Player)) {
                    sender.sendMessage(Utils.formatMessage("Only players can run this command.", false));
                    return true;
                }
                Player player = (Player) sender;
                
                player.sendMessage(Utils.formatMessage(PronounAPI.getPronouns(player.getUniqueId()) + "."));
                break;

            case 1:
                // Get somebody else's pronouns
                if (!player.hasPermission("pronounmc.get.other")) {
                    player.sendMessage(Utils.formatMessage("Missing the pronounmc.get.other permission.", false));
                    return true;
                }

                Player player2 = Bukkit.getPlayer(args[0]);
                if (player2 == null) {
                    player.sendMessage(Utils.formatMessage("Could not find player " + args[0] + ".", false));
                    return true;
                }

                player.sendMessage(Utils.formatMessage(PronounAPI.getPronouns(player2.getUniqueId()) + "."));
                break;

            default:
                return false;
        }

        return true;
    }

}
