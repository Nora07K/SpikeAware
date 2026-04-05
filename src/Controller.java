import java.util.TreeMap;
import java.lang.reflect.*;
import java.util.ArrayList;

public class Controller {

    TreeMap<Integer, MenuItem> MenuMap = new TreeMap<Integer, MenuItem>();
    ConsoleView MyView;
    SpikePersistantRDB MyModel;

    public Controller() {// ParentID	Hotkey	Text to display	Action	Next Menu ID

        MenuMap.put(10, new MenuItem(0, "H", "Dog Painting Home", null, 10));
        MenuMap.put(1, new MenuItem(10, "C", "Choose Colour", null, 1));
        MenuMap.put(2, new MenuItem(10, "D", "Choose Dog", null, 2));
        MenuMap.put(3, new MenuItem(10, "P", "Paint", "Paint", 10));
        MenuMap.put(9, new MenuItem(10, "Q", "Quit", "Quit", 10));
        MenuMap.put(11, new MenuItem(1, "P", "Piros", "ChangeColour", 10));
        MenuMap.put(12, new MenuItem(1, "Z", "Zold", "ChangeColour", 10));
        MenuMap.put(13, new MenuItem(1, "C", "Custom Colour", "InputColour", 10));
        MenuMap.put(19, new MenuItem(1, "H", "Home", null, 10));
        MenuMap.put(21, new MenuItem(2, "T", "Tacsko", "ChangeDog", 10, new ExtraMIdata(101)));
        MenuMap.put(22, new MenuItem(2, "B", "Bernathy", "ChangeDog", 10, new ExtraMIdata(202)));
        MenuMap.put(23, new MenuItem(2, "O", "Other Types", "SearchForTypes", 23));
        MenuMap.put(29, new MenuItem(2, "H", "Home", null, 10));
        MyView = new ConsoleView();
        MyModel = new SpikePersistantRDB();
    }

    public void Quit(MenuItem param, ConsoleView Vparam) {
        System.exit(0);
    }

    public void SearchForTypes(MenuItem param, ConsoleView Vparam) {
        ArrayList<Integer> DeleteMe = new ArrayList<Integer>();
        for (Integer i : MenuMap.keySet()) {
            if (i >= 1000) {
                DeleteMe.add(i);
            }
        }
        for (int i = 0; i < DeleteMe.size(); i++) {
            MenuMap.remove(DeleteMe.get(i));
        }

        MenuMap.put(1000, new MenuItem(23, "1", "Golden Retriever", "ChangeDog", 10, new ExtraMIdata(188)));
        MenuMap.put(1002, new MenuItem(23, "2", "Poodle", "ChangeDog", 10, new ExtraMIdata(548)));
        MenuMap.put(1999, new MenuItem(23, "B", "Back", null, 2));
    }

    public void InputColour(MenuItem param, ConsoleView Vparam) {
        String OutputText = "Please enter the: " + param.Text;
        Vparam.CWdata.DogColour = Vparam.StringInput(OutputText, Vparam.CWdata.DogColour);
        System.out.println(Vparam.CWdata.DogColour);
    }

    public void ChangeColour(MenuItem param, ConsoleView Vparam) {
        Vparam.CWdata.DogColour = param.Text;
        System.out.println(Vparam.CWdata.DogColour);
    }

    public void ChangeDog(MenuItem param, ConsoleView Vparam) {
        Vparam.CWdata.DogType = param.Text;
        System.out.println(Vparam.CWdata.DogType);
        Vparam.CWdata.DogTypeID = param.Xtra.DogTypeID;
    }

    public void Paint(MenuItem param, ConsoleView Vparam) {
        if (Vparam.CWdata.DogType == null || Vparam.CWdata.DogColour == null) {
            System.out.println("Error occured, make sure to have selected a dog type and a dog colour");
        } else {
            System.out.println("I am painting: " + Vparam.CWdata.DogType + " " + Vparam.CWdata.DogTypeID + " " +  Vparam.CWdata.DogColour );
            this.Quit(param, Vparam);
        }
    }

    public void Run(int menuID) {
        int CurrnetMenu = menuID;
        while (true) {
            MyView.DisplayMenuItem(MenuMap.get(CurrnetMenu));
            for (MenuItem v : MenuMap.values()) {
                if (v.ParentID != 0 && v.ParentID == CurrnetMenu) {
                    MyView.DisplayMenuItem(v);
                }
            }
            MenuItem Chosen = MyView.ChooseMenuItem();
            if (Chosen.Action != null) {
                try {
                    String methodName = Chosen.Action;
                    System.out.println("Next action: " + Chosen.Action);
                    Method getNameMethod = this.getClass().getMethod(methodName, MenuItem.class, ConsoleView.class);
                    getNameMethod.invoke(this, Chosen, MyView);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
            System.out.println("We are going to: " + Chosen.NextID);
            CurrnetMenu = Chosen.NextID;
        }
    }
}