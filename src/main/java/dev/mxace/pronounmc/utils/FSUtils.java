package dev.mxace.pronounmc.utils;

import dev.mxace.pronounmc.PronounMC;
import org.bukkit.Bukkit;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

public class FSUtils {

    public static String readResource(String path) throws IOException {
        return StringUtils.readInputStream(PronounMC.pluginInstance.getResource(path));
    }

    public static List<String> readResourceDirectory(String path) {
        List<String> fileList = new ArrayList<String>();
        if (!path.endsWith("/")) {
            path += "/";
        }

        String jarPath = FSUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();

                if (entryName.startsWith(path) && !entry.isDirectory()) {
                    fileList.add(entryName);
                }
            }
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, ex.getMessage());
            Bukkit.getPluginManager().disablePlugin(PronounMC.pluginInstance);
        }

        return fileList;
    }

}
