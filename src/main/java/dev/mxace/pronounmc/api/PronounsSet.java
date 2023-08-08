package dev.mxace.pronounmc.api;

/**
 * The abstract pronouns set class.
 * @author AceKiron
 * @version 2.2
 */
public abstract class PronounsSet {
    /**
     * Get the subject pronoun.
     * @return Subject pronoun.
     */
    public abstract String getSubjectPronoun();

    /**
     * Get the object pronoun.
     * @return Object pronoun.
     */
    public abstract String getObjectPronoun();

    /**
     * Get the possessive adjective.
     * @return Possessive adjective.
     */
    public abstract String getPossessiveAdjective();

    /**
     * Get the possessive pronoun.
     * @return Possessive pronoun.
     */
    public abstract String getPossessivePronoun();

    /**
     * Get the reflexive pronoun.
     * @return Reflexive pronoun.
     */
    public abstract String getReflexivePronoun();

    /**
     * Register the pronouns set when it gets constructed.
     */
    protected PronounsSet() {
        PronounAPI.instance.registerPronounsSet(this);
    }

    /**
     * Get the full name of the pronouns set.
     * @return Full name of the pronouns set.
     */
    public String getFullName() {
        return getSubjectPronoun() + "/" + getObjectPronoun() + "/" + getPossessiveAdjective() + "/" + getPossessivePronoun() + "/" + getReflexivePronoun();
    }

    /**
     * Get the short name of the pronouns set.
     * @return Short name of the pronouns set.
     */
    public String getShortName() {
        return getSubjectPronoun() + "/" + getObjectPronoun() + "/" + getPossessiveAdjective();
    }
}
