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
        if (value instanceof String) {
            System.out.println(indentation + "ExpressionLiteral: " +(String)  value);

        } else {
            System.out.println(indentation + "ExpressionLiteral: " +String.valueOf(value));

        }

        //System.out.println(indentation + "ExpressionLiteral: " + value);
        //System.out.println(indentation+ "\t"+'└'+this.toString());
    }

    @Override
    public Object evaluate(TablaSimbolos tablita) {
        // Simplemente devuelve el valor del literal
        return value;
    }
}
