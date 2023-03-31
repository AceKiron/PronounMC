package dev.mxace.pronounmc;

import dev.mxace.pronounmc.api.PronounAPI;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Placeholder extends PlaceholderExpansion {

    @Override
    public String getAuthor() { return "_Mx_Ace"; }

    @Override
    public String getIdentifier() { return "pronounmc"; }

    @Override
    public String getVersion() { return "2.0"; }

    @Override
    public boolean canRegister() { return true; }

    @Override
    public boolean persist() { return true; }

    @Override
    public String onPlaceholderRequest(Player player, String param) {
        if (param.trim().isEmpty()) return PronounAPI.AsString(PronounAPI.GetDatabase().Get(player.getUniqueId()));
        else return PronounAPI.AsString(PronounAPI.GetDatabase().Get(Bukkit.getServer().getPlayer(param).getUniqueId()));
    }

}
