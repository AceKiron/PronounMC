package io.github.acekironcommunity.pronounmc;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;

public class PronounEventListener implements Listener {

    private String responseContent;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        try {
            URL url = new URL("https://pronoundb.org/api/v1/lookup?platform=minecraft&id=" + event.getPlayer().getUniqueId());
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in .readLine()) != null) {
                    response.append(inputLine);
                } in .close();

                this.responseContent = response.toString();
            } else {
                System.out.println("GET request not worked");
            }

            // messages for debugging pronoundb web request
            if (PronounAPI.pronoundbDebug) {
                System.out.println(http.getHeaderFields());
            }

            http.disconnect();

            // translates pronoundb string to full pronoun
            String pronoun;
            String[] array = responseContent.split("\"");
            switch (array[3]) {
                case "unspecified":
                    pronoun = "Unspecified";
                    break;
                case "hh":
                    pronoun = "He/Him";
                    break;
                case "hi":
                    pronoun = "He/It";
                    break;
                case "hs":
                    pronoun = "He/She";
                    break;
                case "ht":
                    pronoun = "He/They";
                    break;
                case "ih":
                    pronoun = "It/Him";
                    break;
                case "ii":
                    pronoun = "It/Its";
                    break;
                case "is":
                    pronoun = "It/She";
                    break;
                case "it":
                    pronoun = "It/They";
                    break;
                case "shh":
                    pronoun = "She/He";
                    break;
                case "sh":
                    pronoun = "She/Het";
                    break;
                case "si":
                    pronoun = "She/It";
                    break;
                case "st":
                    pronoun = "She/They";
                    break;
                case "th":
                    pronoun = "They/He";
                    break;
                case "ti":
                    pronoun = "They/It";
                    break;
                case "ts":
                    pronoun = "They/She";
                    break;
                case "tt":
                    pronoun = "They/Them";
                    break;
                case "any":
                    pronoun = "Any Pronouns";
                    break;
                case "other":
                    pronoun = "Other Pronouns";
                    break;
                case "ask":
                    pronoun = "Ask Pronouns";
                    break;
                case "avoid":
                    pronoun = "Avoid Pronouns";
                    break;
                default:
                    pronoun = "Error";
            }
            PronounAPI.addPronouns(event.getPlayer().getUniqueId(), pronoun, true);
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.broadcastMessage(event.getPlayer().getName() + "error");
        }
    }
}
