INSERT OR IGNORE INTO UserPronounAcceptanceStatus (
    PronounId,
    UserUUID,
    AcceptanceStatus
) VALUES (
    "%pronoun_identifier%",
    "%player_uuid%",
    %default_acceptance_status%
);