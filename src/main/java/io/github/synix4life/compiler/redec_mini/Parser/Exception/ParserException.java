package io.github.synix4life.compiler.redec_mini.Parser.Exception;

public class ParserException extends RuntimeException {
    /**
     * Throw a ParserException
     * -> A general parser Exception
     * @param message Message
     */
    public ParserException(String message) {
        super(message);
    }
}
