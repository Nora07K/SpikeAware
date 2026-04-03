import java.sql.*;
import java.util.ArrayList;


public class SpikePersistantRDB {

    Connection connection = null;

    public SpikePersistantRDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/persistantstorageuni",
                    "root",
                    "Vollyball_07"
            );
        } catch (Exception e) {
            System.out.println(e);
        }

    }


    public void CreateUser(User Who) {

        System.out.println("Your Username is:  " + Who.Name);
        System.out.println("Your Password is: " + Who.Password);
        System.out.println("Your access level is: " + Who.AccessLevel);

        try {
            String query = "INSERT INTO User (Username, Password, UserID, AccessLevel) " +
            "VALUES (?, ?, ?, ?);";
            PreparedStatement myStmt = connection.prepareStatement(query);
            myStmt.setString(1, Who.Name);
            myStmt.setString(2, Who.Password);
            myStmt.setInt(3, Who.UserID);
            myStmt.setString(4, Who.AccessLevel);
            myStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public ArrayList<User> ListUserDetails() {
        ArrayList<User> ReturnList = new ArrayList<>();

        try {
            String querytolist = "Select * From User;";
            PreparedStatement myStmt = connection.prepareStatement(querytolist);
            ResultSet rs = myStmt.executeQuery(querytolist);
            while (rs.next()) {
                User PotentialUser = new User();
                PotentialUser.Name = rs.getString("Username");
                PotentialUser.Password = rs.getString("Password");
                PotentialUser.UserID = rs.getInt("UserID");
                PotentialUser.AccessLevel = rs.getString("AccessLevel");
                ReturnList.add(PotentialUser);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return ReturnList;
    }




    public Article LoadArticle(int ArticleID) {

        Article MyArticle = new Article();

        String query = "SELECT * FROM Article Where ArticleID = " + ArticleID;
        try (Statement articleretrival = connection.createStatement()) {
            ResultSet rs = articleretrival.executeQuery(query);
            if (rs.next()) {
                MyArticle.ArticleID = rs.getInt("ArticleID");
                MyArticle.CreationDate = rs.getDate("CreationDate");
                MyArticle.ArticleTitle = rs.getString("ArticleTitle");
                MyArticle.ArticleContent = rs.getString("ArticleContent");
                MyArticle.UserIDofAuthor = rs.getInt("UserIDofAuthor");
                MyArticle.ArticleStatus = rs.getString("ArticleStatus");
                MyArticle.ArticleVideoURL = rs.getString("ArticleVideoURL");
                MyArticle.ArticleImageURL = rs.getString("ArticleImageURL");
                MyArticle.ViewCount = rs.getInt("ViewCount");
                MyArticle.LikeCount = rs.getInt("LikeCount");
                MyArticle.DislikeCount = rs.getInt("DislikeCount");
                MyArticle.RejectionReason = rs.getString("RejectionReason");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Please check your code as an error has occured");
            System.exit(0);
        }
        return MyArticle;
    }

    public void CreateArticle(Article saveArticle) {

        try {
            String query = "INSERT INTO Article (ArticleTitle, ArticleContent, UserIDofAuthor, ArticleStatus, ArticleVideoURL, ArticleImageURL) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement myStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            myStmt.setString(1, saveArticle.ArticleTitle);
            myStmt.setString(2, saveArticle.ArticleContent);
            myStmt.setInt(3, saveArticle.UserIDofAuthor);
            myStmt.setString(4, saveArticle.ArticleStatus);
            myStmt.setString(5, saveArticle.ArticleVideoURL);
            myStmt.setString(6, saveArticle.ArticleImageURL);
            myStmt.executeUpdate();
            ResultSet rs = myStmt.getGeneratedKeys();
            if (rs.next()) {
                saveArticle.ArticleID = rs.getInt(1);
            } else {
                System.out.println("Please check your code as an error has occured where the Last ID of an article is unreachable");
                System.exit(0);
            }
        } catch (SQLException e) {
            System.out.println("!3");
            e.printStackTrace();
        }

    }

    public void ModifyArticle(Article param) {
        try {
            String querytoupdate = "Update Article Set ArticleTitle = ?, ArticleContent = ?, ArticleStatus = ?, ArticleVideoURL = ?, ArticleImageURL = ?, RejectionReason = ? " +
                    "Where ArticleID = " + param.ArticleID + ";";
            PreparedStatement myStmt = connection.prepareStatement(querytoupdate);
            myStmt.setString(1, param.ArticleTitle);
            myStmt.setString(2, param.ArticleContent);
            myStmt.setString(3, param.ArticleStatus);
            myStmt.setString(4, param.ArticleVideoURL);
            myStmt.setString(5, param.ArticleImageURL);
            myStmt.setString(6, param.RejectionReason);
            myStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Article> ListArticles(String ArticleStatus, int UserID) {
        ArrayList<Article> ReturnList = new ArrayList<>();

    try {
        String querytolist = "Select * From Article" +
        " Where ArticleStatus = ? AND UserIDofAuthor = ? ;";
        PreparedStatement myStmt = connection.prepareStatement(querytolist);
        myStmt.setString(1, ArticleStatus);
        myStmt.setInt(2, UserID);
        ResultSet rs = myStmt.executeQuery();
        while (rs.next()) {
            Article MyArticle = new Article();
            MyArticle.ArticleID = rs.getInt("ArticleID");
            MyArticle.CreationDate = rs.getDate("CreationDate");
            MyArticle.ArticleTitle = rs.getString("ArticleTitle");
            MyArticle.ArticleContent = rs.getString("ArticleContent");
            MyArticle.UserIDofAuthor = rs.getInt("UserIDofAuthor");
            MyArticle.ArticleStatus = rs.getString("ArticleStatus");
            MyArticle.ArticleVideoURL = rs.getString("ArticleVideoURL");
            MyArticle.ArticleImageURL = rs.getString("ArticleImageURL");
            MyArticle.ViewCount = rs.getInt("ViewCount");
            MyArticle.LikeCount = rs.getInt("LikeCount");
            MyArticle.DislikeCount = rs.getInt("DislikeCount");
            MyArticle.RejectionReason = rs.getString("RejectionReason");
            ReturnList.add(MyArticle);
        }
    } catch (SQLException e){
        throw new RuntimeException(e);
    }
    return ReturnList;
    }


    public ArrayList<Article> ListPendingArticles(String ArticleStatus) {
        ArrayList<Article> ReturnList = new ArrayList<>();

        try {
            String querytolistpending = "Select * From Article" +
                    " Where ArticleStatus = ? ;";
            PreparedStatement myStmt = connection.prepareStatement(querytolistpending);
            myStmt.setString(1, ArticleStatus);
            ResultSet rs = myStmt.executeQuery();
            while (rs.next()) {
                Article MyPendingArticle = new Article();
                MyPendingArticle.ArticleID = rs.getInt("ArticleID");
                MyPendingArticle.CreationDate = rs.getDate("CreationDate");
                MyPendingArticle.ArticleTitle = rs.getString("ArticleTitle");
                MyPendingArticle.ArticleContent = rs.getString("ArticleContent");
                MyPendingArticle.UserIDofAuthor = rs.getInt("UserIDofAuthor");
                MyPendingArticle.ArticleStatus = rs.getString("ArticleStatus");
                MyPendingArticle.ArticleVideoURL = rs.getString("ArticleVideoURL");
                MyPendingArticle.ArticleImageURL = rs.getString("ArticleImageURL");
                MyPendingArticle.ViewCount = rs.getInt("ViewCount");
                MyPendingArticle.LikeCount = rs.getInt("LikeCount");
                MyPendingArticle.DislikeCount = rs.getInt("DislikeCount");
                MyPendingArticle.RejectionReason = rs.getString("RejectionReason");
                ReturnList.add(MyPendingArticle);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return ReturnList;
    }



}