package analyzers;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;
import java.util.Optional;

public class IfAnalyzer extends VoidVisitorAdapter<List<String>> {
    private String analyzeStr = "";
    private String codeBlock;

    void setStringToAnalyze(String analyzeStr) {
        this.analyzeStr = analyzeStr;
        this.codeBlock = "";
    }

    @Override
    public void visit(IfStmt ifStmt, List<String> collector) {
        //   super.visit( ifStmt,collector);
        // System.out.println("if ---->"+ifStmt.getParentNode().toString());
        Optional<BlockStmt> blockStmtOptional = ifStmt.getThenStmt().toBlockStmt();
        if (blockStmtOptional.isPresent()) {
            System.out.println("if <-->");
            analyzeCondition(ifStmt);
            AnalyzerHelper.analyzeAllStatements(blockStmtOptional.get(), collector);
        }
        if (ifStmt.getElseStmt().isPresent()) {
            if (ifStmt.getElseStmt().get().isIfStmt()) {
                 blockStmtOptional = ((IfStmt)ifStmt.getElseStmt().get()).getThenStmt().toBlockStmt();
                if (blockStmtOptional.isPresent()) {
                    System.out.println("-if -->");
                    AnalyzerHelper.analyzeAllStatements(blockStmtOptional.get(), collector);
                }
                Optional<Statement> elseStatement=((IfStmt)ifStmt.getElseStmt().get()).getElseStmt();
                if (elseStatement.isPresent()) {
                    Optional<BlockStmt> blockStmtElseOptional = elseStatement.get().toBlockStmt();
                    if (blockStmtElseOptional.isPresent()) {
                        System.out.println("else  after multiple ifs->");
                        AnalyzerHelper.analyzeAllStatements(blockStmtElseOptional.get(), collector);
                    }
                }
            }
            if (ifStmt.getElseStmt().get() instanceof BlockStmt) {
                System.out.println("final else ->");
                AnalyzerHelper.analyzeAllStatements((BlockStmt) ifStmt.getElseStmt().get(), collector);

            }

        }
    }
    private void  analyzeCondition(IfStmt stmt) {
        Expression expression=stmt.getCondition();
        BinaryExpr left, right;
        BinaryExpr.Operator operatorExpr;
        if (expression instanceof BinaryExpr) {
            if (((BinaryExpr)expression).getLeft() instanceof BinaryExpr) {
                left = (BinaryExpr) ((BinaryExpr) expression).getLeft();
                right = (BinaryExpr) ((BinaryExpr) expression).getRight();
                operatorExpr = ((BinaryExpr) expression).getOperator();
                System.out.println("left" + left.toString());
                System.out.println("right" + right.toString());
                System.out.println("operator" + operatorExpr.toString());
            } else if (((BinaryExpr)expression).getLeft() instanceof NameExpr) {
                NameExpr nameExpr= (NameExpr) ((BinaryExpr)expression).getLeft();
                System.out.println(nameExpr.toString());
            }


        }


    }
}
