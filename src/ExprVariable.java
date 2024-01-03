

class ExprVariable extends Expression {
    final Token name;

    ExprVariable(Token name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Expr Variable:("+name.lexema+")";
    }

    @Override
    public void imprimir(String indentation) {

        System.out.println(indentation + "ExprVariable: " + name.lexema);

        //System.out.println(indentation+"\t"+'└'+this.toString());
    }
}