import java.util.HashMap;

public class Simulator {

    /* 
     * This is the stack that will store the values used within this simulator
     * As it is based off a commonly used stacks, it grows from high to low
     * Additionally, the default values within each stack will be 0xff to
     * highlight the student's values that they place into it
     */
    HashMap<Character, Integer> stack = new HashMap<Character, Integer>();

    /*
     * These are the following registers used for the instruction
     * ip - This is the instruction pointer, wherever this register is set to
     *      will be the instruction executed.
     * sp - This is the stack pointer that will be set to the top of the stack
     */

    
    public Simulator() {

    }

    public void startSimulator() {

    }

    private void initStack() {

    }

    private void initRegs() {

    }
}