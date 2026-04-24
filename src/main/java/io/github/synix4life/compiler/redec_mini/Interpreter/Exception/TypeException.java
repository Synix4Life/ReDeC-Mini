package io.github.synix4life.compiler.redec_mini.Interpreter.Exception;

/**
 * NOTE: Should be moved to the Parser -- In normal compilers, type checking in the parser (I assume)
 */

public class TypeException extends RuntimeException {
    /**
     * Throw a TypeException
     * -> An exception on a variable type mismatch
     */
    public TypeException(String message) {
        super(message);
    }
}
