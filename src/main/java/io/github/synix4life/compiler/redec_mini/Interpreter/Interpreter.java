package io.github.synix4life.compiler.redec_mini.Interpreter;

import io.github.synix4life.compiler.redec_mini.Interpreter.Exception.*;
import io.github.synix4life.compiler.redec_mini.Interpreter.Function.*;
import io.github.synix4life.compiler.redec_mini.Parser.Types.*;
import io.github.synix4life.compiler.redec_mini.Parser.Types.Expr.*;
import io.github.synix4life.compiler.redec_mini.Parser.Types.Fun.*;

import java.util.HashMap;
import java.util.List;

public class Interpreter {
    // ---------------- VARIABLES ---------------- //
    private final HashMap<String, Integer> variableTable;
    private final List<Statement> list;;
    private final FunctionInterpreter f;
    private int pos = 0;



    // ---------------- CONSTRUCTOR ---------------- //

    /**
     * Constructor
     * @param list The AST from the Parser
     */
    public Interpreter(List<Statement> list) {
        this.variableTable = new HashMap<>();
        this.list = list;
        this.f = new FunctionInterpreter();
    }

    /**
     * Constructor (Allowing recursion)
     * @param list The AST from the Parser
     * @param f FunctionInterpreter to pass existing functions
     */
    public Interpreter(List<Statement> list, FunctionInterpreter f) {
        this.variableTable = new HashMap<>();
        this.list = list;
        this.f = f;
    }



    // ---------------- INTERPRET ---------------- //

    /*
    RESERVED FOR FUTURE DEVELOPMENT: First idea was printing variables after assigning. This is the leftover from that.
    private void printVariable(String name, Integer value) {
        System.out.println(name + " : " + value.toString());
    }*/

    private int interpretAssignment(Expression e) {
        if (e instanceof BinaryExpression) {
            int LHS = interpretAssignment(((BinaryExpression) e).LHS);
            int RHS = interpretAssignment(((BinaryExpression) e).RHS);
            return switch (((BinaryExpression) e).op){
                case ADD -> LHS + RHS;
                case SUB -> LHS - RHS;
                case MUL -> LHS * RHS;
                case DIV -> LHS / RHS;
                default -> throw new TypeException("Opcode not fitting! Expected BinaryExpression Opcode, but got " + ((BinaryExpression) e).op);
            };
        } else if(e instanceof BooleanExpression){
            int LHS = interpretAssignment(((BooleanExpression) e).LHS);
            int RHS = interpretAssignment(((BooleanExpression) e).RHS);
            return switch (((BooleanExpression) e).op) {
                case EQEQ -> (LHS == RHS) ? 1 : 0;
                case LT -> (LHS < RHS) ? 1 : 0;
                case LEQ -> (LHS <= RHS) ? 1 : 0;
                case GT -> (LHS > RHS) ? 1 : 0;
                case GEQ -> (LHS >= RHS) ? 1 : 0;
                default ->
                        throw new TypeException("Opcode not fitting! Expected BooleanOpcode, but got" + ((BooleanExpression) e).op.toString());
            };
        } else if (e instanceof NumberExpression n) {
            return n.value;
        } else if (e instanceof VariableExpression v) {
            if (!variableTable.containsKey(v.variable)) {
                throw new TableException("ERROR: " + v.variable + " used without defining it!");
            }
            return variableTable.get(v.variable);
        } else if (e instanceof CallExpression call) {
            return executeFunction(call.name, (List<Expression>)(List<?>)call.args); // Used for assigned calls
        }

        throw new TypeException("LHS doesnt exist");
    }

    private void interpret(Statement statement){
        if (statement instanceof Assignment) {
            String to = ((Assignment) statement).to.variable;
            int val = interpretAssignment(((Assignment) statement).from);
            variableTable.put(to, val);
            /*
            RESERVED FOR FUTURE DEVELOPMENT: First idea was printing variables after assigning. This is the leftover from that.
            printVariable(to, val);
             */
        }
        else if(statement instanceof IfStatement){
            int result = interpretAssignment(((IfStatement) statement).expression);
            if(result == 1){
                interpret(((IfStatement) statement).thenBranch);
                /*
                RESERVED FOR FUTURE MULTI STATEMENT IF → Leads to an error due to unclear scoping
                for(var st: ((IfStatement) statement).thenBranch){
                    interpret(st);
                }*/
            }else if(result == 0){
                interpret(((IfStatement) statement).elseBranch);
                /*
                RESERVED FOR FUTURE MULTI STATEMENT IF → Leads to an error due to unclear scoping
                for(var st: ((IfStatement) statement).elseBranch){
                    interpret(st);
                }*/
            }else{
                throw new TypeException("Expected a BooleanExpression");
            }
        } else if (statement instanceof WhileStatement w){
            int expressionResult = interpretAssignment(w.expression);
            int i = 0;
            while(expressionResult == 1 && i < 100){
                ++i;
                for(var st: w.body){
                    interpret(st);
                }
                expressionResult = interpretAssignment(w.expression);
            }
        } else if (statement instanceof Function) {
            f.register(statement);
        } else if (statement instanceof CallStatement call) {
            executeFunction(call.name, (List<Expression>)(List<?>)call.args); // Used for stand-alone calls
        } else if (statement instanceof ReturnStatement ret) {
            int val = interpretAssignment(ret.expression);
            throw new ReturnException(val);
        } // IN PROGRESS : else if (statement instanceof Predef p)

        pos ++;
    }



    // ---------------- INTERPRETER MAIN ---------------- //

    /**
     * Run the interpreter
     */
    public void run () {
        for (Statement statement : list) {
            interpret(statement);
        }
    }



    // ---------------- GETTER ---------------- //

    /**
     * VariableTable getter, required for the Debugger
     * @return The VariableTable
     */
    public HashMap<String, Integer> getTable(){
        return variableTable;
    }



    // ---------------- FUNCTION EXECUTION ---------------- //

    /**
     * Function to execute a program-internal function on call-statement/ -expression
     * @param name Function name
     * @param passedArgs Arguments passed into the function call
     * @return Return value (int), 0 if no return statement was found inside the function
     */
    private int executeFunction(String name, List<Expression> passedArgs) {
        // 1. Get the function logic from the registered functions
        RFunction func = f.get(name);

        // 2. Create the "executable copy"
        // We pass 'this.f', so the function can call other functions too
        Interpreter child = new Interpreter(func.statements, this.f);

        // 3. Match the numbers of arguments
        // e.g., if call "add(2, 5)", set a=2 and b=5 in the child's table
        for (int i = 0; i < func.args.size(); i++) {
            String paramName = func.args.get(i);
            int value = interpretAssignment(passedArgs.get(i));
            child.variableTable.put(paramName, value);
        }

        // 4. Run the child and catch the return using the ReturnException
        try {
            child.run();
        } catch (ReturnException e) {
            return e.value;
        }
        return 0; // If no return statement was found
    }
}