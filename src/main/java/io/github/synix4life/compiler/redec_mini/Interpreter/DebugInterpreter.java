package io.github.synix4life.compiler.redec_mini.Interpreter;

import java.util.HashMap;
import java.util.Map;

public class DebugInterpreter {
    /**
     * Method to print the VariableTable
     * @param i The interpreter
     * @param debug Are we in debug mode? This is only important because we print specific separation markers when debugging
     */
    public static void printVariableTable(Interpreter i, boolean debug){
        if(debug) { System.out.println("\n # =============== OUTPUT =============== # \n"); }
        HashMap<String, Integer> table = i.getTable();
        for (Map.Entry<String, Integer> entry : table.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
