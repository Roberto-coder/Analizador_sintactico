

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
        System.out.println(indentation + "ExpressionLogical");
        System.out.println(indentation + "\tIzquierda:");
        left.imprimir(indentation + "\t\t");
        System.out.println(indentation + "\tOperador: " + operator.lexema);
        System.out.println(indentation + "\tDerecha:");
        right.imprimir(indentation + "\t\t└>");
        //System.out.println(indentation+ "\t"+'└'+this.toString());
    }

    @Override
    public Object scan(TablaSimbolos tablita) {
        Object leftValue = left.scan(tablita);
        Object rightValue = right.scan(tablita);
        if (!(leftValue instanceof Boolean && rightValue instanceof Boolean)) {
            throw new RuntimeException("Error, las operaciones lógicas solo pueden realizarse con valores booleanos");
        }

        switch (operator.tipo)
        {
            case AND:
                return ((Boolean) leftValue && (Boolean) rightValue);
            case OR:
                return ((Boolean) leftValue || (Boolean) rightValue);
            default:
                throw new RuntimeException("Operador lógico no soportado: " + operator.lexema);
        }


    }

}

