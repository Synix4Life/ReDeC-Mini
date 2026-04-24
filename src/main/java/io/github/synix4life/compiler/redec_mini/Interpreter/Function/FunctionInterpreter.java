package io.github.synix4life.compiler.redec_mini.Interpreter.Function;


import io.github.synix4life.compiler.redec_mini.Interpreter.Exception.*;
import io.github.synix4life.compiler.redec_mini.Parser.Types.*;
import io.github.synix4life.compiler.redec_mini.Parser.Types.Fun.Function;

import java.util.HashMap;

public class FunctionInterpreter {
    // ---------------- VARIABLES ---------------- //
    private final HashMap<String, RFunction> functions;


    // ---------------- CONSTRUCTOR ---------------- //

    /**
     * Constructor
     */
    public FunctionInterpreter() {
        this.functions = new HashMap<>();
    }


    // ---------------- FUNCTIONS ---------------- //

    /**
     * Register a new function and make it callable
     * @param f Function f (Function instanceof Statement)
     * @throws TypeException If no Function is given
     */
    public void register(Statement f) {
        if (!(f instanceof Function fun)) {
            throw new TypeException("No Function given!");
        }
        functions.put(fun.name, new RFunction(fun));
    }

    /**
     * Get a registered Runnable Function
     * @param name Function name
     * @return The RFunction
     * @throws FunctionException If the function doesn't exist
     */
    public RFunction get(String name) {
        if (!exists(name)) {
            throw new FunctionException("Runtime Error: Function '" + name + "' is not defined.");
        }
        return functions.get(name);
    }

    /**
     * Check if a function exists
     * @param name Function name
     * @return Exists?
     */
    public boolean exists(String name) {
        return functions.containsKey(name);
    }
}
