package analizadores;

import java_cup.runtime.Symbol;
import java.util.LinkedList;

%%

// ================= DIRECTRICES ==================
%class Lexico
%public
%line
%column
%cup
%unicode

%{
    public LinkedList<String> listaErrores = new LinkedList<>();
    public LinkedList<TokenInfo> listaTokens = new LinkedList<>();

    private Symbol simbolo(int tipo) {
        return new Symbol(tipo, yyline + 1, yycolumn + 1, yytext());
    }

    private Symbol simbolo(int tipo, Object valor) {
        return new Symbol(tipo, yyline + 1, yycolumn + 1, valor);
    }

    private void imprimir(String token) {
        System.out.println(
            "Token: " + token +
            " | Lexema: '" + yytext() + "'" +
            " | Linea: " + (yyline + 1) +
            " | Columna: " + (yycolumn + 1)
        );

        listaTokens.add(new TokenInfo(
            token,
            yytext(),
            yyline + 1,
            yycolumn + 1
        ));
    }

    private void agregarError(String descripcion) {
        listaErrores.add(
            "Tipo: Léxico | Descripción: " + descripcion +
            " | Línea: " + (yyline + 1) +
            " | Columna: " + (yycolumn + 1)
        );
    }
%}

// ================= EXPRESIONES REGULARES =================
letra = [a-zA-Z]
digito = [0-9]
id = {letra}({letra}|{digito}|_)*
entero = -?{digito}+
decimal = -?{digito}+"."{digito}+
cadena = \"([^\\\"\n]|\\.)*\"
comentarioLinea = "##".*
comentarioMulti = "#*"([^#]|#([^*]))*"#*"

blancos = [ \t\r\n\f]+

%%

// ================= PALABRAS RESERVADAS =================
"database"   { imprimir("DATABASE"); return simbolo(sym.DATABASE); }
"use"        { imprimir("USE"); return simbolo(sym.USE); }
"table"      { imprimir("TABLE"); return simbolo(sym.TABLE); }
"read"       { imprimir("READ"); return simbolo(sym.READ); }
"fields"     { imprimir("FIELDS"); return simbolo(sym.FIELDS); }
"filter"     { imprimir("FILTER"); return simbolo(sym.FILTER); }
"store"      { imprimir("STORE"); return simbolo(sym.STORE); }
"at"         { imprimir("AT"); return simbolo(sym.AT); }
"export"     { imprimir("EXPORT"); return simbolo(sym.EXPORT); }
"add"        { imprimir("ADD"); return simbolo(sym.ADD); }
"update"     { imprimir("UPDATE"); return simbolo(sym.UPDATE); }
"set"        { imprimir("SET"); return simbolo(sym.SET); }
"clear"      { imprimir("CLEAR"); return simbolo(sym.CLEAR); }

"int"        { imprimir("TINT"); return simbolo(sym.TINT); }
"float"      { imprimir("TFLOAT"); return simbolo(sym.TFLOAT); }
"bool"       { imprimir("TBOOL"); return simbolo(sym.TBOOL); }
"string"     { imprimir("TSTRING"); return simbolo(sym.TSTRING); }
"array"      { imprimir("TARRAY"); return simbolo(sym.TARRAY); }
"object"     { imprimir("TOBJECT"); return simbolo(sym.TOBJECT); }

"true"       { imprimir("TRUE"); return simbolo(sym.TRUE); }
"false"      { imprimir("FALSE"); return simbolo(sym.FALSE); }
"null"       { imprimir("NULL"); return simbolo(sym.NULL); }

// ================= SIGNOS =================
"{"          { imprimir("LLAVE_IZQ"); return simbolo(sym.LLAVE_IZQ); }
"}"          { imprimir("LLAVE_DER"); return simbolo(sym.LLAVE_DER); }
"("          { imprimir("PAR_IZQ"); return simbolo(sym.PAR_IZQ); }
")"          { imprimir("PAR_DER"); return simbolo(sym.PAR_DER); }
"["          { imprimir("COR_IZQ"); return simbolo(sym.COR_IZQ); }
"]"          { imprimir("COR_DER"); return simbolo(sym.COR_DER); }
":"          { imprimir("DOS_PUNTOS"); return simbolo(sym.DOS_PUNTOS); }
";"          { imprimir("PT_COMA"); return simbolo(sym.PTCOMA); }
","          { imprimir("COMA"); return simbolo(sym.COMA); }

// ================= OPERADORES RELACIONALES =================
"=="         { imprimir("IGUALIGUAL"); return simbolo(sym.IGUALIGUAL); }
"!="         { imprimir("DIFERENTE"); return simbolo(sym.DIFERENTE); }
">="         { imprimir("MAYORIGUAL"); return simbolo(sym.MAYORIGUAL); }
"<="         { imprimir("MENORIGUAL"); return simbolo(sym.MENORIGUAL); }
">"          { imprimir("MAYOR"); return simbolo(sym.MAYOR); }
"<"          { imprimir("MENOR"); return simbolo(sym.MENOR); }
"="          { imprimir("IGUAL"); return simbolo(sym.IGUAL); }
"*"          { imprimir("ASTERISCO"); return simbolo(sym.ASTERISCO); }

// ================= OPERADORES LOGICOS =================
"&&"         { imprimir("AND"); return simbolo(sym.AND); }
"||"         { imprimir("OR"); return simbolo(sym.OR); }
"!"          { imprimir("NOT"); return simbolo(sym.NOT); }

// ================= LITERALES =================
{decimal}    { imprimir("DECIMAL"); return simbolo(sym.DECIMAL, yytext()); }
{entero}     { imprimir("ENTERO"); return simbolo(sym.ENTERO, yytext()); }
{cadena}     { imprimir("CADENA"); return simbolo(sym.CADENA, yytext()); }

// ================= IDENTIFICADORES =================
{id}         { imprimir("ID"); return simbolo(sym.ID, yytext()); }

// ================= COMENTARIOS =================
{comentarioLinea}  { imprimir("COMENTARIO_LINEA"); }
{comentarioMulti}  { imprimir("COMENTARIO_MULTI"); }

// ================= ESPACIOS =================
{blancos}    { /* ignorar */ }

// ================= FIN DE ARCHIVO =================
<<EOF>>      { return new Symbol(sym.EOF); }

// ================= ERRORES =================
. {
    agregarError("El lexema '" + yytext() + "' no pertenece al lenguaje.");
}