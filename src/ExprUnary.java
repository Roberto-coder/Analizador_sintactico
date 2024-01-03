

public class ExprUnary extends Expression{
    final Token operator;
    final Expression right;

    ExprUnary(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
    }

    @Override
    public String toString() {
        return "Expression Unaria: ( Operator->"+operator.lexema +" Right-> "+right.toString()+ ")";
    }

    @Override
    public void imprimir(String indentation) {
        System.out.println(indentation + "ExprUnary");
        System.out.println(indentation + "\tOperator: " + operator.lexema);
        System.out.println(indentation + "\tRight:");
        right.imprimir(indentation + "\t\t");
        //System.out.println(indentation + "\t"+'└'+this.toString());
    }
}
