

public class ExprSet extends Expression{
    final Expression object;
    final Token name;
    final Expression value;

    ExprSet(Expression object, Token name, Expression value) {
        this.object = object;
        this.name = name;
        this.value = value;
    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "ExprSet");
        System.out.println(indentation + "\tObject:");
        object.imprimir(indentation + "\t\t");
        System.out.println(indentation + "\tName: " + name.lexema);
        System.out.println(indentation + "\tValue:");
        value.imprimir(indentation + "\t\t");
    }
}
