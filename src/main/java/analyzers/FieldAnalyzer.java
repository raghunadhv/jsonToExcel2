package analyzers;

import analyzers.models.EntityField;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public class FieldAnalyzer extends VoidVisitorAdapter<List<EntityField>> {
        @Override
        public void visit(FieldDeclaration fd, List<EntityField> f) {
            System.out.println(fd);
          /*  if (fd.getAnnotations().stream().anyMatch(anno -> anno.getName().equals("Column"))) {
                Class<?> type = null;
                switch (fd.get.toString()) {
                    case "String": type = String.class; break;
                    case "Long": type = Long.class; break;
                    case "Integer": type = Integer.class; break;
                    case "boolean": type = boolean.class; break;
                }
                if (type == null) return;
                f.add(new EntityField(
                        fd.getVariables().get(0).getId().getName(),
                        type,
                        fd.getAnnotations().stream().anyMatch(anno -> anno.getName().getName().equals("Id"))));
            }*/
        }
    }

