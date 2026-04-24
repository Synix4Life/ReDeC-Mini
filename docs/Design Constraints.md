# Design


### Preliminary constraints due to given example

#### 1. No multi-If-statements possible

* If-Statements can only have one Statement per Branch
* This comes from one example, where it would else lead to a scopting error if multi-if-statements are possible


* **Example**

    ```rust
    x = 0
    y = 0
    while x < 3 do if x == 1 then y = 10 else y = y + 1, x = x + 1
    ```
    
  * **Multi-If allowed** would lead to the AST:
      ```rust
        |__ ASSIGNMENT
            |__ TO: x
              |__ FROM:
                |__ NUMBER: 0
        |__ ASSIGNMENT
            |__ TO: y
            |__ FROM:
                |__ NUMBER: 0
        |__ WHILE
            |__ COND:
                |__ BOOL OP: LT
                    |__ VAR: x
                    |__ NUMBER: 3
            |__ BODY:
                |__ IF
                    |__ COND:
                        |__ BOOL OP: EQEQ
                            |__ VAR: x
                            |__ NUMBER: 1
                    |__ THEN:
                        |__ ASSIGNMENT
                            |__ TO: y
                            |__ FROM:
                                |__ NUMBER: 10
                    |__ ELSE:
                        |__ ASSIGNMENT
                            |__ TO: y
                            |__ FROM:
                                |__ BINARY OP: ADD
                                    |__ VAR: y
                                    |__ NUMBER: 1
                        |__ ASSIGNMENT
                            |__ TO: x
                            |__ FROM:
                                |__ BINARY OP: ADD
                                    |__ VAR: x
                                    |__ NUMBER: 1
      ```
    * Since the last assignment is in the else branch, once `x == 1`, the `if`-branch will infinitely be called, since x isn't changed

    * **Only one Statement per If branch allowed** would lead to the AST:
        ```rust
          |__ ASSIGNMENT
              |__ TO: x
                |__ FROM:
                  |__ NUMBER: 0
          |__ ASSIGNMENT
              |__ TO: y
              |__ FROM:
                  |__ NUMBER: 0
          |__ WHILE
              |__ COND:
                  |__ BOOL OP: LT
                      |__ VAR: x
                      |__ NUMBER: 3
              |__ BODY:
                  |__ IF
                      |__ COND:
                          |__ BOOL OP: EQEQ
                              |__ VAR: x
                              |__ NUMBER: 1
                      |__ THEN:
                          |__ ASSIGNMENT
                              |__ TO: y
                              |__ FROM:
                                  |__ NUMBER: 10
                      |__ ELSE:
                          |__ ASSIGNMENT
                              |__ TO: y
                              |__ FROM:
                                  |__ BINARY OP: ADD
                                      |__ VAR: y
                                      |__ NUMBER: 1
                  |__ ASSIGNMENT
                      |__ TO: x
                      |__ FROM:
                          |__ BINARY OP: ADD
                              |__ VAR: x
                              |__ NUMBER: 1
        ```
        * Since the last assignment is not in the if-statement, it will work as expected


* Changed places to fit marked with `RESERVED FOR FUTURE MULTI STATEMENT IF → Leads to an error due to unclear scoping`