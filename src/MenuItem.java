public class MenuItem {

    int ParentID;
    String Hotkey;
    String Text;
    String Action;
    int NextID;
    ExtraMIdata Xtra;

    public MenuItem(int P_ParentID, String P_Hotkey, String P_Text, String P_Action, int P_NextID, ExtraMIdata XtraMIdata) {
        ParentID = P_ParentID;
        Hotkey = P_Hotkey;
        Text = P_Text;
        Action = P_Action;
        NextID = P_NextID;
        Xtra = XtraMIdata;
    }

    public MenuItem(int P_ParentID, String P_Hotkey, String P_Text, String P_Action, int P_NextID) {
        ParentID = P_ParentID;
        Hotkey = P_Hotkey;
        Text = P_Text;
        Action = P_Action;
        NextID = P_NextID;
        Xtra = new ExtraMIdata();
    }
}
