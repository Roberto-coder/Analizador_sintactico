

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
    public Object evaluate(TablaSimbolos tablita) {
        Object evaluatedValue = value.evaluate(tablita);

        if (tablita.existeIdentificador(name.lexema)) {
            tablita.asignar(name.lexema, evaluatedValue);
        } else {
            throw new RuntimeException("La variable '" + name.lexema + "' no está definida.");
        }

        return evaluatedValue;
    }
}
