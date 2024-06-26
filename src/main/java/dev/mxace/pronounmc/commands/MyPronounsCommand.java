package dev.mxace.pronounmc.commands;

import dev.mxace.pronounmc.PronounMC;
import dev.mxace.pronounmc.pronouns.UserPronounAcceptanceStatusesResult;
import dev.mxace.pronounmc.utils.builders.MessageBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class MyPronounsCommand  implements CommandExecutor, TabCompleter {

    public static final MyPronounsCommand commandInstance = new MyPronounsCommand();

    private static final List<String> identifiers = PronounMC.pluginInstance.getPronounsConfig().getAllIdentifiers();
    private static final List<String> acceptanceStatusesStrings = PronounMC.pluginInstance.getAcceptanceStatusesConfig().getAllIdentifiers();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String alias, String[] args) {
        Player p = (Player) commandSender;
        if (p == null) {
            return false;
        }

        return switch (args.length) {
            case 0 -> { // /mypronouns
                try {
                    commandSender.sendMessage(new MessageBuilder(true)
                            .addPartition(PronounMC.pluginInstance.getTextsConfig().getLocaleText("mypronouns_header", PronounMC.pluginInstance.getSimpleConfig().getPreferredLocale(p.getUniqueId())))
                            .addPartition("\n")
                            .addPartition(PronounMC.pluginInstance.getDatabase().getAllPronounsAcceptanceStatusesAsString(PronounMC.pluginInstance.getSimpleConfig().getPreferredLocale(p.getUniqueId()), new HashMap<String, String>() {{
                                put("player_uuid", p.getUniqueId().toString());
                            }}))
                            .finish()
                    );
                } catch (SQLException ex) {
                    Bukkit.getLogger().log(Level.SEVERE, ex.getMessage());
                    Bukkit.getPluginManager().disablePlugin(PronounMC.pluginInstance);
                }

                yield true;
            }

            case 1 -> { // /mypronouns [pronouns]
                if (!identifiers.contains(args[0])) {
                    yield false;
                }

                try {
                    UserPronounAcceptanceStatusesResult userPronounAcceptanceStatus = PronounMC.pluginInstance.getDatabase().getUserPronounAcceptanceStatus(args[0], new HashMap<String, String>() {{
                        put("player_uuid", p.getUniqueId().toString());
                        put("pronoun_identifier", args[0]);
                    }});

                    commandSender.sendMessage(new MessageBuilder(true)
                            .addPartition(acceptanceStatusesStrings.get(userPronounAcceptanceStatus.acceptanceStatus).replaceAll("_", " "))
                            .finish()
                    );
                } catch (SQLException ex) {
                    Bukkit.getLogger().log(Level.SEVERE, ex.getMessage());
                    Bukkit.getPluginManager().disablePlugin(PronounMC.pluginInstance);
                }

                yield true;
            }

            case 2 -> { // /mypronouns [pronouns] [acceptance_status]
                if (!(identifiers.contains(args[0]) || acceptanceStatusesStrings.contains(args[1]))) {
                    yield false;
                }

                int acceptanceStatusIndex = acceptanceStatusesStrings.indexOf(args[1]);

                try {
                    PronounMC.pluginInstance.getDatabase().updateUserPronounAcceptanceStatus(new HashMap<String, String>() {{
                        put("player_uuid", p.getUniqueId().toString());
                        put("pronoun_identifier", args[0]);
                        put("acceptance_status", Integer.toString(acceptanceStatusIndex));
                    }});

                    System.out.println(args[0] + " " + acceptanceStatusIndex);

                    commandSender.sendMessage(new MessageBuilder(true)
                            .addPartition(PronounMC.pluginInstance.getTextsConfig().getLocaleText("mypronouns_updated", PronounMC.pluginInstance.getSimpleConfig().getPreferredLocale(p.getUniqueId())))
                            .finish()
                    );
                } catch (SQLException ex) {
                    Bukkit.getLogger().log(Level.SEVERE, ex.getMessage());
                    Bukkit.getPluginManager().disablePlugin(PronounMC.pluginInstance);
                }

                yield true;
            }

            default -> false;
        };
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
        return switch (args.length) {
            case 1 -> PronounMC.pluginInstance.getPronounsConfig().getAllIdentifiers();
            case 2 -> acceptanceStatusesStrings;
            default -> null;
        };
    }

}
