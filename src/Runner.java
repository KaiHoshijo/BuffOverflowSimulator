class Runner {
    public static void main(String[] args) throws Exception {
        // Starting the simulator
        Simulator sim = new Simulator();
        // Adding the exit functiona at the end of the exit function
        sim.addInstr(Instruction.getSys(Syscall.EXIT));


        // Running the simulator
        sim.runSimulator();
    }
}