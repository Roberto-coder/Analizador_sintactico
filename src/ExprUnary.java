

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
        System.out.println(indentation + "Expresión Unaria:");
        System.out.println(indentation + "\tOperador: " + operator.lexema);
        System.out.println(indentation + "\tExpresión:");
        right.imprimir(indentation + "\t\t");
    }

    @Override
    public Object scan(TablaSimbolos tablita) {
        Object expr = right.scan(tablita);

        switch (operator.tipo) {
            case MINUS:
                if (!(expr instanceof Double)) {
                    throw new RuntimeException("El operador '-' solo puede aplicarse a números. Tipo encontrado: " + expr.getClass().getSimpleName());
                }
                return -(Double) expr;

            case BANG:
                return !isTruthy(expr);

            default:
                throw new RuntimeException("Operador unario no soportado: " + operator.lexema);
        }
    }

    private boolean isTruthy(Object object) {
        if (object == null) return false;
        if (object instanceof Boolean) return (Boolean) object;
        return true;
    }


}
