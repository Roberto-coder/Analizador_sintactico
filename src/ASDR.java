import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
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

    private List<Statement> nodos;
    private TablaSimbolos tablita = new TablaSimbolos();

    public ASDR(List<Token> tokens) {
        this.tokens = tokens;
        preanalisis = this.tokens.get(i);
        nodos = new ArrayList<>();
    }

    /*----------------------PROGRAM--------------------------------*/
    public boolean parse() {
        nodos=Program();

        if (preanalisis.tipo == TipoToken.EOF && !hayErrores) {
            System.out.println("Consulta correcta");
            evaluateStatements(nodos);
            return true;
        } else {
            System.out.println("Se encontraron errores");
        }
        return false;
    }


    public List<Statement> Program() {
        nodos.clear();
        preanalisis = tokens.get(i);

        Declaracion(nodos);

        if (!hayErrores && !preanalisis.equals(finCadena)) {
            System.out.println("Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
        } else if (!hayErrores && preanalisis.equals(finCadena)) {
            System.out.println("Consulta válida");
        }
        return nodos;
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

    void Declaracion(List<Statement> nodos) {
        if (hayErrores) return;
        if (preanalisis.equals(fun)) {
            Statement funDecl=Fun_decl();
            nodos.add(funDecl);
            Declaracion(nodos);
        } else if (preanalisis.equals(var)) {
            Statement varDecl = Var_decl();
            nodos.add(varDecl);
            Declaracion(nodos);
        }  else if(preanalisis.equals(neg_logica) || preanalisis.equals(resta) || preanalisis.equals(para) || preanalisis.equals(si) || preanalisis.equals(imprimir) || preanalisis.equals(regresa) || preanalisis.equals(mientras) || preanalisis.equals(llave_abre) ||  preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo) || preanalisis.equals(numero) || preanalisis.equals(cadena) || preanalisis.equals(identificador) || preanalisis.equals(parentesis_abre)){
            Statement stmt = Statement();
            nodos.add(stmt);
            Declaracion(nodos);
        }  else { //EPSILON

        }
        /*
        if(!preanalisis.equals(finCadena)){
            System.out.println("Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
        }else if(!hayErrores){
            System.out.println("Consulta válida");
        }*/
    }

    Statement Fun_decl() {
        if (hayErrores) return null;
        if (preanalisis.equals(fun)) {
            coincidir(fun);
            Statement funDecl=Function();
            return funDecl;
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba 'fun'.");
            return null;
        }
    }

    Statement Var_decl() {
        if (hayErrores) return null;
        if (preanalisis.equals(var)) {
            coincidir(var);
            if (preanalisis.equals(identificador)) {
                Token name = preanalisis;
                coincidir(identificador);
                Expression initializer = Var_init();
                coincidir(puntoycoma);
                return new StmtVar(name, initializer);
            }
            return null;
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba una 'var'.");
            return null;
        }
    }

    Expression Var_assign2() {
        if (preanalisis.tipo == TipoToken.IDENTIFIER) {
            Token name = preanalisis;

            coincidir(identificador);
            coincidir(igual);
            Expression value = Expression(); // Método que analiza el lado derecho de la asignación
            return new ExprAssign(name, value);
        }
        return null;
    }

    Expression Var_init() {
        if (hayErrores) return null;
        if (preanalisis.equals(igual)) {
            coincidir(igual);
            Expression initializer =Expression();
            return initializer;
        } else { //EPSILON

        }
        return null;
    }


/*---------------------------------------------------------------------*/
/*----------------------SENTENCIAS--------------------------------*/
Statement Statement(){
        if(hayErrores) return null;
        if(preanalisis.equals(neg_logica) || preanalisis.equals(resta) || preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo) || preanalisis.equals(numero) || preanalisis.equals(cadena) || preanalisis.equals(identificador) || preanalisis.equals(parentesis_abre) ){
            Statement expr = Expr_stmt();
            return expr;
        } else if(preanalisis.equals(para)){
            Statement forStmt = For_stmt();
            return forStmt;
        } else if(preanalisis.equals(si)){
            Statement if1 = If_stmt();
            return if1;
        }else if(preanalisis.equals(imprimir)){
            Statement print = Print_stmt();
            return print;
        } else if(preanalisis.equals(regresa)){
            Statement return1 = Return_stmt();
            return return1;
        } else if(preanalisis.equals(mientras)){
            Statement while1 = While_stmt();
            return while1;
        } else if(preanalisis.equals(llave_abre)){
            Statement block = Block();
            return block;
        }else {
            hayErrores = true;
            System.out.println("Error");
            return null;
        }
    }
    Statement Expr_stmt(){
        if(hayErrores) return null;
        Expression expr =Expression();
        if(preanalisis.equals(puntoycoma)){
            coincidir(puntoycoma);
            return new StmtExpression(expr);
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba ';'.");
        }

        return null;
    }
    Statement For_stmt(){
        Statement body=null;
        if(hayErrores) return null;
        if(preanalisis.equals(para)){
            coincidir(para);
            if(preanalisis.equals(parentesis_abre)){
                coincidir(parentesis_abre);
                Statement initializer = For_stmt_1();
                Expression condition = For_stmt_2();
                Expression increment = For_stmt_3();
                if(preanalisis.equals(parentesis_cierra)){
                    coincidir(parentesis_cierra);
                    body = Statement();
                }

                if (increment != null) {
                    body = new StmtBlock(Arrays.asList(body, new StmtExpression(increment)));
                }

                if (condition == null) {
                    condition = new ExprLiteral(true);
                }

                body = new StmtLoop(condition, body);

                if (initializer != null) {
                    body = new StmtBlock(Arrays.asList(initializer, body));
                }


            }
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba 'for'.");
        }
        return body;
    }
    Statement For_stmt_1(){
        if(hayErrores) return null;
        if(preanalisis.equals(var)){
            return Var_decl();
        } else if(preanalisis.equals(neg_logica) || preanalisis.equals(resta) || preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo)|| preanalisis.equals(numero) || preanalisis.equals(cadena) || preanalisis.equals(identificador) || preanalisis.equals(parentesis_abre) ){ //************************************
            Statement stmt =Expr_stmt();
            return stmt;
        } else if(preanalisis.equals(puntoycoma)){
            coincidir(puntoycoma);
            return null;
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba 'var', una 'expresion' ó ';'.");
            return null;
        }

    }

    Expression For_stmt_2(){
        Expression expr;
        if(hayErrores) return null;
        if(preanalisis.equals(neg_logica) || preanalisis.equals(resta) || preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo) || preanalisis.equals(numero) || preanalisis.equals(cadena) || preanalisis.equals(identificador) || preanalisis.equals(parentesis_abre)){ //********************************************
            expr = Expression();
            if(preanalisis.equals(puntoycoma)){
                coincidir(puntoycoma);
                return expr;
            }
        } else if(preanalisis.equals(puntoycoma)){
            coincidir(puntoycoma);
            return null;
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba 'for' o ';'.");
        }
        return null;
    }

    Expression For_stmt_3(){
        if(hayErrores) return null;
        if(preanalisis.equals(neg_logica) || preanalisis.equals(resta) || preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo) || preanalisis.equals(numero) || preanalisis.equals(cadena) || preanalisis.equals(identificador) || preanalisis.equals(parentesis_abre)){
            return Expression();
        } else { //EPSILON

        }
        return null;
    }

    Statement If_stmt(){
        if(hayErrores) return null;
        if(preanalisis.equals(si)){
            coincidir(si);
            if(preanalisis.equals(parentesis_abre)){
                coincidir(parentesis_abre);
                Expression condition = Expression();
                if(preanalisis.equals(parentesis_cierra)){
                    coincidir(parentesis_cierra);
                    Statement thenBranch =Statement();
                    Statement elseBranch = Else_statement();
                    return new StmtIf(condition, thenBranch, elseBranch);
                }
            }
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba 'if'.");
        }
        return null;
    }
    Statement Else_statement(){
        if(hayErrores) return null;
        if(preanalisis.equals(de_otro_modo)){
            coincidir(de_otro_modo);
            return Statement();
        } else { //EPSILON

        }

        return null;
    }

    Statement Print_stmt(){
        if(hayErrores) return null;
        if(preanalisis.equals(imprimir)){
            coincidir(imprimir);
            Expression expr = Expression();
            if(preanalisis.equals(puntoycoma)){
                coincidir(puntoycoma);
                return new StmtPrint(expr);
            }
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba 'print'.");
        }
        return null;
    }

    Statement Return_stmt(){
        Expression expr = null;
        if(hayErrores) return null;
        if(preanalisis.equals(regresa)){
            coincidir(regresa);
            expr =Return_exp_opc(expr);
            if(preanalisis.equals(puntoycoma)){
                coincidir(puntoycoma);
                return new StmtReturn(expr);
            }
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba 'return'.");
            return null;
        }
        return null;
    }
    Expression Return_exp_opc(Expression expr){
        if(hayErrores) return null;
        if(preanalisis.equals(neg_logica) || preanalisis.equals(resta) || preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo) || preanalisis.equals(numero) || preanalisis.equals(cadena) || preanalisis.equals(identificador) || preanalisis.equals(parentesis_abre) ){ //*********
            expr=Expression();
            return expr;
        } else { //EPSILON

        }
        return expr;
    }

    Statement While_stmt(){
        if(hayErrores) return null;
        if(preanalisis.equals(mientras)){
            coincidir(mientras);
            if(preanalisis.equals(parentesis_abre)){
                coincidir(parentesis_abre);
                Expression condition =Expression();
                if(preanalisis.equals(parentesis_cierra)){
                    coincidir(parentesis_cierra);
                    Statement body = Statement();
                    return new StmtLoop(condition, body);
                }
            }
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba WHILE.");
        }
        return null;
    }

    Statement Block(){
        List<Statement> nodos = new ArrayList<>();
        StmtBlock stmtblock=new StmtBlock(nodos);
        if(hayErrores) return null;
        if(preanalisis.equals(llave_abre)){
            coincidir(llave_abre);
            Declaracion(nodos);

            if(preanalisis.equals(llave_cierra)){
                coincidir(llave_cierra);
                return stmtblock;
            }
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba una LLAVE ABRIENDO.");
            return null;
        }
        return null;
    }

    /*void Block_decl(){
        if(hayErrores) return;
        if(preanalisis.equals(fun) || preanalisis.equals(var) || preanalisis.equals(para) || preanalisis.equals(si) || preanalisis.equals(imprimir) || preanalisis.equals(para) || preanalisis.equals(regresa) || preanalisis.equals(mientras) || preanalisis.equals(llave_abre) || preanalisis.equals(neg_logica) || preanalisis.equals(resta)){
            Declaracion();
            Block_decl();
        } else { //EPSILON

        }

    }*/

    /*----------------------------------------------------------------*/
/*----------------------Expresiones--------------------------------*/
    Expression Expression(){

        if (preanalisis.tipo == TipoToken.IDENTIFIER && siguiente().tipo == TipoToken.EQUAL) {
            return Var_assign2();
        }

        if(hayErrores) return null;
        Expression expr =Assignment();
        return expr;
    }

    Expression Assignment(){
        if(hayErrores) return null;
        Expression expr = Logic_or();
        expr = Assignment_opc(expr);
        return expr;
    }

    Expression Assignment_opc(Expression expr){
        if(hayErrores) return null;
        if(preanalisis.equals(igual)){
            if (!(expr instanceof ExprVariable)) {
                throw new RuntimeException("El lado izquierdo de una asignación debe ser una variable.");
            }
            Token operadorL = preanalisis;
            coincidir(igual);
            Expression expr1 = Expression();
            return new ExprAssign(operadorL, expr1);
        } else { //EPSILON

        }
        return expr;
    }

    Expression Logic_or(){
        if(hayErrores) return null;
        Expression expr = Logic_and();
        expr = Logic_or_2(expr);
        return expr;
    }

    Expression Logic_or_2(Expression expr){
        if(hayErrores) return null;
        if(preanalisis.equals(O)){
            Token operadorL =preanalisis;
            coincidir(O);
            Expression expr2 =Logic_and();
            ExprLogical expl = new ExprLogical(expr, operadorL, expr2);
            return Logic_or_2(expl);
        } else { //EPSILON

        }
        return expr;
    }

    Expression Logic_and(){
        if(hayErrores) return null;
        Expression expr = Equality();
        expr =Logic_and_2(expr);
        return expr;
    }

    Expression Logic_and_2(Expression expr){
        if(hayErrores) return null;
        if(preanalisis.equals(Y)){
            Token operadorL = preanalisis;
            coincidir(Y);
            Expression expr2 = Equality();
            ExprLogical expl = new ExprLogical(expr, operadorL, expr2);
            Logic_and_2(expl);
        } else { //EPSILON

        }
        return expr;
    }

    Expression Equality(){
        if(hayErrores) return null;
        Expression expr = Comparison();
        expr = Equality_2(expr);
        return expr;
    }

    Expression Equality_2(Expression expr){
        if(hayErrores) return null;
        if(preanalisis.equals(diferente_de)){
            Token operador = preanalisis;
            coincidir(diferente_de);
            Expression expr2 = Comparison();
            ExprBinary expb = new ExprBinary(expr, operador, expr2,tablita);
            return Equality_2(expb);
        } else if(preanalisis.equals(igual_a)){
            Token operador = preanalisis;
            coincidir(igual_a);
            Expression expr2 =Comparison();
            ExprBinary expb = new ExprBinary(expr, operador, expr2,tablita);
            return Equality_2(expb);
        } else { //EPSILON

        }
        return expr;
    }

    Expression Comparison(){
        if(hayErrores) return null;
        Expression expr = Term();
        expr = Comparison_2(expr);
        return expr;
    }

    Expression Comparison_2(Expression expr){
        if(hayErrores) return null;
        if(preanalisis.equals(mayor_a)){
            Token operador = preanalisis;
            coincidir(mayor_a);
            Expression expr2 = Term();
            ExprBinary expb = new ExprBinary(expr, operador, expr2,tablita);
            return Comparison_2(expb);
        } else if(preanalisis.equals(mayor_iguala)){
            Token operador = preanalisis;
            coincidir(mayor_iguala);
            Expression expr2 = Term();
            ExprBinary expb = new ExprBinary(expr, operador, expr2,tablita);
            return Comparison_2(expb);
        } else if(preanalisis.equals(menor_a)){
            Token operador = preanalisis;
            coincidir(menor_a);
            Expression expr2 = Term();
            ExprBinary expb = new ExprBinary(expr, operador, expr2,tablita);
            return Comparison_2(expb);
        } else if(preanalisis.equals(menor_iguala)){
            Token operador = preanalisis;
            coincidir(menor_iguala);
            Expression expr2 = Term();
            ExprBinary expb = new ExprBinary(expr, operador, expr2,tablita);
            return Comparison_2(expb);
        } else { //EPSILON

        }
        return expr;
    }

    Expression Term(){
        if(hayErrores) return null;
        Expression expr = Factor();
        expr = Term_2(expr);
        return expr;
    }

    Expression Term_2(Expression expr){
        if(hayErrores) return null;
        if(preanalisis.equals(resta)){
            Token operador = preanalisis;
            coincidir(resta);
            Expression expr2=Factor();
            ExprBinary expb = new ExprBinary(expr, operador, expr2,tablita);
            return Term_2(expb);
            //return FACTOR_2(expb);
        } else if(preanalisis.equals(suma)){
            Token operador = preanalisis;
            coincidir(suma);
            Expression expr2=Factor();
            ExprBinary expb = new ExprBinary(expr, operador, expr2,tablita);
            return Term_2(expb);
            //return FACTOR_2(expb);
        } else { //EPSILON

        }
        return expr;
    }

    Expression Factor(){
        if(hayErrores) return null;
        Expression expr = Unary();
        expr = Factor_2(expr);
        return expr;
    }

    Expression Factor_2(Expression expr){
        if(hayErrores) return null;
        if(preanalisis.equals(division)){
            Token operador = preanalisis;
            coincidir(division);
            Expression expr2 = Unary();
            ExprBinary expb = new ExprBinary(expr, operador, expr2,tablita);
            return Factor_2(expb);
        } else if(preanalisis.equals(multiplicacion)){
            Token operador = preanalisis;
            coincidir(multiplicacion);
            Expression expr2 = Unary();
            ExprBinary expb = new ExprBinary(expr, operador, expr2,tablita);
            return Factor_2(expb);
        } else { //EPSILON

        }
        return expr;
    }

    Expression Unary(){
        if(hayErrores) return null;
        if(preanalisis.equals(neg_logica)){
            Token operador = preanalisis;
            coincidir(neg_logica);
            Expression expr = Unary();
            return new ExprUnary(operador, expr);
        } else if(preanalisis.equals(resta)){
            Token operador = preanalisis;
            coincidir(resta);
            Expression expr = Unary();
            return new ExprUnary(operador, expr);
        } else {
            return Call();
        }
    }

    Expression Call(){
        if(hayErrores) return null;
        Expression expr = Primary();
        expr = Call_2(expr);
        return expr;
    }

    Expression Call_2(Expression expr){
        if(hayErrores) return null;
        if(preanalisis.equals(parentesis_abre)){
            coincidir(parentesis_abre);
            List<Expression> lstArguments =Arguments_opc();
            if(preanalisis.equals(parentesis_cierra)){
                coincidir(parentesis_cierra);
                ExprCallFunction ecf = new ExprCallFunction(expr, lstArguments,tablita);
                return ecf;
            }
        } else { //EPSILON

        }
        return expr;
    }

    Expression Primary(){
        if(hayErrores) return null;
        if(preanalisis.equals(verdadero)){
            coincidir(verdadero);
            return new ExprLiteral(true);
        } else if(preanalisis.equals(falso)){
            coincidir(falso);
            return new ExprLiteral(false);
        } else if(preanalisis.equals(nulo)){
            coincidir(nulo);
            return new ExprLiteral(null);
        } else if(preanalisis.equals(numero)){
            Token numero = preanalisis;
            coincidir(numero);
            return new ExprLiteral(numero.literal);
        } else if(preanalisis.equals(cadena)){
            Token cadena = preanalisis;
            coincidir(cadena);
            return new ExprLiteral(cadena.literal);
        } else if(preanalisis.equals(identificador)){
            Token id = preanalisis;
            coincidir(identificador);
            return new ExprVariable(id,tablita);
        } else if(preanalisis.equals(parentesis_abre)){
            coincidir(parentesis_abre);
            Expression expr = Expression();
            if(preanalisis.equals(parentesis_cierra)){
                coincidir(parentesis_cierra);
                return new ExprGrouping(expr);
            }
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba algun PRIMARY.");
            return null;
        }
        return null;
    }



/*----------------------------------------------------------------*/
/*----------------------Otras--------------------------------*/
Statement Function(){
    StmtFunction functionDecl = null;
    if(hayErrores) return null;
    if(preanalisis.equals(identificador)){
        Token name = preanalisis;
        coincidir(identificador);
        if(preanalisis.equals(parentesis_abre)){
            coincidir(parentesis_abre);
            List<Token> params = Parameters_opc();
            if(preanalisis.equals(parentesis_cierra)){
                coincidir(parentesis_cierra);
                Statement body = Block();
                functionDecl = new StmtFunction(name, params, (StmtBlock) body);
                return functionDecl;
            }
        }
    } else {
        hayErrores = true;
        System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba 'identificiador'.");
        return null;
    }
    return null;
}

    void Functions(){
        if(hayErrores) return;
        if(preanalisis.equals(fun)){
            Fun_decl();
            Functions();
        } else { //EPSILON

        }

    }

    List<Token> Parameters_opc(){
        List<Token> params = new ArrayList<>();
        if(hayErrores) return null;
        if(preanalisis.equals(identificador)){
            //coincidir(identificador);
            Parameters(params);
            return params;
        } else { //EPSILON

        }
        return null;
    }

    List<Token> Parameters(List<Token> params){
        if(hayErrores) return null;
        if(preanalisis.equals(identificador)){
            Token paramToken = preanalisis;
            coincidir(identificador);
            params.add(paramToken);
            Parameters_2(params);
        } else {
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba 'identificador'.");
        }
        return null;
    }

    List<Token> Parameters_2(List<Token> parametros){
        if(hayErrores) return null;
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
        return parametros;
    }

    List<Expression> Arguments_opc(){
        List<Expression> arguments = new ArrayList<>();
        if(hayErrores) return null;
        if(preanalisis.equals(neg_logica) || preanalisis.equals(resta) || preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo)  || preanalisis.equals(numero) || preanalisis.equals(cadena) || preanalisis.equals(identificador) || preanalisis.equals(parentesis_abre) ){
            arguments.add(Expression());
            Arguments(arguments);
            return arguments;
        } else { //EPSILON

        }
        return null;
    }

    /*void Arguments(){
        if(hayErrores) return;
        Expression();
        Arguments_2();

    }*/

    List<Expression> Arguments(List<Expression> arguments){
        if(hayErrores) return null;
        if (preanalisis.equals(coma)) {
            coincidir(coma);
            arguments.add(Expression());
            Arguments(arguments);
        }
        return arguments;

    }

    //AQUI TERMINA TODA LA GRAMATICA DEL PROYECTO


/*----------------------------------------------------------------*/

    /*public void printTree() {
        for (Statement stmt : nodos) {
            stmt.imprimir("\t");
        }
    }*/
    private void evaluateStatements(List<Statement> nodos) {
        for (Statement stmt : nodos) {
            stmt.evaluate(tablita);
        }
    }

    private Token siguiente() {
        if (i < tokens.size() - 1) {
            return tokens.get(i + 1);
        } else {
            return new Token(TipoToken.EOF, "");
        }
    }

}