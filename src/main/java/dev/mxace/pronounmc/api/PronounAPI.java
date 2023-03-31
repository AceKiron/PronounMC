package dev.mxace.pronounmc.api;

import dev.mxace.pronounmc.Utils;

import org.apache.commons.lang.StringUtils;

import java.util.*;

public class PronounAPI {

    private static Map<String, Pronoun> registeredPronouns;

    public static List<Pronoun> GetRegisteredPronounsAsList() { return new ArrayList<Pronoun>(registeredPronouns.values()); }

    public static Pronoun GetPronounFromIdentifier(String identifier) { return registeredPronouns.get(identifier); }

    private static PronounsDatabase database;
    public static PronounsDatabase GetDatabase() { return database; }

    public static void Initialize() {
        database = new PronounsDatabase();

        registeredPronouns = new HashMap<>();
    }

    public static void Save() {
        database.Save();
    }

    public static void Register(Pronoun pronoun) { registeredPronouns.put(pronoun.GetFullName(), pronoun); }
    public static void Unregister(Pronoun pronoun) { registeredPronouns.remove(pronoun.GetFullName()); }

    public static void Add(UUID playerUuid, Pronoun pronoun) {
        List<Pronoun> pronouns = database.Get(playerUuid);
        if (!pronouns.contains(pronoun)) pronouns.add(pronoun);
        database.Set(playerUuid, pronouns);
    }

    public static void Remove(UUID playerUuid, Pronoun pronoun) {
        List<Pronoun> pronouns = database.Get(playerUuid);
        if (pronouns.contains(pronoun)) pronouns.remove(pronoun);
        database.Set(playerUuid, pronouns);
    }

    public static void Toggle(UUID playerUuid, Pronoun pronoun) {
        List<Pronoun> pronouns = database.Get(playerUuid);
        if (!pronouns.contains(pronoun)) pronouns.add(pronoun);
        else pronouns.remove(pronoun);
        database.Set(playerUuid, pronouns);
    }

    public static String AsString(List<Pronoun> pronouns) {
        if (pronouns.size() == 0) return "Unspecified";
        else if (pronouns.size() == 1) return Utils.CapitalizeString(pronouns.get(0).GetShortName());
        else return Utils.CapitalizeString(StringUtils.join(pronouns.stream().map(pronoun -> pronoun.GetSubjectPronoun()).toArray(), "/"));
    }

}
