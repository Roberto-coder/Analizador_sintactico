

public class ExprUnary extends Expression{
    final Token operator;
    final Expression right;

    ExprUnary(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
    }

    @Override
    public String toString() {
        return "Expression Unaria: ( Operador->"+operator.lexema +" Derecha-> "+right.toString()+ ")";
    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "ExpressionUnary");
        System.out.println(indentation + "\tOperador: " + operator.lexema);
        System.out.println(indentation + "\tDerecha:");
        right.imprimir(indentation + "\t\t└>");
        //System.out.println(indentation + "\t"+'└'+this.toString());
    }

    @Override
    public Object scan(TablaSimbolos tablita) {
        Object expr = right.scan(tablita);
        switch (operator.tipo) {
            case MINUS:
                if (!(expr instanceof Double)) {
                    throw new RuntimeException("El operador '-' no se puede aplicar ya que no es un numero.");
                }
                if (expr instanceof Double) {
                    return -(double) expr;
                }
            case BANG:
                return !isTruthy(expr);


            default:
                throw new RuntimeException("Operador unario no soportado: " + operator.tipo);
        }
    }

    private boolean isTruthy(Object object) {
        if (object == null) return false;
        if (object instanceof Boolean) return (Boolean) object;
        return true;
    }
}
