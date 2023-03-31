package dev.mxace.pronounmc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PronounMCCommandExecutor implements CommandExecutor {

    private void HandleFor(Player me, Player affected) {
        new PronounsGui(affected).Open(me);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (!player.hasPermission("pronounmc.modify")) {
            player.sendMessage(ChatColor.RED + "You need the 'pronounmc.modify' permission to do this.");
            return true;
        }

        if (args.length > 0) {
            if (!player.hasPermission("pronounmc.modify.other")) {
                player.sendMessage(ChatColor.RED + "You need the 'pronounmc.modify.other' permission to do this.");
                return true;
            }

            HandleFor(player, Bukkit.getServer().getPlayer(args[0]));
        } else {
            HandleFor(player, player);
        }

        return true;
    }

}
