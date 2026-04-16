import java.io.Console;
import java.util.TreeMap;
import java.lang.reflect.*;
import java.util.ArrayList;

public class Controller {

    TreeMap<Integer, MenuItem> MenuMap = new TreeMap<Integer, MenuItem>();
    ConsoleView MyView;
    SpikePersistantRDB MyModel;

    public Controller(String db_host, String db_password, String db_user) {// ParentID	Hotkey	Text to display	Action	Next Menu ID
        //Main Menu
        MenuMap.put(10, new MenuItem(0, "H", "Home", "HomeOf", 10));
        MenuMap.put(1, new MenuItem(10, "1", "My Draft Articles", "SearchForArticles", 1, new ExtraMIdata("Draft", 1)));
        MenuMap.put(2, new MenuItem(10, "2", "My Rejected Articles", "SearchForArticles", 2, new ExtraMIdata("Rejected", 2)));
        MenuMap.put(3, new MenuItem(10, "3", "Search", "SearchFunction", 3));
        MenuMap.put(4, new MenuItem(10, "4", "Sign In", "UserLogIn", 10));
        MenuMap.put(5, new MenuItem(10, "5", "Register", "UserRegisterProcess", 10));
        MenuMap.put(6, new MenuItem(10, "6", "View Pending Articles", "SearchForArticles", 6, new ExtraMIdata("Pending", 6)));
        MenuMap.put(7, new MenuItem(10, "7", "Create Article", "UserVerification", 7));
        MenuMap.put(8, new MenuItem(10, "8", "View Article Metrics", "DisplayAppMetrics", 10));
        MenuMap.put(9, new MenuItem(10, "Q", "Quit", "Quit", 8));

        //Submenu for My Draft Articles
        MenuMap.put(111, new MenuItem(1, "B", "Back", null, 10));
        MenuMap.put(12, new MenuItem(12, "A", "Load Selected Article", "LoadSelectedArticle", 14));
        MenuMap.put(115, new MenuItem(12, "B", "Back", null, 10));
        MenuMap.put(14, new MenuItem(14, "1", "Edit your Draft Article", null, 16));
        MenuMap.put(15, new MenuItem(14, "2", "Submit your Article for approval", "ArticleTOpending", 10));
        MenuMap.put(121, new MenuItem(14, "H", "Home", "HomeOf", 10));
        MenuMap.put(16, new MenuItem(16, "T", "Article Title", "ArticleModification", 16, new ExtraMIdata("ArticleTitle")));
        MenuMap.put(17, new MenuItem(16, "C", "Article Content", "ArticleModification", 16, new ExtraMIdata("ArticleContent")));
        MenuMap.put(18, new MenuItem(16, "V", "Article VideoURL", "ArticleModification", 16, new ExtraMIdata("ArticleVideoURL")));
        MenuMap.put(19, new MenuItem(16, "I", "Article ImageURL", "ArticleModification", 16, new ExtraMIdata("ArticleImageURL")));
        MenuMap.put(20, new MenuItem(16, "B", "Back", null, 14));

        //Submenu for My Rejected Articles
        MenuMap.put(222, new MenuItem(2, "B", "Back", null, 10));
        MenuMap.put(21, new MenuItem(22, "A", "Load Selected Article", "LoadSelectedArticle", 24));
        MenuMap.put(215, new MenuItem(22, "B", "Back", null, 10));
        MenuMap.put(24, new MenuItem(24, "1", "Edit your Rejected Article", null, 26));
        MenuMap.put(25, new MenuItem(24, "2", "Submit your rejected Article back for approval", "ArticleTOpending", 10, new ExtraMIdata("RejectReason")));
        MenuMap.put(122, new MenuItem(24, "H", "Home", "HomeOf", 10));
        MenuMap.put(26, new MenuItem(26, "T", "Article Title", "ArticleModification", 26, new ExtraMIdata("ArticleTitle")));
        MenuMap.put(27, new MenuItem(26, "C", "Article Content", "ArticleModification", 26, new ExtraMIdata("ArticleContent")));
        MenuMap.put(28, new MenuItem(26, "V", "Article VideoURL", "ArticleModification", 26, new ExtraMIdata("ArticleVideoURL")));
        MenuMap.put(29, new MenuItem(26, "I", "Article ImageURL", "ArticleModification", 26, new ExtraMIdata("ArticleImageURL")));
        MenuMap.put(30, new MenuItem(26, "B", "Back", null, 24));

        //Submenu for searching articles
        MenuMap.put(366, new MenuItem(3, "B", "Back", null, 10));
        MenuMap.put(32, new MenuItem(32, "A", "Display Selected Article", "DisplaySelectedArticle", 33));
        MenuMap.put(315, new MenuItem(32, "B", "Back", null, 10));
        MenuMap.put(33, new MenuItem(33, "L", "Give the Article a like", "LikeOf", 3));
        MenuMap.put(325, new MenuItem(33, "B", "Back", null, 10));

        //Submenu for viewing pending articles
        MenuMap.put(666, new MenuItem(6, "B", "Back", null, 10));
        MenuMap.put(62, new MenuItem(62, "A", "Load Selected Article", "LoadSelectedArticle", 64));
        MenuMap.put(615, new MenuItem(62, "B", "Back", null, 10));
        MenuMap.put(64, new MenuItem(64, "1", "Reject the Article", "ArticleModification", 64, new ExtraMIdata("Reject")));
        MenuMap.put(65, new MenuItem(64, "2", "Approve the Article", "ArticleModification", 64, new ExtraMIdata("Approve")));
        MenuMap.put(126, new MenuItem(64, "H", "Home", "HomeOf", 10));

        //Submenu for Create New Article
        MenuMap.put(73, new MenuItem(7, "B", "Back", null, 10));
        MenuMap.put(74, new MenuItem(7, "1", "Enter the Articles Information", null, 76));
        MenuMap.put(75, new MenuItem(74, "2", "Submit your Article for approval", "ArticleTOpending", 10));
        MenuMap.put(127, new MenuItem(74, "H", "Home", "HomeOf", 10));
        MenuMap.put(76, new MenuItem(76, "T", "Article Title", "ArticleCreation", 76, new ExtraMIdata("ArticleTitle")));
        MenuMap.put(77, new MenuItem(76, "C", "Article Content", "ArticleCreation", 76, new ExtraMIdata("ArticleContent")));
        MenuMap.put(78, new MenuItem(76, "V", "Article VideoURL", "ArticleCreation", 76, new ExtraMIdata("ArticleVideoURL")));
        MenuMap.put(79, new MenuItem(76, "I", "Article ImageURL", "ArticleCreation", 76, new ExtraMIdata("ArticleImageURL")));
        MenuMap.put(80, new MenuItem(76, "S", "Submit Article", "ArticleCreation", 74, new ExtraMIdata("CreateArticle")));
        MenuMap.put(81, new MenuItem(76, "B", "Back", null, 74));


        //Submenu for viewing app metrics
   //     MenuMap.put(866, new MenuItem(8, "B", "Back", null, 10));
     //   MenuMap.put(85, new MenuItem(8, "A", "Showing Site Metrics", "DisplayAppMetrics", 8));



        MyView = new ConsoleView();
        MyModel = new SpikePersistantRDB(db_host, db_password, db_user);
    }



    public void Run(int menuID) {
        int CurrentMenu = menuID;
        while (true) {
            MyView.DisplayMenuItem(MenuMap.get(CurrentMenu));
            for (MenuItem v : MenuMap.values()) {
                if (v.ParentID != 0 && v.ParentID == CurrentMenu) {
                    MyView.DisplayMenuItem(v);
                }
            }
            MenuItem Chosen = MyView.ChooseMenuItem();
            if (Chosen.Action != null) {
                try {
                    String methodName = Chosen.Action;
                    //System.out.println("Next action: " + Chosen.Action);
                    Method getNameMethod = this.getClass().getMethod(methodName, MenuItem.class, ConsoleView.class);
                    getNameMethod.invoke(this, Chosen, MyView);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
            //System.out.println("We are going to: " + Chosen.NextID);
            CurrentMenu = Chosen.NextID;
        }
    }



    public void Quit(MenuItem param, ConsoleView Vparam) {
        System.exit(0);
    }

    public void HomeOf(MenuItem param, ConsoleView Vparam) {
        System.out.println("This is the Home Page of: " + MyView.MyUser.Name);
    }

    public void LikeOf(MenuItem param, ConsoleView Vparam) {
        if (MyView.MyUser.UserID == 0) {
            System.out.println("You can't access this feature without the right level of permissions, please Log In.");
            return;
        } else {
            MyModel.GivingLike(MyView.MyUser.UserID, Vparam.CWdata.CWArticleID);
        }
    }

    public void SearchFunction(MenuItem param, ConsoleView Vparam) {
        String U_OutputText = "Please enter the Article Title or Content your searching for ";
        Vparam.CWdata.CWsearchbarvalue = Vparam.StringInput(U_OutputText);
        String tempSearchData = "%" + Vparam.CWdata.CWsearchbarvalue + "%";

        ArrayList<Integer> DeleteMe = new ArrayList<Integer>();
        for (Integer i : MenuMap.keySet()) {
            if (i >= 1000) {
                DeleteMe.add(i);
            }
        }

        for (int i = 0; i < DeleteMe.size(); i++) {
            MenuMap.remove(DeleteMe.get(i));
        }

        ArrayList<Article> ListofSearchedArticles = MyModel.ListArticlesForSearch(tempSearchData);
        for (int i = 0; i < ListofSearchedArticles.size(); i++) {
            Article element = ListofSearchedArticles.get(i);
            MenuMap.put(100 + i, new MenuItem(3, "1" + i, element.ArticleTitle, "SaveArticleID", 32,
            new ExtraMIdata(element.ArticleID)));
        }
    }

    public void UserLogIn(MenuItem param, ConsoleView Vparam) {

        Console console = System.console();
        if (console == null) {
            System.out.println("Console not available");
            return;
        }

        String U_OutputText = "Please enter your Username: ";
        Vparam.CWdata.CWusername = Vparam.StringInput(U_OutputText);
        Vparam.CWdata.CWpassword = new String(console.readPassword("Enter your password: "));

        User P_LogingInUser = MyModel.LogIn(Vparam.CWdata.CWusername, Vparam.CWdata.CWpassword);
        if (P_LogingInUser == null) {
            System.out.println("Error, User not found. Please try again");
        } else {
            MyView.MyUser = P_LogingInUser;
            System.out.println("Successful log in, welcome:");
            MyView.MyUser.Print();
        }
    }

    public void UserRegisterProcess(MenuItem param, ConsoleView Vparam) {

        Console console = System.console();
        if (console == null) {
            System.out.println("Console not available");
            return;
        }

        String CU_OutputText = "Please create and enter your Username: ";
        String CP_OutputText = "Please create and enter your Password: ";

        Vparam.CWdata.CWusername = Vparam.StringInput(CU_OutputText);
        Vparam.CWdata.CWpassword = new String(console.readPassword("Enter your password: "));

        String tempPasswordComparison = new String(console.readPassword("Please re-enter your password: "));

        if (Vparam.CWdata.CWpassword == tempPasswordComparison) {
            User TempDataPass = new User();

            TempDataPass.Name = Vparam.StringInput(CU_OutputText);
            TempDataPass.Password  = Vparam.StringInput(CP_OutputText);
            TempDataPass.AccessLevel = "User";
            MyModel.RegisterNewUser(TempDataPass);

            User P_LogedInUser = MyModel.LogIn(TempDataPass.Name, TempDataPass.Password);
            MyView.MyUser = P_LogedInUser;
            System.out.println("Successful registration, welcome:");
            MyView.MyUser.Print();
        } else {
            System.out.println("Your password doesn't match to the second input, please ensure you have correctly wrote your password");
        }

    }

    public void CreateNewArticle(MenuItem param, ConsoleView Vparam) {
        Article TempArticleForData = new Article();
        String U_OutputText = "Please enter your Username: ";
        String P_OutputText = "Please enter your Password: ";
        Vparam.CWdata.CWusername = Vparam.StringInput(U_OutputText);
        Vparam.CWdata.CWpassword = Vparam.StringInput(P_OutputText);


    }

    public void SearchForArticles(MenuItem param, ConsoleView Vparam) {
        if (MyView.MyUser.UserID == 0) {
            System.out.println("You can't access this feature without the right level of permissions, please Log In.");
            return;
        } else {
            ArrayList<Integer> DeleteMe = new ArrayList<Integer>();
            for (Integer i : MenuMap.keySet()) {
                if (i >= 1000) {
                    DeleteMe.add(i);
                }
            }
            for (int i = 0; i < DeleteMe.size(); i++) {
                MenuMap.remove(DeleteMe.get(i));
            }

            if (param.Xtra.ArticleListParentID == 1) {
                ArrayList<Article> ListofDraftArticles = MyModel.ListArticles(param.Xtra.ArticleModifyType, MyView.MyUser.UserID);
                for (int i = 0; i < ListofDraftArticles.size(); i++) {
                    Article element = ListofDraftArticles.get(i);
                    MenuMap.put(100 + i, new MenuItem(1, "1" + i, element.ArticleTitle, "SaveArticleID", 12,
                            new ExtraMIdata(element.ArticleID))
                    );
                }
            } else if (param.Xtra.ArticleListParentID == 2) {
                ArrayList<Article> ListofRejectedArticles = MyModel.ListArticles(param.Xtra.ArticleModifyType, MyView.MyUser.UserID);
                for (int i = 0; i < ListofRejectedArticles.size(); i++) {
                    Article element = ListofRejectedArticles.get(i);
                    MenuMap.put(100 + i, new MenuItem(2, "1" + i, element.ArticleTitle, "SaveArticleID", 22,
                            new ExtraMIdata(element.ArticleID))
                    );
                }
            } else if (param.Xtra.ArticleListParentID == 6) {
                if (!(MyView.MyUser.AccessLevel.equals("Super Admin"))) {
                    System.out.println("You can't access this feature without being a Super Admin");
                    return;
                } else {
                    ArrayList<Article> ListofPendingArticles = MyModel.ListPendingArticles(param.Xtra.ArticleModifyType);
                    for (int i = 0; i < ListofPendingArticles.size(); i++) {
                        Article element = ListofPendingArticles.get(i);
                        MenuMap.put(100 + i, new MenuItem(6, "1" + i, element.ArticleTitle, "SaveArticleID", 62,
                        new ExtraMIdata(element.ArticleID))
                        );
                    }
                }
            }
        }
    }

    public void SaveArticleID(MenuItem param, ConsoleView Vparam) {
        Vparam.CWdata.CWArticleID = param.Xtra.ArticleID;
    }

    public void LoadSelectedArticle(MenuItem param, ConsoleView Vparam) {
        Article Loaded = MyModel.LoadArticle(Vparam.CWdata.CWArticleID);
        Loaded.Print();
    }

    public void ArticleModification(MenuItem param, ConsoleView Vparam) {
        Article CurrArticle = MyModel.LoadArticle(Vparam.CWdata.CWArticleID);
        //ExtraCWdata has infinite memory
        String OutputText = "Please enter the: " + param.Text;
        if (param.Xtra.ArticleModifyType == "ArticleTitle") {
            CurrArticle.ArticleTitle = Vparam.StringInput(OutputText, CurrArticle.ArticleTitle);
        } else if (param.Xtra.ArticleModifyType == "ArticleContent") {
            CurrArticle.ArticleContent = Vparam.StringInput(OutputText, CurrArticle.ArticleContent);
        }else if (param.Xtra.ArticleModifyType == "ArticleVideoURL") {
            CurrArticle.ArticleVideoURL = Vparam.StringInput(OutputText, CurrArticle.ArticleVideoURL);
        } else if (param.Xtra.ArticleModifyType == "ArticleImageURL") {
            CurrArticle.ArticleImageURL = Vparam.StringInput(OutputText, CurrArticle.ArticleImageURL);
        }else if (param.Xtra.ArticleModifyType == "Approve") {
            CurrArticle.ArticleStatus = "Approved";
            CurrArticle.RejectionReason = null;
        }else if (param.Xtra.ArticleModifyType == "Reject") {
            CurrArticle.ArticleStatus = "Rejected";
            String OutputTextforRejReason = "Please enter the rejection reason";
            CurrArticle.RejectionReason = Vparam.StringInput(OutputTextforRejReason);
        }
        MyModel.ModifyArticle(CurrArticle);
    }

    public void ArticleTOpending(MenuItem param, ConsoleView Vparam) {
        Article CurrArticle = MyModel.LoadArticle(Vparam.CWdata.CWArticleID);
        String OutputText = "Sending Article for approval...";
        if (param.Xtra.ArticleModifyType.equals("RejectReason")) {
            CurrArticle.RejectionReason = null;
        }
        CurrArticle.ArticleStatus = "Pending";
        MyModel.ModifyArticle(CurrArticle);
    }

    public void UserVerification(MenuItem param, ConsoleView Vparam) {
        if (MyView.MyUser.UserID == 0) {
            System.out.println("You can't access this feature without the right level of permissions, please Log In.");
            param.NextID = 10;
        }else {
            param.NextID = 7;
        }
        return;
    }

    public void ArticleCreation(MenuItem param, ConsoleView Vparam) {
        Article New_Article = new Article();
        New_Article.UserIDofAuthor = MyView.MyUser.UserID;
        String OutputText = "Please enter the: " + param.Text;
        if (param.Xtra.ArticleModifyType == "ArticleTitle") {
            New_Article.ArticleTitle = Vparam.StringInput(OutputText, New_Article.ArticleTitle);
        } else if (param.Xtra.ArticleModifyType == "ArticleContent") {
            New_Article.ArticleContent = Vparam.StringInput(OutputText, New_Article.ArticleContent);
        } else if (param.Xtra.ArticleModifyType == "ArticleVideoURL") {
            New_Article.ArticleVideoURL = Vparam.StringInput(OutputText, New_Article.ArticleVideoURL);
        } else if (param.Xtra.ArticleModifyType == "ArticleImageURL") {
            New_Article.ArticleImageURL = Vparam.StringInput(OutputText, New_Article.ArticleImageURL);
        }

        if (New_Article.ArticleTitle != null && New_Article.ArticleContent != null && param.Xtra.ArticleModifyType == "CreateArticle") {
            MyModel.CreateArticle(New_Article);
        }
    }

    public void DisplayAppMetrics(MenuItem param, ConsoleView Vparam) {
        int TotalLikeCount = MyModel.CountTotalLikes();
        int TotalViewCount = MyModel.CountTotalViews();
        System.out.println("Total number of likes across all articles is: " + TotalLikeCount);
        System.out.println("Total number of views across all articles is: " + TotalViewCount);
    }

    public void DisplaySelectedArticle(MenuItem param, ConsoleView Vparam) {
        Article Loaded = MyModel.LoadArticle(Vparam.CWdata.CWArticleID);
        Loaded.Print();
        Loaded.ViewCount++;
        int tempLikeCount = MyModel.CountLikes(Vparam.CWdata.CWArticleID);
        Loaded.LikeCount = tempLikeCount;
        MyModel.ModifyViewCount(Loaded);
        System.out.println("This Article has been viewed " + Loaded.ViewCount + " times");
        System.out.println("This Article has been liked " + tempLikeCount + " times");
    }

}
