# Mini-Compiler Language Specification (v1.0)

This document defines the formal syntax, semantics, and behavior of the language supported by the **ReDeC-Mini Interpreter**.

---

## 1. Core Concepts
* **Execution Model:** Interpreted via Abstract Syntax Tree (AST) walking.
* **Typing:** Weakly typed, Integer-based.
* **Scope:** Lexical Isolation (Stack-based function scoping).

---

## 2. Lexical Grammar

### 2.1 Identifiers & Literals
* **IDENT:** Matches `[a-zA-Z_][a-zA-Z0-9_]*`. (e.g., `my_var`, `x`, `func1`).
* **NUMBER:** 32-bit signed integers. Matches `[0-9]+`.

### 2.2 Keywords
The following strings are reserved keywords and cannot be used as identifiers:
`fun`, `if`, `then`, `else`, `while`, `do`, `return`

### Supported Operators

| Category         | Supported Operators              |
|:-----------------|:---------------------------------|
| **Arithmetic:**  | `+`, `-`, `*`, `/`               |
| **Comparison:**  | `==`, `!=`, `<`, `<=`, `>`, `>=` |
| **Assignment:**  | `=`                              |
| **Delimiters:**  | `(`, `)`, `{`, `}`, `,`          |


---

## 3. Syntax (Grammar Rules)

### 3.1 Programs
A program consists of a sequence of statements executed from top to bottom.

### 3.2 Functions
Functions must be defined before they are called (single-pass limitation) or registered via the Lexer's two-pass call registration.
* **Syntax:** `fun [name]([args]) { [statements] }`
* **Example:** 
```rust
fun add(a, b) { return a + b }
```

### 3.3 Control Flow

* **If-Statement**: Requires an else branch in the current implementation. 
  * `if [condition] then [statement] else [statement]`
* **While-Loop**: A standard while-loop
  * `while [condition] do [body]`

### 3.4 Comments
* **Syntax**: ``# [comment]``
* **Example:**
```rust
x = 2
# If-Statement follows
if x == 2 then x = x + 1 else x = x - 1
```

* **Multi-Line-Comment**:
  * **Syntax**: 
    ```rust
    #-- [comment line 1]
    [comment line 2]
    ...
    [comment line n]
    --#
    ```
  * **Attention**: `--#` must be in its own line

## 4. Semantics
### 4.1 Expressions

* Expressions are evaluated following standard mathematical order of operations (BODMAS/PEMDAS):
* **Parentheses** `()`
* **Multiplication and Division** `*, /`
* **Addition and Subtraction** `+, -`
* **Comparisons** `==, >,` etc.

### 4.2 Function Calls & Scoping

* The language utilizes Call-by-Value semantics:
* Arguments are evaluated in the caller's scope. 
* A new Stack Frame (local variable table) is created. 
* Argument values are bound to parameter names in the local table. 
* The function body is executed. 
* Upon a `return` statement, the result is passed back, the stack frame is destroyed, and execution resumes in the caller's scope.

### 4.3 Boolean Logic

* There exist explicit Boolean type. The language knows
  * `True` is treated as `1`
  * `False` is treated as `0`
* Comparison operators return these integer literals. 
* The parser reduces booleans to integers

### 4.4 Variable Scope

* **General**: Global
  * Variables are all global
  * This includes branch definitions
  * [See here for more infos](Design%20Bugs.md)

* **Functions**: Separated
  * Each function creates a new VariableTable upon creation!
  * [See here for more infos](Design%20Bugs.md)

## 5. Standard Output (Debugger)

* When running in debug mode, the interpreter provides:
  * **Lexer**: The list of generated lexer tokens
  * **AST Tree**: A visual representation of the parsed program structure.
  * **Symbol Table**: A dump of the global variable states upon program termination.

### 5.1 Using Debug Mode

To use the `Debug` mode, the first line of your program file must exactly be `DEBUG`. 

* **Example**:
```rust
DEBUG    
# ↑ This line is important!
x = 0
...
```

* Actually, `DEBUG` is still a valid variable name, since a variable can, by grammar rules, not appear without a definition.
