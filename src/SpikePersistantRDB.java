import java.sql.*;
import java.util.ArrayList;


public class SpikePersistantRDB {

    Connection connection = null;

    public SpikePersistantRDB(String db_host, String db_password, String db_user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    db_host,
                    db_user,
                    db_password
            );
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public ArrayList<Article> ListArticlesForSearch(String UserInputSearchBar) {
        ArrayList<Article> ReturnListofSearched = new ArrayList<>();
        try {
            String querytolist = "Select * FROM Article where ArticleStatus = ? And (ArticleTitle like ? or ArticleContent LIKE ?)";
            PreparedStatement myStmt = connection.prepareStatement(querytolist);
            myStmt.setString(1, "Approved");
            myStmt.setString(2, UserInputSearchBar);
            myStmt.setString(3, UserInputSearchBar);
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
                MyArticle.RejectionReason = rs.getString("RejectionReason");                MyArticle.ArticleContent = rs.getString("ArticleContent");
                ReturnListofSearched.add(MyArticle);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return ReturnListofSearched;
    }

    public Integer CountLikes(int IDofArticle) {
        int NumbOfCount;
        try {
            String query = "SELECT COUNT(ArticleID) FROM likes WHERE ArticleID = ?;";
            PreparedStatement myStmt = connection.prepareStatement(query);
            myStmt.setInt(1, IDofArticle);
            ResultSet rs = myStmt.executeQuery();
            NumbOfCount = 0;
            if (rs.next()) {
                NumbOfCount = rs.getInt("COUNT(ArticleID)");
                return NumbOfCount;
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User RegisterNewUser(User Who) {
        try {
            String query = "INSERT INTO User (Username, Password, AccessLevel) " +
            "VALUES (?, sha2(?, 0), ?);";
            PreparedStatement myStmt = connection.prepareStatement(query);
            myStmt.setString(1, Who.Name);
            myStmt.setString(2, Who.Password);
            myStmt.setString(3, Who.AccessLevel);
            myStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Who;
    }

    public void GivingLike(int IDofLiker, int IDofArticle) {
        boolean IsDuplicate = false;
        try {
            String query = "INSERT INTO likes (UserID, ArticleID) " +
                    "VALUES (?, ?);";
            PreparedStatement myStmt = connection.prepareStatement(query);
            myStmt.setInt(1, IDofLiker);
            myStmt.setInt(2, IDofArticle);
            myStmt.executeUpdate();
        } catch (RuntimeException | SQLException e) {
            String ErrorMess = e.getMessage();
            if (ErrorMess.contains("Duplicate entry")) {
                IsDuplicate = true;
            }
        }
        if (IsDuplicate) {
            return;
        }
        try {
            String querytoupdate = "Update Article Set LikeCount = LikeCount + 1 " +
                    "Where ArticleID = " + IDofArticle + ";";
            PreparedStatement myStmt = connection.prepareStatement(querytoupdate);
            myStmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



    public User LogIn(String RDB_Username, String RDB_Password) {
        try {
            String query = "Select * From User Where Username = ? And  Password = sha2(?, 0)";
            PreparedStatement myStmt = connection.prepareStatement(query);
            myStmt.setString(1, RDB_Username);
            myStmt.setString(2, RDB_Password);
            ResultSet rs = myStmt.executeQuery();
            if (rs.next()) {
                User SignedInUser = new User();
                SignedInUser.Name = rs.getString("Username");
                SignedInUser.UserID = rs.getInt("UserID");
                SignedInUser.AccessLevel = rs.getString("AccessLevel");
                return SignedInUser;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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



    public void ModifyViewCount(Article param) {
        try {
            String querytoupdate = "Update Article Set ViewCount = ?" +
                    "Where ArticleID = " + param.ArticleID + ";";
            PreparedStatement myStmt = connection.prepareStatement(querytoupdate);
            myStmt.setString(1, String.valueOf(param.ViewCount));
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

    public Integer CountTotalLikes() {
        int NumbOfCount;
        try {
            String query = "SELECT COUNT(ArticleID) FROM likes;";
            PreparedStatement myStmt = connection.prepareStatement(query);
            ResultSet rs = myStmt.executeQuery();
            NumbOfCount = 0;
            if (rs.next()) {
                NumbOfCount = rs.getInt("COUNT(ArticleID)");
                return NumbOfCount;
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer CountTotalViews() {
        int NumbOfCount;
        try {
            String query = "SELECT SUM(ViewCount) FROM article;";
            PreparedStatement myStmt = connection.prepareStatement(query);
            ResultSet rs = myStmt.executeQuery();
            NumbOfCount = 0;
            if (rs.next()) {
                NumbOfCount = rs.getInt("SUM(ViewCount)");
                return NumbOfCount;
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}