package io.github.synix4life.compiler.redec_mini.Lexing.Exception;

public class InvalidCommentFormattingException extends RuntimeException {

    /**
     * Throw an InvalidCommentFormattingException
     * -> If a comment isn't formatted well
     * @param message Message
     */
    public InvalidCommentFormattingException(String message) {
        super(message);
    }
}
