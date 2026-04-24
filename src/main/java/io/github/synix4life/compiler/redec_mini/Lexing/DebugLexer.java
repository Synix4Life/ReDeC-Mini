package io.github.synix4life.compiler.redec_mini.Lexing;

import java.util.ArrayList;

public class DebugLexer {

    // ---------------- DEBUGGER ---------------- //

    /**
     * Debug the lexing result (i.e. print the tokens)
     * @param i Token array
     */
    public static void debugLexer(ArrayList<Token> i) {
        System.out.println("\n # =============== LEXING =============== # \n");

        for (Token t : i) {
            System.out.print(t.type() + "(\"" + t.value() + "\") ");
        }
        System.out.println(); System.out.println();
    }
}
