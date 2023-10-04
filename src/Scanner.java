import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private static final Map<String, TipoToken> palabrasReservadas;
    //private static final Map<String, TipoToken> dos_caracteres;

    //private static final Map<String, TipoToken> un_caracter;
    static {

        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and",    TipoToken.AND);
        palabrasReservadas.put("else",   TipoToken.ELSE);
        palabrasReservadas.put("false",  TipoToken.FALSE);
        palabrasReservadas.put("for",    TipoToken.FOR);
        palabrasReservadas.put("fun",    TipoToken.FUN);
        palabrasReservadas.put("if",     TipoToken.IF);
        palabrasReservadas.put("null",   TipoToken.NULL);
        palabrasReservadas.put("or",     TipoToken.OR);
        palabrasReservadas.put("print",  TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("true",   TipoToken.TRUE);
        palabrasReservadas.put("var",    TipoToken.VAR);
        palabrasReservadas.put("while",  TipoToken.WHILE);

        palabrasReservadas.put("<",  TipoToken.LESS);
        palabrasReservadas.put("<=",  TipoToken.LESS_EQUAL);
        palabrasReservadas.put(">",  TipoToken.GREATER);
        palabrasReservadas.put(">=",  TipoToken.GREATER_EQUAL);
        palabrasReservadas.put("!=",  TipoToken.BANG_EQUAL);
        palabrasReservadas.put("!",  TipoToken.BANG);
        palabrasReservadas.put("=",  TipoToken.EQUAL);
        palabrasReservadas.put("==",  TipoToken.EQUAL_EQUAL);

        palabrasReservadas.put("+",  TipoToken.PLUS);
        palabrasReservadas.put("-",  TipoToken.MINUS);
        palabrasReservadas.put("*",  TipoToken.STAR);
        palabrasReservadas.put("/",  TipoToken.SLASH);
        palabrasReservadas.put("{",  TipoToken.LEFT_BRACE);
        palabrasReservadas.put("}",  TipoToken.RIGHT_BRACE);
        palabrasReservadas.put("(",  TipoToken.LEFT_PAREN);
        palabrasReservadas.put(")",  TipoToken.RIGHT_PAREN);
        palabrasReservadas.put(",",  TipoToken.COMMA);
        palabrasReservadas.put(".",  TipoToken.DOT);
        palabrasReservadas.put(";",  TipoToken.SEMICOLON);
        //palabrasReservadas.put(" \" ", TipoToken.DOUBLE_QUOTE);
    }

    private final String source;

    private final List<Token> tokens = new ArrayList<>();
    
    public Scanner(String source){
        this.source = source + " ";
    }

    public List<Token> scan() throws Exception {
        int estado = 0;
        String lexema = "";
        String lexema2="";
        int flag=1;
        char c;

        for(int i=0; i<source.length(); i++){
            c = source.charAt(i);
            //c1 = source.charAt(i+1);
            switch (estado){
                case 0://               Tokens operadores relacionales
                    if(Character.isLetter(c)){
                        estado = 13;
                        lexema += c;
                    }
                    else if(Character.isDigit(c)){
                        estado = 15;
                        lexema += c;

                    }
                    else if(c=='>'){
                        estado=1;
                        lexema += c;
                    }
                    else if(c=='<'){
                        estado=4;
                        lexema += c;
                    }
                    else if(c=='='){
                        estado=7;
                        lexema += c;
                    }
                    else if(c=='!'){
                        estado=10;
                        lexema += c;
                    }
                    else if(c=='/'){//          comentarios
                        lexema += c;
                        estado=26;

                    }
                    else if(c=='"'){//Strings
                        estado=24;
                        lexema += c;
                    }else if(c=='('){//          Tokens de simbolos
                        lexema += c;
                        Token t = new Token(TipoToken.LEFT_PAREN, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }else if(c==')'){
                        lexema += c;
                        Token t = new Token(TipoToken.RIGHT_PAREN, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }else if(c=='{'){
                        lexema += c;
                        Token t = new Token(TipoToken.LEFT_BRACE, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }else if(c=='}'){
                        lexema += c;
                        Token t = new Token(TipoToken.RIGHT_BRACE, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }else if(c==','){
                        lexema += c;
                        Token t = new Token(TipoToken.COMMA, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }else if(c=='.'){
                        lexema += c;
                        Token t = new Token(TipoToken.DOT, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }else if(c==';'){
                        lexema += c;
                        Token t = new Token(TipoToken.SEMICOLON, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }else if(c=='+'){//          Operadores aritmeticos
                        lexema += c;
                        Token t = new Token(TipoToken.PLUS, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }else if(c=='-'){
                        lexema += c;
                        Token t = new Token(TipoToken.MINUS, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }else if(c=='*'){
                        lexema += c;
                        Token t = new Token(TipoToken.STAR, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }

                    break;
                case 1:
                    if(c=='='){
                        lexema += c;
                        Token t = new Token(TipoToken.GREATER_EQUAL, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }else{
                        lexema += c;
                        Token t = new Token(TipoToken.GREATER, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 4:
                    if (c=='='){
                        lexema += c;
                        Token t = new Token(TipoToken.LESS_EQUAL, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }else {
                        lexema += c;
                        Token t = new Token(TipoToken.LESS, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 7:
                if (c=='='){
                    lexema += c;
                    Token t = new Token(TipoToken.EQUAL_EQUAL, lexema);
                    tokens.add(t);
                    estado = 0;
                    lexema = "";
                }else{
                    lexema += c;
                    Token t = new Token(TipoToken.EQUAL, lexema);
                    tokens.add(t);
                    estado = 0;
                    lexema = "";
                    i--;
                }
                break;
                case 10:
                    if (c=='='){
                        lexema += c;
                        Token t = new Token(TipoToken.BANG_EQUAL, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }else{
                        lexema += c;
                        Token t = new Token(TipoToken.BANG, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 13:
                    if(Character.isLetterOrDigit(c)){
                        estado = 13;
                        lexema += c;
                    }
                    else{
                        TipoToken tt = palabrasReservadas.get(lexema);

                        if(tt == null){
                            Token t = new Token(TipoToken.IDENTIFIER, lexema);
                            tokens.add(t);
                        }
                        else{
                            Token t = new Token(tt, lexema);
                            tokens.add(t);
                        }

                        estado = 0;
                        lexema = "";
                        i--;

                    }
                    break;

                case 15:
                    if(Character.isDigit(c)){
                        estado = 15;
                        lexema += c;
                    }
                    else if(c == '.'){
                        estado=16;
                        lexema += c;
                    }
                    else if(c == 'E'){
                        estado=18;
                        //lexema += c;
                    }
                    else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Integer.valueOf(lexema));
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 16:
                    if(Character.isDigit(c)){
                        estado=17;
                        lexema += c;
                    }else{
                        lexema += c;
                        System.out.println(lexema+" no es estado de aceptacion, no genera token");
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 17:
                    if(Character.isDigit(c)){
                        estado=17;
                        lexema += c;
                    }else if(c == 'E'){
                        estado=18;
                        //lexema += c;
                    }else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Float.valueOf(lexema));
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 18:
                    if (c == '+') {
                        estado = 19;
                        lexema2 += c;
                        flag=flag*1;
                    } else if (c == '-') {
                        estado = 19;
                        lexema2 += c;
                        flag=flag*(-1);
                    } else if (Character.isDigit(c)) {
                        estado = 20;
                        lexema2 += c;
                    } else {
                        lexema += c;
                        System.out.println(lexema+" no es estado de aceptacion, no genera token");
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 19:
                    if(Character.isDigit(c)){
                        estado=20;
                        lexema2 += c;
                    }else{
                        lexema += c;
                        System.out.println(lexema+" no es estado de aceptacion, no genera token");
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 20:
                    if(Character.isDigit(c)){
                        estado=20;
                        lexema2 += c;
                    }else{
                        long exponenteL=1L;
                        double exponenteD;
                        double base=10.0;
                        double num1=Double.parseDouble(lexema);
                        double num2=Double.parseDouble(lexema2);
                        exponenteD=Math.pow(base,num2);
                        System.out.println(exponenteD);
                        if(flag==-1){

                            Token t = new Token(TipoToken.NUMBER, lexema+"E"+lexema2, num1*exponenteD);
                            tokens.add(t);

                            estado = 0;
                            lexema = "";
                            i--;
                        }else{

                            exponenteL= (long) ((long)exponenteD*num1);
                            Token t = new Token(TipoToken.NUMBER, lexema+"E"+lexema2, exponenteL);
                            tokens.add(t);

                            estado = 0;
                            lexema = "";
                            i--;
                        }


                    }
                    break;
                case 24:
                    if(c=='"'){
                        lexema += c;
                        Token t = new Token(TipoToken.STRING, lexema,lexema.replace("\"", ""));
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                    }else{
                        estado=24;
                        lexema +=c;
                    }
                    break;
                case 26:
                    if(c=='*'){
                        lexema +=c;
                        estado=27;

                    }else if(c=='/'){
                        lexema +=c;
                        estado=30;

                    }else{
                        lexema +=c;
                        Token t = new Token(TipoToken.SLASH, lexema);
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 27:
                    if(c=='*'){
                        lexema += c;
                        estado=28;

                    }else{
                        lexema+=c;
                        estado=27;

                    }
                    break;
                case 28:
                    if(c=='*'){
                        lexema += c;
                        estado = 28;

                    }else if(c=='/'){
                        lexema += c;
                        System.out.println("comentario no genera token"+lexema);
                        estado = 0;
                        lexema = "";
                    }else{
                        lexema +=c;
                        estado=27;

                    }
                    break;

                case 30:
                    if(c=='\n') {
                        lexema += c;
                        System.out.println("comentario no genera token"+lexema);
                        estado = 0;
                        lexema = "";
                    }else{
                        lexema+=c;
                        estado=30;

                    }
                    break;
            }


        }


        return tokens;
    }
}
