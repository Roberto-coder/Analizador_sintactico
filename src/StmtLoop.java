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
        System.out.println(indentation + "└>StatementLoop");
        System.out.println(indentation + "\tCondicion:");
        condition.imprimir(indentation + "\t\t");

        System.out.println(indentation + "\tCuerpo:");
        body.imprimir(indentation + "\t\t");
        //System.out.println(indentation+"\t"+'└'+this.toString());
    }

    @Override
    public Object recorrer(TablaSimbolos tablita) {
        if (initialization != null) {
            System.out.println("Inicialización del bucle for");
            initialization.recorrer(tablita);
        }

        while (condition == null || (Boolean) condition.scan(tablita)) {
            //System.out.println("Evaluando StmtLoop");
            //System.out.println("Evaluando condición del bucle for, condición es: " + condition.evaluate(tablita));
            // Resto del código del bucle
            body.recorrer(tablita);

            if (update != null) {
                update.scan(tablita);
                // System.out.println("Actualización del bucle for realizada");
            }

        }



        return null;
    }
}
