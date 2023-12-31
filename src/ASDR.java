import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ASDR implements Parser {

    private int i = 0;
    private boolean hayErrores = false;
    private Token preanalisis;
    private final List<Token> tokens;

    private Node root=new Node();
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
    private final Token multiplicacion = new Token(TipoToken.STAR,"*");
    private final Token division = new Token(TipoToken.SLASH,"/");


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
        root=Program();
        TreePrinter.printTree(root);

        if (preanalisis.tipo == TipoToken.EOF && !hayErrores) {
            System.out.println("Consulta correcta");
            return true;
        } else {
            System.out.println("Se encontraron errores");
        }
        return false;
    }


    public Node Program() {
        Node raiz=new Node("Declaration");
        preanalisis = tokens.get(i);

        Declaracion(raiz);

        if (!hayErrores && !preanalisis.equals(finCadena)) {
            System.out.println("Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
        } else if (!hayErrores && preanalisis.equals(finCadena)) {
            System.out.println("Consulta válida");
        }
        return raiz;

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

    void Declaracion(Node node) {
        Node node1=new Node();
        if (hayErrores) return;
        if (preanalisis.equals(fun)) {
            Statement funDecl = Fun_decl(node1);
            node1=new Node("\t"+funDecl.imprimir());
            node.addChild(node1);
            Declaracion(node1);
        } else if (preanalisis.equals(var)) {
            /*Var_decl();
            Declaracion();*/
        }  else if(preanalisis.equals(neg_logica) || preanalisis.equals(resta) || preanalisis.equals(para) || preanalisis.equals(si) || preanalisis.equals(imprimir) || preanalisis.equals(regresa) || preanalisis.equals(mientras) || preanalisis.equals(llave_abre) ||  preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo) || preanalisis.equals(numero) || preanalisis.equals(cadena) || preanalisis.equals(identificador) || preanalisis.equals(parentesis_abre)){
            /*Statement();
            Declaracion();*/
        }  else { //EPSILON

        }


    }

    Statement Fun_decl(Node node) {
        Node nodo1=new Node("");
        if (hayErrores) return null;
        if (preanalisis.equals(fun)) {
            /*coincidir(fun);
            Function();*/
            Statement function= Function(nodo1);
            nodo1=new Node(function.imprimir());
            node.addChild(nodo1);

            return function;

        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba 'fun'.");
            return null;
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
            //Block();
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
                    Else_statement();
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

    void While_stmt(){
        if(hayErrores) return;
        if(preanalisis.equals(mientras)){
            coincidir(mientras);
            if(preanalisis.equals(parentesis_abre)){
                coincidir(parentesis_abre);
                Expression();
                if(preanalisis.equals(parentesis_cierra)){
                    coincidir(parentesis_cierra);
                    Statement();
                }
            }
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba WHILE.");
        }
    }

    Statement Block(Node node){
        Node nodo1=new Node("");
        StmtBlock stmtblock =null;
        if(hayErrores) return null;
        if(preanalisis.equals(llave_abre)){
            coincidir(llave_abre);
            Declaracion(nodo1);
            if(preanalisis.equals(llave_cierra)){
                coincidir(llave_cierra);
                stmtblock=new StmtBlock(nodo1);
                return stmtblock;
            }
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba una LLAVE ABRIENDO.");
            return null;
        }
        return stmtblock;
    }


    /*----------------------------------------------------------------*/
/*----------------------Expresiones--------------------------------*/
    void Expression(){
        if(hayErrores) return;
        Assignment();
    }

    void Assignment(){
        if(hayErrores) return;
        Logic_or();
        Assignment_opc();
    }

    void Assignment_opc(){
        if(hayErrores) return;
        if(preanalisis.equals(igual)){
            coincidir(igual);
            Expression();
        } else { //EPSILON

        }
    }

    void Logic_or(){
        if(hayErrores) return;
        Logic_and();
        Logic_or_2();
    }

    void Logic_or_2(){
        if(hayErrores) return;
        if(preanalisis.equals(O)){
            coincidir(O);
            Logic_and();
            Logic_or_2();
        } else { //EPSILON

        }
    }

    void Logic_and(){
        if(hayErrores) return;
        Equality();
        Logic_and_2();
    }

    void Logic_and_2(){
        if(hayErrores) return;
        if(preanalisis.equals(Y)){
            coincidir(Y);
            Equality();
            Logic_and_2();
        } else { //EPSILON

        }
    }

    void Equality(){
        if(hayErrores) return;
        Comparison();
        Equality_2();
    }

    void Equality_2(){
        if(hayErrores) return;
        if(preanalisis.equals(diferente_de)){
            coincidir(diferente_de);
            Comparison();
            Equality_2();
        } else if(preanalisis.equals(igual_a)){
            coincidir(igual_a);
            Comparison();
            Equality_2();
        } else { //EPSILON

        }
    }

    void Comparison(){
        if(hayErrores) return;
        Term();
        Comparison_2();
    }

    void Comparison_2(){
        if(hayErrores) return;
        if(preanalisis.equals(mayor_a)){
            coincidir(mayor_a);
            Term();
            Comparison_2();
        } else if(preanalisis.equals(mayor_iguala)){
            coincidir(mayor_iguala);
            Term();
            Comparison_2();
        } else if(preanalisis.equals(menor_a)){
            coincidir(menor_a);
            Term();
            Comparison_2();
        } else if(preanalisis.equals(menor_iguala)){
            coincidir(menor_iguala);
            Term();
            Comparison_2();
        } else { //EPSILON

        }
    }

    void Term(){
        if(hayErrores) return;
        Factor();
        Term_2();
    }

    void Term_2(){
        if(hayErrores) return;
        if(preanalisis.equals(resta)){
            coincidir(resta);
            Factor();
            Term_2();
        } else if(preanalisis.equals(suma)){
            coincidir(suma);
            Factor();
            Term_2();
        } else { //EPSILON

        }
    }

    void Factor(){
        if(hayErrores) return;
        Unary();
        Factor_2();
    }

    void Factor_2(){
        if(hayErrores) return;
        if(preanalisis.equals(division)){
            coincidir(division);
            Unary();
            Factor_2();
        } else if(preanalisis.equals(multiplicacion)){
            coincidir(multiplicacion);
            Unary();
            Factor_2();
        } else { //EPSILON

        }
    }

    void Unary(){
        if(hayErrores) return;
        if(preanalisis.equals(neg_logica)){
            coincidir(neg_logica);
            Unary();
        } else if(preanalisis.equals(resta)){
            coincidir(resta);
            Unary();
        } else {
            Call();
        }
    }

    void Call(){
        if(hayErrores) return;
        Primary();
        Call_2();
    }

    void Call_2(){
        if(hayErrores) return;
        if(preanalisis.equals(parentesis_abre)){
            coincidir(parentesis_abre);
            Arguments_opc();
            if(preanalisis.equals(parentesis_cierra)){
                coincidir(parentesis_cierra);
            }
        } else { //EPSILON

        }
    }

    void Primary(){
        if(hayErrores) return;
        if(preanalisis.equals(verdadero)){
            coincidir(verdadero);
        } else if(preanalisis.equals(falso)){
            coincidir(falso);
        } else if(preanalisis.equals(nulo)){
            coincidir(nulo);
        } else if(preanalisis.equals(numero)){
            coincidir(numero);
        } else if(preanalisis.equals(cadena)){
            coincidir(cadena);
        } else if(preanalisis.equals(identificador)){
            coincidir(identificador);
        } else if(preanalisis.equals(parentesis_abre)){
            coincidir(parentesis_abre);
            Expression();
            if(preanalisis.equals(parentesis_cierra)){
                coincidir(parentesis_cierra);
            }
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba algun PRIMARY.");
        }
    }



/*----------------------------------------------------------------*/
/*----------------------Otras--------------------------------*/
Statement Function(Node node){
    Node node1=new Node("");
    StmtFunction functionDecl = null;
    if(hayErrores) return null;
    if(preanalisis.equals(identificador)){
        Token name = preanalisis;
        coincidir(identificador);
        if(preanalisis.equals(parentesis_abre)){
            coincidir(parentesis_abre);
            List<Token> params = Parameters_opc(node1);
            if(preanalisis.equals(parentesis_cierra)){
                coincidir(parentesis_cierra);
                Statement body = Block(node1);
                functionDecl = new StmtFunction(name, params, (StmtBlock) body);
                node1=new Node(functionDecl.imprimir());
                node.addChild(node1);
                return functionDecl;
            }
        }
    } else {
        hayErrores = true;
        System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba 'identificiador'.");
        return null;
    }
    return functionDecl;
}

    void Functions(){
        if(hayErrores) return;
        if(preanalisis.equals(identificador)){
           /* Fun_decl();
            Functions();*/
        } else { //EPSILON

        }

    }

    List<Token>  Parameters_opc(Node node){
        Node node1=new Node("");
        List<Token> params = new ArrayList<>();
        if(hayErrores) return params;;
        if(preanalisis.equals(identificador)){
            //coincidir(identificador);
            Parameters(params);
        } else { //EPSILON
            return params;
        }
        return params;
    }

    void Parameters(List<Token> params){
        if(hayErrores) return;
        if(preanalisis.equals(identificador)){
            Token paramToken = preanalisis;
            coincidir(identificador);
            params.add(paramToken);
            Parameters_2(params);
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba 'identificador'.");
        }
    }

    void Parameters_2(List<Token> parametros){
        if(hayErrores) return;
        if(preanalisis.equals(coma)){
            coincidir(coma);
            if(preanalisis.equals(identificador)){
                Token name = preanalisis;
                coincidir(identificador);
                parametros.add(name);

                Parameters_2(parametros);
            }
        } else { //EPSILON

        }

    }

    void Arguments_opc(){
        if(hayErrores) return;
        if(preanalisis.equals(neg_logica) || preanalisis.equals(resta) || preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo)  || preanalisis.equals(numero) || preanalisis.equals(cadena) || preanalisis.equals(identificador) || preanalisis.equals(parentesis_abre) ){
            Expression();
            Arguments();
        } else { //EPSILON

        }

    }

    /*void Arguments(){
        if(hayErrores) return;
        Expression();
        Arguments_2();

    }*/

    void Arguments(){
        if(hayErrores) return;
        if(preanalisis.equals(coma)){
            coincidir(coma);
            Expression();
            Arguments();
        } else { //EPSILON

        }

    }

    //AQUI TERMINA TODA LA GRAMATICA DEL PROYECTO





/*----------------------------------------------------------------*/


}