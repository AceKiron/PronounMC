package dev.mxace.pronounmc.api.pronounssets;

import dev.mxace.pronounmc.api.PronounsSet;

/**
 * The he/him pronouns set class.
 * @author AceKiron
 * @version 2.2
 * @see dev.mxace.pronounmc.api.PronounsSet
 */
public class HeHimPronounsSet extends PronounsSet {
    /**
     * Singleton instance of the he/him pronouns set class.
     * @see dev.mxace.pronounmc.api.PronounsSet
     */
    public final static HeHimPronounsSet instance = new HeHimPronounsSet();

    /**
     * Make constructor private.
     */
    private HeHimPronounsSet() {
        super();
    }

    /**
     * "he"
     * @return "he"
     */
    @Override
    public String getSubjectPronoun() {
        return "he";
    }

    /**
     * "him"
     * @return "him"
     */
    @Override
    public String getObjectPronoun() {
        return "him";
    }

    /**
     * "his"
     * @return "his"
     */
    @Override
    public String getPossessiveAdjective() {
        return "his";
    }

    /**
     * "his"
     * @return "his"
     */
    @Override
    public String getPossessivePronoun() {
        return "his";
    }

    /**
     * "himself"
     * @return "himself"
     */
    @Override
    public String getReflexivePronoun() {
        return "himself";
    }
}
