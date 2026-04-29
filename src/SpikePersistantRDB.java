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

    public ArrayList<Article> ListArticlesForSearch(String UserInputSearchBar) throws SQLException {
        ArrayList<Article> ReturnListofSearched = new ArrayList<>();
        String querytolist = "Select * FROM Article where ArticleStatus = ? And (ArticleTitle like ? or ArticleContent LIKE ?)";
        PreparedStatement myStmt = connection.prepareStatement(querytolist);
        myStmt.setString(1, "Approved");
        myStmt.setString(2, UserInputSearchBar);
        myStmt.setString(3, UserInputSearchBar);
        ResultSet rs = myStmt.executeQuery();
        while (rs.next()) {
            Article MyArticle = new Article();
            MyArticle.setArticleID(rs.getInt("ArticleID"));
            MyArticle.setCreationDate(rs.getDate("CreationDate"));
            MyArticle.setArticleTitle(rs.getString("ArticleTitle"));
            MyArticle.setArticleContent(rs.getString("ArticleContent"));
            MyArticle.setUserIDofAuthor(rs.getInt("UserIDofAuthor"));
            MyArticle.setArticleStatus(rs.getString("ArticleStatus"));
            MyArticle.setArticleVideoURL(rs.getString("ArticleVideoURL"));
            MyArticle.setArticleImageURL(rs.getString("ArticleImageURL"));
            MyArticle.setViewCount(rs.getInt("ViewCount"));
            MyArticle.setLikeCount(rs.getInt("LikeCount"));
            MyArticle.setRejectionReason(rs.getString("RejectionReason"));
            ReturnListofSearched.add(MyArticle);
        }
        return ReturnListofSearched;
    }

    public Integer CountLikes(int IDofArticle) throws SQLException {
        int NumbOfCount;
        String query = "SELECT COUNT(ArticleID) FROM likes WHERE ArticleID = ?;";
        PreparedStatement myStmt = connection.prepareStatement(query);
        myStmt.setInt(1, IDofArticle);
        ResultSet rs = myStmt.executeQuery();
        NumbOfCount = 0;
        if (rs.next()) {
            NumbOfCount = rs.getInt("COUNT(ArticleID)");
            return NumbOfCount;
        }
        return NumbOfCount;
    }

    public User RegisterNewUser(User Who) throws SQLException {
        String query = "INSERT INTO User (Username, Password, AccessLevel) " +
                "VALUES (?, sha2(?, 0), ?);";
        PreparedStatement myStmt = connection.prepareStatement(query);
        myStmt.setString(1, Who.getName());
        myStmt.setString(2, Who.getPassword());
        myStmt.setString(3, Who.getAccessLevel());
        myStmt.executeUpdate();
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


    public User LogIn(String RDB_Username, String RDB_Password) throws SQLException {
        String query = "Select * From User Where Username = ? And  Password = sha2(?, 0)";
        PreparedStatement myStmt = connection.prepareStatement(query);
        myStmt.setString(1, RDB_Username);
        myStmt.setString(2, RDB_Password);
        ResultSet rs = myStmt.executeQuery();
        if (rs.next()) {
            User SignedInUser = new User();
            SignedInUser.setName(rs.getString("Username"));
            SignedInUser.setUserID(rs.getInt("UserID"));
            SignedInUser.setAccessLevel(rs.getString("AccessLevel"));
            return SignedInUser;
        }
        return null;
    }


    public Article LoadArticle(int ArticleID) throws SQLException {
        Article MyArticle = new Article();
        String query = "SELECT * FROM Article Where ArticleID = " + ArticleID;
        Statement articleretrival = connection.createStatement();
        ResultSet rs = articleretrival.executeQuery(query);
        if (rs.next()) {
            MyArticle.setArticleID(rs.getInt("ArticleID"));
            MyArticle.setCreationDate(rs.getDate("CreationDate"));
            MyArticle.setArticleTitle(rs.getString("ArticleTitle"));
            MyArticle.setArticleContent(rs.getString("ArticleContent"));
            MyArticle.setUserIDofAuthor(rs.getInt("UserIDofAuthor"));
            MyArticle.setArticleStatus(rs.getString("ArticleStatus"));
            MyArticle.setArticleVideoURL(rs.getString("ArticleVideoURL"));
            MyArticle.setArticleImageURL(rs.getString("ArticleImageURL"));
            MyArticle.setViewCount(rs.getInt("ViewCount"));
            MyArticle.setLikeCount(rs.getInt("LikeCount"));
            MyArticle.setRejectionReason(rs.getString("RejectionReason"));
        }
        return MyArticle;
    }

    public int CreateArticle(Article saveArticle) throws SQLException {
        String query = "INSERT INTO Article (ArticleTitle, ArticleContent, UserIDofAuthor, ArticleStatus, ArticleVideoURL, ArticleImageURL) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement myStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        myStmt.setString(1, saveArticle.getArticleTitle());
        myStmt.setString(2, saveArticle.getArticleContent());
        myStmt.setInt(3, saveArticle.getUserIDofAuthor());
        myStmt.setString(4, saveArticle.getArticleStatus());
        myStmt.setString(5, saveArticle.getArticleVideoURL());
        myStmt.setString(6, saveArticle.getArticleImageURL());
        myStmt.executeUpdate();
        ResultSet rs = myStmt.getGeneratedKeys();
        if (rs.next()) {
            saveArticle.setArticleID(rs.getInt(1));
        }
        return saveArticle.getArticleID();
    }

    public void ModifyArticle(Article param) throws SQLException {
        String querytoupdate = "Update Article Set ArticleTitle = ?, ArticleContent = ?, ArticleStatus = ?, ArticleVideoURL = ?, ArticleImageURL = ?, RejectionReason = ? " +
                "Where ArticleID = " + param.getArticleID() + ";";
        PreparedStatement myStmt = connection.prepareStatement(querytoupdate);
        myStmt.setString(1, param.getArticleTitle());
        myStmt.setString(2, param.getArticleContent());
        myStmt.setString(3, param.getArticleStatus());
        myStmt.setString(4, param.getArticleVideoURL());
        myStmt.setString(5, param.getArticleImageURL());
        myStmt.setString(6, param.getRejectionReason());
        myStmt.executeUpdate();
    }


    public void ModifyViewCount(Article param) throws SQLException {
        String querytoupdate = "Update Article Set ViewCount = ?" +
                "Where ArticleID = " + param.getArticleID() + ";";
        PreparedStatement myStmt = connection.prepareStatement(querytoupdate);
        myStmt.setString(1, String.valueOf(param.getViewCount()));
        myStmt.executeUpdate();
    }

    public ArrayList<Article> ListArticles(String ArticleStatus, int UserID) throws SQLException {
        ArrayList<Article> ReturnList = new ArrayList<>();
        String querytolist = "Select * From Article" +
                " Where ArticleStatus = ? AND UserIDofAuthor = ? ;";
        PreparedStatement myStmt = connection.prepareStatement(querytolist);
        myStmt.setString(1, ArticleStatus);
        myStmt.setInt(2, UserID);
        ResultSet rs = myStmt.executeQuery();
        while (rs.next()) {
            Article MyArticle = new Article();
            MyArticle.setArticleID(rs.getInt("ArticleID"));
            MyArticle.setCreationDate(rs.getDate("CreationDate"));
            MyArticle.setArticleTitle(rs.getString("ArticleTitle"));
            MyArticle.setArticleContent(rs.getString("ArticleContent"));
            MyArticle.setUserIDofAuthor(rs.getInt("UserIDofAuthor"));
            MyArticle.setArticleStatus(rs.getString("ArticleStatus"));
            MyArticle.setArticleVideoURL(rs.getString("ArticleVideoURL"));
            MyArticle.setArticleImageURL(rs.getString("ArticleImageURL"));
            MyArticle.setViewCount(rs.getInt("ViewCount"));
            MyArticle.setLikeCount(rs.getInt("LikeCount"));
            MyArticle.setRejectionReason(rs.getString("RejectionReason"));
            ReturnList.add(MyArticle);
        }
        return ReturnList;
    }


    public ArrayList<Article> ListPendingArticles(String ArticleStatus) throws SQLException {
        ArrayList<Article> ReturnList = new ArrayList<>();
        String querytolistpending = "Select * From Article" +
                " Where ArticleStatus = ? ;";
        PreparedStatement myStmt = connection.prepareStatement(querytolistpending);
        myStmt.setString(1, ArticleStatus);
        ResultSet rs = myStmt.executeQuery();
        while (rs.next()) {
            Article MyPendingArticle = new Article();
            MyPendingArticle.setArticleID(rs.getInt("ArticleID"));
            MyPendingArticle.setCreationDate(rs.getDate("CreationDate"));
            MyPendingArticle.setArticleTitle(rs.getString("ArticleTitle"));
            MyPendingArticle.setArticleContent(rs.getString("ArticleContent"));
            MyPendingArticle.setUserIDofAuthor(rs.getInt("UserIDofAuthor"));
            MyPendingArticle.setArticleStatus(rs.getString("ArticleStatus"));
            MyPendingArticle.setArticleVideoURL(rs.getString("ArticleVideoURL"));
            MyPendingArticle.setArticleImageURL(rs.getString("ArticleImageURL"));
            MyPendingArticle.setViewCount(rs.getInt("ViewCount"));
            MyPendingArticle.setLikeCount(rs.getInt("LikeCount"));
            MyPendingArticle.setRejectionReason(rs.getString("RejectionReason"));
            ReturnList.add(MyPendingArticle);
        }
        return ReturnList;
    }

    public ArrayList<Article> ListApprovedArticlesStatistic(String ArticleStatus) throws SQLException {
        ArrayList<Article> ReturnList = new ArrayList<>();
        String querytolistpending = "Select * From Article" +
                " Where ArticleStatus = ? ;";
        PreparedStatement myStmt = connection.prepareStatement(querytolistpending);
        myStmt.setString(1, ArticleStatus);
        ResultSet rs = myStmt.executeQuery();
        while (rs.next()) {
            Article MyApprovedArticleStats = new Article();
            MyApprovedArticleStats.setArticleID(rs.getInt("ArticleID"));
            MyApprovedArticleStats.setArticleTitle(rs.getString("ArticleTitle"));
            MyApprovedArticleStats.setViewCount(rs.getInt("ViewCount"));
            MyApprovedArticleStats.setLikeCount(rs.getInt("LikeCount"));
            ReturnList.add(MyApprovedArticleStats);
        }
        return ReturnList;
    }


    public Integer CountTotalLikes() throws SQLException {
        int NumbOfCount;
        String query = "SELECT COUNT(ArticleID) FROM likes;";
        PreparedStatement myStmt = connection.prepareStatement(query);
        ResultSet rs = myStmt.executeQuery();
        NumbOfCount = 0;
        if (rs.next()) {
            NumbOfCount = rs.getInt("COUNT(ArticleID)");
            return NumbOfCount;
        }
        return null;
    }

    public Integer CountTotalViews() throws SQLException {
        int NumbOfCount;
        String query = "SELECT SUM(ViewCount) FROM article";
        PreparedStatement myStmt = connection.prepareStatement(query);
        ResultSet rs = myStmt.executeQuery();
        NumbOfCount = 0;
        if (rs.next()) {
            NumbOfCount = rs.getInt("SUM(ViewCount)");
        }
        return NumbOfCount;
    }
}