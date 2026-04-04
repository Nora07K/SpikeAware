import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleView {
    User who = new User();
    //ConsoleView DisplayMenu = new ConsoleView();
    ArrayList<MenuItem> MenuMemory = new ArrayList<MenuItem>();

    public String StringInput() {
        return "";
    }

    public MenuItem ChooseMenuItem() {
        for (int i = 0; i < MenuMemory.size(); i++) {
            System.out.println(MenuMemory.get(i).Hotkey);
        }
        MenuItem MenuDecision = null;
        while (true) {
            Scanner ChooseMenuHotkey = new Scanner(System.in);
            String UserInput = ChooseMenuHotkey.nextLine();

            for (int i = 0; i < MenuMemory.size(); i++) {
                if (UserInput.equals(MenuMemory.get(i).Hotkey)) {
                    MenuDecision = MenuMemory.get(i);
                }
            }
            if (! (MenuDecision == null)) {
                break;
            }
            System.out.println("Please try again. Invalid input of:" + UserInput);
        }
        return MenuDecision;
    }


    public void DisplayMenuItem(MenuItem param) {
        System.out.println(param.ParentID + " " + param.Hotkey + " " + param.Text + " " + param.Action + " " + param.NextID);
        MenuMemory.add(param);
    }

    public void DisplayArticle(Article abc) {

    }
}
