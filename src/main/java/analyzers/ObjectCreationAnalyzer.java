package analyzers;

import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;
import java.util.Optional;

public class ObjectCreationAnalyzer extends VoidVisitorAdapter<List<String>>

    {
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
        Optional<BlockStmt> blockStmtOptional= ifStmt.getThenStmt().toBlockStmt();
        if ( blockStmtOptional.isPresent()) {
            AnalyzerHelper.analyzeAllStatements(blockStmtOptional.get(),collector);
        }
    }
    }
