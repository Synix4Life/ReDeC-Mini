package io.github.synix4life.compiler.redec_mini.Parser.Types;

import java.util.Objects;

import io.github.synix4life.compiler.redec_mini.Parser.Types.Expr.Expression;

/**
 * Return Statement
 */
public class ReturnStatement implements Statement{
    public Expression expression;

    public ReturnStatement() { }

    public ReturnStatement(Expression expression) { this.expression = expression; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReturnStatement other)) return false;

        return Objects.equals(expression, other.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }
}
