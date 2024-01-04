

public class StmtVar extends Statement {
    final Token name;
    final Expression initializer;

    StmtVar(Token name, Expression initializer) {
        this.name = name;
        this.initializer = initializer;
    }

    @Override
    public String toString() {
        if (initializer != null) {
            return "Declara variable(Nombre->" + name.lexema + " = " +" Valor->" + initializer.toString() + ";";
        } else {
            return "var " + name.lexema + ";";
        }
    }

    @Override
    public void imprimir(String indentation) {
        System.out.print(indentation + "└>StatementVar: ");
        System.out.print(indentation + "Nombre de la variable " + name.lexema);
        if (initializer != null) {
            System.out.print(" igual a ");
            initializer.imprimir(""); // Sin indentación adicional para el inicializador
        }
        System.out.print(";");
        //System.out.println(indentation+"\t"+'└'+this.toString());

    }
}
