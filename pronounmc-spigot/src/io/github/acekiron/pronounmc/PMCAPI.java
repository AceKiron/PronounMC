package io.github.acekiron.pronounmc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

public class PMCAPI {

    private static HashMap<UUID,   String> cache       = new HashMap<UUID,   String>();
    private static HashMap<String, String> pronounsMap = new HashMap<String, String>();

    private static JavaPlugin plugin;
    
    public static void InitPMCAPI(JavaPlugin plugin) {
    	PMCAPI.plugin = plugin;
    	
    	pronounsMap.put("unspecified", "Unspecified");
    	
    	pronounsMap.put("he/him", "He/him");
    	pronounsMap.put("he/it", "He/it");
    	pronounsMap.put("he/she", "He/she");
    	pronounsMap.put("he/they", "He/they");
    	
    	pronounsMap.put("it/him", "It/him");
    	pronounsMap.put("it/its", "It/its");
    	pronounsMap.put("it/she", "It/she");
    	pronounsMap.put("it/they", "It/they");

    	pronounsMap.put("she/he", "She/he");
    	pronounsMap.put("she/her", "She/her");
    	pronounsMap.put("she/it", "She/it");
    	pronounsMap.put("she/they", "She/they");
    	
    	pronounsMap.put("they/he", "They/he");
    	pronounsMap.put("they/it", "They/it");
    	pronounsMap.put("they/she", "They/she");
    	pronounsMap.put("they/them", "They/them");

		pronounsMap.put("any", "Any");
		pronounsMap.put("other", "Other");
		pronounsMap.put("ask", "Ask");
		pronounsMap.put("avoid", "Avoid");
		
		System.out.println("Initialized PronounMC API.");
    }
    
    public static List<String> getAllCodes() {
    	List<String> list = new ArrayList<String>();
    	
    	for (Map.Entry<String, String> entry : pronounsMap.entrySet()) {
    		list.add(entry.getKey());
    	}
    	
    	return list;
    }
    
    public static String getPronouns(UUID uuid) {
    	if (cache.containsKey(uuid)) {
    		return cache.get(uuid);
    	} else {
    		return fetchPronouns(uuid);
    	}
    }
    
    private static String fetchPronouns(UUID uuid) {
    	// Load from config
    	String code = plugin.getConfig().getString("pronouns-" + uuid);
    	
    	if (code == null) {
    		code = "unspecified";
    	}
    	
    	cache.put(uuid, code);
    	return code;
    }
    
    public static boolean setPronouns(UUID uuid, String code) {
    	if (pronounsMap.containsKey(code)) {
	    	cache.put(uuid, code);
	    	
	    	// Save to config
	    	plugin.getConfig().set("pronouns-" + uuid, code);
	    	plugin.saveConfig();
	    	
	    	return true;
    	}
    	
    	return false;
    }
    
    public static String getString(String code) {
    	return pronounsMap.get(code);
    }
    
    public static String getCode(String string) {
    	for (Map.Entry<String, String> entry : pronounsMap.entrySet()) {
    		if (string.equalsIgnoreCase(entry.getValue().replace("/", ""))) return entry.getKey();
    	}
    	
    	return "unspecified";
    }
    
}
