package io.github.synix4life.compiler.redec_mini.Lexing;

import io.github.synix4life.compiler.redec_mini.Lexing.Exception.InvalidCommentFormattingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * These Lexer tests are specifically designed to test the exception cases.
 * Basically, if an exception is thrown when necessary
 * <br>
 * This test file is divided into two parts:
 *  (1.) COMMENT : This is about the comment formatting
 *  (2.) OTHER : Other exceptions
 */
public class LexerExceptionTriggerTest {

    // ---------------- (1.) COMMENT ---------------- //

    @Test
    void testLexerExceptionC1(){
        final String[] dataString = new String[]{
                "#--",
                "y = 1"
        };

        Lexer lexer = new Lexer(dataString);
        assertThrows(InvalidCommentFormattingException.class, lexer::parse);
    }

    @Test
    void testLexerExceptionC2(){
        final String[] dataString = new String[]{
                "x = 0",
                "# x = 0",
                "--#",
                "z = x + y"
        };

        Lexer lexer = new Lexer(dataString);
        assertThrows(InvalidCommentFormattingException.class, lexer::parse);
    }


    // ---------------- (2.) OTHER ---------------- //

    @Test
    void testLexerExceptionO1(){
        final String[] dataString = new String[]{
                "#--",
                "y = 1"
        };

        Lexer lexer = new Lexer(dataString);
        assertThrows(NullPointerException.class, lexer::getTokens);
    }

    @Test
    void testLexerExceptionO1NoThrows(){
        final String[] dataString = new String[]{
                ""
        };

        Lexer lexer = new Lexer(dataString);
        assertDoesNotThrow(lexer::parse);
        assertDoesNotThrow(lexer::getTokens);
    }

    @Test
    void testLexerExceptionO2NoThrows(){
        final String[] dataString = new String[]{
                " "
        };

        Lexer lexer = new Lexer(dataString);
        assertDoesNotThrow(lexer::parse);
        assertDoesNotThrow(lexer::getTokens);
    }

    @Test
    void testLexerExceptionO3NoThrows(){
        final String[] dataString = new String[]{};

        Lexer lexer = new Lexer(dataString);
        assertDoesNotThrow(lexer::parse);
        assertDoesNotThrow(lexer::getTokens);
    }
}
