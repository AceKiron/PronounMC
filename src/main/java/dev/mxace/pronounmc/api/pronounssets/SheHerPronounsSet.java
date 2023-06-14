package dev.mxace.pronounmc.api.pronounssets;

import dev.mxace.pronounmc.api.PronounsSet;

public class SheHerPronounsSet extends PronounsSet {
    public final static SheHerPronounsSet instance = new SheHerPronounsSet();

    @Override
    public String getSubjectPronoun() {
        return "she";
    }

    @Override
    public String getObjectPronoun() {
        return "her";
    }

    @Override
    public String getPossessiveAdjective() {
        return "her";
    }

    @Override
    public String getPossessivePronoun() {
        return "hers";
    }

    @Override
    public String getReflexivePronoun() {
        return "herself";
    }
}
