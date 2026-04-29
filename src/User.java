public class User {
    private String Name = "Anonym";
    private String AccessLevel = "";
    private String Password = "";
    private int UserID = 0;

    public User() {
    }

    public User(String Name, String Password) {
    }

    // Getters
    public String getName() { return Name; }
    public String getAccessLevel() { return AccessLevel; }
    public String getPassword() { return Password; }
    public int getUserID() { return UserID; }

    // Setters
    public void setName(String name) { this.Name = name; }
    public void setAccessLevel(String accessLevel) { this.AccessLevel = accessLevel; }
    public void setPassword(String password) { this.Password = password; }
    public void setUserID(int userID) { this.UserID = userID; }


    public String toString() {
        return "Username: " + this.Name + "\n" + "UserID: " + this.UserID + "\n" + "Access Level: " + this.AccessLevel;
    }



}
