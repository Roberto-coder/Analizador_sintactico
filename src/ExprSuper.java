

public class ExprSuper extends Expression {
    // final Token keyword;
    final Token method;

    ExprSuper(Token method) {
        this.method = method;
    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "ExprSuper");
        System.out.println(indentation + "\tMethod: " + method.lexema);
    }
}