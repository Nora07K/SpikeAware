public class ExtraMIdata {
    int ArticleID;
    String ArticleModifyType = "";
    int ArticleListParentID;

    public ExtraMIdata(String P_ArticleModifyType, int P_ArticleListParentID) {
        ArticleModifyType = P_ArticleModifyType;
        ArticleListParentID = P_ArticleListParentID;
    }

    public ExtraMIdata(String P_ArticleModifyType) {
        ArticleModifyType = P_ArticleModifyType;
    }

    public ExtraMIdata(int P_ArticleID) {
        ArticleID = P_ArticleID;
    }

    public ExtraMIdata() {

    }
}
