

class ExprVariable extends Expression {
    final Token name;
    final TablaSimbolos tablita;

    ExprVariable(Token name, TablaSimbolos tablita) {

        this.name = name;
        this.tablita=tablita;
    }

    @Override
    public String toString() {
        return name.lexema;
    }

    @Override
    public void imprimir(String indentation) {

        System.out.println(indentation + "└>ExpressionVariable: " + name.lexema);

        //System.out.println(indentation+"\t"+'└'+this.toString());
    }

    @Override
    public Object scan(TablaSimbolos tablita) {

        if (tablita.existeID(name.lexema)) {
            return tablita.obtenerID(name.lexema);
        } else {
            throw new RuntimeException("La variable '" + name.lexema + "' no está definida.");
        }
    }

    public String getName(){
        return name.lexema;
    }
}