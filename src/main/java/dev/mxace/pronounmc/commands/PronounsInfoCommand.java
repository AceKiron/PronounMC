package dev.mxace.pronounmc.commands;

import dev.mxace.pronounmc.PronounMC;
import dev.mxace.pronounmc.pronouns.LocalePronouns;
import dev.mxace.pronounmc.pronouns.Pronouns;
import dev.mxace.pronounmc.utils.StringUtils;
import dev.mxace.pronounmc.utils.builders.MessageBuilder;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class PronounsInfoCommand implements CommandExecutor, TabCompleter {

    public static final PronounsInfoCommand commandInstance = new PronounsInfoCommand();

    private String getPronounsInformation(String identifier, String locale) {
        Pronouns pronouns = PronounMC.pluginInstance.getPronounsConfig().getPronouns(identifier);
        if (pronouns == null) {
            return new MessageBuilder(false)
                    .addPartition(StringUtils.formatString(
                            PronounMC.pluginInstance.getTextsConfig().getLocaleText("pronouns_not_found_prefix", PronounMC.pluginInstance.getSimpleConfig().localeExists(locale) ? locale : PronounMC.pluginInstance.getSimpleConfig().defaultLocale),
                            new HashMap<String, String>() {{
                                    put("pronouns_identifier", identifier);
                                    put("available_pronouns", String.join(", ", PronounMC.pluginInstance.getPronounsConfig().getAllIdentifiers()));
                            }}
                    ))
                    .finish();
        }

        LocalePronouns localePronouns = pronouns.getLocalePronouns(locale);

        MessageBuilder builder = new MessageBuilder(true)
                .addPartition(localePronouns.getLongString());

        if (localePronouns.note != null) {
            builder
                    .addPartition("\n")
                    .setColor(ChatColor.AQUA)
                    .addPartition(StringUtils.formatString(
                            PronounMC.pluginInstance.getTextsConfig().getLocaleText("note", PronounMC.pluginInstance.getSimpleConfig().localeExists(locale) ? locale : PronounMC.pluginInstance.getSimpleConfig().defaultLocale),
                            new HashMap<String, String>() {{
                                put("note", localePronouns.note);
                            }}
                    ));
        }

        return builder.finish();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String alias, String[] args) {
        Player p = (Player) commandSender;
        if (p == null) {
            return false;
        }

        return switch (args.length) {
            case 0 -> { // /pronounsinfo
                commandSender.sendMessage(
                        new MessageBuilder(true)
                                .addPartition(PronounMC.pluginInstance.getTextsConfig().getLocaleText("pronounsinfo_header", PronounMC.pluginInstance.getSimpleConfig().getPreferredLocale(p.getUniqueId())))
                                .finish()
                );

                yield true;
            }

            case 1 -> { // /pronounsinfo [pronouns]
                commandSender.sendMessage(getPronounsInformation(args[0], PronounMC.pluginInstance.getSimpleConfig().getPreferredLocale(p.getUniqueId())));

                yield true;
            }

            case 2 -> { // /pronounsinfo [pronouns] [language]
                commandSender.sendMessage(getPronounsInformation(args[0], args[1]));
                PronounMC.pluginInstance.getSimpleConfig().setPreferredLocale(p.getUniqueId(), args[1]);

                yield true;
            }

            default -> false;
        };
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
        return switch (args.length) {
            case 1 -> PronounMC.pluginInstance.getPronounsConfig().getAllIdentifiers();
            case 2 -> PronounMC.pluginInstance.getSimpleConfig().getAllLocales();
            default -> null;
        };
    }

}
