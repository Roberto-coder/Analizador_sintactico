public class StmtIf extends Statement {
    final Expression condition;
    final Statement thenBranch;
    final Statement elseBranch;

    StmtIf(Expression condition, Statement thenBranch, Statement elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    public String toString() {
        if (elseBranch != null) {
            return "Declara IF:(Condicion->"+condition.toString()+" THEN-> "+thenBranch.toString()+" ELSE-> "+elseBranch.toString();
        }else{
            return "Declara IF:(Condicion->"+condition.toString()+" THEN-> "+thenBranch.toString()+" ELSE->Vacio";
        }

    }

   @Override
   public void imprimir(String indentation) {
       System.out.println(indentation + "└> StatementIf");
       System.out.println(indentation + "\t└> Condición:");
       condition.imprimir(indentation + "\t\t");

       System.out.println(indentation + "\t└> Then (Rama verdadera):");
       thenBranch.imprimir(indentation + "\t\t");

       if (elseBranch != null) {
           System.out.println(indentation + "\t└> Else (Rama falsa):");
           elseBranch.imprimir(indentation + "\t\t");
       } else {
           System.out.println(indentation + "\t└> Sin rama Else");
       }
   }

    @Override
    public Object recorrer(TablaSimbolos tablita) {
        Object condicion = condition.scan(tablita);
        if (!(condicion instanceof Boolean)) {
            throw new RuntimeException("La condición de 'if' no es booleana.");
        }

        if ((Boolean) condicion) {
            return thenBranch.recorrer(tablita);
        } else  {
            return elseBranch.recorrer(tablita);
        }


    }

}
