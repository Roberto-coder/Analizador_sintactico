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
        // Concatenar la indentación con la representación del valor literal
        String valorLiteral = (value instanceof String) ? (String) value : String.valueOf(value);
        System.out.println(indentation + "ExpressionLiteral: " + valorLiteral);
    }

    @Override
    public Object scan(TablaSimbolos tablita) {
        // Simplemente devuelve el valor del literal
        return value;
    }
}
