class ExprLiteral extends Expression {
    final Object value;

    ExprLiteral(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value instanceof String) {
            // Contiene comillas
            return "Expression para literales:(Valor: "+(String) value+")";
        } else {
            // convertir otros objetos a una cadena
            return "Expression para literales:(Valor: "+String.valueOf(value)+")";
        }
    }

    @Override
    public void imprimir(String indentation) {

        System.out.println(indentation + "ExprLiteral: " + value);
        //System.out.println(indentation+ "\t"+'└'+this.toString());
    }
}
