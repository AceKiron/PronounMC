package dev.mxace.pronounmc.api.pronounssets;

import dev.mxace.pronounmc.api.PronounsSet;

public class HeHimPronounsSet extends PronounsSet {
    public final static HeHimPronounsSet instance = new HeHimPronounsSet();

    @Override
    public String getSubjectPronoun() {
        return "he";
    }

    @Override
    public String getObjectPronoun() {
        return "him";
    }

    @Override
    public String getPossessiveAdjective() {
        return "his";
    }

    @Override
    public String getPossessivePronoun() {
        return "his";
    }

    @Override
    public String getReflexivePronoun() {
        return "himself";
    }
}
