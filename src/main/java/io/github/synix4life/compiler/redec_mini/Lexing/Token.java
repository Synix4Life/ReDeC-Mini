package io.github.synix4life.compiler.redec_mini.Lexing;

//  (Converted to record class, non-converted class below)

/**
 * Token class
 */
public record Token(TokenType type, String value) {}

/*
 * public class Token {
 *     private final TokenType type;
 *     private final String value;
 *     public Token(TokenType type, String value){
 *         this.type = type;
 *         this.value = value;
 *     }
 *     public TokenType getType(){
 *         return type;
 *     }
 *     public String getValue(){
 *         return value;
 *     }
 * }
 */