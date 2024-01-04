

public class ExprBinary extends Expression{
    final Expression left;
    final Token operator;
    final Expression right;

    ExprBinary(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    @Override
    public String toString() {
        return "Expresion Binaria: (Izquierda->" + left.toString() + " Operador->" + operator.lexema + " Derecha->" + right.toString() + ")";
    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation +"Expresion Binaria");
        System.out.print(indentation + "\tIzquierda: ");
        left.imprimir(indentation + "");
        System.out.println(indentation + "\tOperador: " + operator.lexema);
        System.out.println(indentation + "\tDerecha:");
        right.imprimir(indentation + "\t\t└>");
        //System.out.println(indentation+"\t"+ '└'+this.toString());
    }

}
