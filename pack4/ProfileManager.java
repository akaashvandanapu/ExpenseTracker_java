package pack4;

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
}
