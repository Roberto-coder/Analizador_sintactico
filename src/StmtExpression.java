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
        System.out.println(indentation + "StmtExpression");
        expression.imprimir(indentation + "\t");
        //System.out.println(indentation+"\t"+'└'+this.toString());
    }
}
