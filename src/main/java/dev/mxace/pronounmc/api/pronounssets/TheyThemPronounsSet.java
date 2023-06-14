package dev.mxace.pronounmc.api.pronounssets;

import dev.mxace.pronounmc.api.PronounsSet;

public class TheyThemPronounsSet extends PronounsSet {
    public final static TheyThemPronounsSet instance = new TheyThemPronounsSet();

    @Override
    public String getSubjectPronoun() {
        return "they";
    }

    @Override
    public String getObjectPronoun() {
        return "them";
    }

    @Override
    public String getPossessiveAdjective() {
        return "their";
    }

    @Override
    public String getPossessivePronoun() {
        return "theirs";
    }

    @Override
    public String getReflexivePronoun() {
        return "themselves";
    }
}
