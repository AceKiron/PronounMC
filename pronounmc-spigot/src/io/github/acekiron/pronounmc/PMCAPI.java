package io.github.acekiron.pronounmc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

public class PMCAPI {

    private static HashMap<UUID, List<String>> cache = new HashMap<UUID, List<String>>();

    private static List<String> pronouns = new ArrayList<String>(Arrays.asList(new String[] {
    	"he", "she", "they", "it",
    	"any", "other", "ask", "username"
    }));

    private static JavaPlugin plugin;
    
    public static void InitPMCAPI(JavaPlugin plugin) {
    	PMCAPI.plugin = plugin;
		System.out.println("Initialized PronounMC API.");
    }
    
    public static List<String> getAllCodes() {
    	return pronouns;
    }
    
    public static String getPronouns(UUID uuid) {
    	List<String> list = fetchPronouns(uuid);
    	
    	if (list.size() == 0)
    		return "Unspeficied";
    	
    	String string = String.join("/", pronouns);
    	return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
    
    private static List<String> fetchPronouns(UUID uuid) {
    	if (cache.containsKey(uuid)) {
    		return cache.get(uuid);
    	}
    	
    	// Load from config
    	@SuppressWarnings("unchecked")
		List<String> code = (List<String>) plugin.getConfig().getList("pronouns-" + uuid);
    	
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
		    	plugin.getConfig().set("pronouns-" + uuid, pronouns);
		    	plugin.saveConfig();
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
	    	plugin.getConfig().set("pronouns-" + uuid, pronouns);
	    	plugin.saveConfig();
	    	
	    	return true;
    	}
    	
    	return false;
    }
    
}
