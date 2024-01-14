
import java.util.ArrayList;
import java.util.List;

public class ExprCallFunction extends Expression{
    final Expression callee;
    // final Token paren;
    final List<Expression> arguments;
    final TablaSimbolos tablita;

    ExprCallFunction(Expression callee, /*Token paren,*/ List<Expression> arguments,TablaSimbolos tablita) {
        this.callee = callee;
        // this.paren = paren;
        this.arguments = (arguments != null) ? arguments : new ArrayList<>();
        this.tablita=tablita;
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

    @Override
    public Object evaluate(TablaSimbolos tablita) {
        Object function = tablita.obtenerFuncion(callee.toString());

        if (function instanceof StmtFunction) {
            StmtFunction funcionDef = (StmtFunction) function;

            if (arguments.size() != funcionDef.params.size()) {
                throw new RuntimeException("Número incorrecto de argumentos para la funcion: " + funcionDef.name.lexema);
            }
            tablita.entrarAlcance();
            try {
                for (int i = 0; i < funcionDef.params.size(); i++) {
                    if (tablita.existeIdentificador(funcionDef.params.get(i).lexema)) {
                        Object argValor = arguments.get(i).evaluate(tablita);
                        tablita.declarar(funcionDef.params.get(i).lexema, argValor);
                    }else{
                        throw new RuntimeException("El parametro: "+funcionDef.params.get(i).lexema+" no esta declarado");
                    }

                }
                return funcionDef.body.evaluate(tablita);
            } finally {
                tablita.salirAlcance();
            }
        } else {
            throw new RuntimeException("El objeto al que deseas llamar no es una función.");
        }
    }
}
