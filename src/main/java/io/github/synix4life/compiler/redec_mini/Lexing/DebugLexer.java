package io.github.synix4life.compiler.redec_mini.Lexing;

import java.util.ArrayList;

public class DebugLexer {

    // ---------------- DEBUGGER ---------------- //

    /**
     * Debug the lexing result (i.e. print the tokens)
     * @param lexer The lexer object with the lexed tokens
     */
    public static void debugLexer(Lexer lexer) {
        System.out.println("\n # =============== LEXING =============== # \n");

        for (Token t : lexer.getTokens()) {
            System.out.print(t.type() + "(\"" + t.value() + "\") ");
        }
        System.out.println(); System.out.println();
    }
}
