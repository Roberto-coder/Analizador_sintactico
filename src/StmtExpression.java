public class StmtExpression extends Statement {
    final Expression expression;

    StmtExpression(Expression expression) {
        this.expression = expression;
    }


    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "StmtExpression");
        expression.imprimir(indentation + "\t");
    }
}
