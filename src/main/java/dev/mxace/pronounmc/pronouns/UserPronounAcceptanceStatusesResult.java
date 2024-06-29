package dev.mxace.pronounmc.pronouns;

public class UserPronounAcceptanceStatusesResult {

    public final String pronounId;
    public final int acceptanceStatus;
    public final boolean positive;

    public UserPronounAcceptanceStatusesResult(String pronounId, int acceptanceStatus, boolean positive) {
        this.pronounId = pronounId;
        this.acceptanceStatus = acceptanceStatus;
        this.positive = positive;
    }

}
