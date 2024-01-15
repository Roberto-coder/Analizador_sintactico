

public class ExprAssign extends Expression{
    final Token name;
    final Expression value;

    ExprAssign(Token name, Expression value) {
        this.name = name;
        this.value = value;
    }
    @Override
    public String toString() {
        return "Asignacion de Variable: (Nombre de la variable-> " + name.lexema + " Valor->" + value.toString() + ")";
    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "Asignacion de Variable: " + name.lexema);
        value.imprimir(indentation +"\t└>");
        //System.out.println(indentation+"\t"+ '└'+this.toString());
    }
    @Override
    public Object scan(TablaSimbolos tablita) {
        // Evaluamos el valor de la expresión
        Object evaluatedValue = value.scan(tablita);
        // Verificamos si el identificador existe en la tabla de símbolos
        if (!tablita.existeID(name.lexema)) {
            // Si no existe, lanzamos una excepción
            throw new RuntimeException("La variable '" + name.lexema + "' no está definida.");
        }
        // Si el identificador existe, asignamos el valor evaluado
        tablita.asignar(name.lexema, evaluatedValue);

        // Retornamos el valor evaluado
        return evaluatedValue;
    }
}
