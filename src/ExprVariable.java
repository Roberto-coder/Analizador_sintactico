

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
        // Verificar si la variable está definida
        if (tablita.existeID(name.lexema)) {
            System.out.println(indentation + "\t└>Valor: " + tablita.obtenerID(name.lexema));
        } else {
            System.out.println(indentation + "\t└>[Variable no definida]");
        }
    }

    @Override
    public Object scan(TablaSimbolos tablita) {
        // Verificar si la variable existe en la tabla de símbolos
        if (tablita.existeID(name.lexema)) {
            // Devolver el valor de la variable
            return tablita.obtenerID(name.lexema);
        } else {
            // Lanzar una excepción si la variable no está definida
            throw new RuntimeException("La variable '" + name.lexema + "' no está definida.");
        }
    }

}