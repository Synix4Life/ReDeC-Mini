package io.github.synix4life.compiler.redec_mini.Interpreter.Exception;

/**
 * Return Exception needed for return calls
 */
public class ReturnException extends RuntimeException {
    public final int value;

    /**
     * Throw a ReturnException
     * @param value Integer value (return value)
     */
    public ReturnException(int value) { this.value = value; }
}
