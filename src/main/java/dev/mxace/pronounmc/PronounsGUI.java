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
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * GUI framework for PronounMC.
 * @see org.bukkit.event.Listener
 * @author AceKiron
 * @version 2.3
 */
public class PronounsGUI implements Listener {
    /**
     * The inventory that was made.
     * @see org.bukkit.inventory.Inventory
     */
    private final Inventory m_Inventory;

    /**
     * The player whose pronouns will be affected.
     * @see org.bukkit.entity.Player
     */
    private final Player m_Affected;

    /**
     * A list pronouns sets registered when the constructor gets called.
     * @see java.util.List
     * @see dev.mxace.pronounmc.api.PronounsSet
     */
    private static final List<PronounsSet> m_AvailablePronounsSets = PronounAPI.instance.getRegisteredPronouns();

    /**
     * A list of all possible approvement statuses.
     * @see dev.mxace.pronounmc.api.PronounsSetApprovementStatus
     */
    private static final PronounsSetApprovementStatus[] m_AvailableApprovementStatuses = PronounsSetApprovementStatus.values();

    /**
     * Constructor for the GUI framework.
     * Creates a new 4-row GUI with as title "%player_name%'s pronouns"
     * @param affected Player whose pronouns will be affected.
     * @see org.bukkit.event.Listener
     */
    public PronounsGUI(@NotNull Player affected) {
        m_Affected = affected;
        m_Inventory = Bukkit.createInventory(null, 9 * 4, affected.getDisplayName() + "'s pronouns");

        Bukkit.getPluginManager().registerEvents(this, PronounMC.instance);

        initItems();
    }

    /**
     * Puts the items in the GUI.
     * @see org.bukkit.inventory.Inventory
     */
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

    /**
     * Creates an item that can be put in the GUI.
     * @param material What item it should be.
     * @param name The name of the item.
     * @param lore The purple text you see when hovering over an item.
     * @return Item that can be put in the GUI.
     * @see org.bukkit.inventory.ItemStack
     */
    private ItemStack createGuiItem(@NotNull Material material, @NotNull String name, String... lore) {
        ItemStack item = new ItemStack(material, 1);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    /**
     * Shows the GUI to the player.
     * @param player Player who will see the GUI.
     * @see org.bukkit.entity.Player
     */
    public void open(@NotNull Player player) {
        player.openInventory(m_Inventory);
    }

    /**
     * Handles the event when someone clicks in their inventory.
     * @param e Inventory click event.
     * @see org.bukkit.event.inventory.InventoryClickEvent
     */
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(m_Inventory)) return;
        if (e.isCancelled()) return;
        e.setCancelled(true);

        handle(e.getRawSlot(), e.getClick().isLeftClick());
    }

    /**
     * Handles the event when someone drags in their inventory.
     * @param e Inventory drag event.
     * @see org.bukkit.event.inventory.InventoryDragEvent
     */
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(m_Inventory)) e.setCancelled(true);
    }

    /**
     * Handles the inventory click event, if it happened in this inventory.
     * @param rawSlot The slot in the inventory which was clicked.
     * @param isLeftClick True when left-clicked, false when right-clicked.
     * @see dev.mxace.pronounmc.api.PronounsDatabase
     */
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

    /**
     * Increments or decrements the selected pronouns set's approvement status.
     * @param pronounsSet The selected pronouns set.
     * @param add Increments the status index by 1 if true, otherwise decrement 1.
     * @return New pronouns set approvement status.
     */
    private PronounsSetApprovementStatus getNewStatus(@NotNull PronounsSet pronounsSet, boolean add) {
        int newStatus = PronounsDatabase.instance.getApprovementStatus(m_Affected, pronounsSet).ordinal() + (add ? 1 : -1);

        if (newStatus < 0) newStatus += m_AvailableApprovementStatuses.length;
        else if (newStatus >= m_AvailableApprovementStatuses.length) newStatus -= m_AvailableApprovementStatuses.length;

        return PronounsSetApprovementStatus.values()[newStatus];
    }
}
