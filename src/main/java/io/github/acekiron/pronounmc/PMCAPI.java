package io.github.acekiron.pronounmc;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PMCAPI {

    private static HashMap<UUID, List<String>> cache = new HashMap<UUID, List<String>>();

    private static File playerPronounsFile;
    private static YamlConfiguration playerPronounsConfig;

    private static List<String> pronouns;

//    private static List<String> pronouns = new ArrayList<String>(Arrays.asList(new String[] {
//        "he", "she", "they", "it",
//        "any", "other", "ask", "username"
//    }));

    public static void InitPMCAPI(JavaPlugin plugin) {
        System.out.println("Initialized PronounMC API.");

        pronouns = plugin.getConfig().getStringList("available-pronouns");

        // Use a separate file for storing players' pronouns
        playerPronounsFile = new File(plugin.getDataFolder(), "pronouns-db.yml");
        if (!playerPronounsFile.exists()) {
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
        return pronouns;
    }

    public static String getPronouns(UUID uuid) {
        List<String> list = fetchPronouns(uuid);

        if (list.size() == 0)
            return "Unspecified";

        String string = String.join("/", list);
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    private static List<String> fetchPronouns(UUID uuid) {
        if (cache.containsKey(uuid)) {
            return cache.get(uuid);
        }

        // Load from config
        List<String> code = playerPronounsConfig.getStringList("pronouns-" + uuid);

        if (code == null) {
            code = new ArrayList<String>();
        }

        cache.put(uuid, code);
        return code;
    }

    public static boolean addPronouns(UUID uuid, String code) {
        if (pronouns.contains(code)) {
            List<String> pronouns = fetchPronouns(uuid);

            if (!pronouns.contains(code)) {
                pronouns.add(code);
                cache.put(uuid, pronouns);

                // Save to config
                playerPronounsConfig.set("pronouns-" + uuid, pronouns);

                try {
                    playerPronounsConfig.save(playerPronounsFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return true;
        }

        return false;
    }

    public static boolean removePronouns(UUID uuid, String code) {
        if (pronouns.contains(code)) {
            List<String> pronouns = fetchPronouns(uuid);
            pronouns.remove(code);
            cache.put(uuid, pronouns);

            // Save to config
            playerPronounsConfig.set("pronouns-" + uuid, pronouns);

            try {
                playerPronounsConfig.save(playerPronounsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }

        return false;
    }

}
