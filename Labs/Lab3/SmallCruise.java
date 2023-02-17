class SmallCruise extends Cruise {
    private static final int SMALL_CRUISE_TIME = 30;

    SmallCruise(String identifier, int arrivalTime) {
        super(identifier, arrivalTime, 1, SMALL_CRUISE_TIME);
    }
}
