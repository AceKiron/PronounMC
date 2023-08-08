package dev.mxace.pronounmc.api.pronounssets;

import dev.mxace.pronounmc.api.PronounsSet;

/**
 * The one/one's pronouns set class.
 * @author AceKiron
 * @version 2.2
 * @see dev.mxace.pronounmc.api.PronounsSet
 */
public class OneOnesPronounsSet extends PronounsSet {
    /**
     * Singleton instance of the one/one's pronouns set class.
     * @see dev.mxace.pronounmc.api.PronounsSet
     */
    public final static OneOnesPronounsSet instance = new OneOnesPronounsSet();

    /**
     * Make constructor private.
     */
    private OneOnesPronounsSet() {
        super();
    }

    /**
     * "one"
     * @return "one"
     */
    @Override
    public String getSubjectPronoun() {
        return "one";
    }

    /**
     * "one"
     * @return "one"
     */
    @Override
    public String getObjectPronoun() {
        return "one";
    }

    /**
     * "one's"
     * @return "one's"
     */
    @Override
    public String getPossessiveAdjective() {
        return "one's";
    }

    /**
     * "one's"
     * @return "one's"
     */
    @Override
    public String getPossessivePronoun() {
        return "one's";
    }

    /**
     * "oneself"
     * @return "oneself"
     */
    @Override
    public String getReflexivePronoun() {
        return "oneself";
    }
}
