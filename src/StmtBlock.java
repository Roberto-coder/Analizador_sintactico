import javax.swing.plaf.nimbus.State;
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
        return sb.toString();
    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "StmtBlock");
        for (Statement stmt : statements) {
            stmt.imprimir(indentation + "\t");
        }
    }
}
