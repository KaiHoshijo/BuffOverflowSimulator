public class Instruction {
    public static int getAdd(int value) {
        int instr = 0x7d000000;
        instr += value << 8;
        return instr;
    }

    public static int getMov(int value) {
        int instr = 0x7d000001;
        instr += value << 8;
        return instr;
    }

    public static int getSys(Syscall value) {
        int instr = 0x7d000002;
        switch (value) {
            case PRINTSTRING:
                instr += 0x0000 << 8;
                break;
            case RETADDR:
                instr += 0x0001 << 8;
                break;
            case INITREGS:
                instr += 0x0002 << 8;
                break;
            case EXIT:
                instr += 0x0003 << 8;
                break;
            case READSTR:
                instr += 0x0004 << 8;
                break;
            case MAKEBUFFER:
                instr += 0x0005 << 8;
                break;
            default:
                instr += 0xffff << 8;
        }
        return instr;
    }

    public static ISA convertInstr(int instr) {
        int instr_num = instr & 0xff;
        return ISA.fromInteger(instr_num);
    }

    public static int getVal(int instr) {
        return (instr >> 8) & 0xffff;
    }
}
