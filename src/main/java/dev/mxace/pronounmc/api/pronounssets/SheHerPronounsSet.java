package dev.mxace.pronounmc.api.pronounssets;

import dev.mxace.pronounmc.api.PronounsSet;

/**
 * The she/her pronouns set class.
 * @author AceKiron
 * @version 2.2
 * @see dev.mxace.pronounmc.api.PronounsSet
 */
public class SheHerPronounsSet extends PronounsSet {
    /**
     * Singleton instance of the she/her pronouns set class.
     * @see dev.mxace.pronounmc.api.PronounsSet
     */
    public final static SheHerPronounsSet instance = new SheHerPronounsSet();

    /**
     * Make constructor private.
     */
    private SheHerPronounsSet() {
        super();
    }

    /**
     * "she"
     * @return "she"
     */
    @Override
    public String getSubjectPronoun() {
        return "she";
    }

    /**
     * "her"
     * @return "her"
     */
    @Override
    public String getObjectPronoun() {
        return "her";
    }

    /**
     * "her"
     * @return "her"
     */
    @Override
    public String getPossessiveAdjective() {
        return "her";
    }

    /**
     * "hers"
     * @return "hers"
     */
    @Override
    public String getPossessivePronoun() {
        return "hers";
    }

    /**
     * "herself"
     * @return "herself"
     */
    @Override
    public String getReflexivePronoun() {
        return "herself";
    }
}
