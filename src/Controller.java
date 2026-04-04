import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.TreeMap;

public class Controller {
    ConsoleView MyView = new ConsoleView();
    HashMap<Integer, String[]> MenuMap = new HashMap<Integer, String[]>();

    public Controller() {// ParentID	Hotkey	Text to display	Action	Next Menu ID

        MenuMap.put(10, new String[]{null, "H", "Home", null, "10"});
        MenuMap.put(1, new String[]{"10", "A", "Alma", null, "10"});
        MenuMap.put(2, new String[]{"10", "K", "Kutya", null, "10"});
        MenuMap.put(11, new String[]{"1", "P", "Piros", null, "10"});
        MenuMap.put(12, new String[]{"1", "Z", "Zold", null, "10"});
        MenuMap.put(21, new String[]{"2", "T", "Tacsko", null, "10"});
        MenuMap.put(22, new String[]{"2", "B", "Bernathy", null, "10"});
    }

    public void Run(int menuID) {
        MyView.DisplayMenu(MenuMap.get(menuID));
        //System.out.println(MenuMap.get(menuID)[2]);

        for (String[] v : MenuMap.values()) {
            if (v[0] != null && Integer.parseInt(v[0]) == menuID) {
                MyView.DisplayMenu(v);
                //System.out.println(v[2]);
            }
        }
    }
}