package dev.mxace.pronounmc.config;

import dev.mxace.pronounmc.pronouns.Pronouns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PronounsConfig extends BaseConfig {

    private final Map<String, Pronouns> pronounsMap;

    public PronounsConfig() {
        super("pronouns.yml");

        pronounsMap = new HashMap<String, Pronouns>();

        for (String identifier : config.getKeys(false)) {
            pronounsMap.put(identifier, Pronouns.parse(Objects.requireNonNull(config.getConfigurationSection(identifier))));
        }
    }

    public Pronouns getPronouns(String identifier) {
        return pronounsMap.get(identifier);
    }

    public List<String> getAllIdentifiers() {
        return pronounsMap.keySet().stream().toList();
    }

}
