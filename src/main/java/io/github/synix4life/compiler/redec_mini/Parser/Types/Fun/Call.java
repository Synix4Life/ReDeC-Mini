package io.github.synix4life.compiler.redec_mini.Parser.Types.Fun;

import io.github.synix4life.compiler.redec_mini.Parser.Types.Expr.Expression;

import java.util.List;

/**
 * Abstract Call -> Two specific Calls exist, this is the superclass
 */
public abstract class Call {
    public Function function;
    public List<Expression> args;
    public String name;
}
