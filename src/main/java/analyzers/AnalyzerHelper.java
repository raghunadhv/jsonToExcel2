package analyzers;

import analyzers.models.EntityField;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitor;

import java.util.List;
import java.util.Optional;

// https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/ExpressionStmt.html
public class AnalyzerHelper {
    static VoidVisitor<List<String>> ifAnalyzer=new IfAnalyzer();
    static VoidVisitor<List<String>> whileAnalyzer=new WhileAnalyzer();
    static VoidVisitor<List<EntityField>> fieldAnalyzer=new FieldAnalyzer();
    //static VoidVisitor<List<String>> objectAnalyzer=new ObjectCreationAnalyzer();

    public static void analyzeAllStatements(BlockStmt blockStmt,  List<String> collector) {
        blockStmt.getStatements().forEach(statement -> {
            if (statement.isEmptyStmt()) {
                System.out.println("empty"+ statement.toString());
            }
            if (statement.isLabeledStmt()) {
                System.out.println("labeled"+ statement.toString());
            }
            if (statement.isExpressionStmt()) {
                Expression expression = statement.asExpressionStmt().getExpression();
                if (expression.isAssignExpr()) {
                    System.out.println("assignment " + statement.toString() + " -> " + statement.asExpressionStmt().getExpression().isAssignExpr());

                }
                if (expression.isMethodCallExpr()) {
                    System.out.println("method call " + statement.toString() + "  -> " + statement.asExpressionStmt().getExpression().isMethodCallExpr());

                }
                if (expression.isObjectCreationExpr()) {
                    System.out.println("object creation call " + statement.toString() + "  -> " + statement.asExpressionStmt().getExpression().isObjectCreationExpr());
                    AnalyzerHelper.analyzeAssignExpression(statement.asExpressionStmt().getExpression());
                }
                if (expression.isVariableDeclarationExpr()) {
                    System.out.println("variable declaration call " + statement.toString() + "  -> " + statement.asExpressionStmt().getExpression().isVariableDeclarationExpr());
                    AnalyzerHelper.analyzeVariableDeclarationExpression(statement.asExpressionStmt().getExpression());

                }
            }
            if (statement.isIfStmt()) {
               // System.out.println("nested If -->");
                ifAnalyzer.visit((IfStmt)statement,collector);
             //   System.out.println("after if --->");

            }
            if (statement.isWhileStmt()) {
                System.out.println("nested while -->");
                whileAnalyzer.visit((WhileStmt)statement,collector);
            }
            System.out.println("-------");
        });
    }
    private static void analyzeAssignExpression(Expression expression) {
        AssignExpr assignExpr=expression.asAssignExpr();
       System.out.println("target:->"+ assignExpr.getTarget());
        System.out.println("Value:->"+assignExpr.getValue());

    }

    private static void analyzeVariableDeclarationExpression(Expression expression) {
        //   Iterator<Map.Entry<String, JsonNode>> fieldIterator = jsonNode.fields();
        //
        VariableDeclarationExpr variableDeclarationExpr=expression.asVariableDeclarationExpr();
        variableDeclarationExpr.getVariables().forEach(variableDeclarator -> {
        //    System.out.println("name:->"+ variableDeclarator.getName());
        //    System.out.println("initializer:->"+ variableDeclarator.getInitializer());
         //   System.out.println("name:->"+ variableDeclarator.getType());

        });
      //  System.out.println("target:->"+ variableDeclarationExpr.getVariables());
    }
    void analyzeElseBlock(IfStmt ifStmt,List<String> collector) {
        if (ifStmt.getElseStmt().isPresent()) {
            Optional<BlockStmt> blockStmtElseOptional = ifStmt.getElseStmt().get().toBlockStmt();
            if (blockStmtElseOptional.isPresent()) {
                System.out.println("else ->");
                AnalyzerHelper.analyzeAllStatements(blockStmtElseOptional.get(), collector);
            }
        }
    }
}
