package io.github.synix4life.compiler.redec_mini.Interpreter.Function;

import io.github.synix4life.compiler.redec_mini.Parser.Types.Fun.Function;
import io.github.synix4life.compiler.redec_mini.Parser.Types.Statement;

import java.util.ArrayList;
import java.util.List;

public class RFunction {
    // ---------------- VARIABLES ---------------- //
    public final String name;
    public List<String> args;
    public List<Statement> statements;


    // ---------------- CONSTRUCTOR ---------------- //

    /**
     * Constructor
     * @param f The function f to extract the necessary information from
     */
    public RFunction(Function f){
        this.name = f.name;
        this.statements = f.statements;
        args = new ArrayList<String>();
        for(var arg: f.args){
            args.add(arg.variable);
        }
    }
}
