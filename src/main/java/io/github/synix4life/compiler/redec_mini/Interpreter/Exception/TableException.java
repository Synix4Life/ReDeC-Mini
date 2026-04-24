package io.github.synix4life.compiler.redec_mini.Interpreter.Exception;

public class TableException extends RuntimeException {
    /**
     * Throw a TableException
     * -> An exception if VariableTable operations failed
     * @param message Message
     */
    public TableException(String message) {
        super(message);
    }

    /**
     * Throw a TableException
     * -> An exception if VariableTable operations failed
     */
    public TableException() {
        super();
    }
}
