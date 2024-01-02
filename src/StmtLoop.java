public class StmtLoop extends Statement {
    final Expression condition;
    final Statement body;

    StmtLoop(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }


    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "StmtLoop");
        System.out.println(indentation + "\tCondition:");
        condition.imprimir(indentation + "\t\t");

        System.out.println(indentation + "\tBody:");
        body.imprimir(indentation + "\t\t");
    }
}
