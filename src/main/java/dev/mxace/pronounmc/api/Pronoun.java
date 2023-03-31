package dev.mxace.pronounmc.api;

public abstract class Pronoun {

    public abstract String GetSubjectPronoun();
    public abstract String GetObjectPronoun();
    public abstract String GetPossessiveAdjective();
    public abstract String GetPossessivePronoun();
    public abstract String GetReflexivePronoun();

    public Pronoun() {
        PronounAPI.Register(this);
    }

    public String GetFullName() {
        return GetSubjectPronoun() + "/" + GetObjectPronoun() + "/" + GetPossessiveAdjective() + "/" + GetPossessivePronoun() + "/" + GetReflexivePronoun();
    }

    public String GetShortName() {
        return GetSubjectPronoun() + "/" + GetObjectPronoun() + "/" + GetPossessiveAdjective();
    }

}
