package dev.mxace.pronounmc.api;

import dev.mxace.pronounmc.api.pronounssets.PronounsSet;
import org.bukkit.entity.Player;

public interface PronounsEventListener {
    void onPronounsSetRegistered(PronounsSet pronounsSet);
    void onPronounsSetUnregistered(PronounsSet pronounsSet);
    void onPronounsSetApprovementStatusChanged(Player player, PronounsSet pronounsSet, PronounsSetApprovementStatus oldApprovementStatus, PronounsSetApprovementStatus newApprovementStatus);
}
