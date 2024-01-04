public class StmtIf extends Statement {
    final Expression condition;
    final Statement thenBranch;
    final Statement elseBranch;

    StmtIf(Expression condition, Statement thenBranch, Statement elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    /*@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("(if ").append(condition).append("\n");
        builder.append("(then ").append(thenBranch).append(")\n");

        if (elseBranch != null) {
            builder.append("(else ").append(elseBranch).append(")\n");
        }

        builder.append(")");
        return builder.toString();
    }*/

    public String toString() {
        if (elseBranch != null) {
            return "Declara IF:(Condicion->"+condition.toString()+" THEN-> "+thenBranch.toString()+" ELSE-> "+elseBranch.toString();
        }else{
            return "Declara IF:(Condicion->"+condition.toString()+" THEN-> "+thenBranch.toString()+" ELSE->Vacio";
        }

    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "└>StatementIf");
        System.out.println(indentation + "\tCondicion:");
        condition.imprimir(indentation + "\t\t");

        System.out.println(indentation + "\tThen:");
        thenBranch.imprimir(indentation + "\t\t");

        if (elseBranch != null) {
            System.out.println(indentation + "\tElse:");
            elseBranch.imprimir(indentation + "\t\t");
        }
        //System.out.println(indentation+"\t"+'└'+this.toString());
    }
}
