import java.util.List;
public class ASDR implements Parser {

    private int i = 0;
    private boolean hayErrores = false;
    private Token preanalisis;
    private final List<Token> tokens;
    //tokens
    private final Token identificador = new Token(TipoToken.IDENTIFIER, "");
    private final Token coma = new Token(TipoToken.COMMA, ",");
    private final Token punto = new Token(TipoToken.DOT, ".");
    private final Token var = new Token(TipoToken.VAR, "var");
    private final Token imprimir = new Token(TipoToken.PRINT, "print");
    private final Token fun = new Token(TipoToken.FUN, "fun");
    private final Token puntoycoma = new Token(TipoToken.SEMICOLON, ";");
    private final Token si = new Token(TipoToken.IF, "if");

    private final Token igual = new Token(TipoToken.EQUAL, "=");
    private final Token para = new Token(TipoToken.FOR, "for");
    private final Token de_otro_modo = new Token(TipoToken.ELSE, "else");
    private final Token mientras = new Token(TipoToken.WHILE, "while");
    private final Token regresa = new Token(TipoToken.RETURN, "return");
    private final Token O = new Token(TipoToken.OR, "or");
    private final Token Y = new Token(TipoToken.AND, "and");
    private final Token diferente_de = new Token(TipoToken.BANG_EQUAL, "!=");
    private final Token igual_a = new Token(TipoToken.EQUAL_EQUAL, "==");
    private final Token mayor_a = new Token(TipoToken.GREATER, ">");
    private final Token mayor_iguala = new Token(TipoToken.GREATER_EQUAL, ">=");
    private final Token menor_a = new Token(TipoToken.LESS, "<");
    private final Token menor_iguala = new Token(TipoToken.LESS_EQUAL, "<=");
    private final Token resta = new Token(TipoToken.MINUS, "-");
    private final Token suma = new Token(TipoToken.PLUS, "+");
    private final Token neg_logica = new Token(TipoToken.BANG, "!");


    private final Token verdadero = new Token(TipoToken.TRUE, "true");
    private final Token falso = new Token(TipoToken.FALSE, "false");
    private final Token nulo = new Token(TipoToken.NULL, "null");
    private final Token numero = new Token(TipoToken.NUMBER, "number");
    private final Token cadena = new Token(TipoToken.STRING, "string");
    private final Token parentesis_abre = new Token(TipoToken.LEFT_PAREN, "(");
    private final Token parentesis_cierra = new Token(TipoToken.RIGHT_PAREN, ")");
    private final Token llave_abre = new Token(TipoToken.LEFT_BRACE, "{");
    private final Token llave_cierra = new Token(TipoToken.RIGHT_BRACE, "}");
    private final Token finCadena = new Token(TipoToken.EOF, "");

    public ASDR(List<Token> tokens) {
        this.tokens = tokens;
        preanalisis = this.tokens.get(i);
    }

    /*----------------------PROGRAM--------------------------------*/
    public boolean parse() {
        Program();

        if (preanalisis.tipo == TipoToken.EOF && !hayErrores) {
            System.out.println("Consulta correcta");
            return true;
        } else {
            System.out.println("Se encontraron errores");
        }
        return false;
    }


    public void Program() {
        preanalisis = tokens.get(i);

        Declaracion();

        if (!hayErrores && !preanalisis.equals(finCadena)) {
            System.out.println("Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
        } else if (!hayErrores && preanalisis.equals(finCadena)) {
            System.out.println("Consulta válida");
        }

    }

    void coincidir(Token t) { //FUNCION QUE COMPARA LOS TOKENS, con parametro de un token(match)
        // if(hayErrores) return;

        if (preanalisis.tipo == t.tipo) {
            i++;
            preanalisis = tokens.get(i);
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba un  " + t.tipo);
        }
    }
    /*--------------------------------------------------------------*/
    /*----------------------DECLARACIONES--------------------------------*/

    void Declaracion() {
        if (hayErrores) return;
        if (preanalisis.equals(fun)) {
            Fun_decl();
            Declaracion();
        } else if (preanalisis.equals(var)) {
            Var_decl();
            Declaracion();
        } else if (preanalisis.equals(igual)) {
            //Statement();
            Declaracion();
        } else { //EPSILON

        }
        /*
        if(!preanalisis.equals(finCadena)){
            System.out.println("Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
        }else if(!hayErrores){
            System.out.println("Consulta válida");
        }*/
    }

    void Fun_decl() {
        if (hayErrores) return;
        if (preanalisis.equals(fun)) {
            coincidir(fun);
            //Function();
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba 'fun'.");
        }
    }

    void Var_decl() {
        if (hayErrores) return;
        if (preanalisis.equals(var)) {
            coincidir(var);
            if (preanalisis.equals(identificador)) {
                coincidir(identificador);
                Var_init();
                if (preanalisis.equals(puntoycoma)) {
                    coincidir(puntoycoma);
                }
            }
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba una 'var'.");
        }
    }

    void Var_init() {
        if (hayErrores) return;
        if (preanalisis.equals(igual)) {
            coincidir(igual);
            Expression();
        } else { //EPSILON

        }

    }


/*---------------------------------------------------------------------*/
/*----------------------SENTENCIAS--------------------------------*/
    void Statement(){
        if(hayErrores) return;
        if(preanalisis.equals(neg_logica) || preanalisis.equals(resta) || preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo) || preanalisis.equals(numero) || preanalisis.equals(cadena) || preanalisis.equals(identificador) || preanalisis.equals(parentesis_abre) ){
            Expr_stmt();
        } else if(preanalisis.equals(para)){
            For_stmt();
        } else if(preanalisis.equals(si)){
            If_stmt();
        }else if(preanalisis.equals(imprimir)){
            Print_stmt();
        } else if(preanalisis.equals(regresa)){
            Return_stmt();
        } else if(preanalisis.equals(mientras)){
            While_stmt();
        } else if(preanalisis.equals(llave_abre)){
            Block();
        }
    }
    void Expr_stmt(){
        if(hayErrores) return;
        Expression();
        if(preanalisis.equals(puntoycoma)){
            coincidir(puntoycoma);
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba ';'.");
        }

        //************************
    }
    void For_stmt(){
        if(hayErrores) return;
        if(preanalisis.equals(para)){
            coincidir(para);
            if(preanalisis.equals(parentesis_abre)){
                coincidir(parentesis_abre);
                For_stmt_1();
                For_stmt_2();
                For_stmt_3();
                if(preanalisis.equals(parentesis_cierra)){
                    coincidir(parentesis_cierra);
                    Statement();
                }
            }
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba 'for'.");
        }
    }
    void For_stmt_1(){
        if(hayErrores) return;
        if(preanalisis.equals(var)){
            Var_decl();
        } else if(preanalisis.equals(neg_logica) || preanalisis.equals(resta) || preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo)|| preanalisis.equals(numero) || preanalisis.equals(cadena) || preanalisis.equals(identificador) || preanalisis.equals(parentesis_abre) ){ //************************************
            Expr_stmt();
        } else if(preanalisis.equals(puntoycoma)){
            coincidir(puntoycoma);
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba 'var', una 'expresion' ó ';'.");
        }

    }

    void For_stmt_2(){
        if(hayErrores) return;
        if(preanalisis.equals(neg_logica) || preanalisis.equals(resta) || preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo) || preanalisis.equals(numero) || preanalisis.equals(cadena) || preanalisis.equals(identificador) || preanalisis.equals(parentesis_abre)){ //********************************************
            Expression();
            if(preanalisis.equals(puntoycoma)){
                coincidir(puntoycoma);
            }
        } else if(preanalisis.equals(puntoycoma)){
            coincidir(puntoycoma);
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba 'for' o ';'.");
        }
    }

    void For_stmt_3(){
        if(hayErrores) return;
        if(preanalisis.equals(neg_logica) || preanalisis.equals(resta) || preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo) || preanalisis.equals(numero) || preanalisis.equals(cadena) || preanalisis.equals(identificador) || preanalisis.equals(parentesis_abre)){
            Expression();
        } else { //EPSILON

        }

    }

    void If_stmt(){
        if(hayErrores) return;
        if(preanalisis.equals(si)){
            coincidir(si);
            if(preanalisis.equals(parentesis_abre)){
                coincidir(parentesis_abre);
                Expression();
                if(preanalisis.equals(parentesis_cierra)){
                    coincidir(parentesis_cierra);
                    Statement();
                    //Else_statement();
                }
            }
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba 'if'.");
        }
    }
    void Else_statement(){
        if(hayErrores) return;
        if(preanalisis.equals(de_otro_modo)){
            coincidir(de_otro_modo);
            Statement();
        } else { //EPSILON

        }
        //**
    }

    void Print_stmt(){
        if(hayErrores) return;
        if(preanalisis.equals(imprimir)){
            coincidir(imprimir);
            Expression();
            if(preanalisis.equals(puntoycoma)){
                coincidir(puntoycoma);
            }
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba 'print'.");
        }
    }

    void Return_stmt(){
        if(hayErrores) return;
        if(preanalisis.equals(regresa)){
            coincidir(regresa);
            Return_exp_opc();
            if(preanalisis.equals(puntoycoma)){
                coincidir(puntoycoma);
            }
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba 'return'.");
        }
    }
    void Return_exp_opc(){
        if(hayErrores) return;
        if(preanalisis.equals(neg_logica) || preanalisis.equals(resta) || preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo) || preanalisis.equals(numero) || preanalisis.equals(cadena) || preanalisis.equals(identificador) || preanalisis.equals(parentesis_abre) ){ //*********
            Expression();
        } else { //EPSILON

        }

    }





/*----------------------------------------------------------------*/
/*----------------------Expresiones--------------------------------*/
void Expression(){
    if(hayErrores) return;
    //Assignment();
}



/*----------------------------------------------------------------*/
/*----------------------Otras--------------------------------*/




/*----------------------------------------------------------------*/

}