package io.github.synix4life.compiler.redec_mini.Parser.Types;

import java.util.Objects;

import io.github.synix4life.compiler.redec_mini.Parser.Types.Expr.Expression;
import io.github.synix4life.compiler.redec_mini.Parser.Types.Expr.VariableExpression;

/**
 * Assignment statement
 */
public class Assignment implements Statement {
    public VariableExpression to;
    public Expression from;

    public Assignment() { }

    public Assignment(VariableExpression to, Expression from) {
        this.to = to;
        this.from = from;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Assignment other)) return false;

        return Objects.equals(to, other.to)
            && Objects.equals(from, other.from);
    }

    @Override
    public int hashCode() {
        return Objects.hash(to, from);
    }
}
