package dev.mxace.pronounmc;

import dev.mxace.pronounmc.api.PronounAPI;
import dev.mxace.pronounmc.api.PronounsDatabase;
import dev.mxace.pronounmc.api.PronounsSetApprovementStatus;
import dev.mxace.pronounmc.api.PronounsSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

public class PronounsGUI implements Listener {
    private Inventory m_Inventory;
    private Player m_Affected;
    private List<PronounsSet> m_AvailablePronounsSets;
    private PronounsSetApprovementStatus[] m_AvailableApprovementStatuses;

    public PronounsGUI(Player affected) {
        m_Affected = affected;
        m_Inventory = Bukkit.createInventory(null, 9 * 4, affected.getDisplayName() + "'s pronouns");
        m_AvailablePronounsSets = PronounAPI.instance.getRegisteredPronouns();
        m_AvailableApprovementStatuses = PronounsSetApprovementStatus.values();

        Bukkit.getPluginManager().registerEvents(this, PronounMC.instance);

        initItems();
    }

    private void initItems() {
        for (int i = 0; i < m_AvailablePronounsSets.size(); i++) {
            PronounsSet pronounsSet = m_AvailablePronounsSets.get(i);
            Material material;

            PronounsSetApprovementStatus approvementStatus = PronounsDatabase.instance.getApprovementStatus(m_Affected, pronounsSet);

            switch (approvementStatus) {
                case ASK:
                    material = Material.GRAY_CONCRETE;
                    break;
                case YES:
                    material = Material.GREEN_CONCRETE;
                    break;
                case JOKINGLY:
                    material = Material.ORANGE_CONCRETE;
                    break;
                case ONLY_IF_WE_ARE_CLOSE:
                    material = Material.PINK_CONCRETE;
                    break;
                case OKAY:
                    material = Material.LIGHT_GRAY_CONCRETE;
                    break;
                case NOPE:
                    material = Material.RED_CONCRETE;
                    break;

                default:
                    material = Material.BEDROCK;
                    break;
            }

            m_Inventory.setItem(
                i,
                createGuiItem(
                    material,
                    Utils.instance.capitalizeString(pronounsSet.getShortName()),
                    ChatColor.AQUA + "Full set: " + ChatColor.BLUE + Utils.instance.capitalizeString(pronounsSet.getFullName()),
                    ChatColor.AQUA + "Current status: " + ChatColor.BLUE + Utils.instance.capitalizeString(PronounAPI.instance.approvementStatusToString(approvementStatus)),
                    ChatColor.AQUA + "- Left click to change to: " + ChatColor.BLUE + Utils.instance.capitalizeString(PronounAPI.instance.approvementStatusToString(getNewStatus(pronounsSet, true))),
                    ChatColor.AQUA + "- Right click to change to: " + ChatColor.BLUE + Utils.instance.capitalizeString(PronounAPI.instance.approvementStatusToString(getNewStatus(pronounsSet, false)))
                )
            );
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

    public void open(Player player) {
        player.openInventory(m_Inventory);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(m_Inventory)) return;
        if (e.isCancelled()) return;
        e.setCancelled(true);

        handle(e.getRawSlot(), e.getClick().isLeftClick());
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(m_Inventory)) e.setCancelled(true);
    }

    private void handle(int rawSlot, boolean isLeftClick) {
        if (rawSlot < 0 || rawSlot >= m_AvailablePronounsSets.size()) return;

        PronounsSet pronounsSet = m_AvailablePronounsSets.get(rawSlot);

        PronounsDatabase.instance.setApprovementStatus(
            m_Affected,
            pronounsSet,
            getNewStatus(
                pronounsSet,
                isLeftClick
            )
        );

        m_Inventory.clear();
        initItems();
    }

    private PronounsSetApprovementStatus getNewStatus(PronounsSet pronounsSet, boolean add) {
        int newStatus = PronounsDatabase.instance.getApprovementStatus(m_Affected, pronounsSet).ordinal() + (add ? 1 : -1);

        if (newStatus < 0) newStatus += m_AvailableApprovementStatuses.length;
        else if (newStatus >= m_AvailableApprovementStatuses.length) newStatus -= m_AvailableApprovementStatuses.length;

        return PronounsSetApprovementStatus.values()[newStatus];
    }
}
