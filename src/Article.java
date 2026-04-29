import java.util.Date;

public class Article {

    private int ArticleID;
    private java.util.Date CreationDate;
    private String ArticleTitle = null;
    private String ArticleContent = null;
    private int UserIDofAuthor = 0;
    private String ArticleStatus = null;
    private String ArticleVideoURL = null;
    private String ArticleImageURL = null;
    private int ViewCount;
    private int LikeCount;
    private String RejectionReason = "";


    public Article() {
        ArticleStatus = "Draft";
    }

    // Getters
    public int getArticleID() { return ArticleID; }
    public Date getCreationDate() { return CreationDate; }
    public String getArticleTitle() { return ArticleTitle; }
    public String getArticleContent() { return ArticleContent; }
    public int getUserIDofAuthor() { return UserIDofAuthor; }
    public String getArticleStatus() { return ArticleStatus; }
    public String getArticleVideoURL() { return ArticleVideoURL; }
    public String getArticleImageURL() { return ArticleImageURL; }
    public int getViewCount() { return ViewCount; }
    public int getLikeCount() { return LikeCount; }
    public String getRejectionReason() { return RejectionReason; }

    // Setters
    public void setArticleID(int articleID) { this.ArticleID = articleID; }
    public void setCreationDate(Date creationDate) { this.CreationDate = creationDate; }
    public void setArticleTitle(String articleTitle) { this.ArticleTitle = articleTitle; }
    public void setArticleContent(String articleContent) { this.ArticleContent = articleContent; }
    public void setUserIDofAuthor(int userIDofAuthor) { this.UserIDofAuthor = userIDofAuthor; }
    public void setArticleStatus(String articleStatus) { this.ArticleStatus = articleStatus; }
    public void setArticleVideoURL(String articleVideoURL) { this.ArticleVideoURL = articleVideoURL; }
    public void setArticleImageURL(String articleImageURL) { this.ArticleImageURL = articleImageURL; }
    public void setViewCount(int viewCount) { this.ViewCount = viewCount; }
    public void setLikeCount(int likeCount) { this.LikeCount = likeCount; }
    public void setRejectionReason(String rejectionReason) { this.RejectionReason = rejectionReason; }

    public String toString() {
        if (this.ArticleContent == null) {
            return "Article Title: " + this.ArticleTitle + "\n" + "Articles Like Count: " + this.LikeCount + "\n" + "Articles View Count: " + this.ViewCount;
        }
        return "Article Title: " + this.ArticleTitle + "\n" + "Article Content: " + this.ArticleContent + "\n" + "Articles Images: " + this.ArticleImageURL + "\n"
                + "Articles Videos: " + this.ArticleVideoURL + "\n" + "Articles Like Count: " + this.LikeCount + "\n" + "Articles View Count: " + this.ViewCount;
    }



}
