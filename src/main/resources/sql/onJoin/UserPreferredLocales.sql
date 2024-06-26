INSERT OR IGNORE INTO UserPreferredLocales (
    UserUUID,
    Locale
) VALUES (
    "%player_uuid%",
    "%default_locale%"
);