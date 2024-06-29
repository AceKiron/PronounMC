package dev.mxace.pronounmc;

import dev.mxace.pronounmc.pronouns.UserPronounAcceptanceStatusesResult;
import dev.mxace.pronounmc.utils.FSUtils;
import dev.mxace.pronounmc.utils.StringUtils;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class PronounMCDatabase {

    private final Connection connection;

    private final String getMyPronounsQuery, getMySpecificPronounsQuery, setMySpecificPronounsQuery;
    private final String getPreferredLocaleQuery, setPreferredLocaleQuery;

    public PronounMCDatabase(String path) throws SQLException, IOException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + PronounMC.pluginInstance.getDataFolder().getAbsolutePath() + "/" + path);

        executeResourceDirectory("sql/onEnable");

        getMyPronounsQuery = FSUtils.readResource("sql/pronouns/getMyPronouns.sql");
        getMySpecificPronounsQuery = FSUtils.readResource("sql/pronouns/getMySpecificPronouns.sql");
        setMySpecificPronounsQuery = FSUtils.readResource("sql/pronouns/setMySpecificPronouns.sql");

        getPreferredLocaleQuery = FSUtils.readResource("sql/preferredLocales/getPreferredLocale.sql");
        setPreferredLocaleQuery = FSUtils.readResource("sql/preferredLocales/setPreferredLocale.sql");
    }

    public void executeResourceDirectory(String directory) throws SQLException, IOException {
        for (String path : FSUtils.readResourceDirectory(directory)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(FSUtils.readResource(path));
            }
        }
    }

    public void executeQuery(String query, Map<String, String> map) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(StringUtils.formatString(query, map));
        }
    }

    public List<UserPronounAcceptanceStatusesResult> getUserPronounAcceptanceStatuses(Map<String, String> map) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(StringUtils.formatString(getMyPronounsQuery, map));
            List<UserPronounAcceptanceStatusesResult> result = new ArrayList<>();

            while (rs.next()) {
                result.add(new UserPronounAcceptanceStatusesResult(rs.getString("PronounId"), rs.getInt("AcceptanceStatus"), PronounMC.pluginInstance.getAcceptanceStatusesConfig().get(rs.getInt("AcceptanceStatus")).isPositive()));
            }

            return result;
        }
    }

    public UserPronounAcceptanceStatusesResult getUserPronounAcceptanceStatus(String identifier, Map<String, String> map) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(StringUtils.formatString(getMySpecificPronounsQuery, map));
            rs.next();

            return new UserPronounAcceptanceStatusesResult(identifier, rs.getInt("AcceptanceStatus"), PronounMC.pluginInstance.getAcceptanceStatusesConfig().get(rs.getInt("AcceptanceStatus")).isPositive());
        }
    }

    public void updateUserPronounAcceptanceStatus(Map<String, String> map) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(StringUtils.formatString(setMySpecificPronounsQuery, map));
        }
    }

    public String getAllPronounsAcceptanceStatusesAsString(String locale, Map<String, String> map) throws SQLException {
        String result = "";

        List<UserPronounAcceptanceStatusesResult> userPronounAcceptanceStatuses = getUserPronounAcceptanceStatuses(map);

        boolean firstHasPassed = false;

        for (int i = 0; i < userPronounAcceptanceStatuses.size(); i++) {
            UserPronounAcceptanceStatusesResult userPronounAcceptanceStatusesResult = userPronounAcceptanceStatuses.get(i);
            if (PronounMC.pluginInstance.getSimpleConfig().hideNegativePronounsAcceptance && !userPronounAcceptanceStatusesResult.positive) continue;

            if (!firstHasPassed) {
                firstHasPassed = true;
            } else {
                result += "\n";
            }

            result += StringUtils.capitalize(PronounMC.pluginInstance.getPronounsConfig().getPronouns(userPronounAcceptanceStatusesResult.pronounId).getLocalePronouns(locale).getLongString()) + ": " + PronounMC.pluginInstance.getAcceptanceStatusesConfig().get(userPronounAcceptanceStatusesResult.acceptanceStatus).getLocaleString(locale) + ".";
        }

        return result;
    }

    public String getPreferredLocale(Map<String, String> map) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(StringUtils.formatString(getPreferredLocaleQuery, map));
            rs.next();

            return rs.getString("Locale");
        }
    }

    public void setPreferredLocale(Map<String, String> map) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(StringUtils.formatString(setPreferredLocaleQuery, map));
        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

}
