package io.github.synix4life.compiler.redec_mini.Parser.Types;

import java.util.Objects;

import io.github.synix4life.compiler.redec_mini.Parser.Types.Expr.Expression;

/**
 * If Statement
 */
public class IfStatement implements Statement{
    public Expression expression;
    public Statement thenBranch;
    public Statement elseBranch;

    public IfStatement() { }

    public IfStatement(Expression expression, Statement thenBranch, Statement elseBranch) {
        this.expression = expression;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    /*
    RESERVED FOR FUTURE MULTI STATEMENT IF → Leads to an error due to unclear scoping
    public List<Statement> thenBranch;
    public List<Statement> elseBranch;

    public  IfStatement(){
        thenBranch = new ArrayList<>();
        elseBranch = new ArrayList<>();
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IfStatement other)) return false;

        return Objects.equals(expression, other.expression) && 
               Objects.equals(thenBranch, other.thenBranch) && 
               Objects.equals(elseBranch, other.elseBranch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, thenBranch, elseBranch);
    }
}
