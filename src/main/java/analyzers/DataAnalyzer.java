package analyzers;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class DataAnalyzer {

    private static final String FILE_PATH = "/Users/raghunadhvadranam/jsonToExcel/src/main/java/testFiles/TestJava.java";

    public static void main(String args[]) throws FileNotFoundException {
        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
        List<String>methodNames=new ArrayList<>();
        VoidVisitor<List<String>> methodNameCollector=new MethodAnalyzer();
        methodNameCollector.visit(cu,methodNames);
       // methodNames.forEach(n->System.out.println("MethodNameCollected:"+n));
    }
}
