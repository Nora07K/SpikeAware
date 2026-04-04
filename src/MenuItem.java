public class MenuItem {

    String ParentID;
    String Hotkey;
    String Text;
    String Action;
    String NextID;

    public MenuItem(String P_ParentID, String P_Hotkey, String P_Text, String P_Action, String P_NextID) {
        ParentID = P_ParentID;
        Hotkey = P_Hotkey;
        Text = P_Text;
        Action = P_Action;
        NextID = P_NextID;
    }
}
