package io.github.synix4life.compiler.redec_mini.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.synix4life.compiler.redec_mini.Lexing.Token;
import io.github.synix4life.compiler.redec_mini.Lexing.TokenType;
import io.github.synix4life.compiler.redec_mini.Parser.Types.*;
import io.github.synix4life.compiler.redec_mini.Parser.Types.Expr.*;
import io.github.synix4life.compiler.redec_mini.Parser.Types.Fun.CallExpression;
import io.github.synix4life.compiler.redec_mini.Parser.Types.Fun.CallStatement;
import io.github.synix4life.compiler.redec_mini.Parser.Types.Fun.Function;

public class ParserTest {
    // ---------------- (1.) SIMPLE ---------------- //

    @Test
    void testParserS1(){
        // - - - - - - - Expected - - - - - - - //
        final ArrayList<Statement> expected = new ArrayList<>();

        // x = 0
        Assignment xAssignment = new Assignment(
            new VariableExpression("x"),
            new NumberExpression(0)
        );
        expected.add(xAssignment);

        // y = 1
        Assignment yAssignment = new Assignment(
            new VariableExpression("y"),
            new NumberExpression(1)
        );
        expected.add(yAssignment);

        // z = x + y
        Assignment zAssignment = new Assignment(
            new VariableExpression("z"),
            new BinaryExpression(
                new VariableExpression("x"), 
                Opcodes.ADD, 
                new VariableExpression("y")
            )
        );
        expected.add(zAssignment);
        
        // - - - - - - - - Input - - - - - - - - //
        final ArrayList<Token> input = new ArrayList<>(List.of(
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
        ));

        // - - - - - - - Calculation - - - - - - - //
        Parser parser = new Parser(input);

        parser.parse();

        // - - - - - - - Asserting - - - - - - - //
        assertEquals(expected, parser.getStatementList());
    }

    @Test
    void testParserS2(){
        // - - - - - - - Expected - - - - - - - //
        final ArrayList<Statement> expected = new ArrayList<>();

        // x = 0
        Assignment xAssignment = new Assignment(
            new VariableExpression("x"),
            new NumberExpression(0)
        );
        expected.add(xAssignment);

        // y = 4
        Assignment yAssignment = new Assignment(
            new VariableExpression("y"),
            new NumberExpression(4)
        );
        expected.add(yAssignment);

        // if x == 0 then y = y + 1 else y = y - 1
        IfStatement ifStatement = new IfStatement(
            new BooleanExpression(
                new VariableExpression("x"),
                Opcodes.EQEQ,
                new NumberExpression(0)
            ),
            new Assignment(
                new VariableExpression("y"),
                new BinaryExpression(
                    new VariableExpression("y"), 
                    Opcodes.ADD, 
                    new NumberExpression(1)
                )
            ),
            new Assignment(
                new VariableExpression("y"),
                new BinaryExpression(
                    new VariableExpression("y"), 
                    Opcodes.SUB, 
                    new NumberExpression(1)
                )
            )
        );
        expected.add(ifStatement);


        // - - - - - - - - Input - - - - - - - - //
        final ArrayList<Token> input = new ArrayList<>(List.of(
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
        ));

        // - - - - - - - Calculation - - - - - - - //
        Parser parser = new Parser(input);

        parser.parse();

        // - - - - - - - Asserting - - - - - - - //
        assertEquals(expected, parser.getStatementList());

    }

    @Test
    void testParserS3(){
        // - - - - - - - Expected - - - - - - - //
        final ArrayList<Statement> expected = new ArrayList<>();

        // x = 10
        expected.add(
            new Assignment(
                new VariableExpression("x"),
                new NumberExpression(10)
            )
        );

        // y = 0
        expected.add(
            new Assignment(
                new VariableExpression("y"),
                new NumberExpression(0)
            )
        );
         
        // while x > 10 do y = y + 1, x = x - 1
        WhileStatement whileStatement = new WhileStatement();
        whileStatement.expression = new BooleanExpression(
            new VariableExpression("x"), 
            Opcodes.GT, 
            new NumberExpression(10)
        );
        whileStatement.body.add(
            new Assignment(
                new VariableExpression("y"),
                new BinaryExpression(
                    new VariableExpression("y"), 
                    Opcodes.ADD, 
                    new NumberExpression(1)
                )
            )
        );
        whileStatement.body.add(
            new Assignment(
                new VariableExpression("x"),
                new BinaryExpression(
                    new VariableExpression("x"), 
                    Opcodes.SUB, 
                    new NumberExpression(1)
                )
            )
        );
        expected.add(whileStatement);

        // - - - - - - - - Input - - - - - - - - //
        final ArrayList<Token> input = new ArrayList<>(List.of(
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

                new Token(TokenType.IDENT, "y"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.IDENT, "y"),
                new Token(TokenType.ADD, "+"),
                new Token(TokenType.NUMBER, "1"),

                new Token(TokenType.COMMA, ","),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.EQUALS, "="),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.SUB, "-"),
                new Token(TokenType.NUMBER, "1")
        ));

        // - - - - - - - Calculation - - - - - - - //
        Parser parser = new Parser(input);

        parser.parse();

        // - - - - - - - Asserting - - - - - - - //
        assertEquals(expected, parser.getStatementList());
    }

    @Test
    void testParserS4(){
        // - - - - - - - Expected - - - - - - - //
        final ArrayList<Statement> expected = new ArrayList<>();

        // x = 10
        expected.add(
            new Assignment(
                new VariableExpression("x"),
                new NumberExpression(10)
            )
        );

        // y = 0
        expected.add(
            new Assignment(
                new VariableExpression("y"),
                new NumberExpression(0)
            )
        );
         
        // while x > 10 do if x < 5 then y = y + 1 else y = y + 2, x = x - 1
        WhileStatement whileStatement = new WhileStatement();
        whileStatement.expression = new BooleanExpression(
            new VariableExpression("x"), 
            Opcodes.GT, 
            new NumberExpression(10)
        );
        whileStatement.body.add(
            new IfStatement(
                new BooleanExpression(
                    new VariableExpression("x"),
                    Opcodes.LT,
                    new NumberExpression(5)
                ),
                new Assignment(
                    new VariableExpression("y"),
                    new BinaryExpression(
                        new VariableExpression("y"), 
                        Opcodes.ADD, 
                        new NumberExpression(1)
                    )
                ),
                new Assignment(
                    new VariableExpression("y"),
                    new BinaryExpression(
                        new VariableExpression("y"), 
                        Opcodes.ADD, 
                        new NumberExpression(2)
                    )
                )
            )
        );
        whileStatement.body.add(
            new Assignment(
                new VariableExpression("x"),
                new BinaryExpression(
                    new VariableExpression("x"), 
                    Opcodes.SUB, 
                    new NumberExpression(1)
                )
            )
        );
        expected.add(whileStatement);

        // - - - - - - - - Input - - - - - - - - //
        final ArrayList<Token> input = new ArrayList<>(List.of(
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
        ));

        // - - - - - - - Calculation - - - - - - - //
        Parser parser = new Parser(input);

        parser.parse();

        // - - - - - - - Asserting - - - - - - - //
        assertEquals(expected, parser.getStatementList());
    }


    // ---------------- (2.) FUNCTIONS ---------------- //

    @Test
    void testParserF1(){
        // - - - - - - - Expected - - - - - - - //
        final ArrayList<Statement> expected = new ArrayList<>();

        // fun t() { return 1}
        Function f = new Function();
        f.name = "t";
        f.statements.add(
            new ReturnStatement(
                new NumberExpression(1)
            )
        );
        expected.add(f);
        
        // x = t()
        expected.add(
            new Assignment(
                new VariableExpression("x"),
                new CallExpression(
                    f,
                    new ArrayList<>(),
                    "t"
                )
            )
        );

        // - - - - - - - - Input - - - - - - - - //
        final ArrayList<Token> input = new ArrayList<>(List.of(
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
        ));

        // - - - - - - - Calculation - - - - - - - //
        Parser parser = new Parser(input);

        parser.parse();

        // - - - - - - - Asserting - - - - - - - //
        assertEquals(expected, parser.getStatementList());
    }

    @Test
    void testParserF2(){
        // - - - - - - - Expected - - - - - - - //
        final ArrayList<Statement> expected = new ArrayList<>();

        // fun ion(x) {}
        Function f = new Function();
        f.name = "ion";
        f.args = new ArrayList<>(List.of(
            new VariableExpression("x")
        ));
        expected.add(f);
        
        // ion(1)
        expected.add(
            new CallStatement(
                f,
                new ArrayList<>(List.of(
                    new NumberExpression(1)
                )),
                "ion"
            )
        );

        // - - - - - - - - Input - - - - - - - - //
        final ArrayList<Token> input = new ArrayList<>(List.of(
                new Token(TokenType.FUN, "fun"),
                new Token(TokenType.IDENT, "ion"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.IDENT, "x"),
                new Token(TokenType.RPAREN, ")"),

                new Token(TokenType.LBRACE, "{"),
                new Token(TokenType.RBRACE, "}"),

                new Token(TokenType.CALL, "ion"),
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.NUMBER, "1"),
                new Token(TokenType.RPAREN, ")")
        ));

        // - - - - - - - Calculation - - - - - - - //
        Parser parser = new Parser(input);

        parser.parse();

        // - - - - - - - Asserting - - - - - - - //
        assertEquals(expected, parser.getStatementList());
    }
}
