package dev.mxace.pronounmc.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class StringUtils {

    public static String readInputStream(InputStream stream) throws IOException {
        StringBuilder builder = new StringBuilder();

        try (Reader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                builder.append((char) c);
            }
        }

        return builder.toString();
    }

    public static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static String formatString(String s, Map<String, String> map) {
        for (String key : map.keySet()) {
            s = s.replaceAll("%" + key + "%", map.get(key));
        }

        return s;
    }

}
