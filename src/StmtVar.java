

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
        System.out.println(indentation + "└> StatementVar: Nombre de la variable '" + name.lexema + "'");
        if (initializer != null) {
            System.out.print(indentation + "\t└> Inicializador: ");
            initializer.imprimir(indentation + "\t");
        } else {
            System.out.println(indentation + "\t└> Sin inicializador");
        }
    }

    @Override
    public Object recorrer(TablaSimbolos tablita) {
        // Evaluar el inicializador si está presente
        Object value = (initializer != null) ? initializer.scan(tablita) : null;

        // Declarar la variable en la tabla de símbolos
        if (!tablita.existeID(name.lexema)) {
            tablita.declarar(name.lexema, value);
        } else {
            throw new RuntimeException("La variable '" + name.lexema + "' ya está declarada.");
        }

        return null;
    }
}
