package io.github.synix4life.compiler.redec_mini.Parser.Types.Fun;

import io.github.synix4life.compiler.redec_mini.Parser.Types.Expr.VariableExpression;
import io.github.synix4life.compiler.redec_mini.Parser.Types.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Function Statement
 */
public class Function implements Statement {
    public String name;
    public List<VariableExpression> args = new ArrayList<>();
    public List<Statement> statements = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Function other)) return false;
        return Objects.equals(name, other.name) &&
               Objects.equals(args, other.args) &&
               Objects.equals(statements, other.statements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, args, statements);
    }
}
