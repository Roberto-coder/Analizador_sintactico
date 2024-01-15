

import java.util.List;

public class StmtFunction extends Statement {
    final Token name;
    final List<Token> params;
    final StmtBlock body;

    StmtFunction(Token name, List<Token> params, StmtBlock body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }

    /*public String toString() {

        return "Declara Funcion:(Nombre->"+name.toString()+" Parametros->"+params.toString()+" Cuerpo->"+body.toString()+")";
    }*/
    public String toString() {

        return name.toString();
    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "StatementFunction: " + name.lexema);

        // Imprimir parámetros
        if (params.isEmpty()) {
            System.out.println(indentation + "\tParametros: Ninguno");
        } else {
            System.out.println(indentation + "\tParametros:");
            for (Token param : params) {
                System.out.println(indentation + "\t\t" + param.lexema);
            }
        }

        // Imprimir el cuerpo de la función
        System.out.println(indentation + "\tCuerpo de la función:");
        body.imprimir(indentation + "\t\t");
    }

    @Override
    public Object recorrer(TablaSimbolos tablita) {
        tablita.registrarFuncion(name.lexema, this);
        return null;
    }

}
