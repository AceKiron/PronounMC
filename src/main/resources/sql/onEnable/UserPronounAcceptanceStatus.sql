CREATE TABLE IF NOT EXISTS UserPronounAcceptanceStatus (
    PronounId VARCHAR(64) NOT NULL,
    UserUUID CHAR(36) NOT NULL,

    AcceptanceStatus INT NOT NULL,

    UNIQUE (
        PronounId,
        UserUUID
    )
);