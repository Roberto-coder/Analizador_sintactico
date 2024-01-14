

class ExprVariable extends Expression {
    final Token name;
    final TablaSimbolos tablita;

    ExprVariable(Token name, TablaSimbolos tablita) {

        this.name = name;
        this.tablita=tablita;
    }

    @Override
    public String toString() {
        return "Expr Variable:("+name.lexema+")";
    }

    @Override
    public void imprimir(String indentation) {

        System.out.println(indentation + "└>ExpressionVariable: " + name.lexema);

        //System.out.println(indentation+"\t"+'└'+this.toString());
    }

    @Override
    public Object evaluate(TablaSimbolos tablita) {

        if (tablita.existeIdentificador(name.lexema)) {
            return tablita.obtener(name.lexema);
        } else {
            throw new RuntimeException("La variable '" + name.lexema + "' no está definida.");
        }
    }

    public String getName(){
        return name.lexema;
    }
}