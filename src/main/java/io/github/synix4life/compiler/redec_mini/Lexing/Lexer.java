package io.github.synix4life.compiler.redec_mini.Lexing;

import io.github.synix4life.compiler.redec_mini.Lexing.Exception.InvalidCommentFormattingException;
import io.github.synix4life.compiler.redec_mini.Lexing.Exception.LexingException;

import java.util.ArrayList;

public class Lexer {
    private final String[] data;
    private ArrayList<Token> tokens;

    /**
     * Constructor
     * @param data The string data read from stdin
     */
    public Lexer(String[] data) {
        this.data = data;
        this.tokens = null;
    }


    // ---------------- LEXER MAIN ---------------- //

    /**
     * Method to lex the data string
     * @return The lexed token list
     */
    public void parse() {
        tokens = new ArrayList<>();
        boolean isMultiLineComment = false;

        for (String line : data) {
            /* ----- Comment Handling ----- */
            if(line.startsWith("--#")){
                if(!isMultiLineComment){ throw new InvalidCommentFormattingException("Reached comment end token without a start token!"); }
                isMultiLineComment = false;
                continue;
            }
            if(line.startsWith("#") || isMultiLineComment){
                if(line.startsWith("#--")){
                    isMultiLineComment = true;
                }
                continue;
            }

            /* ----- Tokenization ----- */
            ArrayList<Token> token = tokenize(line);
            tokens.addAll(token);
        }
    }


    // ---------------- TOKENIZER ---------------- //

    /**
     * Method to split and tokenize the string line by line.
     * @param line The current line.
     * @return The list of tokens from that line.
     */
    private ArrayList<Token> tokenize(String line) {
        ArrayList<Token> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            // ----------------------------
            // 1. HANDLE WHITESPACE → FLUSH
            // ----------------------------
            if (Character.isWhitespace(c)) {
                if (!current.isEmpty()) {
                    result.add(createToken(current.toString()));
                    current.setLength(0);
                }
                continue;
            }

            // ----------------------------
            // 2. MULTI-CHAR OPERATORS
            // ----------------------------
            if (i + 1 < line.length()) {
                String two = "" + c + line.charAt(i + 1);

                if (two.equals("==") || two.equals("!=") ||
                        two.equals(">=") || two.equals("<=")) {

                    if (!current.isEmpty()) {
                        result.add(createToken(current.toString()));
                        current.setLength(0);
                    }

                    result.add(createToken(two));
                    i++;
                    continue;
                }
            }

            // ----------------------------
            // 3. SINGLE CHAR OPERATORS / PUNCTUATION
            // ----------------------------
            if ("(){}[],+-*/=<>".indexOf(c) != -1) {

                if (!current.isEmpty()) {
                    result.add(createToken(current.toString()));
                    current.setLength(0);
                }

                result.add(createToken(String.valueOf(c)));
                continue;
            }

            // ----------------------------
            // 4. IDENTIFIERS / NUMBERS
            // ----------------------------
            if (Character.isLetterOrDigit(c) || c == '_') {
                current.append(c);
            } else {
                if (!current.isEmpty()) {
                    result.add(createToken(current.toString()));
                    current.setLength(0);
                }
                result.add(createToken(String.valueOf(c)));
            }
        }

        // ----------------------------
        // 5. FLUSH LAST TOKEN
        // ----------------------------
        if (!current.isEmpty()) {
            result.add(createToken(current.toString()));
        }

        return result;
    }


    // ---------------- TOKEN CREATION ---------------- //

    /**
     * Method to create the required token. Performs a regex match on the string from the program.
     * @param entry The value string
     * @return The created Token{TokenType, String}
     */
    private Token createToken(String entry) {
        return switch (entry) {
            case "while" -> new Token(TokenType.WHILE, entry);
            case "if" -> new Token(TokenType.IF, entry);
            case "else" -> new Token(TokenType.ELSE, entry);
            case "fun" -> new Token(TokenType.FUN, entry);
            case "then" -> new Token(TokenType.THEN, entry);
            case "do" -> new Token(TokenType.DO, entry);
            case "return" -> new Token(TokenType.RETURN, entry);
            case "true" -> new Token(TokenType.TRUE, entry);
            case "false" -> new Token(TokenType.FALSE, entry);
            case "+" -> new Token(TokenType.ADD, entry);
            case "-" -> new Token(TokenType.SUB, entry);
            case "*" -> new Token(TokenType.MUL, entry);
            case "/" -> new Token(TokenType.DIV, entry);
            case "(" -> new Token(TokenType.LPAREN, entry);
            case ")" -> new Token(TokenType.RPAREN, entry);
            case "{" -> new Token(TokenType.LBRACE, entry);
            case "}" -> new Token(TokenType.RBRACE, entry);
            case "=" -> new Token(TokenType.EQUALS, entry);
            case "," -> new Token(TokenType.COMMA, entry);
            case "EOF" -> new Token(TokenType.EOF, entry);
            case "<" -> new Token(TokenType.LT, entry);
            case "<=" -> new Token(TokenType.LEQ, entry);
            case ">" -> new Token(TokenType.GT, entry);
            case ">=" -> new Token(TokenType.GEQ, entry);
            case "==" -> new Token(TokenType.EQEQ, entry);
            default -> {
                if (entry.matches("\\d+")) {
                    yield new Token(TokenType.NUMBER, entry);
                }
                yield new Token(TokenType.IDENT, entry);
            }
        };
    }



    // ---------------- TOKEN MANIPULATION ---------------- //

    /**
     * Static Method to correctly register Calls following the lexing process.
     * It is executed post-lexing to ensure all usages can correctly be registered and assigned.
     */
    private void registerCalls() {
        ArrayList<String> funNames = new ArrayList<>();

        // Pass 1: Find all function names
        for (int i = 0; i < tokens.size() - 1; i++) {
            if (tokens.get(i).type() == TokenType.FUN) {
                funNames.add(tokens.get(i + 1).value());
            }
        }

        // Pass 2: Update IDENT to CALL
        for (int i = 0; i < tokens.size(); i++) {
            Token t = tokens.get(i);
            if (t.type() == TokenType.IDENT && funNames.contains(t.value()) && i > 0 && tokens.get(i-1).type() != TokenType.FUN) {
                tokens.set(i, new Token(TokenType.CALL, t.value()));
            }
        }
    }

    /**
     * Function to call several optimizations for the lexed results
     * Example: Convert IDENT -> CALL
     */
    public void optimizeLex(){
        registerCalls();
    }



    // ---------------- GETTER ---------------- //

    /**
     * Get the List of tokens
     * @return The lexed tokens
     * @throws NullPointerException If the tokens haven't yet been created
     */
    public ArrayList<Token> getTokens() throws NullPointerException{
        if(tokens == null){
            throw new NullPointerException("No tokens created yet");
        }
        return this.tokens;
    }
}