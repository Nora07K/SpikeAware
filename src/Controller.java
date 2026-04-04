import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.TreeMap;

public class Controller {

    HashMap<String, MenuItem> MenuMap = new HashMap<String, MenuItem>();

    public Controller() {// ParentID	Hotkey	Text to display	Action	Next Menu ID

        MenuMap.put("10", new MenuItem(null, "H", "Home", null, "10"));
        MenuMap.put("1", new MenuItem("10", "A", "Alma", null, "1"));
        MenuMap.put("2", new MenuItem("10", "K", "Kutya", null, "2"));
        MenuMap.put("9", new MenuItem("10", "Q", "Quit", "Quit", "10"));
        MenuMap.put("11", new MenuItem("1", "P", "Piros", "def", "1"));
        MenuMap.put("12", new MenuItem("1", "Z", "Zold", "uhw", "1"));
        MenuMap.put("13", new MenuItem("1", "H", "Home", null, "10"));
        MenuMap.put("21", new MenuItem("2", "T", "Tacsko", "nupe", "2"));
        MenuMap.put("22", new MenuItem("2", "B", "Bernathy", "asll", "2"));
        MenuMap.put("23", new MenuItem("2", "H", "Home", null, "10"));

    }

    public void Run(String menuID) {
        String CurrnetMenu = menuID;
        while (true) {
            ConsoleView MyView = new ConsoleView();
            MyView.DisplayMenuItem(MenuMap.get(CurrnetMenu));
            //System.out.println(MenuMap.get(menuID)[2]);

            for (MenuItem v : MenuMap.values()) {
                if (v.ParentID != null && v.ParentID == CurrnetMenu) {
                    MyView.DisplayMenuItem(v);
                    //System.out.println(v[2]);
                }
            }
            MenuItem Chosen = MyView.ChooseMenuItem();
            System.out.println(Chosen.Action);

            if (Chosen.Action != null && Chosen.Action == "Quit") {
                System.exit(0);
            }
            CurrnetMenu = Chosen.NextID;
        }
    }
}