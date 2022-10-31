package io.github.acekironcommunity.pronounmc;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import io.github.acekironcommunity.pronounmc.tpo.PronounDBOverrider;

public class PronounAPI {

    private static HashMap<UUID, List<String>> cache = new HashMap<UUID, List<String>>();

    private static File playerPronounsFile;
    private static YamlConfiguration playerPronounsConfig;

    private static List<String> pronounCodes;
    
    private static boolean logChanges;
    private static String pronounOverride;

    public PronounAPI(MyPlugin plugin) {
        Utils.log("Initialized PronounMC API.", true);

        logChanges = plugin.getConfig().getBoolean("log-changes");
        pronounOverride = Utils.getPronounOverride();

        pronounCodes = plugin.getConfig().getStringList("available-pronouns");

        // Use a separate file for storing players' pronouns
        playerPronounsFile = new File(plugin.getDataFolder(), "pronouns-db.yml");

        if (!playerPronounsFile.exists()) {
            Utils.log("Creating pronouns-db.yml file", false);
            playerPronounsFile.getParentFile().mkdirs();
            plugin.saveResource("pronouns-db.yml", false);
        }

        playerPronounsConfig = new YamlConfiguration();
        try {
            playerPronounsConfig.load(playerPronounsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getAllCodes() {
        return (List<String>) ((ArrayList<String>) pronounCodes).clone();
    }

    /*
        Fetches pronouns either from cache or the config files.
        Returns the pronoun codes.
     */
    private static List<String> fetchPronouns(UUID playerUUID) {
        if (cache.containsKey(playerUUID)) {
            return cache.get(playerUUID);
        }

        List<String> codes;
        Utils.log("Fetch pronouns for player with UUID " + playerUUID, true);

        if (!Utils.getPronounOverrideEnabled()) {
            codes = playerPronounsConfig.getStringList("pronouns-" + playerUUID);
        } else {
            switch (pronounOverride) {
                case "pronoundb":
                    codes = PronounDBOverrider.fetch(playerUUID);
                    break;

                default:
                    Utils.log(pronounOverride + " is not a supported third party overrider.", false);
                    codes = new ArrayList<String>();
                    break;
            }
        }

        cache.put(playerUUID, codes);
        return codes;
    }

    /*
        Get pronouns string and automatically capitalize first letter.
     */
    public static String getPronouns(UUID playerUUID) {
        return getPronouns(playerUUID, true);
    }

    /*
        Get pronouns string.
        Returns either "Unspecified" or a list of a player's pronouns.
     */
    public static String getPronouns(UUID playerUUID, boolean capitalizeFirstLetter) {
        List<String> list = fetchPronouns(playerUUID);

        if (list.size() == 0)  return "Unspecified";

        String string = String.join("/", list);
        if (capitalizeFirstLetter) string = string.substring(0, 1).toUpperCase() + string.substring(1);

        return string;
    }

    /*
        Saves a set of pronouns to both cache and the config files.
     */
    private static void save(UUID playerUUID, List<String> pronouns) {
        cache.put(playerUUID, pronouns);

        playerPronounsConfig.set("pronouns-" + playerUUID, pronouns);

        try {
            playerPronounsConfig.save(playerPronounsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
        Add a pronoun to a player by code, for example: "he", "she", "they".
        Returns true if pronoun code is valid, otherwise returns false.
     */
    public static boolean addPronouns(UUID playerUUID, String code) {
        code = code.toLowerCase();

        if (!pronounCodes.contains(code)) return false;

        List<String> pronouns = fetchPronouns(playerUUID);

        // Only add them if they aren't added yet
        if (!pronouns.contains(code)) {
            pronouns.add(code);
            if (logChanges) Utils.log("Pronoun " + code + " was added to player with UUID " + playerUUID, false);

            save(playerUUID, pronouns);
        }

        return true;
    }

    /*
        Removes a pronoun from a player by code, for example: "he", "she", "they".
        Returns true if pronoun code is valid, otherwise returns false.
     */
    public static boolean removePronouns(UUID playerUUID, String code) {
        code = code.toLowerCase();

        if (!pronounCodes.contains(code)) return false;

        List<String> pronouns = fetchPronouns(playerUUID);

        pronouns.remove(code);
        if (logChanges) Utils.log("Pronoun " + code + " was removed from player with UUID " + playerUUID, false);

        save(playerUUID, pronouns);

        return true;
    }

}
