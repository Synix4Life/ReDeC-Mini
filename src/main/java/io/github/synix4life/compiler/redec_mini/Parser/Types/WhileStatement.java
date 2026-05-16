package io.github.synix4life.compiler.redec_mini.Parser.Types;

import io.github.synix4life.compiler.redec_mini.Parser.Types.Expr.Expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * While Statement
 */
public class WhileStatement implements Statement{
    public Expression expression;
    public List<Statement> body;

    public WhileStatement(){
        body = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WhileStatement other)) return false;

        return Objects.equals(expression, other.expression) && 
               Objects.equals(body, other.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, body);
    }
}
