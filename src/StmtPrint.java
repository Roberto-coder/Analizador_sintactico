public class StmtPrint extends Statement {
    final Expression expression;

    StmtPrint(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String imprimir() {
        return "null";
    }
}
