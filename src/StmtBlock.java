
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
        System.out.println(indentation + "StatementBlock");
        for (Statement stmt : statements) {
            stmt.imprimir(indentation + "\t└>");
        }
        //System.out.println(indentation+"\t"+'└'+this.toString());
    }

    @Override
    public Object recorrer(TablaSimbolos tablita) {
        Object returnValue = null;
        try {
            for (Statement stmt : statements) {
                returnValue = stmt.recorrer(tablita);
                if (returnValue instanceof StmtReturn) {
                    break;
                }
            }
        } finally {

        }
        return returnValue;
    }



}
