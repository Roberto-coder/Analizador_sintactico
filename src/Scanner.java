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
        char c;

        for(int i=0; i<source.length(); i++){
            c = source.charAt(i);
            //c1 = source.charAt(i+1);
            switch (estado){
                case 0:
                    if(Character.isLetter(c)){
                        estado = 13;
                        lexema += c;
                    }
                    else if(Character.isDigit(c)){
                        estado = 15;
                        lexema += c;

                        /*while(Character.isDigit(c)){
                            lexema += c;
                            i++;
                            c = source.charAt(i);
                        }
                        Token t = new Token(TipoToken.NUMBER, lexema, Integer.valueOf(lexema));
                        lexema = "";
                        estado = 0;
                        tokens.add(t);
                        */

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
                    else if(c=='/'){
                        lexema += c;
                        estado=26;

                    }
                    else if(c=='"'){
                        estado=24;
                        lexema += c;
                    }
                    break;
                case 1:
                    if(c=='='){
                        lexema += c;
                        Token t = new Token(TipoToken.GREATER_EQUAL, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }else{
                        estado=3;
                        lexema += c;
                    }
                    break;
                case 3:

                    break;
                case 4:
                    if (c=='='){
                        lexema += c;
                        Token t = new Token(TipoToken.LESS_EQUAL, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }else {
                        estado=6;
                        lexema += c;
                    }
                    break;
                case 6:
                    if (c==' '){
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
                    i--;
                }else{

                }
                break;
                case 10:
                    if (c=='='){
                        lexema += c;
                        Token t = new Token(TipoToken.BANG_EQUAL, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }else{

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
                        lexema += c;
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
                    }
                    break;
                case 17:
                    if(Character.isDigit(c)){
                        estado=17;
                        lexema += c;
                    }else if(c == 'E'){
                        estado=18;
                        lexema += c;
                    }else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Float.valueOf(lexema));
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 18:
                    if (c=='+' || c=='-'){
                        estado=19;
                        lexema += c;
                    }else if(Character.isDigit(c)){
                        estado=20;
                        lexema += c;
                    }
                    break;
                case 19:
                    if(Character.isDigit(c)){
                        estado=20;
                        lexema += c;
                    }
                    break;
                case 20:
                    if(Character.isDigit(c)){
                        estado=20;
                        lexema += c;
                    }else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Float.valueOf(lexema));
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 24:
                    if(c=='"'){
                        Token t = new Token(TipoToken.STRING, lexema.toString());
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        i--;
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
                        estado=32;
                        lexema +=c;
                        /*Token t = new Token(TipoToken.SLASH, lexema);
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        i--;*/
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
                        i--;
                    }else{
                        lexema +=c;
                        estado=27;

                    }
                    break;

                case 30:
                    if(c=='\n') {
                        lexema += c;
                    }else if(c==' '){
                        System.out.println("comentario no genera token"+lexema);

                        estado = 0;
                        lexema = "";
                        i--;
                    }else{
                        lexema+=c;
                        estado=30;

                    }
                    break;
                case 32:

                    break;
            }


        }


        return tokens;
    }
}
