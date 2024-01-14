public class StmtReturn extends Statement {
    final Expression value;

    StmtReturn(Expression value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value != null) {
            return "Return(Valor->"+value.toString()+")";

        }else{
            return "Return(Valor->Null)";
        }

    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "└>StatementReturn");
        if (value != null) {
            value.imprimir(indentation + "\t");
        }else {
            value.imprimir(indentation + "\t"+" return vacio");
        }

        //System.out.println(indentation+"\t"+'└'+this.toString());
    }

    @Override
    public Object evaluate(TablaSimbolos tablita) {
        //System.out.println(value.evaluate(tablita));

        return value.evaluate(tablita);

    }

}
