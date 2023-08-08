package dev.mxace.pronounmc.api;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Event listener that listens to PronounMC events.
 * @author AceKiron
 * @version 2.3
 */
public interface PronounsEventListener {
    /**
     * Called whenever a pronouns set is registered.
     * @param pronounsSet The pronouns set which was registered.
     * @see dev.mxace.pronounmc.api.PronounsSet
     */
    void onPronounsSetRegistered(@NotNull PronounsSet pronounsSet);

    /**
     * Called whenever a pronouns set is unregistered.
     * @param pronounsSet The pronouns set which was unregistered.
     * @see dev.mxace.pronounmc.api.PronounsSet
     */
    void onPronounsSetUnregistered(@NotNull PronounsSet pronounsSet);

    /**
     * Called whenever a pronouns set approvement status is changed.
     * @param player The player whose pronouns set approvement status changed.
     * @param pronounsSet The pronouns set of which teh approvement status changed.
     * @param oldApprovementStatus The old approvement status.
     * @param newApprovementStatus The new approvement status.
     */
    void onPronounsSetApprovementStatusChanged(@NotNull Player player, @NotNull PronounsSet pronounsSet, @NotNull PronounsSetApprovementStatus oldApprovementStatus, @NotNull PronounsSetApprovementStatus newApprovementStatus);
}
