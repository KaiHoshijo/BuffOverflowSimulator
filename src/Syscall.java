enum Syscall {
    PRINTSTRING,
    RETADDR,
    INITREGS,
    READSTR,
    MAKEBUFFER,
    EXIT;

    public static Syscall fromInteger(int val) {
        // Ensures that values can be converted to the correct enum
        switch (val) {
            case 0:
                return PRINTSTRING;
            case 1:
                return RETADDR;
            case 2:
                return INITREGS;
            case 3:
                return EXIT;
            case 4:
                return READSTR;
            case 5:
                return MAKEBUFFER;
            default:
                return null;
        }
    }
}