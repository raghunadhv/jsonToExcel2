package models;

public class StatementInfo {
    private int lineNo;
    private String currentComment;
    private String codeAnalysisComment;

    public int getLineNo() {
        return lineNo;
    }

    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }

    public String getCurrentComment() {
        return currentComment;
    }

    public void setCurrentComment(String currentComment) {
        this.currentComment = currentComment;
    }

    public String getCodeAnalysisComment() {
        return codeAnalysisComment;
    }

    public void setCodeAnalysisComment(String codeAnalysisComment) {
        this.codeAnalysisComment = codeAnalysisComment;
    }
}
