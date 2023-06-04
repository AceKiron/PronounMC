package dev.mxace.pronounmc.commandexecutors;

import dev.mxace.pronounmc.PronounsGUI;
import dev.mxace.pronounmc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PronounmcCommandExecutor implements CommandExecutor {
    public final static PronounmcCommandExecutor instance = new PronounmcCommandExecutor();
    private PronounmcCommandExecutor() {

    }

    private boolean handleFor(Player me, Player affected) {
        if (me == null || affected == null) return false;

        new PronounsGUI(affected).open(me);

        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.instance.formatString("Only players can use this command.", false));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("pronounmc.modify")) {
            player.sendMessage(Utils.instance.formatString("You need the 'pronounmc.modify' permission to do this.", false));
            return true;
        }

        if (args.length == 0) return handleFor(player, player);
        else if (!player.hasPermission("pronounmc.modify.other")) {
            player.sendMessage(Utils.instance.formatString("You need the 'pronounmc.modify.other' permission to do this.", false));
            return true;
        }

        return handleFor(player, Bukkit.getServer().getPlayer(args[0]));
    }
}
