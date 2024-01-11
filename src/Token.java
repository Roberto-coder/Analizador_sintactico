public class Token {

    final TipoToken tipo;
    final String lexema;
    final Object literal;

    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = null;
    }

    public Token(TipoToken tipo, String lexema, Object literal) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Token)) {
            return false;
        }

        Token otherToken = (Token) o;
        return this.tipo == otherToken.tipo;
    }

    @Override
    public String toString() {
        return tipo + " " + lexema + " " + (literal == null ? "" : literal.toString());
    }

    public boolean esOperando(){
        switch (this.tipo){
            case IDENTIFIER:
            case NUMBER:
            case STRING:
            case TRUE:
            case FALSE:
                return true;
            default:
                return false;
        }
    }

    public boolean esOperador(){
        switch (this.tipo){
            case PLUS: //ARITMETICOS
            case MINUS:
            case STAR:
            case SLASH:
            case EQUAL: //RELACIONALES
            case EQUAL_EQUAL:
            case GREATER:
            case GREATER_EQUAL:
            case LESS:
            case LESS_EQUAL:
            case BANG_EQUAL:
                //BOOLEANOS
            case AND:
            case OR:
                return true;
            default:
                return false;
        }
    }

    public boolean esPalabraReservada(){
        switch (this.tipo){
            case VAR:
            case IF:
            case PRINT:
            case ELSE:
            case WHILE:
            case FOR:
                return true;
            default:
                return false;
        }
    }

    public boolean esEstructuraDeControl(){
        switch (this.tipo){
            case IF:
            case ELSE:
            case FOR:
            case WHILE:
                return true;
            default:
                return false;
        }
    }

    public boolean precedenciaMayorIgual(Token t) {
        return this.obtenerPrecedencia() >= t.obtenerPrecedencia();
    }

    private int obtenerPrecedencia(){
        switch (this.tipo){
            //ARTIRMETICAS
            case STAR:
            case SLASH:
                return 7;
            case PLUS:
            case MINUS:
                return 6;
            case EQUAL:
                return 1;
            //RELACIONALES
            case GREATER:
            case GREATER_EQUAL:
            case LESS:
            case LESS_EQUAL:
                return 5;
            case EQUAL_EQUAL:
            case BANG_EQUAL:
                return 4;
            //BOOLEANAS
            case AND:
                return 3;
            case OR:
                return 2;
        }

        return 0;
    }

    public int aridad(){
        switch (this.tipo) {
            case STAR:
            case SLASH:
            case PLUS:
            case MINUS:
            case EQUAL:
            case GREATER:
            case GREATER_EQUAL:
            case LESS_EQUAL:
            case LESS:
            case EQUAL_EQUAL:
            case AND:
            case OR:
                return 2;
        }
        return 0;
    }
}