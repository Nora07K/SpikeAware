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
        MenuMap.put(8, new MenuItem(10, "8", "View Page Metrics", null, 8));
        MenuMap.put(9, new MenuItem(10, "Q", "Quit", "Quit", 8));

        //Submenu for My Draft Articles
        MenuMap.put(111, new MenuItem(1, "B", "Back", null, 10));
        MenuMap.put(12, new MenuItem(12, "A", "Load Selected Article", "LoadSelectedArticle", 14));
        MenuMap.put(115, new MenuItem(12, "B", "Back", null, 10));
        MenuMap.put(14, new MenuItem(14, "1", "Edit your Draft Article", null, 16));
        MenuMap.put(15, new MenuItem(14, "2", "Submit your Article for approval", "ArticleTOpending", 10, new ExtraMIdata("Pending")));
        MenuMap.put(121, new MenuItem(14, "H", "Home", "HomeOf", 10));
        MenuMap.put(16, new MenuItem(16, "T", "Article Title", "ArticleModification", 16, new ExtraMIdata("ArticleTitle")));
        MenuMap.put(17, new MenuItem(16, "C", "Article Content", "ArticleModification", 16, new ExtraMIdata("ArticleContent")));
        MenuMap.put(18, new MenuItem(16, "V", "Article VideoURL", "ArticleModification", 16, new ExtraMIdata("ArticleVideoURL")));
        MenuMap.put(19, new MenuItem(16, "I", "Article ImageURL", "ArticleModification", 16, new ExtraMIdata("ArticleImageURL")));
        MenuMap.put(20, new MenuItem(16, "B", "Back", null, 14));

        //Submenu for My Rejected Articles
        MenuMap.put(222, new MenuItem(2, "B", "Back", null, 10));
        MenuMap.put(22, new MenuItem(22, "A", "Load Selected Article", "LoadSelectedArticle", 24));
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
        MenuMap.put(64, new MenuItem(64, "1", "Reject the Article", "ArticleModification", 10, new ExtraMIdata("Reject")));
        MenuMap.put(65, new MenuItem(64, "2", "Approve the Article", "ArticleModification", 10, new ExtraMIdata("Approve")));
        MenuMap.put(126, new MenuItem(64, "H", "Home", "HomeOf", 10));

        //Submenu for Create New Article
        MenuMap.put(73, new MenuItem(7, "B", "Back", null, 10));
        MenuMap.put(74, new MenuItem(7, "1", "Enter the Articles Information", null, 76));
        MenuMap.put(75, new MenuItem(74, "2", "Submit your Article for approval", "ArticleTOpending",10, new ExtraMIdata("Pending")));
        MenuMap.put(127, new MenuItem(74, "H", "Home", "HomeOf", 10));
        MenuMap.put(76, new MenuItem(76, "T", "Article Title", "ArticleCreation", 76, new ExtraMIdata("ArticleTitle")));
        MenuMap.put(77, new MenuItem(76, "C", "Article Content", "ArticleCreation", 76, new ExtraMIdata("ArticleContent")));
        MenuMap.put(78, new MenuItem(76, "V", "Article VideoURL", "ArticleCreation", 76, new ExtraMIdata("ArticleVideoURL")));
        MenuMap.put(79, new MenuItem(76, "I", "Article ImageURL", "ArticleCreation", 76, new ExtraMIdata("ArticleImageURL")));
        MenuMap.put(80, new MenuItem(76, "S", "Submit Article", "ArticleCreation", 74, new ExtraMIdata("CreateArticle")));
        MenuMap.put(81, new MenuItem(76, "B", "Back", null, 74));


        //Submenu for viewing app metrics
        MenuMap.put(866, new MenuItem(8, "B", "Back", null, 10));
        MenuMap.put(85, new MenuItem(8, "A", "Showing Site Metrics", "DisplayAppMetrics", 8));
        MenuMap.put(86, new MenuItem(8, "E", "ListIndividualizedAppMetrics", "ListIndividualizedAppMetrics", 8));



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
            boolean ErrorOccurs = false;
            if (Chosen.Action != null) {
                try {
                    String methodName = Chosen.Action;
                    //System.out.println("Next action: " + Chosen.Action);
                    Method getNameMethod = this.getClass().getMethod(methodName, MenuItem.class, ConsoleView.class);
                    ErrorOccurs = (boolean) getNameMethod.invoke(this, Chosen, MyView);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
            if (ErrorOccurs) {
                this.Run(CurrentMenu);
            }
            CurrentMenu = Chosen.NextID;
        }
    }



    public void Quit(MenuItem param, ConsoleView Vparam) {
        System.exit(0);
    }

    public boolean HomeOf(MenuItem param, ConsoleView Vparam) {
        Vparam.PrintContent("This is the Home Page of: " + String.valueOf(MyView.MyUser));
        return false;
    }

    public boolean LikeOf(MenuItem param, ConsoleView Vparam) {
        if (MyView.MyUser.getUserID() == 0) {
            Vparam.PrintContent("You can't access this feature without the right level of permissions, please Log In.");
            return true;
        } else {
            try {
                MyModel.GivingLike(MyView.MyUser.getUserID(), Vparam.CWdata.CWArticleID);
            } catch (Exception e) {
                Vparam.PrintContent(String.valueOf(e));
                return true;
            }
            return false;
        }
    }

    public boolean SearchFunction(MenuItem param, ConsoleView Vparam) {
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

        ArrayList<Article> ListofSearchedArticles = null;
        try {
            ListofSearchedArticles = MyModel.ListArticlesForSearch(tempSearchData);
        } catch (Exception e) {
            Vparam.PrintContent(String.valueOf(e));
            return true;
        }
        for (int i = 0; i < ListofSearchedArticles.size(); i++) {
            Article element = ListofSearchedArticles.get(i);
            MenuMap.put(100 + i, new MenuItem(3, "1" + i, element.getArticleTitle(), "SaveArticleID", 32,
            new ExtraMIdata(element.getArticleID())));
        }
        return false;
    }

    public boolean UserLogIn(MenuItem param, ConsoleView Vparam) {
        String U_OutputText = "Please enter your Username: ";
        String P_OutputText = "Please enter your Password: ";
        //Vparam.CWdata.CWpassword = Vparam.SecureStringInput(P_OutputText);
        Vparam.CWdata.CWusername = Vparam.StringInput(U_OutputText);
        Vparam.CWdata.CWpassword = Vparam.StringInput(P_OutputText);
        User P_LogingInUser = new User();
        try {
            P_LogingInUser = MyModel.LogIn(Vparam.CWdata.CWusername, Vparam.CWdata.CWpassword);
        } catch (Exception e) {
            Vparam.PrintContent(String.valueOf(e));
            return true;
        }
        if (P_LogingInUser == null) {
            Vparam.PrintContent("Error, User not found. Please try again");
            return true;
        } else {
            MyView.MyUser = P_LogingInUser;
            Vparam.PrintContent("Successful log in, welcome:");
            String TempPrintValue;
            try {
                TempPrintValue = String.valueOf(MyView.MyUser);
            } catch (Exception e) {
                Vparam.PrintContent(String.valueOf(e));
                return true;
            }
            Vparam.PrintContent(TempPrintValue);
            return false;
        }
    }

    public boolean UserRegisterProcess(MenuItem param, ConsoleView Vparam) {

        String U_OutputText = "Please enter your Username: ";
        String P_OutputText = "Please enter your Password: ";
        Vparam.CWdata.CWusername = Vparam.StringInput(U_OutputText);
        Vparam.CWdata.CWpassword = Vparam.StringInput(P_OutputText);
//        Vparam.CWdata.CWpassword = Vparam.SecureStringInput(P_OutputText);
        String tempPasswordComparison = Vparam.StringInput("Please re-enter your password: ");

        if (Vparam.CWdata.CWpassword.equals(tempPasswordComparison)) {
            User TempDataPass = new User();

            TempDataPass.setName(Vparam.CWdata.CWusername);
            TempDataPass.setPassword(Vparam.CWdata.CWpassword);
            TempDataPass.setAccessLevel("User");
            try {
                MyModel.RegisterNewUser(TempDataPass);
            } catch (Exception e) {
                Vparam.PrintContent(String.valueOf(e));
                return true;
            }

            try {
                MyView.MyUser = MyModel.LogIn(TempDataPass.getName(), TempDataPass.getPassword());
            } catch (Exception e) {
                Vparam.PrintContent(String.valueOf(e));
                return true;
            }
            Vparam.PrintContent("Successful registration, welcome:");
            String TempPrintValue = String.valueOf(MyView.MyUser);
            Vparam.PrintContent(TempPrintValue);
            return false;
        } else {
            Vparam.PrintContent("Your password doesn't match to the second input, please ensure you have correctly wrote your password");
            return true;
        }
    }


    public boolean SearchForArticles(MenuItem param, ConsoleView Vparam) {
        if ((MyView.MyUser.getUserID()) == 0) {
            Vparam.PrintContent("You can't access this feature without the right level of permissions, please Log In.");
            return true;
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
                ArrayList<Article> ListofDraftArticles;
                try {
                    ListofDraftArticles = MyModel.ListArticles(param.Xtra.ArticleModifyType, MyView.MyUser.getUserID());
                } catch (Exception e) {
                    Vparam.PrintContent(String.valueOf(e));
                    return true;
                }
                for (int i = 0; i < ListofDraftArticles.size(); i++) {
                    Article element = ListofDraftArticles.get(i);
                    MenuMap.put(100 + i, new MenuItem(1, "1" + i, element.getArticleTitle(), "SaveArticleID", 12,
                            new ExtraMIdata(element.getArticleID()))
                    );
                }
            } else if (param.Xtra.ArticleListParentID == 2) {
                ArrayList<Article> ListofRejectedArticles;
                try {
                    ListofRejectedArticles = MyModel.ListArticles(param.Xtra.ArticleModifyType, MyView.MyUser.getUserID());
                } catch (Exception e) {
                    Vparam.PrintContent(String.valueOf(e));
                    return true;
                }
                for (int i = 0; i < ListofRejectedArticles.size(); i++) {
                    Article element = ListofRejectedArticles.get(i);
                    MenuMap.put(100 + i, new MenuItem(2, "1" + i, element.getArticleTitle(), "SaveArticleID", 22,
                            new ExtraMIdata(element.getArticleID()))
                    );
                }
            } else if (param.Xtra.ArticleListParentID == 6) {
                if (!(MyView.MyUser.getAccessLevel().equals("Super Admin"))) {
                    Vparam.PrintContent("You can't access this feature without being a Super Admin");
                    return true;
                } else {
                    ArrayList<Article> ListofPendingArticles;
                    try {
                        ListofPendingArticles = MyModel.ListPendingArticles(param.Xtra.ArticleModifyType);
                    } catch (Exception e) {
                        Vparam.PrintContent(String.valueOf(e));
                        return true;
                    }
                    for (int i = 0; i < ListofPendingArticles.size(); i++) {
                        Article element = ListofPendingArticles.get(i);
                        MenuMap.put(100 + i, new MenuItem(6, "1" + i, element.getArticleTitle(), "SaveArticleID", 62,
                        new ExtraMIdata(element.getArticleID()))
                        );
                    }
                }
            }
            return false;
        }
    }

    public boolean SaveArticleID(MenuItem param, ConsoleView Vparam) {
        Vparam.CWdata.CWArticleID = param.Xtra.ArticleID;
        return false;
    }

    public boolean LoadSelectedArticle(MenuItem param, ConsoleView Vparam) {
        Article Loaded = new Article();
        try {
            Loaded = MyModel.LoadArticle(Vparam.CWdata.CWArticleID);
        } catch (Exception e) {
            Vparam.PrintContent(String.valueOf(e));
            return true;
        }
        String TempPrintValue = String.valueOf(Loaded);
        Vparam.PrintContent(TempPrintValue);
        return false;
    }

    public boolean ArticleModification(MenuItem param, ConsoleView Vparam) {
        Article CurrArticle = new Article();
        try {
            CurrArticle = MyModel.LoadArticle(Vparam.CWdata.CWArticleID);
        } catch (Exception e) {
            Vparam.PrintContent(String.valueOf(e));
            return true;
        }
        String OutputText = "Please enter the: " + param.Text;
        if (param.Xtra.ArticleModifyType == "ArticleTitle") {
            CurrArticle.setArticleTitle(Vparam.StringInput(OutputText, CurrArticle.getArticleTitle()));
        } else if (param.Xtra.ArticleModifyType == "ArticleContent") {
            CurrArticle.setArticleContent(Vparam.StringInput(OutputText, CurrArticle.getArticleContent()));
        }else if (param.Xtra.ArticleModifyType == "ArticleVideoURL") {
            CurrArticle.setArticleVideoURL(Vparam.StringInput(OutputText, CurrArticle.getArticleVideoURL()));
        } else if (param.Xtra.ArticleModifyType == "ArticleImageURL") {
            CurrArticle.setArticleImageURL(Vparam.StringInput(OutputText, CurrArticle.getArticleImageURL()));
        }else if (param.Xtra.ArticleModifyType == "Approve") {
            CurrArticle.setArticleStatus("Approved");
            CurrArticle.setRejectionReason(null);
        }else if (param.Xtra.ArticleModifyType == "Reject") {
            CurrArticle.setArticleStatus("Rejected");
            String OutputTextforRejReason = "Please enter the rejection reason";
            CurrArticle.setRejectionReason(Vparam.StringInput(OutputTextforRejReason));
        }
        try {
            MyModel.ModifyArticle(CurrArticle);
        } catch (Exception e) {
            Vparam.PrintContent(String.valueOf(e));
            return true;
        }
        return false;
    }

    public boolean ArticleTOpending(MenuItem param, ConsoleView Vparam) {
        Article CurrArticle = new Article();
        try {
            CurrArticle = MyModel.LoadArticle(Vparam.CWdata.CWArticleID);
        } catch (Exception e) {
            Vparam.PrintContent(String.valueOf(e));
            return true;
        }
//        System.out.println("Sending Article for approval...");
        if (param.Xtra.ArticleModifyType.equals("RejectReason")) {
            CurrArticle.setRejectionReason(null);
        }
        if (param.Xtra.ArticleModifyType.equals("Pending")) {
            CurrArticle.setArticleStatus("Pending");
        }
        try {
            MyModel.ModifyArticle(CurrArticle);
        } catch (Exception e) {
            Vparam.PrintContent(String.valueOf(e));
            return true;
        }
        return false;
    }

    public boolean UserVerification(MenuItem param, ConsoleView Vparam) {
        if (MyView.MyUser.getUserID() == 0) {
            Vparam.PrintContent("You can't access this feature without the right level of permissions, please Log In.");
            return true;
        }
        return false;
    }

    public boolean ArticleCreation(MenuItem param, ConsoleView Vparam) {
        Article New_Article = Vparam.CurrentArticle;
        New_Article.setUserIDofAuthor(MyView.MyUser.getUserID());
        String OutputText = "Please enter the: " + param.Text;
        if (param.Xtra.ArticleModifyType.equals("ArticleTitle")) {
            New_Article.setArticleTitle(Vparam.StringInput(OutputText, New_Article.getArticleTitle()));
        } else if (param.Xtra.ArticleModifyType.equals("ArticleContent")) {
            New_Article.setArticleContent(Vparam.StringInput(OutputText, New_Article.getArticleContent()));
        } else if (param.Xtra.ArticleModifyType.equals("ArticleVideoURL")) {
            New_Article.setArticleVideoURL(Vparam.StringInput(OutputText, New_Article.getArticleVideoURL()));
        } else if (param.Xtra.ArticleModifyType.equals("ArticleImageURL")) {
            New_Article.setArticleImageURL(Vparam.StringInput(OutputText, New_Article.getArticleImageURL()));
        }
        int ArticleID =0;
        if ((New_Article.getArticleTitle()) != null && (New_Article.getArticleContent()) != null && param.Xtra.ArticleModifyType.equals("CreateArticle")) {
            try {
                ArticleID = MyModel.CreateArticle(New_Article);
            } catch (Exception e) {
                Vparam.PrintContent(String.valueOf(e));
                return true;
            }
            Vparam.PrintContent("Successful Article Creation!");
        }
        Vparam.CWdata.CWArticleID = ArticleID;

        return false;
    }


    public boolean DisplayAppMetrics(MenuItem param, ConsoleView Vparam) {
        int TotalLikeCount;
        int TotalViewCount;
        try {
            TotalLikeCount = MyModel.CountTotalLikes();
            TotalViewCount = MyModel.CountTotalViews();
        } catch (Exception e) {
            Vparam.PrintContent(String.valueOf(e));
            return true;
        }
        Vparam.PrintContent("Total number of likes across all articles is: " + TotalLikeCount);
        Vparam.PrintContent("Total number of views across all articles is: " + TotalViewCount);
        return false;
    }

    public boolean ListIndividualizedAppMetrics(MenuItem param, ConsoleView Vparam) {
        ArrayList<Article> ListofApprovedArticlesStats;
        try {
            ListofApprovedArticlesStats = MyModel.ListApprovedArticlesStatistic("Approved");
        } catch (Exception e) {
            Vparam.PrintContent(String.valueOf(e));
            return true;
        }
        for (int i = 0; i < ListofApprovedArticlesStats.size(); i++) {
            Article element = ListofApprovedArticlesStats.get(i);
            String TempPrintValue = String.valueOf(element);
            Vparam.PrintContent(TempPrintValue);
        }
        return false;
    }

    public boolean DisplaySelectedArticle(MenuItem param, ConsoleView Vparam) {
        Article Loaded = new Article();
        try {
            Loaded = MyModel.LoadArticle(Vparam.CWdata.CWArticleID);
            Loaded.setViewCount(Loaded.getViewCount()+1);
            MyModel.ModifyViewCount(Loaded);
        } catch (Exception e) {
            Vparam.PrintContent(String.valueOf(e));
            return true;
        }
        String TempPrintValue = String.valueOf(Loaded);
        Vparam.PrintContent(TempPrintValue);
        return false;
    }

}
