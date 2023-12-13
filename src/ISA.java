enum ISA {
    ADD,
    MOV,
    SYS;

    public static ISA fromInteger(int val) {
        switch (val) {
            case 0:
                return ADD;
            case 1:
                return MOV;
            case 2:
                return SYS;
            default:
                return null;
        }
    }
}