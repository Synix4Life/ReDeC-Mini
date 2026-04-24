package io.github.synix4life.compiler.redec_mini.Parser.Types;

import io.github.synix4life.compiler.redec_mini.Parser.Types.Expr.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * While Statement
 */
public class WhileStatement implements Statement{
    public Expression expression;
    public List<Statement> body;

    public WhileStatement(){
        body = new ArrayList<>();
    }
}
