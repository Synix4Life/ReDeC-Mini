package io.github.synix4life.compiler.redec_mini.Parser.Types.Expr;

import java.util.Objects;

/**
 * Number Expression -> Variable is e.g., 1
 */
public class NumberExpression implements Expression {
    public Integer value;

    public NumberExpression(){ }

    public NumberExpression(Integer value){ this.value = value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumberExpression other)) return false;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
