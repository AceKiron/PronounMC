package dev.mxace.pronounmc;

import dev.mxace.pronounmc.api.Pronoun;
import dev.mxace.pronounmc.api.PronounAPI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class PronounsGui implements Listener {

    private static List<Pronoun> availablePronouns;

    private Inventory inv;
    private Player affected;

    public PronounsGui(Player affected) {
        inv = Bukkit.createInventory(null, 9 * 1, "PronounMC: " + affected.getDisplayName());

        this.affected = affected;

        availablePronouns = PronounAPI.GetRegisteredPronounsAsList();

        Bukkit.getPluginManager().registerEvents(this, PronounMC.GetInstance());

        InitializeItems();
    }

    private void InitializeItems() {
        List<Pronoun> playerPronouns = PronounAPI.GetDatabase().Get(affected.getUniqueId());

        for (int i = 0; i < availablePronouns.size(); i++) {
            Pronoun pronoun = availablePronouns.get(i);
            Material material;

            if (playerPronouns.contains(pronoun)) material = Material.EMERALD;
            else material = Material.REDSTONE;

            inv.setItem(i, createGuiItem(material, Utils.CapitalizeString(pronoun.GetShortName()), Utils.CapitalizeString(pronoun.GetFullName())));
        }
    }

    private ItemStack createGuiItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material, 1);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);

        return item;
    }

    public void Open(Player player) {
        player.openInventory(inv);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(inv)) return;
        e.setCancelled(true);

        Handle(e.getRawSlot());
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(inv)) e.setCancelled(true);
    }

    private void Handle(int rawSlot) {
        if (rawSlot >= availablePronouns.size()) return; // We're clicking nothing

        PronounAPI.Toggle(affected.getUniqueId(), availablePronouns.get(rawSlot));

        inv.clear();
        InitializeItems();
    }

}
