package org.xbib.io.iso23950.pqf;

import java.io.*;

%%
%class PQFLexer
%implements PQFTokens
%unicode 
%integer
%eofval{ 
    return 0; 
%eofval}
%line
%column

%{
    private Object yylval;
    private int token;
    private StringBuilder sb = new StringBuilder();
      
    public int getToken() {        
        return token;
    }
    
    public int nextToken() {
        try {
            token = yylex();            
            return token;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Object getSemantic() {
        return yylval;
    }
    
    public int getLine() {
        return yyline;
    }
    
    public int getColumn() {
        return yycolumn;
    }
    
%}
NL  = \n | \r | \r\n
AND = "@and"
OR = "@or"
NOT = "@not"
ATTR = "@attr"
ATTRSET = "@attrset"
TERM = "@term"
SET = "@set"
VOID = "void"
KNOWN = "known"
PRIVATE = "private"
CHARSTRING1 = [^ .\t\"()=<>\\]+ 
CHARSTRING2 = [^\"]
INTEGER = [0-9]+
EQUALS = "="
OPERATORS = "=" | ">" | "<" | ">=" | "<=" | "<>"
TERMTYPE = "general" | "numeric" | "string" | "oid" | "datetime" | "null"

%state STRING2

%%

<YYINITIAL>\"	{
        yybegin(STRING2); 
        sb.setLength(0);
    }

<STRING2> {
 
\\\"            { 
        sb.append("\""); 
    }
{CHARSTRING2}    { 
        sb.append(yytext()); 
    }
\"	            { 
        yybegin(YYINITIAL);
        yylval = sb.toString();
        return CHARSTRING2; 
    }
}

<YYINITIAL>{NL} { 
        return NL;
    }

<YYINITIAL>" "|\t {
    }

<YYINITIAL>{EQUALS}  { 
        yylval = yytext();
        return EQUALS; 
    }	
				
<YYINITIAL>{OPERATORS}  { 
        yylval = yytext();
        return OPERATORS; 
    }	

<YYINITIAL>{INTEGER}    { 
        yylval = Integer.parseInt(yytext());
        return INTEGER; 
    }
				
<YYINITIAL>{AND}  {
        yylval = yytext();
        return AND; 
    }

<YYINITIAL>{OR}  {
        yylval = yytext();
        return OR; 
    }

<YYINITIAL>{NOT}  {
        yylval = yytext();
        return NOT; 
    }

<YYINITIAL>{ATTR}  {
        yylval = yytext();
        return ATTR; 
    }

<YYINITIAL>{ATTRSET}  {
        yylval = yytext();
        return ATTRSET; 
    }

<YYINITIAL>{TERM}  {
        yylval = yytext();
        return TERM; 
    }

<YYINITIAL>{SET}  {
        yylval = yytext();
        return SET; 
    }

<YYINITIAL>{VOID}  {
        yylval = yytext();
        return VOID; 
    }

<YYINITIAL>{KNOWN}  {
        yylval = yytext();
        return KNOWN; 
    }

<YYINITIAL>{PRIVATE}  {
        yylval = yytext();
        return PRIVATE; 
    }

<YYINITIAL>{TERMTYPE} {
        yylval = yytext();
        return TERMTYPE; 
    }

<YYINITIAL>{CHARSTRING1} {
        yylval = yytext();          
        return CHARSTRING1;
    }

    
