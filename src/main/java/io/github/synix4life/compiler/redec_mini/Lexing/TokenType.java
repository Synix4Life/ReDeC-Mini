package io.github.synix4life.compiler.redec_mini.Lexing;

/**
 * TokenTypes for the lexing-step
 */
public enum TokenType {
    IDENT, NUMBER, STRING,
    ADD, SUB, MUL, DIV,
    TRUE, FALSE,
    EQUALS,
    IF, ELSE, WHILE, FUN, RETURN, THEN, DO, CALL,
    LPAREN, RPAREN, LBRACE, RBRACE,
    COMMA,
    GT, LT, EQEQ, LEQ, GEQ,
    EOF
}