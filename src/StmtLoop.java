public class StmtLoop extends Statement {
    final Statement initialization; // Para bucles for
    final Expression condition;
    final Expression update; // incremento
    final Statement body;

    StmtLoop(Statement initialization,Expression condition, Expression update, Statement body) {
        this.initialization = initialization;
        this.condition = condition;
        this.body = body;
        this.update = update;
    }

    StmtLoop(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
        this.initialization = null;
        this.update = null;
    }

    public String toString() {

        return "Expression:(Bucle->"+condition.toString()+" Cuerpo->"+body.toString()+")";
    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "└> StatementLoop");

        if (initialization != null) {
            System.out.println(indentation + "\tInicialización:");
            initialization.imprimir(indentation + "\t\t");
        }

        System.out.println(indentation + "\tCondición:");
        if (condition != null) {
            condition.imprimir(indentation + "\t\t");
        } else {
            System.out.println(indentation + "\t\tSiempre verdadero (bucle infinito)");
        }

        System.out.println(indentation + "\tCuerpo:");
        body.imprimir(indentation + "\t\t");

        if (update != null) {
            System.out.println(indentation + "\tActualización:");
            update.imprimir(indentation + "\t\t");
        }
    }

    @Override
    public Object recorrer(TablaSimbolos tablita) {
        // Inicialización, si está presente (típicamente en bucles for)
        if (initialization != null) {
            tablita.insertarAlcance();
            //System.out.println("Bucle for");
            initialization.recorrer(tablita);
        }

        // Comprobar si la condición es nula (bucle infinito) o evaluar la primera vez
        boolean condicionEsVerdadera = (condition == null) || (Boolean) condition.scan(tablita);

        while (condicionEsVerdadera) {
            // Ejecutar el cuerpo del bucle
            body.recorrer(tablita);

            // Actualizar, si está presente (típicamente en bucles for)
            if (update != null) {
                update.scan(tablita);
            }

            // Evaluar la condición para la siguiente iteración, si no es un bucle infinito
            if (condition != null) {
                condicionEsVerdadera = (Boolean) condition.scan(tablita);
            }
        }

        if (initialization != null) {
            tablita.salirAlcance();
        }

        return null;
    }

}
