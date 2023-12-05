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
}
