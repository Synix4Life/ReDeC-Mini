package io.github.synix4life.compiler.redec_mini.Interpreter.Exception;

public class FunctionException extends RuntimeException {
    /**
     * Throw a FunctionException
     * -> An exception if an RFunction isn't registered
     * @param message Message
     */
    public FunctionException(String message) {
        super(message);
    }
}
