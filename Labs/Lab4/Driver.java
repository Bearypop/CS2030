abstract class Driver {
    private final String licensePlate;
    private final int waitingTime;

    Driver(String licensePlate, int waitingTime) {
        this.licensePlate = licensePlate;
        this.waitingTime = waitingTime;
    }

    int getWaitingTime() {
        return this.waitingTime;
    }

    String getLicensePlate() {
        return this.licensePlate;
    }

    abstract Service selectService(Request request);

    abstract int getSelectedServiceFare(Request request);

    abstract Service notSelectedService(Request request);

    abstract int getNotSelectedServiceFare(Request request);
}
