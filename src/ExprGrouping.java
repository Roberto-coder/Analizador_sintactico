public class ExprGrouping extends Expression {
    final Expression expression;

    ExprGrouping(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "ExprGrouping");
        expression.imprimir(indentation + "\t");
    }
}
