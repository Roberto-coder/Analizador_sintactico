

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
        System.out.println(indentation + "Asigna Expresion: " + name.lexema);
        value.imprimir(indentation +"\t");
        //System.out.println(indentation+"\t"+ '└'+this.toString());
    }
}
