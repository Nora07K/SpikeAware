import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;


public class Main {

    public static void main(String[] args) {

        SpikePersistantRDB MyBackend = new SpikePersistantRDB();


/*        //Register
        try {User MyUser = new User();
            Scanner UserRegister= new Scanner(System.in);
            System.out.println("1. Register");
            System.out.println("2. Home");
            int MenuDecision = UserRegister.nextInt();
            UserRegister.nextLine();
            if (MenuDecision == 1) {
                System.out.print("Please enter your Username: ");
                MyUser.Name = UserRegister.nextLine();
                System.out.print("Please enter your Password: ");
                MyUser.Password = UserRegister.nextLine();
                MyUser.AccessLevel = "User";
                MyUser.UserID = 56;
                MyBackend.CreateUser(MyUser);
            } else if (MenuDecision == 2) {
                //Code to go back to home menu
                System.exit(0);
            }
            else {
                System.out.println(MenuDecision + " is not a valid input, please try again");
                //Return to the start of decision between Register and Home
            }

        } catch (Exception e) {
            System.out.println("An error has occured: "+ e + ". Please check your inputs.");
            System.exit(0);
        }
*/


/*     //Sign In
        User LoggedUser = new User();
        boolean LogInValid = false;
        Scanner UserSignIn= new Scanner(System.in);
        System.out.println("1. Sign In");
        System.out.println("2. Home");
        int MenusDecision = UserSignIn.nextInt();
        UserSignIn.nextLine();
        if (MenusDecision == 1) {
            System.out.println("Please enter your Username:");
            LoggedUser.Name = UserSignIn.nextLine();
            System.out.println("Please enter your Password:");
            LoggedUser.Password = UserSignIn.nextLine();

            ArrayList<User> ListUserDetails = MyBackend.ListUserDetails();
            for (int i = 0; i < ListUserDetails.size(); i++) {
                User element = ListUserDetails.get(i);
                if (element.Name.equals(LoggedUser.Name) && element.Password.equals(LoggedUser.Password)) {
                    LogInValid = true;
                    System.out.println("Log in details match. Welcome: " + LoggedUser.Name);
                    //Code to go back to home menu
                }
            }
            if (LogInValid == false) {
                System.out.println("invalid log in credentials");
                //Return to the start of decision between Sign In and Home
            }
        } else if (MenusDecision == 2) {
        //Code to go back to home menu
        System.exit(0);
        } else {
        System.out.println(MenusDecision + " is not a valid input, please try again");
        //Return to the start of decision between Sign In and Home
        }
*/


        //My Draft Articles
        Scanner ArticleReadID= new Scanner(System.in);
        try {
            System.out.println("1. View My Draft Articles");
            System.out.println("2. Home");
            int MenusDecisions = ArticleReadID.nextInt();
            ArticleReadID.nextLine();
            if (MenusDecisions == 1) {
                ArrayList<Article> ListofDraftArticles = MyBackend.ListArticles("Draft", 10122);
                for (int i = 0; i < ListofDraftArticles.size(); i++) {
                    Article element = ListofDraftArticles.get(i);
                    element.Print();
                }
                System.out.println("Enter Article ID you want to read:");
                int ArticleIDtoLoad = ArticleReadID.nextInt();
                ArticleReadID.nextLine();
                Article ArticlefromDB = MyBackend.LoadArticle(ArticleIDtoLoad);
                if (ArticlefromDB == null) {
                    System.out.println("Error, Article not found. Please try again");
                    // code to return back to view draft article menu
                    System.exit(0);
                } else {
                    System.out.println("Do you want to edit your article?");
                    String ArticleEditDecisionUserSide = ArticleReadID.nextLine();
                    if (ArticleEditDecisionUserSide.equals("Yes") || ArticleEditDecisionUserSide.equals("yes")) {
                        System.out.println("Do you want to edit the Articles Title?");
                        String TitleEdit = ArticleReadID.nextLine();
                        if (TitleEdit.equals("Yes") || TitleEdit.equals("yes")) {
                            System.out.println("Edited Article Title:");
                            ArticlefromDB.ArticleTitle = ArticleReadID.nextLine();
                        }
                        System.out.println("Do you want to edit the Articles Content?");
                        String ContentEdit = ArticleReadID.nextLine();
                        if (ContentEdit.equals("Yes") || ContentEdit.equals("yes")) {
                            System.out.println("Edited Article Content:");
                            ArticlefromDB.ArticleContent = ArticleReadID.nextLine();
                        }
                        System.out.println("Do you want to edit the Articles Video URLs?");
                        String VidURLEdit = ArticleReadID.nextLine();
                        if (VidURLEdit.equals("Yes") || VidURLEdit.equals("yes")) {
                            System.out.println("Edited Article Video URL:");
                            ArticlefromDB.ArticleVideoURL = ArticleReadID.nextLine();
                        }
                        System.out.println("Do you want to edit the Articles Image URLs?");
                        String ImURLEdit = ArticleReadID.nextLine();
                        if (ImURLEdit.equals("Yes") || ImURLEdit.equals("yes")) {
                            System.out.println("Edited Article Image URLs:");
                            ArticlefromDB.ArticleImageURL = ArticleReadID.nextLine();
                        }
                    }
                    System.out.println("Do you want to submit your article for approval?");
                    String ArticleDecisionUserSide = ArticleReadID.nextLine();
                    if (ArticleDecisionUserSide.equals("Yes") || ArticleDecisionUserSide.equals("yes")) {
                        ArticlefromDB.ArticleStatus = "Pending";
                    }
                    MyBackend.ModifyArticle(ArticlefromDB);
                    //code to go back to main menu
                }
            } else if (MenusDecisions == 2) {
            //Code to go back to main menu
            System.exit(0);
            } else {
                System.out.println(MenusDecisions + " is not a valid input, please try again");
                //Return to the start of decision between View draft articles and Home
            }
        } catch (InputMismatchException java_error) {
            System.out.println("An error has occured. Please check your inputs");
            //code to go back to the view my draft articles and home menu
            System.exit(0);
        }




/*        //My Rejected Articles
        Scanner ArticleReadRejected= new Scanner(System.in);
        try {
            System.out.println("1. View My Rejected Articles");
            System.out.println("2. Home");
            int TheMenuDecision = ArticleReadRejected.nextInt();
            ArticleReadRejected.nextLine();
            if (TheMenuDecision == 1) {
                ArrayList<Article> ListofrejectedArticles = MyBackend.ListArticles("Rejected", 10122);
                for (int i = 0; i < ListofrejectedArticles.size(); i++) {
                    Article element = ListofrejectedArticles.get(i);
                    element.Print();
                }
                System.out.println("Enter Article ID you want to read:");
                int RejArticleIDtoLoad = ArticleReadRejected.nextInt();
                ArticleReadRejected.nextLine();
                Article RejectedArticlefromDB = MyBackend.LoadArticle(RejArticleIDtoLoad);
                if (RejectedArticlefromDB == null) {
                    System.out.println("Error, Article not found. Please try again");
                    System.exit(0);
                } else {
                    System.out.println("Do you want to edit your article? Editing the Article will send it back to your drafts");
                    RejectedArticlefromDB.ArticleStatus = "Draft";
                    RejectedArticlefromDB.RejectionReason = "";
                    String ArticleEditDecisionUserSide = ArticleReadRejected.nextLine();
                    if (ArticleEditDecisionUserSide.equals("Yes") || ArticleEditDecisionUserSide.equals("yes")) {
                        System.out.println("Do you want to edit the Articles Title?");
                        String TitleEdit = ArticleReadRejected.nextLine();
                        if (TitleEdit.equals("Yes") || TitleEdit.equals("yes")) {
                            System.out.println("Edited Article Title:");
                            RejectedArticlefromDB.ArticleTitle = ArticleReadRejected.nextLine();
                        }
                        System.out.println("Do you want to edit the Articles Content?");
                        String ContentEdit = ArticleReadRejected.nextLine();
                        if (ContentEdit.equals("Yes") || ContentEdit.equals("yes")) {
                            System.out.println("Edited Article Content:");
                            RejectedArticlefromDB.ArticleContent = ArticleReadRejected.nextLine();
                        }
                        System.out.println("Do you want to edit the Articles Video URLs?");
                        String VidURLEdit = ArticleReadRejected.nextLine();
                        if (VidURLEdit.equals("Yes") || VidURLEdit.equals("yes")) {
                            System.out.println("Edited Article Video URL:");
                            RejectedArticlefromDB.ArticleVideoURL = ArticleReadRejected.nextLine();
                        }
                        System.out.println("Do you want to edit the Articles Image URLs?");
                        String ImURLEdit = ArticleReadRejected.nextLine();
                        if (ImURLEdit.equals("Yes") || ImURLEdit.equals("yes")) {
                            System.out.println("Edited Article Image URLs:");
                            RejectedArticlefromDB.ArticleImageURL = ArticleReadRejected.nextLine();
                        }
                        System.out.println("Do you want to resubmit your article for approval?");
                        String ArticleDecisionUserSide = ArticleReadRejected.nextLine();
                        if (ArticleDecisionUserSide.equals("Yes") || ArticleDecisionUserSide.equals("yes")) {
                            RejectedArticlefromDB.ArticleStatus = "Pending";
                        }
                        MyBackend.ModifyArticle(RejectedArticlefromDB);
                    }
                }
            } else if (TheMenuDecision == 2) {
                //Code to go back to main menu
                System.exit(0);
            } else {
                System.out.println(TheMenuDecision + " is not a valid input, please try again");
                //Return to the start of decision between View draft articles and Home
            }
        } catch (InputMismatchException java_error) {
            System.out.println("An error has occured. Please check your inputs");
            //Return to the start of decision between View draft articles and Home
            System.exit(0);
        }
*/

/*        //Approve Pending Articles
        Scanner ArticleReadPending= new Scanner(System.in);
        try {
            System.out.println("1. Approve Pending Articles");
            System.out.println("2. Home");
            int TheMenusDecision = ArticleReadPending.nextInt();
            ArticleReadPending.nextLine();
            if (TheMenusDecision == 1) {
                ArrayList<Article> ListofpendingArticles = MyBackend.ListPendingArticles("Pending");
                for (int i = 0; i < ListofpendingArticles.size(); i++) {
                    Article element = ListofpendingArticles.get(i);
                    element.Print();
                }
                System.out.println("Enter Article ID you want to view:");
                int PenArticleIDtoLoad = ArticleReadPending.nextInt();
                ArticleReadPending.nextLine();
                Article PendingArticlefromDB = MyBackend.LoadArticle(PenArticleIDtoLoad);
                if (PendingArticlefromDB == null) {
                    System.out.println("Error, Article not found. Please try again");
                    System.exit(0);
                } else {
                    System.out.println("Do you want to approve the article?");
                    String ArticleApproveDecisionAdminSide = ArticleReadPending.nextLine();
                    if (ArticleApproveDecisionAdminSide.equals("Yes") || ArticleApproveDecisionAdminSide.equals("yes")) {
                        PendingArticlefromDB.ArticleStatus = "Approved";
                    } else if (ArticleApproveDecisionAdminSide.equals("No") || ArticleApproveDecisionAdminSide.equals("no")) {
                        PendingArticlefromDB.ArticleStatus = "Rejected";
                        System.out.println("Why did you reject this article?");
                        PendingArticlefromDB.RejectionReason = ArticleReadPending.nextLine();
                    }
                    MyBackend.ModifyArticle(PendingArticlefromDB);
                    //Code to go back to main menu
                }
            } else if (TheMenusDecision == 2) {
                //Code to go back to main menu
                System.exit(0);
            } else {
                System.out.println(TheMenusDecision + " is not a valid input, please try again");
                //Return to the start of decision between View pending articles and Home
            }
        } catch (InputMismatchException java_error) {
            System.out.println("An error has occured. Please check your inputs");
            System.exit(0);
            //Return to the start of decision between View pending articles and Home
        }
*/


/*        //Create Article
        Article NewArticleCreate = new Article();
        Scanner ArticleMake= new Scanner(System.in);
        try {
            System.out.println("1. Create Article");
            System.out.println("2. Home");
            int TheMenusDecisions = ArticleMake.nextInt();
            ArticleMake.nextLine();
            if (TheMenusDecisions == 1) {
                System.out.println("You are creating a new article");
                System.out.println("Article Title: ");
                NewArticleCreate.ArticleTitle = ArticleMake.nextLine();
                System.out.println("Article Content: ");
                NewArticleCreate.ArticleContent = ArticleMake.nextLine();
                NewArticleCreate.UserIDofAuthor = 93;
                System.out.println("Article Video URL: ");
                NewArticleCreate.ArticleVideoURL = ArticleMake.nextLine();
                System.out.println("Article Image URL: ");
                NewArticleCreate.ArticleImageURL = ArticleMake.nextLine();
                MyBackend.CreateArticle(NewArticleCreate);
                System.out.println("Your newly created articles ID is: " + NewArticleCreate.ArticleID);
            } else if (TheMenusDecisions == 2) {
                //Code to go back to main menu
                System.exit(0);
            } else {
                System.out.println(TheMenusDecisions + " is not a valid input, please try again");
                //Return to the start of decision between create article and Home
            }
        } catch (InputMismatchException java_error) {
            System.out.println("An error has occured. Please check your inputs");
            System.exit(0);
            //Return to the start of decision between create article and Home
        }
*/



























 /*       Article UpdateArticle = MyBackend.LoadArticle(12567505);
        if (UpdateArticle == null) {
            System.out.println("ERROR!!!");
        }
        else {
            UpdateArticle.Print();
        }
        UpdateArticle.ArticleTitle = "QQQQQzzz";
        UpdateArticle.ArticleStatus = "Rejected";
        UpdateArticle.RejectionReason = "Not enough content";
        MyBackend.ModifyArticle(UpdateArticle);



    */


    /*    ArrayList<Article> ListofArticles = MyBackend.ListArticles("Draft", 10122);

        for (int i = 0; i < ListofArticles.size(); i++) {
            Article element = ListofArticles.get(i);
            element.Print();
        }
    */
/*        //Read One Article Use for View Pending Articles, Rejected Articles, Approve Articles
        Scanner ArticleReadID= new Scanner(System.in);
        try {
            System.out.println("Enter Article ID you want to read:");
            int ArticleIDtoLoad = ArticleReadID.nextInt();
            Article ArticlefromDB = MyBackend.LoadArticle(ArticleIDtoLoad);
            if (ArticlefromDB == null) {
                System.out.println("Error, Article not found. Please try again");
            } else {
                ArticlefromDB.Print();
            }
        } catch (InputMismatchException java_error) {
            System.out.println("An error has occured.Please check your inputed Article ID");
            System.exit(0);
        }
*/

/*
        User MyUser = new User();
        //MyUser.Print();

*/

    }
}