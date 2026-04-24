package io.github.synix4life.compiler.redec_mini.Parser.Types;

import io.github.synix4life.compiler.redec_mini.Parser.Types.Expr.Expression;
import io.github.synix4life.compiler.redec_mini.Parser.Types.Expr.VariableExpression;

/**
 * Assignment statement
 */
public class Assignment implements Statement {
    public VariableExpression to;
    public Expression from;
}
