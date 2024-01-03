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
        System.out.println(indentation + "StmtReturn");
        if (value != null) {
            value.imprimir(indentation + "\t");
        }
        //System.out.println(indentation+"\t"+'└'+this.toString());
    }
}
