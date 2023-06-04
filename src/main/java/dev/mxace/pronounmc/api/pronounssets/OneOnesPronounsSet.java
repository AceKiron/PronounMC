package dev.mxace.pronounmc.api.pronounssets;

public class OneOnesPronounsSet extends PronounsSet {
    public final static OneOnesPronounsSet instance = new OneOnesPronounsSet();

    @Override
    public String getSubjectPronoun() {
        return "one";
    }

    @Override
    public String getObjectPronoun() {
        return "one";
    }

    @Override
    public String getPossessiveAdjective() {
        return "one's";
    }

    @Override
    public String getPossessivePronoun() {
        return "one's";
    }

    @Override
    public String getReflexivePronoun() {
        return "oneself";
    }
}
