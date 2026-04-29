import java.io.Console;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleView {
    User MyUser;
    ArrayList<MenuItem> MenuMemory;
    ExtraCWdata CWdata;
    Article CurrentArticle;

    public ConsoleView() {
        MenuMemory = new ArrayList<MenuItem>();
        MyUser = new User();
        CWdata = new ExtraCWdata();
        CurrentArticle = new Article();
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

    public String StringInput(String PinputText) {
        Scanner InputOfUser = new Scanner(System.in);
        System.out.println(PinputText);
        String InputString = InputOfUser.nextLine();
        return InputString;
    }

    public String SecureStringInput(String SecPinputText) {
        Console console = System.console();
        if (console == null) {
            System.out.println("Console not available please run this through Console");
            return null;
        }
        String tempvalue = new String(console.readPassword(SecPinputText));
        return tempvalue;
    }

    public void PrintContent(String StringValue) {
        System.out.println(StringValue);
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
        System.out.println(param.Hotkey + " " + param.Text);
        MenuMemory.add(param);
    }

}
