package io.github.synix4life.compiler.redec_mini;

import io.github.synix4life.compiler.redec_mini.Interpreter.Interpreter;
import io.github.synix4life.compiler.redec_mini.Lexing.Lexer;
import io.github.synix4life.compiler.redec_mini.Parser.Parser;

import static io.github.synix4life.compiler.redec_mini.Interpreter.DebugInterpreter.printVariableTable;
import static io.github.synix4life.compiler.redec_mini.Lexing.DebugLexer.debugLexer;
import static io.github.synix4life.compiler.redec_mini.Parser.ParserDebug.debugParser;

public class Pipeline {
    /**
     * Method to run the interpreter pipeline
     * @param data The data in form of a String
     * @param debugMode Debug-Mode [yes/no] → Debug-Mode gives extra information
     */
    public static void runPipeline(String[] data, boolean debugMode){

        // ---------------- LEXER ---------------- //

        Lexer lexer = new Lexer(data);
        lexer.parse();
        lexer.optimizeLex();

        if(debugMode) { debugLexer(lexer); }

        // ---------------- PARSER ---------------- //

        Parser parser = new Parser(lexer.getTokens());
        parser.parse();

        if(debugMode){ debugParser(parser); }

        // ---------------- INTERPRETER ---------------- //

        Interpreter interpreter = new Interpreter(parser.getStatementList());
        interpreter.run();

        printVariableTable(interpreter, debugMode);
    }
}
