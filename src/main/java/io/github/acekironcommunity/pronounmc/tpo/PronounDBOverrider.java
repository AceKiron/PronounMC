package io.github.acekironcommunity.pronounmc.tpo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import io.github.acekironcommunity.pronounmc.Utils;

public class PronounDBOverrider {

    public static List<String> fetch(UUID playerUUID) {
        try {
            String responseContent = "";

            URL url = new URL("https://pronoundb.org/api/v1/lookup?platform=minecraft&id=" + playerUUID);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in .readLine()) != null) {
                    response.append(inputLine);
                }
                
                in.close();

                responseContent = response.toString();
            }

            Utils.log(responseContent, true);
            http.disconnect();

            // Translates pronoundb string to full pronoun
            String pronoun;
            String[] array = responseContent.split("\"");
            switch (array[3]) {
                case "unspecified": return new ArrayList<String>();
                case "hh": return new ArrayList<String>(Arrays.asList("he", "him"));
                case "hi": return new ArrayList<String>(Arrays.asList("he", "it"));
                case "hs": return new ArrayList<String>(Arrays.asList("he", "she"));
                case "ht": return new ArrayList<String>(Arrays.asList("he", "they"));
                case "ih": return new ArrayList<String>(Arrays.asList("it", "him"));
                case "ii": return new ArrayList<String>(Arrays.asList("it", "its"));
                case "is": return new ArrayList<String>(Arrays.asList("it", "she"));
                case "it": return new ArrayList<String>(Arrays.asList("it", "they"));
                case "shh": return new ArrayList<String>(Arrays.asList("she", "he"));
                case "sh": return new ArrayList<String>(Arrays.asList("she", "her"));
                case "si": return new ArrayList<String>(Arrays.asList("she", "it"));
                case "st": return new ArrayList<String>(Arrays.asList("she", "they"));
                case "th": return new ArrayList<String>(Arrays.asList("they", "he"));
                case "ti": return new ArrayList<String>(Arrays.asList("they", "it"));
                case "ts": return new ArrayList<String>(Arrays.asList("they", "she"));
                case "tt": return new ArrayList<String>(Arrays.asList("they", "them"));
                case "any": return new ArrayList<String>(Arrays.asList("any"));
                case "other": return new ArrayList<String>(Arrays.asList("other"));
                case "ask": return new ArrayList<String>(Arrays.asList("ask"));
                case "avoid": return new ArrayList<String>(Arrays.asList("username"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}