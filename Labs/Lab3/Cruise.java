class Cruise {
    private final String identifier;
    private final int arrivalTime;
    private final int numOfLoader;
    private final int serviceTime;
    private static final int MINUTES_IN_HOUR = 60;

    Cruise(String identifier, int arrivalTime, int numOfLoader, int serviceTime) {
        this.identifier = identifier;
        this.arrivalTime = arrivalTime;
        this.numOfLoader = numOfLoader;
        this.serviceTime = serviceTime;
    }

    int getServiceCompletionTime() {
        return this.getArrivalTime() + this.serviceTime;
    }

    int getArrivalTime() {
        return (this.arrivalTime / 100) * MINUTES_IN_HOUR + this.arrivalTime % 100;
    }

    int getNumOfLoadersRequired() {
        return this.numOfLoader;
    }

    @Override
    public String toString() {
        return this.identifier + "@" + String.format("%04d", this.arrivalTime);
    }
}
