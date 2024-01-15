public class StmtPrint extends Statement {
    final Expression expression;

    StmtPrint(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Imprimir(Expression->"+expression.toString()+")";
    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "└> StatementPrint");
        System.out.print(indentation + "\tExpresión a imprimir: ");
        expression.imprimir(indentation + "\t\t");
    }

    @Override
    public Object recorrer(TablaSimbolos tablita) {
        try {
            Object resultado = expression.scan(tablita);
            System.out.println(resultado);
            return null;
        } catch (RuntimeException e) {
            System.err.println("Error al imprimir expresión: " + e.getMessage());
            throw e;
        }
    }
}
