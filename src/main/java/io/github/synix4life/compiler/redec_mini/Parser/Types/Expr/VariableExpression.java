package io.github.synix4life.compiler.redec_mini.Parser.Types.Expr;

import java.util.Objects;

/**
 * Variable Expression -> Variable is e.g., "x"
 */
public class VariableExpression implements Expression {
    public String variable;

    public VariableExpression(){ }

    public VariableExpression(String variable) {
        this.variable = variable;
    }

    @Override
    public String toString() {
        return "VAR(" + variable + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VariableExpression other)) return false;
        return Objects.equals(variable, other.variable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variable);
    }
}
