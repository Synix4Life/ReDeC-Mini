package io.github.synix4life.compiler.redec_mini.Parser;

import org.junit.jupiter.api.Test;

import io.github.synix4life.compiler.redec_mini.Lexing.Token;
import io.github.synix4life.compiler.redec_mini.Lexing.TokenType;
import io.github.synix4life.compiler.redec_mini.Parser.Exception.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class ParserExceptionTriggerTest {

    // ---------------- (1.) PARSER EXCEPTION ---------------- //

    @Test
    void testParserExceptionParseStatement1(){
        // - - - - - - - - Input - - - - - - - - //
        final ArrayList<Token> input = new ArrayList<>(List.of(
            new Token(TokenType.EQUALS, "="),
            new Token(TokenType.NUMBER, "0")
        ));

        // - - - - - - - Calculation - - - - - - - //
        Parser parser = new Parser(input);
        assertThrows(ParserException.class, parser::parse);
    }

    @Test
    void testParserExceptionCall1(){
        // - - - - - - - - Input - - - - - - - - //
        final ArrayList<Token> input = new ArrayList<>(List.of(
            new Token(TokenType.IDENT, "x"),
            new Token(TokenType.EQUALS, "="),
            new Token(TokenType.CALL, "t"),
            new Token(TokenType.LPAREN, "("),
            new Token(TokenType.RPAREN, ")")
        ));

        // - - - - - - - Calculation - - - - - - - //
        Parser parser = new Parser(input);
        assertThrows(ParserException.class, parser::parse);
    }

    @Test
    void testParserExceptionMalformedExpression1(){
        // - - - - - - - - Input - - - - - - - - //
        final ArrayList<Token> input = new ArrayList<>(List.of(
            new Token(TokenType.IDENT, "x"),
            new Token(TokenType.EQUALS, "="),
            new Token(TokenType.IF, "if"),
            new Token(TokenType.IDENT, "x"),
            new Token(TokenType.LT, "<"),
            new Token(TokenType.NUMBER, "5"),

            new Token(TokenType.THEN, "then"),
            new Token(TokenType.IDENT, "y"),
            new Token(TokenType.EQUALS, "="),
            new Token(TokenType.IDENT, "y"),
            new Token(TokenType.ADD, "+"),
            new Token(TokenType.NUMBER, "1"),

            new Token(TokenType.ELSE, "else"),
            new Token(TokenType.IDENT, "y"),
            new Token(TokenType.EQUALS, "="),
            new Token(TokenType.IDENT, "y"),
            new Token(TokenType.ADD, "+"),
            new Token(TokenType.NUMBER, "2")
        ));

        // - - - - - - - Calculation - - - - - - - //
        Parser parser = new Parser(input);
        assertThrows(ParserException.class, parser::parse);
    }

    // ---------------- (2.) SUB-CLASS PARSER EXCEPTION ---------------- //

    @Test
    void testSubClassParserExceptionWhileStatement1(){
        // - - - - - - - - Input - - - - - - - - //
        final ArrayList<Token> input = new ArrayList<>(List.of(
            new Token(TokenType.IDENT, "x"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.NUMBER, "1"),

                new Token(TokenType.WHILE, "while"),

                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.ADD, "+"),
                new Token(TokenType.NUMBER, "10"),

                new Token(TokenType.DO, "do"),
                
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.NUMBER, "1")
        ));

        // - - - - - - - Calculation - - - - - - - //
        Parser parser = new Parser(input);
        assertThrows(SubClassParserException.class, parser::parse);
    }


    // ---------------- (3.) OTHER ---------------- //

    @Test
    void testNullPointerException1(){
        // - - - - - - - - Input - - - - - - - - //
        final ArrayList<Token> input = new ArrayList<>(List.of(
            new Token(TokenType.IDENT, "x")
        ));

        // - - - - - - - Calculation - - - - - - - //
        Parser parser = new Parser(input);
        assertThrows(NullPointerException.class, parser::getStatementList);
    }
}
