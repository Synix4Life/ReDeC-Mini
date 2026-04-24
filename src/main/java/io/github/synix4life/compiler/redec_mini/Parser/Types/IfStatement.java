package io.github.synix4life.compiler.redec_mini.Parser.Types;

import io.github.synix4life.compiler.redec_mini.Parser.Types.Expr.Expression;

/**
 * If Statement
 */
public class IfStatement implements Statement{
    public Expression expression;
    public Statement thenBranch;
    public Statement elseBranch;
    /*
    RESERVED FOR FUTURE MULTI STATEMENT IF → Leads to an error due to unclear scoping
    public List<Statement> thenBranch;
    public List<Statement> elseBranch;

    public  IfStatement(){
        thenBranch = new ArrayList<>();
        elseBranch = new ArrayList<>();
    }*/
}
