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
        System.out.println(indentation + "└>StatementPrint");
        expression.imprimir(indentation + "\texpression");

        //System.out.println(indentation+"\t"+'└'+this.toString());
    }

    @Override
    public Object evaluate(TablaSimbolos tablita) {
        System.out.println(expression.evaluate(tablita));
        return null;

    }

}
