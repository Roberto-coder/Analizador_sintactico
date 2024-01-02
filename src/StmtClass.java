

import java.util.List;

public class StmtClass extends Statement {
    final Token name;
    final ExprVariable superclass;
    final List<StmtFunction> methods;

    StmtClass(Token name, ExprVariable superclass, List<StmtFunction> methods) {
        this.name = name;
        this.superclass = superclass;
        this.methods = methods;
    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "StmtClass: " + name.lexema);
        if (superclass != null) {
            System.out.println(indentation + "\tSuperclass: " + superclass.toString());
        }
        System.out.println(indentation + "\tMethods:");
        for (StmtFunction method : methods) {
            method.imprimir(indentation + "\t\t");
        }
    }
}
