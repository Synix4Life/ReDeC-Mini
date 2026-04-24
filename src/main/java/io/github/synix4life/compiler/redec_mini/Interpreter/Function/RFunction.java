package io.github.synix4life.compiler.redec_mini.Interpreter.Function;

import io.github.synix4life.compiler.redec_mini.Parser.Types.Fun.Function;
import io.github.synix4life.compiler.redec_mini.Parser.Types.Statement;

import java.util.ArrayList;
import java.util.List;

public class RFunction {
    public final String name;
    public List<String> args;
    public List<Statement> statements;

    public RFunction(Function f){
        this.name = f.name;
        this.statements = f.statements;
        args = new ArrayList<String>();
        for(var arg: f.args){
            args.add(arg.variable);
        }
    }
}
