package io.github.acekiron.pronounmc;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PHExtension extends PlaceholderExpansion {

    @Override
    public String getAuthor() { return "AceKiron"; }

    @Override
    public String getIdentifier() { return "pronounmc"; }

    @Override
    public String getVersion() { return "0.8"; }

    @Override
    public boolean canRegister() { return true; }

    @Override
    public boolean persist() { return true; }

    @Override
    public String onPlayerholderRequest(Player player, String params) {
        if (player == null) return "";

        return PMCAPI.getPronouns(player.getUniqueId(), true);
    }

}
