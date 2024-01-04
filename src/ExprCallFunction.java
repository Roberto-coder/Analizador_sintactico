

import java.util.List;

public class ExprCallFunction extends Expression{
    final Expression callee;
    // final Token paren;
    final List<Expression> arguments;

    ExprCallFunction(Expression callee, /*Token paren,*/ List<Expression> arguments) {
        this.callee = callee;
        // this.paren = paren;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        // Manejar un posible valor nulo para callee
        String calleeStr = (callee != null) ? callee.toString() : "null";

        // Construir la representación en cadena de la lista de argumentos
        StringBuilder argsStr = new StringBuilder();
        if (arguments != null) {
            for (int i = 0; i < arguments.size(); i++) {
                if (arguments.get(i) != null) {
                    argsStr.append(arguments.get(i).toString());
                } else {
                    argsStr.append("null");
                }
                if (i < arguments.size() - 1) {
                    argsStr.append(", ");
                }
            }
        }

        // Devolver la representación completa de la llamada a función
        return "Expresion: Llamar a la Funcion:"+"Nombre->"+calleeStr + " Argumentos-> (" + argsStr.toString() + ")";
    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "Llamar funcion");
        System.out.println(indentation + "\tLlamar:");
        callee.imprimir(indentation + "\t\t");

        System.out.println(indentation + "\tArgumentos:");
        for (Expression arg : arguments) {
            arg.imprimir(indentation + "\t\t└>");
        }
        //System.out.println(indentation+ "\t"+'└'+this.toString());
    }
}
