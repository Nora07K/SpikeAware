import java.util.Date;

public class Article {

    int ArticleID;
    java.util.Date CreationDate;
    String ArticleTitle = null;
    String ArticleContent = null;
    int UserIDofAuthor = 0;
    String ArticleStatus = null;
    String ArticleVideoURL = null;
    String ArticleImageURL = null;
    int ViewCount;
    int LikeCount;
    int DislikeCount;
    String RejectionReason = "";


    public Article() {
        ArticleStatus = "Draft";
    }


    public void Print() {
        System.out.println("The ID of the Article is: " + ArticleID);
        System.out.println("The time of creation of the Article is: " + CreationDate);
        System.out.println("The title of the Article is: " + ArticleTitle);
        System.out.println("The content of the Article is: " + ArticleContent);
        System.out.println("The status of the Article is: " + ArticleStatus);
        if (RejectionReason != null) {
            System.out.println("The reason for the rejection of this article is: " + RejectionReason);

        }
        System.out.println(" ");
    }

}
