

public class ExprLogical extends Expression{
    final Expression left;
    final Token operator;
    final Expression right;

    ExprLogical(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public String toString() {
        return "Expression Logica: (Izquierda->" + left.toString()+" Operador->"+operator.lexema +" Derecha-> "+right.toString()+ ")";
    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "Expresión Lógica:");
        System.out.println(indentation + "\tIzquierda: ");
        left.imprimir(indentation + "\t\t");
        System.out.println(indentation + "\tOperador: " + operator.lexema);
        System.out.println(indentation + "\tDerecha: ");
        right.imprimir(indentation + "\t\t");
    }

    @Override
    public Object scan(TablaSimbolos tablita) {
        Object leftValue = left.scan(tablita);
        Object rightValue = right.scan(tablita);

        // Asegurarse de que ambos valores sean booleanos antes de realizar operaciones lógicas
        if (!(leftValue instanceof Boolean) || !(rightValue instanceof Boolean)) {
            throw new RuntimeException("Error: las operaciones lógicas requieren valores booleanos. Encontrado: Izquierda: "
                    + leftValue.getClass().getSimpleName() + ", Derecha: "
                    + rightValue.getClass().getSimpleName());
        }

        switch (operator.tipo) {
            case AND:
                return (Boolean) leftValue && (Boolean) rightValue;
            case OR:
                return (Boolean) leftValue || (Boolean) rightValue;
            default:
                throw new RuntimeException("Operador lógico no soportado: " + operator.lexema);
        }
    }

}

