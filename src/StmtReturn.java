public class StmtReturn extends Statement {
    final Expression value;

    StmtReturn(Expression value) {
        this.value = value;
    }


    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "StmtReturn");
        if (value != null) {
            value.imprimir(indentation + "\t");
        }
    }
}
