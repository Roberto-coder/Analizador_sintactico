
import java.util.List;

public class StmtBlock extends Statement{
    final List<Statement> statements;

    StmtBlock(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Statement statement : statements) {
            sb.append(statement.toString());
        }
        return "Block:(Argumentos->"+sb.toString()+")";
    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "StatementBlock:");
        for (Statement stmt : statements) {
            stmt.imprimir(indentation + "\t");
        }
    }

    @Override
    public Object recorrer(TablaSimbolos tablita) {
        Object returnValue = null;
        for (Statement stmt : statements) {
            returnValue = stmt.recorrer(tablita);
            // Interrumpir la ejecución si se encuentra un statement de retorno
            if (returnValue instanceof StmtReturn) {
                break;
            }
        }
        return returnValue;
    }
}
