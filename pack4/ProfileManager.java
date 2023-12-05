package pack4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DatabaseConnector;

public class ProfileManager {
    private Profile[] profiles;
    private int profileCount;

    public ProfileManager(int maxSize) {
        this.profiles = new Profile[maxSize];
        this.profileCount = 0;
    }

    public void addProfile(Profile profile) {
        if (profileCount < profiles.length) {
            profiles[profileCount] = profile;
            profileCount++;
        } else {
            System.out.println("Cannot add more profiles. Maximum limit reached.");
        }
    }

    public void displayAllProfiles() {
        for (int i = 0; i < profileCount; i++) {
            profiles[i].displayProfile();
        }
    }

    public Profile getProfileById(int profileId) {
        for (int i = 0; i < profileCount; i++) {
            if (profiles[i].getProfileId() == profileId) {
                return profiles[i];
            }
        }
        return null;
    }

    public boolean profileExistsInDatabase(int profileId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String sql = "SELECT * FROM profiles WHERE profile_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, profileId);
                ResultSet resultSet = preparedStatement.executeQuery();

                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
