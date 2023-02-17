class BigCruise extends Cruise {
    private static final int METRES_PER_LOADER = 40;
    private static final int PASSENGERS_PER_MINUTE = 50;

    BigCruise(String identifier, int arrivalTime, int length, int passengers) {
        super(identifier, arrivalTime, (int)Math.ceil((double)length / METRES_PER_LOADER),
                (int)Math.ceil((double)passengers / PASSENGERS_PER_MINUTE));
    }
}
