package dev.mxace.pronounmc.api.pronounssets;

import dev.mxace.pronounmc.api.PronounsSet;

/**
 * The they/them pronouns set class.
 * @author AceKiron
 * @version 2.2
 * @see dev.mxace.pronounmc.api.PronounsSet
 */
public class TheyThemPronounsSet extends PronounsSet {
    /**
     * Singleton instance of the they/them pronouns set class.
     * @see dev.mxace.pronounmc.api.PronounsSet
     */
    public final static TheyThemPronounsSet instance = new TheyThemPronounsSet();

    /**
     * Make constructor private.
     */
    private TheyThemPronounsSet() {
        super();
    }

    /**
     * "they"
     * @return "they"
     */
    @Override
    public String getSubjectPronoun() {
        return "they";
    }

    /**
     * "them"
     * @return "them"
     */
    @Override
    public String getObjectPronoun() {
        return "them";
    }

    /**
     * "their"
     * @return "their"
     */
    @Override
    public String getPossessiveAdjective() {
        return "their";
    }

    /**
     * "theirs"
     * @return "theirs"
     */
    @Override
    public String getPossessivePronoun() {
        return "theirs";
    }

    /**
     * "themselves"
     * @return "themselves"
     */
    @Override
    public String getReflexivePronoun() {
        return "themselves";
    }
}
