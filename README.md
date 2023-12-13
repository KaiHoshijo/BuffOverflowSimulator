# BuffOverflowSimulator
## Assignment Description
This is the final project for the class CS Teaching Practices at Colorado School of Mines, where the following requirements are required:
- Three to four lesson plans
- Lecture slides
- Essay (not included)
- Module materials
---
## Project Summary
This is an introduction to cybersecurity, specifically the offensive portion of the industry. This project will demonstrate to students how to find and perform buffer overflows given a vulnerable program. The following assumptions are made about the students:
- The students understand Java (Have taken AP Computer Science A or a similar class)
- The students understand memory to some degree (Although the first lesson plan goes over memory, it is still assumed that this is not the first time seeing memory)
The lesson plans follow the current order:
1. Overview of memory, the stack, and a traditional call frame (x86 is used)
2. Introduction to the simulator, its call frame, and the custom instruction set architectue
2. Introduction to buffer overflows and how they utilize the call frame
3. More details on buffer overflows
---
## Explanation of the Simulator
### How is execution performed?
To reduce the complexity of the simulator, most of the preliminary actions, such as filling the stack, initializing the reigsters, and getting user input, will all be performed by Java before switching over. However, the instruction pointer will still be aware of these functions. For example, the initial execution will look like the following:
```
ip   -> 0000: sys 2
        0004: sys 3
        ...
```
Depending on the homework, the following instructions (or functions) will change.
### How is the stack implemented?
The simulator has a stack that is 4-bytes long, where it grows down from the high address of 0xffff to 0x2000. This simulator does not support heap memory as it is outside the scope of the project. The stack is used within an integer array, where the index is supposed to represent the address and the value is the associated value.
### How many registers are implemented?
The 4-byte reigsters implemented are described below:
- ip: The simulator will execute the instruction that this register is set to (must be a multiple of 4)
- sp: This is tracking the current head of the top of the stack.
- x0: A variable used to store values
### How are the instructions written?
The 8-byte instructions are all implemented as the following:
```
|-------------------------------------------------|
|---JD----4-byte input----2-byte Instruction #----|
|-------------------------------------------------|
```
The instruction must have the hex `0x7d` at the beginning of the instruction. Otherwise, the instruction will not run and the simulator will instead error out.
### What instruction are implemented?
The following instructions, following the format above, have been implemented (where the list number is its instr #):
0. add -> Adds the 4-byte input to the register x0
1. mov -> Sets the 4-byte input to the reigster x0
2. sys -> A system call that will call whichever Java function is associated with its input
### What are the syscall inputs?
The following inputs will call and execute the associated Java function:
0. printString
1. retAddr
2. initRegs
3. exit