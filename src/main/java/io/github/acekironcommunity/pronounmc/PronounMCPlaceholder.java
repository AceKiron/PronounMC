package io.github.acekironcommunity.pronounmc;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PronounMCPlaceholder extends PlaceholderExpansion {

    @Override
    public String getAuthor() { return "AceKiron"; }

    @Override
    public String getIdentifier() { return "pronounmc"; }

    @Override
    public String getVersion() { return "1.1"; }

    @Override
    public boolean canRegister() { return true; }

    @Override
    public boolean persist() { return true; }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        Utils.log("PlaceholderAPI request made", true);

        if (player == null) return "";
        return PronounAPI.getPronouns(player.getUniqueId(), true);
    }

}
