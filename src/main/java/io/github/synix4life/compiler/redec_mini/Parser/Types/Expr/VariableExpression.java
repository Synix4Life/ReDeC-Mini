package io.github.synix4life.compiler.redec_mini.Parser.Types.Expr;

/**
 * Variable Expression -> Variable is e.g., "x"
 */
public class VariableExpression implements Expression {
    public String variable;

    @Override
    public String toString() {
        return "VAR(" + variable + ")";
    }
}
