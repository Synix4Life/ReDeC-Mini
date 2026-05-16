package io.github.synix4life.compiler.redec_mini.Parser.Types.Fun;

import io.github.synix4life.compiler.redec_mini.Parser.Types.Expr.Expression;

import java.util.List;
import java.util.Objects;

/**
 * Abstract Call -> Two specific Calls exist, this is the superclass
 */
public abstract class Call {
    public Function function;
    public List<Expression> args;
    public String name;

    public Call(){ }

    public Call(Function function, List<Expression> args, String name){
        this.function = function;
        this.args = args;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Call other)) return false;
        return Objects.equals(function, other.function) &&
               Objects.equals(args, other.args) &&
               Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(function, args, name);
    }
}
