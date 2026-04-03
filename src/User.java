public class User {
    String Name = "";
    String AccessLevel = "";
    String Password = "";
    int UserID;

    public User() {
    }

    public void Register(String Username, String AccessAbility, SpikePersistantRDB Model) {
        Name = Username;
        AccessLevel = AccessAbility;
        Model.CreateUser(this);
    }

    public void Print() {
        System.out.println(Name);
        System.out.println(Password);
        System.out.println(UserID);
        System.out.println(AccessLevel);
    }

}
