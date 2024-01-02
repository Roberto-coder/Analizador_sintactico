

public class ExprGet extends Expression{
    final Expression object;
    final Token name;

    ExprGet(Expression object, Token name) {
        this.object = object;
        this.name = name;
    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "ExprGet");
        System.out.println(indentation + "\tObject:");
        object.imprimir(indentation + "\t\t");

        System.out.println(indentation + "\tName: " + name.lexema);
    }
}
