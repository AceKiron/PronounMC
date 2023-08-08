package dev.mxace.pronounmc.api;

/**
 * The pronouns set approvement status enumerator.
 * @author AceKiron
 * @version 2.2
 */
public enum PronounsSetApprovementStatus {
    /**
     * Ask me if I'm okay with this pronouns set.
     */
    ASK,

    /**
     * I prefer this pronouns set.
     */
    YES,

    /**
     * I'm okay with this pronouns set, but only jokingly.
     */
    JOKINGLY,

    /**
     * Only use this pronouns set if we're close.
     */
    ONLY_IF_WE_ARE_CLOSE,

    /**
     * I'm okay with this pronouns set.
     */
    OKAY,

    /**
     * I don't use this pronouns set.
     */
    NOPE
}