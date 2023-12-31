public class StmtReturn extends Statement {
    final Expression value;

    StmtReturn(Expression value) {
        this.value = value;
    }

    @Override
    public String imprimir() {
        return "null";
    }
}
