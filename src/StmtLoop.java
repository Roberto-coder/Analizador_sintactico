public class StmtLoop extends Statement {
    final Expression condition;
    final Statement body;

    StmtLoop(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    public String toString() {

        return "Expression:(Bucle->"+condition.toString()+" Cuerpo->"+body.toString()+")";
    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "└>StatementLoop");
        System.out.println(indentation + "\tCondicion:");
        condition.imprimir(indentation + "\t\t");

        System.out.println(indentation + "\tCuerpo:");
        body.imprimir(indentation + "\t\t");
        //System.out.println(indentation+"\t"+'└'+this.toString());
    }
}
