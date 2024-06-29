UPDATE UserPronounAcceptanceStatus
SET AcceptanceStatus = %acceptance_status%
WHERE UserUUID = "%player_uuid%"
AND PronounId = "%pronoun_identifier%"
