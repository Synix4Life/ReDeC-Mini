package io.github.synix4life.compiler.redec_mini.Parser.Types.Expr;

import java.util.Objects;

/**
 * Boolean Expression -> Expression that evaluates to 0 (false) or 1 (true)
 */
public class BooleanExpression implements Expression{
    public Expression LHS;
    public Opcodes op;
    public Expression RHS;

    public BooleanExpression(Expression LHS, Opcodes op, Expression RHS){
        this.LHS = LHS;
        this.op = op;
        this.RHS = RHS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BooleanExpression other)) return false;

        return Objects.equals(LHS, other.LHS)
            && op == other.op
            && Objects.equals(RHS, other.RHS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(LHS, op, RHS);
    }
}
