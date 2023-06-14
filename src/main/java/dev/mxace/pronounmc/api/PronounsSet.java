package dev.mxace.pronounmc.api;

public abstract class PronounsSet {
    public abstract String getSubjectPronoun();
    public abstract String getObjectPronoun();
    public abstract String getPossessiveAdjective();
    public abstract String getPossessivePronoun();
    public abstract String getReflexivePronoun();

    protected PronounsSet() {
        PronounAPI.instance.registerPronounsSet(this);
    }

    public String getFullName() {
        return getSubjectPronoun() + "/" + getObjectPronoun() + "/" + getPossessiveAdjective() + "/" + getPossessivePronoun() + "/" + getReflexivePronoun();
    }

    public String getShortName() {
        return getSubjectPronoun() + "/" + getObjectPronoun() + "/" + getPossessiveAdjective();
    }
}
