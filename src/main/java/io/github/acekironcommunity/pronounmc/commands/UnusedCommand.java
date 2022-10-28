package io.github.acekironcommunity.pronounmc.commands;

import io.github.acekironcommunity.pronounmc.Utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnusedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(Utils.formatMessage("This command is unavailable because the server operators enabled override from a third party.", false));
        return true;
    }

}
