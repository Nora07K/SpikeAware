import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleView {
    User MyUser;
    ArrayList<MenuItem> MenuMemory;
    ExtraCWdata CWdata;

    public ConsoleView() {
        MenuMemory = new ArrayList<MenuItem>();
        MyUser = new User();
        CWdata = new ExtraCWdata();
    }

    public String StringInput(String PinputText, String P_OriginalValue) {
        Scanner InputOfUser = new Scanner(System.in);
        System.out.println(PinputText + ". Current Value: " + P_OriginalValue);
        String InputString = InputOfUser.nextLine();
        if (InputString == "") {
            return P_OriginalValue;
        } else {
            return InputString;
        }
    }

    public MenuItem ChooseMenuItem() {
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
        MenuMemory = new ArrayList<MenuItem>();
        return MenuDecision;
    }


    public void DisplayMenuItem(MenuItem param) {
        System.out.println(param.ParentID + " " + param.Hotkey + " " + param.Text + " " + param.Action + " " + param.NextID);
        MenuMemory.add(param);
    }

    public void DisplayArticle(Article abc) {

    }
}
