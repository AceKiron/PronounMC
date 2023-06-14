package dev.mxace.pronounmc.api.pronounssets;

import dev.mxace.pronounmc.api.PronounsSet;

public class ItItsPronounsSet extends PronounsSet {
    public final static ItItsPronounsSet instance = new ItItsPronounsSet();

    @Override
    public String getSubjectPronoun() {
        return "it";
    }

    @Override
    public String getObjectPronoun() {
        return "it";
    }

    @Override
    public String getPossessiveAdjective() {
        return "its";
    }

    @Override
    public String getPossessivePronoun() {
        return "its";
    }

    @Override
    public String getReflexivePronoun() {
        return "itself";
    }
}
