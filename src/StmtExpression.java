public class StmtExpression extends Statement {
    final Expression expression;

    StmtExpression(Expression expression) {
        this.expression = expression;
    }

    public String toString() {
        /*StringBuilder sb = new StringBuilder();
        for (Statement statement : statements) {
            sb.append(statement.toString());
        }*/
        return "Expression:(Argumentos->"+expression.toString()+")";
    }


    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "StatementExpression:");
        // Imprimir la expresión contenida en este statement
        System.out.println(indentation + "\t└> Expresión: ");
        expression.imprimir(indentation + "\t\t");
    }

    @Override
    public Object recorrer(TablaSimbolos tablita) {
        // Ejecutar y devolver el resultado de la expresión contenida en este statement
        return expression.scan(tablita);
    }
}
