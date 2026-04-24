package io.github.synix4life.compiler.redec_mini;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ReferenceTest {
    @Test
    @DisplayName("Simple Assignment Test")
    void testSimpleProgram() throws Exception {
        // ----- INPUT (stdin) ----- //
        String input = "x = 2\ny = x + 3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // ----- CAPTURE stdout ----- //
        ByteArrayOutputStream content = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(content));

        try {
            Main.main(new String[]{});
        } finally {
            System.setOut(originalOut);
        }

        // ----- ASSERT OUTPUT ----- //
        String output = content.toString().trim();

        assertTrue(output.contains("x: 2"));
        assertTrue(output.contains("y: 5"));
    }

    @Test
    @DisplayName("Simple If Test")
    void testSimpleIf() throws Exception {
        // ----- INPUT (stdin) ----- //
        String input = "x = 20\nif x > 10 then y = 100 else y = 0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // ----- CAPTURE stdout ----- //
        ByteArrayOutputStream content = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(content));

        try {
            Main.main(new String[]{});
        } finally {
            System.setOut(originalOut);
        }

        // ----- ASSERT OUTPUT ----- //
        String output = content.toString().trim();

        assertTrue(output.contains("x: 20"));
        assertTrue(output.contains("y: 100"));
    }

    @Test
    @DisplayName("Simple While Test")
    void testSimpleWhile() throws Exception {
        // ----- INPUT (stdin) ----- //
        String input = "x = 0\ny = 0\nwhile x < 3 do if x == 1 then y = 10 else y = y + 1, x = x + 1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // ----- CAPTURE stdout ----- //
        ByteArrayOutputStream content = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(content));

        try {
            Main.main(new String[]{});
        } finally {
            System.setOut(originalOut);
        }

        // ----- ASSERT OUTPUT ----- //
        String output = content.toString().trim();

        assertTrue(output.contains("x: 3"));
        assertTrue(output.contains("y: 11"));
    }

    @Test
    @DisplayName("Simple Function Test")
    void testSimpleFunc() throws Exception {
        // ----- INPUT (stdin) ----- //
        String input = "fun add(a, b) { return a + b }\nfour = add( 2, 2)\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // ----- CAPTURE stdout ----- //
        ByteArrayOutputStream content = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(content));

        try {
            Main.main(new String[]{});
        } finally {
            System.setOut(originalOut);
        }

        // ----- ASSERT OUTPUT ----- //
        String output = content.toString().trim();

        assertTrue(output.contains("four: 4"));
    }

    @Test
    @DisplayName("Recursive Function Test")
    void testFuncRec() throws Exception {
        // ----- INPUT (stdin) ----- //
        String input = "fun fact_rec(n) { if n <= 0 then return 1 else return n*fact_rec(n-1) }\na = fact_rec(5)\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // ----- CAPTURE stdout ----- //
        ByteArrayOutputStream content = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(content));

        try {
            Main.main(new String[]{});
        } finally {
            System.setOut(originalOut);
        }

        // ----- ASSERT OUTPUT ----- //
        String output = content.toString().trim();

        assertTrue(output.contains("a: 120"));
    }

    @Test
    @DisplayName("Iterative Function Test")
    void testFuncIt() throws Exception {
        // ----- INPUT (stdin) ----- //
        String input = "fun fact_iter(n) { r = 1, while true do if n == 0 then return r else r = r * n, n = n - 1 }\nb = fact_iter(5)\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // ----- CAPTURE stdout ----- //
        ByteArrayOutputStream content = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(content));

        try {
            Main.main(new String[]{});
        } finally {
            System.setOut(originalOut);
        }

        // ----- ASSERT OUTPUT ----- //
        String output = content.toString().trim();

        assertTrue(output.contains("b: 120"));
    }
}
