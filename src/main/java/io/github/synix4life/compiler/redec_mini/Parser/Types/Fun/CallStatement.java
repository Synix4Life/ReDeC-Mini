package io.github.synix4life.compiler.redec_mini.Parser.Types.Fun;

import java.util.List;

import io.github.synix4life.compiler.redec_mini.Parser.Types.Statement;
import io.github.synix4life.compiler.redec_mini.Parser.Types.Expr.Expression;

/**
 * Call Statement (i.e. with no return,  e.g., print(1))
 */
public class CallStatement extends Call implements Statement {
    public CallStatement(){ super(); }

    public CallStatement(Function function, List<Expression> args, String name){ super(function, args, name); }
}
