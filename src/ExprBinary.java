

public class ExprBinary extends Expression{
    final Expression left;
    final Token operator;
    final Expression right;
    final TablaSimbolos tablita;

    ExprBinary(Expression left, Token operator, Expression right,TablaSimbolos tablita) {
        this.left = left;
        this.operator = operator;
        this.right = right;
        this.tablita=tablita;
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


    @Override
    public Object scan(TablaSimbolos tablita) {
        Object leftVal = left.scan(tablita);
        Object rightVal = right.scan(tablita);

        // Unificar los tipos para simplificar la lógica de operaciones
        if (leftVal instanceof Integer && rightVal instanceof Double) {
            leftVal = ((Integer) leftVal).doubleValue();
        } else if (leftVal instanceof Double && rightVal instanceof Integer) {
            rightVal = ((Integer) rightVal).doubleValue();
        }

        // Operaciones para números (Double e Integer)
        if (leftVal instanceof Number && rightVal instanceof Number) {
            return performNumberOperation((Number) leftVal, (Number) rightVal, operator.tipo);
        }

        // Concatenación para el operador PLUS con tipos no numéricos
        if (operator.tipo == TipoToken.PLUS) {
            return leftVal.toString() + rightVal.toString();
        }

        throw new RuntimeException("Operador no soportado para los tipos: " + leftVal.getClass() + ", " + rightVal.getClass());
    }

    private Object performNumberOperation(Number left, Number right, TipoToken operatorType) {
        switch (operatorType) {
            case PLUS:
                return left.doubleValue() + right.doubleValue();
            case MINUS:
                return left.doubleValue() - right.doubleValue();
            case STAR:
                return left.doubleValue() * right.doubleValue();
            case SLASH:
                if (right.doubleValue() == 0) {
                    throw new RuntimeException("Error: División por cero");
                }
                return left.doubleValue() / right.doubleValue();
            case LESS:
                return left.doubleValue() < right.doubleValue();
            case LESS_EQUAL:
                return left.doubleValue() <= right.doubleValue();
            case GREATER:
                return left.doubleValue() > right.doubleValue();
            case GREATER_EQUAL:
                return left.doubleValue() >= right.doubleValue();
            case EQUAL:
            case EQUAL_EQUAL:
                return left.doubleValue() == right.doubleValue();
            case BANG:
                return left.doubleValue() != right.doubleValue();
            default:
                throw new RuntimeException("Operador no soportado: " + operatorType);
        }
    }

}
