package analyzers;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;
import java.util.Optional;

public class MethodAnalyzer extends VoidVisitorAdapter<List<String>>{
    private String analyzeStr="";
    private String codeBlock;
    VoidVisitor<List<String>> ifAnalyzer=new IfAnalyzer();
    void setStringToAnalyze(String analyzeStr) {
        this.analyzeStr=analyzeStr;
        this.codeBlock="";
    }

        @Override
        public void visit(MethodDeclaration md, List<String> collector) {
            super.visit(md, collector);
            collector.add(md.getNameAsString());
            System.out.println(" method "+ md.getNameAsString());

            Optional<BlockStmt> blockStmtOptional= md.getBody();
            if ( blockStmtOptional.isPresent()) {
                blockStmtOptional.get().getStatements().forEach(statement -> {
                  if (statement.isIfStmt()) {
                      System.out.println("statement"+ md.getNameAsString());
                       ifAnalyzer.visit((IfStmt)statement,collector);
                    }
                    System.out.println("-------");
                });
            }
        }
}
