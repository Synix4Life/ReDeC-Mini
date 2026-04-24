package io.github.synix4life.compiler.redec_mini.Parser.Types.Expr;

/**
 * Binary Expression -> Expression that evaluates to an integer
 */
public class BinaryExpression implements Expression{
    public Expression LHS;
    public Opcodes op;
    public Expression RHS;

    public BinaryExpression(Expression LHS, Opcodes op, Expression RHS){
        this.LHS = LHS;
        this.op = op;
        this.RHS = RHS;
    }
}
