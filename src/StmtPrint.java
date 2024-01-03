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
        System.out.println(indentation + "StmtPrint");
        expression.imprimir(indentation + "\t");
        //System.out.println(indentation+"\t"+'└'+this.toString());
    }
}
