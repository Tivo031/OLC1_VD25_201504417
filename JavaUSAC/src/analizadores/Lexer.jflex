package analizadores;

import java_cup.runtime.Symbol;


%%
//  Código Java que sera insertado dentro de la clase scanner
// variable para almacenar los caracteres de una cadena
%{
    
    String cadena = "";
%}

// valor inicial de lineas y columnas
%init{
    yyline = 1;
    yycolumn = 1;
%init}

%cup  // indica que trabajará con CUP
%class scanner //nombre de la clase
%public //acceso de la clase
%line //conteo de lineas
%column //conteo de columnas
%char // conteo de caracteres
%full //reconocimiento de caracteres
%debug //depuracion
%ignorecase // ignora mayúsculas/minúsculas al reconocer tokens

// estado cadena para leer texto entre comillas dobles
%state CADENA

// EXPRESIONES REGULARES
ENTERO = [0-9]+
DECIMAL = [0-9]+"."[0-9]+
BLANCOS = [\ \r\t\n\f]+
CHAR = \'([^\'\\]|\\.)\'

//SIMBOLOS
PAR1 = "("
PAR2 = ")"
LLAVE1 = "{"
LLAVE2 = "}"
COR1 = "["
COR2 = "]"
PUNTO = "."
COMA = ","
DOSPUNTOS = ":"
FINCADENA = ";"


// ARITMETICOS
MAS = "+"
MENOS = "-"
MULT= "*"
DIV = "/"
MOD = "%"
POT = "**"

// LOGICOS
AND = "&&"
OR = "||"
NOT = "!"

// RELACIONALES
IGUALIGUAL = "=="
DIFERENTE = "!="
MENORIGUAL = "<="
MAYORIGUAL = ">="
MENOR = "<"
MAYOR = ">"
ASIGNACION = "="


// PALABRAS RESERVADAS
PRINT = "print"
TRUE="true"
FALSE="false"

%%

// REGLAS LEXICAS

//EXPRESIONES REGULARES
<YYINITIAL> {BLANCOS} {}
<YYINITIAL> {DECIMAL} {return new Symbol(sym.DECIMAL, yyline, yycolumn, yytext());}
<YYINITIAL> {ENTERO} {return new Symbol(sym.ENTERO, yyline, yycolumn, yytext());}
<YYINITIAL> {CHAR} {
    String texto = yytext();       // 'A'
    char contenido = texto.charAt(1);  // A
    return new Symbol(sym.CHAR, yyline, yycolumn, contenido);
}

//SIMBOLOS
<YYINITIAL> {PAR1} {return new Symbol(sym.PAR1, yyline, yycolumn, yytext());}
<YYINITIAL> {PAR2} {return new Symbol(sym.PAR2, yyline, yycolumn, yytext());}
<YYINITIAL> {LLAVE1} {return new Symbol(sym.LLAVE1, yyline, yycolumn, yytext());}
<YYINITIAL> {LLAVE2} {return new Symbol(sym.LLAVE2, yyline, yycolumn, yytext());}
<YYINITIAL> {COR1} {return new Symbol(sym.COR1, yyline, yycolumn, yytext());}
<YYINITIAL> {COR2} {return new Symbol(sym.COR2, yyline, yycolumn, yytext());}
<YYINITIAL> {PUNTO} {return new Symbol(sym.PUNTO, yyline, yycolumn, yytext());}
<YYINITIAL> {COMA} {return new Symbol(sym.COMA, yyline, yycolumn, yytext());}
<YYINITIAL> {DOSPUNTOS} {return new Symbol(sym.DOSPUNTOS, yyline, yycolumn, yytext());}
<YYINITIAL> {FINCADENA} {return new Symbol(sym.FINCADENA, yyline, yycolumn, yytext());}

// ARITMETICOS
<YYINITIAL> {MAS} {return new Symbol(sym.MAS, yyline, yycolumn, yytext());}
<YYINITIAL> {MENOS} {return new Symbol(sym.MENOS, yyline, yycolumn, yytext());}
<YYINITIAL> {MULT} {return new Symbol(sym.MULT, yyline, yycolumn, yytext());}
<YYINITIAL> {DIV} {return new Symbol(sym.DIV, yyline, yycolumn, yytext());}
<YYINITIAL> {MOD} {return new Symbol(sym.MOD, yyline, yycolumn, yytext());}
<YYINITIAL> {POT} {return new Symbol(sym.POT, yyline, yycolumn, yytext());}

// LOGICOS
<YYINITIAL> {AND} {return new Symbol(sym.AND, yyline, yycolumn, yytext());}
<YYINITIAL> {OR} {return new Symbol(sym.OR, yyline, yycolumn, yytext());}
<YYINITIAL> {NOT} {return new Symbol(sym.NOT, yyline, yycolumn, yytext());}

// RELACIONALES
<YYINITIAL> {IGUALIGUAL} {return new Symbol(sym.IGUALIGUAL, yyline, yycolumn, yytext());}
<YYINITIAL> {DIFERENTE} {return new Symbol(sym.DIFERENTE, yyline, yycolumn, yytext());}
<YYINITIAL> {MENORIGUAL} {return new Symbol(sym.MENORIGUAL, yyline, yycolumn, yytext());}
<YYINITIAL> {MAYORIGUAL} {return new Symbol(sym.MAYORIGUAL, yyline, yycolumn, yytext());}
<YYINITIAL> {MENOR} {return new Symbol(sym.MENOR, yyline, yycolumn, yytext());}
<YYINITIAL> {MAYOR} {return new Symbol(sym.MAYOR, yyline, yycolumn, yytext());}
<YYINITIAL> {ASIGNACION} {return new Symbol(sym.ASIGNACION, yyline, yycolumn, yytext());}


// PALABRAS RESERVADAS
<YYINITIAL> {PRINT} {return new Symbol(sym.PRINT, yyline, yycolumn, yytext());}
<YYINITIAL> {TRUE} {return new Symbol(sym.TRUE, yyline, yycolumn,yytext());}
<YYINITIAL> {FALSE} {return new Symbol(sym.FALSE, yyline, yycolumn,yytext());}

//  Detección del inicio de una cadena. Cuando encuentra una comilla, cambia al estado CADENA
// yybegin(CADENA); -> inicia el estado de la cadena
<YYINITIAL> [\"]        {cadena = ""; yybegin(CADENA);} 

<CADENA> {
    [\"]    {String tmp= cadena;  // guarda la cadena construida
            cadena="";            // reinicia la variable
            yybegin(YYINITIAL);  // regresa al estado YYInitial
            return new Symbol(sym.CADENA, yyline, yycolumn,tmp);} //regresa la cadena con su valor
    
    [^\"]   {cadena+=yytext();}
}