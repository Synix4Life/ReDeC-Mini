package io.github.synix4life.compiler.redec_mini.Parser.Types.Expr;

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
}
