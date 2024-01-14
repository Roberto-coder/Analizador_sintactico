public class ExprGrouping extends Expression {
    final Expression expression;

    ExprGrouping(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Expression Grouping: (Expression->" + expression.toString() + ")";
    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "ExpressionGrouping");
        expression.imprimir(indentation + "\t└>");
        //System.out.println(indentation + "\t"+ '└'+this.toString());
    }

    @Override
    public Object evaluate(TablaSimbolos tablita) {
        return expression.evaluate(tablita);
    }
}
