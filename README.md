# ReDeC-Mini 🚀

![Java](https://img.shields.io/badge/java-fcad3f?style=for-the-badge&logo=openjdk&logoColor=black)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)

![License](https://img.shields.io/badge/License-GNU_GPL--3.0-fcad3f?style=for-the-badge)
![Version](https://img.shields.io/badge/Version-1.0.1-fcad3f?style=for-the-badge)

![Tests Passed](https://img.shields.io/badge/Tests_Passed-0/0-fcad3f?style=for-the-badge)
![Reference Tests Passed](https://img.shields.io/badge/Reference_Tests_Passed-6/6-02303A.svg?style=for-the-badge)

---

![Unit Testing](https://img.shields.io/badge/Unit--Testing-JUnit5-02303A?style=for-the-badge&logo=junit5&logoColor=white)
![Comment](https://img.shields.io/badge/Commenting-Partially--Covered-02303A?style=for-the-badge&logo=pandoc&logoColor=white)

![Compiler Engineering](https://img.shields.io/badge/Compiler--Engineering-fcad3f?style=for-the-badge&logo=probot&logoColor=black)

**Note**: This project currently implements an interpreter. No bytecode or machine code generation is performed.

---

This project features 'ReDeC Mini', an interpreter with a recursive-descent parser as core.
It is designed for a (minimal) custom functional programming language.

This project features a full pipeline:
1. Lexer
2. Parser
3. Runtime interpreter with scoped function execution.

## ⚡ Quick Start

Prerequisites: JDK 21+ and Gradle.

Build: 
```bash
gradle build
```

Run a script:
```bash
gradle run < yourFile.*
```

## Documentations

- [Read about the language specification here](docs/Language%20Specification.md)

- [Read about the design decisions here](docs/Design%20Constraints.md)



## 🛠 Project Architecture

The compiler follows a traditional three-stage pipeline:

1. Lexer: Tokenizes raw text using regex and multi-char operator detection.

2. Parser: A recursive-descent parser that generates an Abstract Syntax Tree (AST).

3. Interpreter: Executes the AST. It uses a "Stack Frame" approach to handle function calls, ensuring local variables don't pollute the global scope.

## 📜 Info

This compiler was created as part for the JetBrains Internship Application for "Showing the Return Value on Method Exit Breakpoints in the Java Debugger".

#### Formal Requirements from the application task
- Create an interpreter for an artificial programming language. 
- Interpreter reads a source program from standard input, executes it, and prints the values of all variables to standard output

## Changelog

- $\textsf{\color{#fcad3f}Version 1.0.0}$
    - Initial Upload
    - Full Functionality for the following features:
      - Standard Assignments
      - Arithmetic and boolean expressions (chained too, considers BODMAS/PEMDAS)
      - Special Control Flow Statements: IF, WHILE
      - Function Definitions and Calls (Call Expression and Call Statement)
      - Application reference test cases passed (6/6)

      - $\textsf{\color{#fcad3f}Version 1.0.1}$
        - Minimal fixes
        - Specific pipeline class
        - Documentation / Structuring improvements
