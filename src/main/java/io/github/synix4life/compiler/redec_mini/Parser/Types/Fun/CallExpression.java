package io.github.synix4life.compiler.redec_mini.Parser.Types.Fun;

import java.util.List;

import io.github.synix4life.compiler.redec_mini.Parser.Types.Expr.Expression;

/**
 * Call Expression (i.e. with return,  e.g., a = add(1,2))
 */
public class CallExpression extends Call implements Expression {
    public CallExpression(){ super(); }

    public CallExpression(Function function, List<Expression> args, String name){ super(function, args, name); }
}
