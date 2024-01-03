

public class ExprGet extends Expression{
    final Expression object;
    final Token name;

    ExprGet(Expression object, Token name) {
        this.object = object;
        this.name = name;
    }
    @Override
    public String toString() {
        return "Expression Get: Nombre de la variable: " + name.lexema + " Objeto:" + object.toString() + ")";
    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "ExprGet");
        System.out.println(indentation + "\tObject:");
        object.imprimir(indentation + "\t\t");

        System.out.println(indentation + "\tName: " + name.lexema);
        //System.out.println(indentation+ "\t"+'└'+this.toString());
    }
}
