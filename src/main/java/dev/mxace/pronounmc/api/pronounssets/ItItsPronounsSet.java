package dev.mxace.pronounmc.api.pronounssets;

import dev.mxace.pronounmc.api.PronounsSet;

/**
 * The it/its pronouns set class.
 * @author AceKiron
 * @version 2.2
 * @see dev.mxace.pronounmc.api.PronounsSet
 */
public class ItItsPronounsSet extends PronounsSet {
    /**
     * Singleton instance of the it/its pronouns set class.
     * @see dev.mxace.pronounmc.api.PronounsSet
     */
    public final static ItItsPronounsSet instance = new ItItsPronounsSet();

    /**
     * Make constructor private.
     */
    private ItItsPronounsSet() {
        super();
    }

    /**
     * "it"
     * @return "it"
     */
    @Override
    public String getSubjectPronoun() {
        return "it";
    }

    /**
     * "it"
     * @return "it"
     */
    @Override
    public String getObjectPronoun() {
        return "it";
    }

    /**
     * "its"
     * @return "its"
     */
    @Override
    public String getPossessiveAdjective() {
        return "its";
    }

    /**
     * "its"
     * @return "its"
     */
    @Override
    public String getPossessivePronoun() {
        return "its";
    }

    /**
     * "itself"
     * @return "itself"
     */
    @Override
    public String getReflexivePronoun() {
        return "itself";
    }
}
