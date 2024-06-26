package dev.mxace.pronounmc.pronouns;

import dev.mxace.pronounmc.utils.StringUtils;
import org.bukkit.configuration.ConfigurationSection;

public class LocalePronouns {

    public final String subject, object, possessiveDeterminer, possessivePronoun, reflexive, note;

    private LocalePronouns(String subject, String object, String possessiveDeterminer, String possessivePronoun, String reflexive, String note) {
        this.subject = subject;
        this.object = object;
        this.possessiveDeterminer = possessiveDeterminer;
        this.possessivePronoun = possessivePronoun;
        this.reflexive = reflexive;
        this.note = note;
    }

    public static LocalePronouns parse(ConfigurationSection section) {
        return new LocalePronouns(
            section.getString("subject"),
            section.getString("object"),
            section.getString("possessiveDeterminer"),
            section.getString("possessivePronoun"),
            section.getString("reflexive"),
            section.getString("note")
        );
    }

    public String getLongString() {
        return StringUtils.capitalize(subject) + "/" + object + "/" + possessiveDeterminer + "/" + possessivePronoun + "/" + reflexive;
    }

}
