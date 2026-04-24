package io.github.synix4life.compiler.redec_mini.Lexing.Exception;

public class LexingException extends RuntimeException {
    /**
     * Throw a LexingException
     * -> General lexer exception
     * @param message Message
     */
    public LexingException(String message) {
        super(message);
    }
}
