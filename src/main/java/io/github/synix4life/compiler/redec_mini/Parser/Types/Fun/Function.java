package io.github.synix4life.compiler.redec_mini.Parser.Types.Fun;

import io.github.synix4life.compiler.redec_mini.Parser.Types.Expr.VariableExpression;
import io.github.synix4life.compiler.redec_mini.Parser.Types.Statement;

import java.util.List;

/**
 * Function Statement
 */
public class Function implements Statement {
    public String name;
    public List<VariableExpression> args;
    public List<Statement> statements;
}
