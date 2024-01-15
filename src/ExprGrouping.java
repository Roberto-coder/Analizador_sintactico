public class ExprGrouping extends Expression {
    final Expression expression;

    ExprGrouping(Expression expression) {
        this.expression = expression;
    }

   @Override
   public void imprimir(String indentation) {
       System.out.println(indentation + "Grupo de Expresión:");
       if (expression != null) {
           expression.imprimir(indentation + "\t└> ");
       } else {
           System.out.println(indentation + "\t└> [Expresión nula]");
       }
   }

    @Override
    public Object scan(TablaSimbolos tablita) {
        if (expression == null) {
            throw new RuntimeException("Intento de escanear una expresión nula en un grupo.");
        }
        return expression.scan(tablita);
    }
}
