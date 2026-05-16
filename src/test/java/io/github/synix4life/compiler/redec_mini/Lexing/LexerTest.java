package io.github.synix4life.compiler.redec_mini.Lexing;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * These Lexer tests are based to test the basics.
 * They shall not test each combination by itself, but test general programs that make use of the minimal required basics for a programming language.
 * Lexer tests that cover exceptions, edge cases etc are based in other test files.
 * Furthermore, tests of predefined functions (not implemented as of Version 1.0.1) are NOT tested here, as only the basic concepts are covered.
 * <br>
 * The test are structured into four different categories:
 *  (1.) SIMPLE : These test use simple strings and convert them to lexing results.
 *          These include standard mathematical principles, assignments, loops, and if-statements.
 *  (2.) FUNCTIONS : These tests define custom functions and execute them
 *  (3.) ADVANCED FUNCTIONS : These tests test advanced function, i.e. recursive or iterative functions
 *  (4.) NESTED : These tests explore nested loops, nested if-statements and nested function calls
 * <br>
 * Note that these tests shall never fail. Since these test are the bare minimum, they must pass all the time.
 * However, as structure might change, the test from (5.) may fail due to language reconstruction, but this shall remain the problem of future work.
 */
public class LexerTest {

    // ---------------- (1.) SIMPLE ---------------- //

    @Test
    void testLexerS1(){
        final List<Token> expected = List.of(
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.NUMBER, "0"),

                new Token(TokenType.IDENT, "y"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.NUMBER, "1"),

                new Token(TokenType.IDENT, "z"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.ADD, "+"),
                new Token(TokenType.IDENT, "y")
        );

        final String[] dataString = new String[]{
                "x = 0",
                "y = 1",
                "z = x + y"
        };

        Lexer lexer = new Lexer(dataString);
        lexer.parse();
        lexer.optimizeLex();

        assertEquals(expected.size(), lexer.getTokens().size());
        assertEquals(expected, lexer.getTokens());
    }

    @Test
    void testLexerS2(){
        final List<Token> expected = List.of(
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.NUMBER, "0"),

                new Token(TokenType.IDENT, "y"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.NUMBER, "4"),

                new Token(TokenType.IF, "if"),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.EQEQ, "=="),
                new Token(TokenType.NUMBER, "0"),

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
                new Token(TokenType.SUB, "-"),
                new Token(TokenType.NUMBER, "1")
        );

        final String[] dataString = new String[]{
                "x= 0",
                "y =4",
                "if x == 0 then y = y + 1 else y = y - 1"
        };

        Lexer lexer = new Lexer(dataString);
        lexer.parse();
        lexer.optimizeLex();

        assertEquals(expected.size(), lexer.getTokens().size());
        assertEquals(expected, lexer.getTokens());
    }

    @Test
    void testLexerS3(){
        final List<Token> expected = List.of(
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.NUMBER, "10"),

                new Token(TokenType.IDENT, "y"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.NUMBER, "0"),

                new Token(TokenType.WHILE, "while"),

                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.GT, ">"),
                new Token(TokenType.NUMBER, "10"),

                new Token(TokenType.DO, "do"),

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
                new Token(TokenType.NUMBER, "2"),

                new Token(TokenType.COMMA, ","),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.SUB, "-"),
                new Token(TokenType.NUMBER, "1")
        );

        final String[] dataString = new String[]{
                "x = 10",
                "y = 0",
                "while x > 10 do if x < 5 then y = y + 1 else y = y + 2, x = x - 1"
        };

        Lexer lexer = new Lexer(dataString);
        lexer.parse();
        lexer.optimizeLex();

        assertEquals(expected.size(), lexer.getTokens().size());
        assertEquals(expected, lexer.getTokens());
    }


    // ---------------- (2.) FUNCTIONS ---------------- //

    @Test
    void testLexerF1(){
        final List<Token> expected = List.of(
                new Token(TokenType.FUN, "fun"),
                new Token(TokenType.IDENT, "t"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.RPAREN, ")"),

                new Token(TokenType.LBRACE, "{"),

                new Token(TokenType.RETURN, "return"),
                new Token(TokenType.NUMBER, "1"),

                new Token(TokenType.RBRACE, "}"),

                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.CALL, "t"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.RPAREN, ")")
        );

        final String[] dataString = new String[]{
                "fun t() {return 1}",
                "x = t()"
        };

        Lexer lexer = new Lexer(dataString);
        lexer.parse();
        lexer.optimizeLex();

        assertEquals(expected.size(), lexer.getTokens().size());
        assertEquals(expected, lexer.getTokens());
    }

    @Test
    void testLexerF2(){
        final List<Token> expected = List.of(
                new Token(TokenType.FUN, "fun"),
                new Token(TokenType.IDENT, "add"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.IDENT, "a"),
                new Token(TokenType.COMMA, ","),
                new Token(TokenType.IDENT, "b"),
                new Token(TokenType.COMMA, ","),
                new Token(TokenType.IDENT, "c"),
                new Token(TokenType.RPAREN, ")"),

                new Token(TokenType.LBRACE, "{"),

                new Token(TokenType.RETURN, "return"),
                new Token(TokenType.IDENT, "a"),
                new Token(TokenType.ADD, "+"),
                new Token(TokenType.IDENT, "b"),
                new Token(TokenType.ADD, "+"),
                new Token(TokenType.IDENT, "c"),

                new Token(TokenType.RBRACE, "}"),

                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.CALL, "add"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.NUMBER, "1"),
                new Token(TokenType.COMMA, ","),
                new Token(TokenType.NUMBER, "2"),
                new Token(TokenType.COMMA, ","),
                new Token(TokenType.NUMBER, "4"),
                new Token(TokenType.RPAREN, ")")
        );

        final String[] dataString = new String[]{
                "fun add(a,b,c) {return a+b+c }",
                "x = add(1,2,4)"
        };

        Lexer lexer = new Lexer(dataString);
        lexer.parse();
        lexer.optimizeLex();

        assertEquals(expected.size(), lexer.getTokens().size());

        for(int i = 0; i < expected.size(); i++){
            assertEquals(expected.get(i), lexer.getTokens().get(i));
        }
    }

    @Test
    void testLexerF3(){
        final List<Token> expected = List.of(
                new Token(TokenType.FUN, "fun"),
                new Token(TokenType.IDENT, "t"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.RPAREN, ")"),

                new Token(TokenType.LBRACE, "{"),

                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.ADD, "+"),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.COMMA, ","),

                new Token(TokenType.RETURN, "return"),
                new Token(TokenType.IDENT, "x"),

                new Token(TokenType.RBRACE, "}"),

                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.CALL, "t"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.NUMBER, "1"),
                new Token(TokenType.RPAREN, ")")
        );

        final String[] dataString = new String[]{
                "fun t(x) { x = x + x , return x }",
                "x = t(1)"
        };

        Lexer lexer = new Lexer(dataString);
        lexer.parse();
        lexer.optimizeLex();

        assertEquals(expected.size(), lexer.getTokens().size());

        for(int i = 0; i < expected.size(); i++){
            assertEquals(expected.get(i), lexer.getTokens().get(i));
        }
    }

    // ---------------- (3.) ADVANCED FUNCTIONS ---------------- //

    @Test
    void testLexerFA1(){
        final List<Token> expected = List.of(
                new Token(TokenType.FUN, "fun"),
                new Token(TokenType.IDENT, "t"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.RPAREN, ")"),

                new Token(TokenType.LBRACE, "{"),

                new Token(TokenType.IDENT, "n"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.NUMBER, "0"),
                new Token(TokenType.COMMA, ","),

                new Token(TokenType.IDENT, "r"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.NUMBER, "0"),
                new Token(TokenType.COMMA, ","),

                new Token(TokenType.WHILE, "while"),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.GT, ">"),
                new Token(TokenType.IDENT, "n"),

                new Token(TokenType.DO, "do"),

                new Token(TokenType.IDENT, "n"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.IDENT, "n"),
                new Token(TokenType.ADD, "+"),
                new Token(TokenType.NUMBER, "1"),
                new Token(TokenType.COMMA, ","),

                new Token(TokenType.IDENT, "r"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.IDENT, "r"),
                new Token(TokenType.ADD, "+"),
                new Token(TokenType.IDENT, "n"),
                new Token(TokenType.SUB, "-"),
                new Token(TokenType.IDENT, "x"),

                new Token(TokenType.RBRACE, "}"),

                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.CALL, "t"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.NUMBER, "50"),
                new Token(TokenType.RPAREN, ")")
        );

        final String[] dataString = new String[]{
                "fun t(x) { n = 0, r = 0, while x > n do n = n + 1 , r = r + n - x }",
                "x = t(50)"
        };

        Lexer lexer = new Lexer(dataString);
        lexer.parse();
        lexer.optimizeLex();

        assertEquals(expected.size(), lexer.getTokens().size());

        for(int i = 0; i < expected.size(); i++){
            assertEquals(expected.get(i), lexer.getTokens().get(i));
        }
    }

    @Test
    void testLexerFA2(){
        final List<Token> expected = List.of(
                new Token(TokenType.FUN, "fun"),
                new Token(TokenType.IDENT, "rec"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.RPAREN, ")"),
                new Token(TokenType.LBRACE, "{"),

                new Token(TokenType.IF, "if"),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.GT, ">"),
                new Token(TokenType.NUMBER, "0"),

                new Token(TokenType.THEN, "then"),

                new Token(TokenType.RETURN, "return"),
                new Token(TokenType.CALL, "rec"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.SUB, "-"),
                new Token(TokenType.NUMBER, "1"),
                new Token(TokenType.RPAREN, ")"),
                new Token(TokenType.SUB, "-"),
                new Token(TokenType.NUMBER, "1"),

                new Token(TokenType.ELSE, "else"),
                new Token(TokenType.NUMBER, "50"),

                new Token(TokenType.RBRACE, "}"),

                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.CALL, "rec"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.NUMBER, "30"),
                new Token(TokenType.RPAREN, ")")
        );

        final String[] dataString = new String[]{
                "fun rec(x) { if x > 0 then return rec(x-1) - 1 else 50 }",
                "x = rec(30)"
        };

        Lexer lexer = new Lexer(dataString);
        lexer.parse();
        lexer.optimizeLex();

        assertEquals(expected.size(), lexer.getTokens().size());

        for(int i = 0; i < expected.size(); i++){
            assertEquals(expected.get(i), lexer.getTokens().get(i));
        }
    }

    // ---------------- (4.) NESTED ---------------- //

    @Test
    void testLexerN1(){
        final List<Token> expected = List.of(
                new Token(TokenType.FUN, "fun"),
                new Token(TokenType.IDENT, "other"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.RPAREN, ")"),
                new Token(TokenType.LBRACE, "{"),
                new Token(TokenType.RETURN, "return"),
                new Token(TokenType.NUMBER, "2"),
                new Token(TokenType.MUL, "*"),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.RBRACE, "}"),

                new Token(TokenType.FUN, "fun"),
                new Token(TokenType.IDENT, "func"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.COMMA, ","),
                new Token(TokenType.IDENT, "y"),
                new Token(TokenType.RPAREN, ")"),
                new Token(TokenType.LBRACE, "{"),

                new Token(TokenType.IDENT, "n"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.NUMBER, "0"),
                new Token(TokenType.COMMA, ","),

                new Token(TokenType.IF, "if"),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.GT, ">"),
                new Token(TokenType.NUMBER, "5"),
                new Token(TokenType.THEN, "then"),

                new Token(TokenType.IDENT, "n"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.CALL, "other"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.IDENT, "y"),
                new Token(TokenType.RPAREN, ")"),

                new Token(TokenType.ELSE, "else"),

                new Token(TokenType.IDENT, "n"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.CALL, "other"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.RPAREN, ")"),
                new Token(TokenType.COMMA, ","),

                new Token(TokenType.RETURN, "return"),
                new Token(TokenType.IDENT, "n"),

                new Token(TokenType.RBRACE, "}"),

                new Token(TokenType.IDENT, "result"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.CALL, "func"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.NUMBER, "10"),
                new Token(TokenType.COMMA, ","),
                new Token(TokenType.NUMBER, "3"),
                new Token(TokenType.RPAREN, ")")
        );

        final String[] dataString = new String[]{
                "fun other(x){return 2 * x }",
                "fun func(x,y){n = 0, if x > 5 then n = other(y) else n = other(x), return n}",
                "result = func(10,3)"
        };

        Lexer lexer = new Lexer(dataString);
        lexer.parse();
        lexer.optimizeLex();

        assertEquals(expected.size(), lexer.getTokens().size());

        for(int i = 0; i < expected.size(); i++){
            assertEquals(expected.get(i), lexer.getTokens().get(i));
        }
    }

    @Test
    void testLexerN2(){
        final List<Token> expected = List.of(
                new Token(TokenType.FUN, "fun"),
                new Token(TokenType.IDENT, "test"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.COMMA, ","),
                new Token(TokenType.IDENT, "y"),
                new Token(TokenType.RPAREN, ")"),
                new Token(TokenType.LBRACE, "{"),

                new Token(TokenType.IDENT, "n"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.NUMBER, "0"),
                new Token(TokenType.COMMA, ","),

                new Token(TokenType.WHILE, "while"),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.LT, "<"),
                new Token(TokenType.NUMBER, "0"),
                new Token(TokenType.DO, "do"),

                new Token(TokenType.IDENT, "n"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.IDENT, "n"),
                new Token(TokenType.ADD, "+"),
                new Token(TokenType.NUMBER, "1"),
                new Token(TokenType.COMMA, ","),

                new Token(TokenType.WHILE, "while"),
                new Token(TokenType.IDENT, "y"),
                new Token(TokenType.LT, "<"),
                new Token(TokenType.NUMBER, "5"),
                new Token(TokenType.DO, "do"),

                new Token(TokenType.IDENT, "y"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.IDENT, "y"),
                new Token(TokenType.ADD, "+"),
                new Token(TokenType.NUMBER, "1"),

                new Token(TokenType.RBRACE, "}"),

                new Token(TokenType.CALL, "test"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.NUMBER, "0"),
                new Token(TokenType.COMMA, ","),
                new Token(TokenType.NUMBER, "0"),
                new Token(TokenType.RPAREN, ")")
        );

        final String[] dataString = new String[]{
                "fun test(x,y){ n = 0, while x < 0 do n = n + 1, while y < 5 do y = y+1 }",
                "test(0,0)"
        };

        Lexer lexer = new Lexer(dataString);
        lexer.parse();
        lexer.optimizeLex();

        assertEquals(expected.size(), lexer.getTokens().size());

        for(int i = 0; i < expected.size(); i++){
            assertEquals(expected.get(i), lexer.getTokens().get(i));
        }
    }
}
