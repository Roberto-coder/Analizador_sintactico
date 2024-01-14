

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
    public Object evaluate(TablaSimbolos tablita) {
        Object izq = left.evaluate(tablita);
        //System.out.println(izq+": "+izq.getClass());
        Object der = right.evaluate(tablita);
        //System.out.println(der+": "+der.getClass());
        if (izq instanceof Double && der instanceof Double) {
            switch (operator.tipo) {
                case PLUS:
                    return (Double) izq + (Double) der;
                case MINUS:
                    return (Double) izq - (Double) der;
                case STAR:
                    return (Double) izq * (Double) der;
                case SLASH:
                    if ((Double) der == 0) {
                        throw new RuntimeException("Error: División por cero");
                    }
                    return (Double) izq / (Double) der;
                case LESS:
                    return ((Double)izq < (Double) der);
                case LESS_EQUAL:
                    return ((Double)izq <= (Double) der);
                case GREATER:
                    return ((Double)izq > (Double) der);
                case GREATER_EQUAL:
                    return ((Double)izq >= (Double) der);
                case EQUAL:
                    return (((Double)izq).equals((Double)der));
                case EQUAL_EQUAL:
                    return (((Double)izq).equals((Double)der));
                case BANG:
                    return (!((Double)izq).equals((Double)der));

                default:
                    throw new RuntimeException("Operador no soportado para números: " + operator.lexema);
            }
        }
        if (izq instanceof Integer && der instanceof Integer) {
            switch (operator.tipo) {
                case PLUS:
                    return (Integer) izq + (Integer) der;
                case MINUS:
                    return (Integer) izq - (Integer) der;
                case STAR:
                    return (Integer) izq * (Integer) der;
                case SLASH:
                    if ((Integer) der == 0) {
                        throw new RuntimeException("Error: División por cero");
                    }
                    return (Integer) izq / (Integer) der;
                case LESS:
                    return ((Integer)izq < (Integer) der);
                case LESS_EQUAL:
                    return ((Integer)izq <= (Integer) der);
                case GREATER:
                    return ((Integer)izq > (Integer) der);
                case GREATER_EQUAL:
                    return ((Integer)izq >= (Integer) der);
                case EQUAL:
                    return (((Integer)izq).equals((Integer)der));
                case EQUAL_EQUAL:
                    return (((Integer)izq).equals((Integer)der));
                case BANG:
                    return (!((Integer)izq).equals((Integer)der));

                default:
                    throw new RuntimeException("Operador no soportado para números: " + operator.lexema);
            }
        }
        if (izq instanceof Integer && der instanceof Double) {
            switch (operator.tipo) {
                case PLUS:
                    return (Double) ((Integer) izq + (Double) der);
                case MINUS:
                    return (Double)((Integer) izq - (Double) der);
                case STAR:
                    return (Double)((Integer) izq * (Double) der);
                case SLASH:
                    if ((Double) der == 0) {
                        throw new RuntimeException("Error: División por cero");
                    }
                    return (Double)((Integer) izq / (Double) der);
                case LESS:
                    return ((Integer) izq < (Double) der);
                case LESS_EQUAL:
                    return ((Integer)izq <= (Double) der);
                case GREATER:
                    return ((Integer)izq > (Double) der);
                case GREATER_EQUAL:
                    return ((Integer)izq >= (Double) der);
                case EQUAL:
                    return (((Integer)izq).equals((Double)der));
                case EQUAL_EQUAL:
                    return ((Double)((Integer)izq).doubleValue()).equals(((Double)der));
                case BANG:
                    return (!((Integer)izq).equals((Double)der));

                default:
                    throw new RuntimeException("Operador no soportado para números: " + operator.lexema);
            }
        }
        if (izq instanceof Double && der instanceof Integer) {
            switch (operator.tipo) {
                case PLUS:
                    return (Double) ((Double) izq + (Integer) der);
                case MINUS:
                    return (Double)((Double) izq - (Integer) der);
                case STAR:
                    System.out.println((Double)((Double) izq * (Integer) der));
                    return (Double)((Double) izq * (Integer) der);
                case SLASH:
                    if ((Integer) der == 0) {
                        throw new RuntimeException("Error: División por cero");
                    }
                    return (Double) izq / (Integer) der;
                case LESS:
                    return ((Double)izq < (Integer) der);
                case LESS_EQUAL:
                    return ((Double)izq <= (Integer) der);
                case GREATER:
                    return ((Double)izq > (Integer) der);
                case GREATER_EQUAL:
                    return ((Double)izq >= (Integer) der);
                case EQUAL:
                    return (((Double)izq).equals((Integer)der));
                case EQUAL_EQUAL:
                    return ((Double)izq).equals((Double)((Integer)der).doubleValue());
                case BANG:
                    return (!((Double)izq).equals((Integer)der));

                default:
                    throw new RuntimeException("Operador no soportado para números: " + operator.lexema);
            }
        }
        if (operator.tipo == TipoToken.PLUS) {
            return izq.toString() + der.toString();
        }
        throw new RuntimeException("Error, no se puede realizar operaciones con variables de diferente tipo");

    }

}
