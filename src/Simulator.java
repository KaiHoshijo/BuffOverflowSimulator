import java.util.*;

public class Simulator {

    /*
     * This is the memory used in this program. The bytes located from
     * 0xffff down to 0x2000 will be used for the stack. The values located
     * from 0x0000 to 0x2000 will be ussed for the code. The default
     * of the memory is going to be zero.
     */
    private int[] memory = new int[0xffff];

    /*
     * These are the following registers used for the instruction
     * ip - This is the instruction pointer, wherever this register is set to
     *      will be the instruction executed.
     * sp - This is the stack pointer that will be set to the top of the stack
     * x0 - This is the register used to store temporary 
     */
    private int reg_ip;
    private int reg_sp;
    private int reg_x0;

    private Scanner sc = new Scanner(System.in);

    
    public Simulator() {
        // Setting the initial action to be initialize the registers
        try {
            setMemory(0x0000, Instruction.getSys(Syscall.INITREGS));
            System.out.println("Initial sim funcs set");
        } catch (Exception e) {
            System.out.println("Error with simulator init");
            System.out.println(e);
        }
    }

    public void runSimulator() throws Exception {
        // Continues executing code until syscall 4 (exit)
        boolean run = true;
        while (run) {
            if (!isValidIp(reg_ip)) {
                throw new Exception("The instruction pointer is not set to a properly formatted address");
            }
            if (!isValidInstr(memory[reg_ip])) {
                throw new Exception("An invalid instruction has been found");
            }
            ISA currentInstr = Instruction.convertInstr(memory[reg_ip]);
            int currentVal   = Instruction.getVal(memory[reg_ip]); 
            System.out.println(currentInstr);
            if (currentInstr == ISA.SYS && Syscall.fromInteger(currentVal) == Syscall.EXIT) {
                run = false;
            } else {
                executeInstr(currentInstr, currentVal);
            }
            reg_ip += 8;
        }
    }

    private void initRegs() {
        reg_ip = 0x0000;
        reg_sp = 0xffff;
        reg_x0 = 0x0;
    }

    private void setMemory(int address, int value) throws Exception {
        // Checks if address is within the valid space of 0x0000 up to 0xffff
        if (address > 0xffff || address < 0) {
            throw new Exception("Address not in range of 0x7fffffff and 0x0000: " + Integer.toString(address));
        }
        // Ensures that the value supplied is valid
        if (value > Integer.MAX_VALUE || value < 0) {
            throw new Exception("Value not in range of 0x7fffffff and 0x0000: " + String.format("0x%08X", value));
        }
        // Sets the value to the address if both are valid
        memory[address] = value;
    }

    private boolean isValidIp(int ip) {
        // Only addresses within the range will be valid
        return ip >= 0 && ip < Integer.MAX_VALUE;
    }

    private boolean isValidInstr(int instr) {
        /*
         * Instructions must have 0x7d as the most significant bytes
         * It must also be an instruction that has been declared
         */
        return (((instr >> 24) & 0xff) == 0x7d) && Instruction.convertInstr(instr) != null;
    }

    private void executeInstr(ISA instr, int val) throws Exception {
        // Handles the different instructions
        switch (instr) {
            // Adds val to the x0 register
            case ADD:
                reg_x0 += val;
                break;
            // Sets the x0 register to val
            case MOV:
                reg_x0 = val;
                break;
            // Performs one of the syscalls
            case SYS:
                executeSys(Syscall.fromInteger(val));
                break;
            default:
                throw new Exception("Invalid Instruction");
        }
    }

    private void executeSys(Syscall val) throws Exception{
        // Handles the different syscall values
        switch (val) {
            // Prints the
            case PRINTSTRING:
                printString();
                break;
            case RETADDR:
                retAddr();
                break;
            case INITREGS:
                initRegs();
                break;
            case READSTR:
                readStr();
                break;
            case MAKEBUFFER:
                makeBuffer();
                break;
            default:
                throw new Exception("Invalid Syscall");
        }
    }

    private void printString() {
        int reg_cpy = reg_sp;
        while (memory[reg_cpy] != 0) {
            System.out.print(memory[reg_cpy]);
            reg_cpy--;
        }
    }

    private void retAddr() {
        reg_ip = memory[reg_sp] + 8;
        reg_sp += 8;
    }

    private void readStr() {
        String str = sc.nextLine();
        for (int i = 0; i < str.length(); i++) {
            memory[reg_sp + i] = str.charAt(i);
        }
        memory[reg_sp + str.length()] = '\0';
    }

    private void makeBuffer() {
        // "Allocates" a buffer of size x0 with the last value being \0
        for (int i = 0; i < reg_x0; i++) {
            memory[reg_sp--] = 0x0;
        }
    }

    public void addInstr(int instr) {
        memory[reg_ip+8] = instr; 
    }
}