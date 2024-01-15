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
        System.out.println(indentation + "└> StatementReturn");
        if (value != null) {
            System.out.print(indentation + "\tValor de retorno: ");
            value.imprimir(indentation + "\t");
        } else {
            System.out.println(indentation + "\tRetorno vacío (return;)");
        }
    }

    @Override
    public Object recorrer(TablaSimbolos tablita) {
        // Evaluar y retornar el valor de la expresión, si está presente
        if (value != null) {
            return value.scan(tablita);
        }
        // Retornar null en caso de un retorno vacío
        return null;
    }
}
