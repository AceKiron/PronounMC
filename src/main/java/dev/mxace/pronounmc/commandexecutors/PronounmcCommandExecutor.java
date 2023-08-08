package dev.mxace.pronounmc.commandexecutors;

import dev.mxace.pronounmc.PronounsGUI;
import dev.mxace.pronounmc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The command executor for the /pronounmc command.
 * Opens a GUI which lets you change your pronouns.
 * @see org.bukkit.command.CommandExecutor
 * @author AceKiron
 * @version 2.2
 */
public class PronounmcCommandExecutor implements CommandExecutor {
    /**
     * Singleton instance of /pronounmc's command executor.
     * @see org.bukkit.command.CommandExecutor
     */
    public final static PronounmcCommandExecutor instance = new PronounmcCommandExecutor();

    /**
     * Make constructor private.
     */
    private PronounmcCommandExecutor() {

    }

    /**
     * Creates and opens a GUI for editing the affected player's pronouns sets approvement statuses.
     * @param me The player executing the command.
     * @param affected The player whose pronouns will be affected.
     * @return True if the player whose pronouns will be affected was found, otherwise returns false.
     * @see org.bukkit.entity.Player
     */
    private boolean handleFor(@NotNull Player me, @Nullable Player affected) {
        if (affected == null) return false;

        new PronounsGUI(affected).open(me);

        return true;
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
