# DesignBugs

This document lists Design Bugs, i.e. bugs that arise from the nature of this prototype compiler.

These are considered semi-error, since they are expected in a prototype, but can be handled with cautious programming.

### Calling Functions create an empty variable table

There exists just a very basic form of scoping. The can also be seen in the entry below. 

All variables created inside a non-call-diverging program are considered global to this program. 
However, calling a function creates an empty VariableTable, in which the global variables DO NOT get copied to.

* **Example 1**

    ```rust
    fun add(a,b) { return a + b }
    
    x = 1
    x = add(x,x)
    # ↑ Works! All variables are defined
    ```
  * Variable tables after execution finishes
    * Program: `{x=2}`
    * Add-call `{a=1, b=1}`

* **Example 2**

    ```rust
    x = 0
    
    fun add(a) { return a + x }
    
    x = add(x)  
    # ↑ ERROR! "x" in add is NOT defined
   ```
  * Variable tables once the exception occurs
      * Program: `{x=0}`
      * Add-call `{a=0}` ← X not defined, because not a param, nor assigned in the function


### Branching without definition

Until now, branching can create previously not defined variables. 
This, in turn, can lead to variables not being defined after the branching-statement ends.

**Example**

```rust
x = 1
if x == 0 then y = 0 else x = x + 1
x = x + y  
# ↑ ERROR: "y" is not defined here, since the if-statement evaluates to false
```