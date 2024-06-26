package dev.mxace.pronounmc.config;

import dev.mxace.pronounmc.PronounMC;
import dev.mxace.pronounmc.pronouns.AcceptanceStatus;

import java.util.*;

public class AcceptanceStatusesConfig extends BaseConfig {

    private final Map<String, AcceptanceStatus> acceptanceStatusesMap;
    private final List<String> identifiers;

    public final int defaultAcceptanceStatusIndex;

    public AcceptanceStatusesConfig() {
        super("acceptanceStatuses.yml");

        acceptanceStatusesMap = new HashMap<String, AcceptanceStatus>();
        identifiers = config.getKeys(false).stream().toList();
        defaultAcceptanceStatusIndex = config.getKeys(false).stream().toList().indexOf(PronounMC.pluginInstance.getSimpleConfig().defaultAcceptanceStatus);

        for (String identifier : identifiers) {
            acceptanceStatusesMap.put(identifier, AcceptanceStatus.parse(Objects.requireNonNull(config.getConfigurationSection(identifier))));
        }
    }

    public List<String> getAllIdentifiers() {
        return identifiers;
    }

    public AcceptanceStatus get(String identifier) {
        return acceptanceStatusesMap.get(identifier);
    }

    public AcceptanceStatus get(int index) {
        return acceptanceStatusesMap.get(identifiers.get(index));
    }

}
