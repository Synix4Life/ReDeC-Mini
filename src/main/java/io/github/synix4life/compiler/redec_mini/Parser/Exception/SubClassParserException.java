package io.github.synix4life.compiler.redec_mini.Parser.Exception;

public class SubClassParserException extends RuntimeException {
    /**
     * Throw a SubClassParserException
     * -> InstanceOf Exception -> If A should be InstanceOf B but isn't
     * @param message Message
     */
    public SubClassParserException(String message) {
        super(message);
    }
}
