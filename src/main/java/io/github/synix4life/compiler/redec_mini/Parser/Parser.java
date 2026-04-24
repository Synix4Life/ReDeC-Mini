package io.github.synix4life.compiler.redec_mini.Parser;

import io.github.synix4life.compiler.redec_mini.Lexing.*;
import io.github.synix4life.compiler.redec_mini.Parser.Exception.ParserException;
import io.github.synix4life.compiler.redec_mini.Parser.Exception.SubClassParserException;
import io.github.synix4life.compiler.redec_mini.Parser.Types.*;
import io.github.synix4life.compiler.redec_mini.Parser.Types.Expr.*;
import io.github.synix4life.compiler.redec_mini.Parser.Types.Fun.*;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Parser {
    // ---------------- VARIABLES ---------------- //
    private final ArrayList<Token> lex;
    private int pos = 0;
    private final HashMap<String, Function> f = new HashMap<String, Function>();

    private final List<Statement> statementList = new ArrayList<>();



    // ---------------- CONSTRUCTOR ---------------- //

    /**
     * Constructor
     * @param lex The lexing results
     */
    public Parser(ArrayList<Token> lex){
        this.lex = lex;
    }



    // ---------------- PARSER MAIN ---------------- //

    /**
     * Parser root
     */
    public void parse(){
        while(pos < lex.size()){
            statementList.add(parseStatement());
        }
    }



    // ---------------- HELPER ---------------- //

    /**
     * Check if the current token is a specific token type
     * @param t TokenType
     * @return Is the current token the specific token type?
     */
    private boolean check(TokenType t){
        return pos < lex.size() && lex.get(pos).type() == t;
    }

    /**
     * Consume a token -> Check if it is the expected and move forwards
     * @param regex TokenType that it shall be
     * @throws ParserException If we currently don't have the regex-TokenType
     */
    private void consume(TokenType regex){
        if(!(lex.get(pos).type() == regex)){
            throw new ParserException("Not the expected TokenType!");
        }
        pos++;
    }

    /**
     * Consume a token and return its value
     * @param regex TokenType that it shall be
     * @return The value of the consumed token
     * @throws ParserException If we currently don't have the regex-TokenType
     */
    private String consumeReturn(TokenType regex){
        if(!(lex.get(pos).type() == regex)){
            throw new ParserException("Not the expected TokenType!");
        }
        pos++;
        return lex.get(pos-1).value();
    }

    /**
     * Method to perform an opcode regex (TokenType -> Opcode)
     * @return Opcode
     * @throws ParserException If the TokenType isn't convertable to an Opcode
     */
    private Opcodes opcodeRegex() throws ParserException{
        return switch (lex.get(pos).type()) {
            case TokenType.ADD -> Opcodes.ADD;
            case TokenType.SUB -> Opcodes.SUB;
            case TokenType.MUL -> Opcodes.MUL;
            case TokenType.DIV -> Opcodes.DIV;
            case TokenType.GT -> Opcodes.GT;
            case TokenType.GEQ -> Opcodes.GEQ;
            case TokenType.LT -> Opcodes.LT;
            case TokenType.LEQ -> Opcodes.LEQ;
            case TokenType.EQEQ -> Opcodes.EQEQ;
            default -> throw new ParserException("Opcode not recognized");
        };
    }



    // ================ PARSER ================ //

    // ---------------- STATEMENTS ---------------- //

    /**
     * Parse a Statement
     * @return The parsed Statement
     * @throws ParserException If current Statement wasn't recognized
     */
    private Statement parseStatement() throws ParserException{
        if (check(TokenType.IDENT)) {
            return parseAssignment();
        }
        if (check(TokenType.IF)) {
            return parseIfStatement();
        }
        if (check(TokenType.WHILE)) {
            return parseWhileStatement();
        }
        if (check(TokenType.FUN)) {
            return parseFunction();
        }
        if(check(TokenType.RETURN)){
            return parseReturn();
        }

        if(check(TokenType.CALL)){
            return parseFunctionCallStatement();
        }

        throw new ParserException("Not recognized (parseStatement) - Right now at " + lex.get(pos).value());
    }



    // ---------------- IF STATEMENT ---------------- //

    /**
     * Parse an If-Statement
     * @return The parsed If-Statement
     */
    private Statement parseIfStatement() {
        consume(TokenType.IF);
        IfStatement statement = new IfStatement();

        statement.expression = parseExpression();
        consume(TokenType.THEN);

        statement.thenBranch = parseStatement();

        /*
        RESERVED FOR FUTURE MULTI STATEMENT IF → Leads to an error due to unclear scoping
        statement.thenBranch.add(parseStatement());
        System.out.println("Parsing other");
        while(check(TokenType.COMMA)){
            consume(TokenType.COMMA);
            System.out.println("Going onto next then");
            statement.thenBranch.add(parseStatement());
        }*/
        consume(TokenType.ELSE);

        statement.elseBranch = parseStatement();
        /*
        RESERVED FOR FUTURE MULTI STATEMENT IF → Leads to an error due to unclear scoping
        statement.elseBranch.add(parseStatement());
        System.out.println("parsing other else");
        while(check(TokenType.COMMA)){
            consume(TokenType.COMMA);
            System.out.println("going onto next else");
            statement.elseBranch.add(parseStatement());
        }*/

        return statement;
    }



    // ---------------- WHILE LOOP ---------------- //

    /**
     * Parse a While-Statement
     * @return The parsed While-Statement
     * @throws SubClassParserException If the loop-expression isn't a boolean
     *
     * @implNote Fun fact: The throwing of the exception is the only real type checking on parsing as of Version 1.0.0. This should be extended in the future
     */
    private Statement parseWhileStatement() throws SubClassParserException{
        consume(TokenType.WHILE);

        WhileStatement statement = new WhileStatement();
        statement.expression = parseExpression();
        if(! ((statement.expression instanceof BooleanExpression) || (statement.expression instanceof NumberExpression))){
            throw new SubClassParserException("While expression is not a Boolean Expression");
        }
        consume(TokenType.DO);
        statement.body.add(parseStatement());
        while(check(TokenType.COMMA)){
            consume(TokenType.COMMA);
            statement.body.add(parseStatement());
        }
        return statement;
    }



    // ---------------- FUNCTION ---------------- //

    /**
     * Parse the Function-Call Arguments
     * @return The parsed Args
     */
    private List<Expression> parseCallArguments(){
        List<Expression> args = new ArrayList<>();
        consume(TokenType.LPAREN);
        while(!check(TokenType.RPAREN)){
            args.add(parseExpression());
            if(check(TokenType.COMMA)){ consume(TokenType.COMMA); }
        }
        consume(TokenType.RPAREN);
        return args;
    }

    /**
     * Parse a Function-Statement
     * @return The parsed Function
     */
    private Statement parseFunction(){
        consume(TokenType.FUN);
        Function function = new Function();
        function.name = parseVariableExpression().variable;

        f.put(function.name, function); // Put early to allow recursion

        List<VariableExpression> args = new ArrayList<>();
        consume(TokenType.LPAREN);
        while(!check(TokenType.RPAREN)){
            args.add(parseVariableExpression());
            if(check(TokenType.COMMA)){ consume(TokenType.COMMA); }
        }
        consume(TokenType.RPAREN);
        function.args = args;

        List<Statement> statements = new ArrayList<>();
        consume(TokenType.LBRACE);
        while(!check(TokenType.RBRACE)){
            statements.add(parseStatement());
            if(check(TokenType.COMMA)){ consume(TokenType.COMMA); }
        }
        consume(TokenType.RBRACE);
        function.statements = statements;

        return function;
    }

    /**
     * Parse a Return-Statement
     * @return The parsed Return-Statement
     */
    private Statement parseReturn(){
        consume(TokenType.RETURN);
        ReturnStatement returnStatement = new ReturnStatement();
        returnStatement.expression = parseExpression();
        return returnStatement;
    }

    /**
     * Parse a Function-Call Statement (i.e. shall have no return)
     * @return The parsed Call Statement
     * @throws ParserException If no such function is registered
     */
    private Statement parseFunctionCallStatement(){
        CallStatement call = new CallStatement();
        call.name = consumeReturn(TokenType.CALL);
        call.args = parseCallArguments();
        if(!f.containsKey(call.name)){ throw new ParserException("Parsing call Statement Failed -> No such Function registered"); }
        call.function = f.get(call.name);
        return call;
    }

    /**
     * Parse a Function-Call Expression (i.e. shall have return)
     * @return The parsed Call Expression
     * @throws ParserException If no such function is registered
     */
    private Expression parseFunctionCallExpression(){
        CallExpression call = new CallExpression();
        call.name = consumeReturn(TokenType.CALL);
        call.args = parseCallArguments();
        if(!f.containsKey(call.name)){ throw new ParserException("Parsing call Expression Failed -> No such Function registered"); }
        call.function = f.get(call.name);
        return call;
    }



    // ---------------- ASSIGNMENTS ---------------- //

    /**
     * Parse am Assignment
     * @return The parsed Assignment
     */
    private Statement parseAssignment() throws ParserException{
        Assignment assignment = new Assignment();
        assignment.to = parseVariableExpression();
        consume(TokenType.EQUALS);
        assignment.from = parseExpression();
        return assignment;
    }



    // ---------------- EXPRESSIONS ---------------- //

    /**
     * Parse an Expression (Comparison)
     * @return The parsed Expression
     */
    private Expression parseExpression(){
        Expression left = parseArithmetic();

        while(check(TokenType.LT) || check(TokenType.LEQ) || check(TokenType.GT) || check(TokenType.GEQ) || check(TokenType.EQEQ)){
            Opcodes op = opcodeRegex();
            pos++;
            Expression right = parseArithmetic();
            left = new BooleanExpression(left, op, right);
        }
        return left;
    }

    /**
     * Parse an Arithmetic Expression (Addition/ Subtraction)
     * @return The parsed Expression
     */
    private Expression parseArithmetic() {
        Expression left = parseTerm();

        while (check(TokenType.ADD) || check(TokenType.SUB)) {
            Opcodes op = opcodeRegex();
            pos++;
            Expression right = parseTerm();
            left = new BinaryExpression(left, op, right);
        }

        return left;
    }

    /**
     * Parse a Term Expression (Multiplication/ Division)
     * @return The parsed Expression
     */
    private Expression parseTerm() {
        Expression left = parseFactor();

        while (check(TokenType.MUL) || check(TokenType.DIV)) {
            Opcodes op = opcodeRegex();
            pos++;
            Expression right = parseFactor();
            left = new BinaryExpression(left, op, right);
        }

        return left;
    }

    /**
     * Parse a Factor Expression (i.e. the smallest unit, which include PARENs, CALLs, IDENTifiers, ...)
     * @return The parsed factor
     */
    private Expression parseFactor() {
        if(check(TokenType.CALL)){
            return parseFunctionCallExpression();
        }
        if (check(TokenType.NUMBER)) {
            return parseNumberExpression();
        }
        if (check(TokenType.IDENT)) {
            return parseVariableExpression();
        }
        if(check(TokenType.TRUE) || check(TokenType.FALSE)) {
            return parseBooleanExpression();
        }

        if (check(TokenType.LPAREN)) {
            consume(TokenType.LPAREN);
            Expression expr = parseExpression();
            consume(TokenType.RPAREN);
            return expr;
        }

        throw new ParserException("Expected expression. Right now at pos(" + pos+ ") with " + lex.get(pos).value());
    }

    /**
     * Parse a Number Expression (Integer Variable)
     * @return The parsed Number Expression
     */
    private NumberExpression parseNumberExpression(){
        NumberExpression e = new NumberExpression();
        e.value = Integer.parseInt(consumeReturn(TokenType.NUMBER));
        return e;
    }

    /**
     * Parse a Variable Expression (Variable (e.g., "x"))
     * @return The parsed Variable Expression
     */
    private VariableExpression parseVariableExpression(){
        VariableExpression v = new VariableExpression();
        v.variable = consumeReturn(TokenType.IDENT);
        return v;
    }

    /**
     * Parse a Boolean Expression (true/ false)
     * @return The parsed Boolean Expression
     * @throws ParserException If the current variable isn't a boolean
     */
    private NumberExpression parseBooleanExpression(){
        NumberExpression e = new NumberExpression();
        if(check(TokenType.TRUE)){
            e.value = 1;
        }
        else if(check(TokenType.FALSE)){
            e.value = 0;
        }
        else {
            throw new ParserException("Boolean type neither true, nor false");
        }
        pos++;
        return e;
    }



    // ---------------- EXPRESSIONS ---------------- //

    /**
     * Get the list of statements
     * @return List of Statements -> AST
     * @throws NullPointerException If the list hasn't yet been created
     */
    public List<Statement> getStatementList() throws NullPointerException{
        if(statementList.isEmpty()){
            throw new NullPointerException("StatementList not initialized");
        }
        return statementList;
    }
}





























