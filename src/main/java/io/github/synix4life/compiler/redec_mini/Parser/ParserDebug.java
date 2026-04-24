package io.github.synix4life.compiler.redec_mini.Parser;

import io.github.synix4life.compiler.redec_mini.Parser.Types.Expr.*;
import io.github.synix4life.compiler.redec_mini.Parser.Types.*;
import io.github.synix4life.compiler.redec_mini.Parser.Types.Fun.CallExpression;
import io.github.synix4life.compiler.redec_mini.Parser.Types.Fun.CallStatement;
import io.github.synix4life.compiler.redec_mini.Parser.Types.Fun.Function;

import java.util.List;

public class ParserDebug {

    // ---------------- DEBUGGER ---------------- //

    /**
     * Parse debug (i.e. print the AST)
     * @param statements List of the statements
     */
    public static void debugParser(List<Statement> statements) {
        System.out.println("\n # =============== AST =============== # \n");

        for (Statement s : statements) {
            printStatement(s, 0);
        }
        System.out.println();
    }


    // ---------------- HELPER ---------------- //

    private static void printIndent(int level) {
        System.out.print("   ".repeat(level));
    }

    private static void printBranch(int level) {
        System.out.print("|__ ");
    }


    // ---------------- STATEMENTS ---------------- //

    private static void printStatement(Statement s, int level) {

        if (s instanceof Assignment a) {
            printIndent(level);
            printBranch(level);
            System.out.println("ASSIGNMENT");

            printIndent(level + 1);
            printBranch(level + 1);
            System.out.println("TO: " + a.to.variable);

            printIndent(level + 1);
            printBranch(level + 1);
            System.out.println("FROM:");

            printExpression(a.from, level + 2);
        }
        else if(s instanceof IfStatement i){
            printIndent(level);
            printBranch(level);
            System.out.println("IF");

            printIndent(level + 1);
            printBranch(level + 1);
            System.out.println("COND: ");
            printExpression(i.expression, level + 2);

            printIndent(level + 1);
            printBranch(level + 1);
            System.out.println("THEN:");

            printStatement(i.thenBranch, level + 2);
            /*
            RESERVED FOR FUTURE MULTI STATEMENT IF → Leads to an error due to unclear scoping
            for(var thenStatement: i.thenBranch){
                printStatement(thenStatement, level + 2);
            }*/

            printIndent(level + 1);
            printBranch(level + 1);
            System.out.println("ELSE:");

            printStatement(i.elseBranch, level + 2);
            /*
            RESERVED FOR FUTURE MULTI STATEMENT IF → Leads to an error due to unclear scoping
            for(var elseStatement: i.elseBranch){
                printStatement(elseStatement, level + 2);
            }*/
        } else if(s instanceof WhileStatement w){
            printIndent(level);
            printBranch(level);
            System.out.println("WHILE");

            printIndent(level + 1);
            printBranch(level + 1);
            System.out.println("COND:");
            printExpression(w.expression, level + 2);

            printIndent(level + 1);
            printBranch(level + 1);
            System.out.println("BODY:");
            for(var statement: w.body){
                printStatement(statement, level + 2);
            }
        } else if(s instanceof ReturnStatement r){
            printIndent(level);
            printBranch(level);
            System.out.println("RETURN");

            printIndent(level + 1);
            printBranch(level + 1);
            System.out.println("EXPRESSION: ");
            printExpression(r.expression, level + 2);
        } else if (s instanceof Function) {
            printFunction((Function) s, level);
        } else if (s instanceof CallStatement c) {
            printIndent(level);
            printBranch(level);
            System.out.println("CALL: " + c.name);
            printArguments(c.args, level + 1);
        }
    }


    // ---------------- EXPRESSIONS ---------------- //

    private static void printExpression(Expression e, int level) {

        if (e instanceof BinaryExpression b) {

            printIndent(level);
            printBranch(level);
            System.out.println("BINARY OP: " + b.op);

            printExpression(b.LHS, level + 1);

            printExpression(b.RHS, level + 1);
        } else if(e instanceof BooleanExpression b){
            printIndent(level);
            printBranch(level);
            System.out.println("BOOL OP: " + b.op);

            printExpression(b.LHS, level + 1);

            printExpression(b.RHS, level + 1);
        } else if (e instanceof VariableExpression v) {

            printIndent(level);
            printBranch(level);
            System.out.println("VAR: " + v.variable);
        } else if (e instanceof NumberExpression n) {

            printIndent(level);
            printBranch(level);
            System.out.println("NUMBER: " + n.value);
        } else if (e instanceof CallExpression c) {
            printIndent(level);
            printBranch(level);
            System.out.println("CALL: " + c.name);
            printArguments(c.args, level + 1);
        }
    }


    // ---------------- FUNCTION ---------------- //

    private static void printFunction(Function f, int level){
        printIndent(level);
        printBranch(level);
        System.out.println("FUNCTION \"" + f.name + "\"");

        printIndent(level + 1);
        printBranch(level + 1);
        System.out.println("ARGS: ");

        for(var arg: f.args){
            printIndent(level + 2);
            printBranch(level + 2);
            System.out.println(arg.variable);
        }

        printIndent(level + 1);
        printBranch(level + 1);
        System.out.println("STATEMENTS:");

        for(Statement statement: f.statements){
            printStatement(statement, level+2);
        }
    }

    private static void printArguments(List<Expression> args, int level) {
        printIndent(level);
        printBranch(level);
        System.out.println("ARGS:");
        for (Expression arg : args) {
            printExpression(arg, level + 1);
        }
    }
}