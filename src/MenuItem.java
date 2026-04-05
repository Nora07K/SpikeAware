public class MenuItem {

    int ParentID;
    String Hotkey;
    String Text;
    String Action;
    int NextID;

    public MenuItem(int P_ParentID, String P_Hotkey, String P_Text, String P_Action, int P_NextID) {
        ParentID = P_ParentID;
        Hotkey = P_Hotkey;
        Text = P_Text;
        Action = P_Action;
        NextID = P_NextID;
    }
}
