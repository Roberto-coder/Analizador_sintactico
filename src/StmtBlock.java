import javax.swing.plaf.nimbus.State;
import java.util.List;

public class StmtBlock extends Statement{
    /*final List<Statement> statements;

    StmtBlock(List<Statement> statements) {
        this.statements = statements;
    }
*/
    final Node statements;


    StmtBlock(Node statements) {
        this.statements = statements;
    }

    @Override
    public String imprimir() {
        return "null";
        //TreePrinter.printTree(root);
    }
}
