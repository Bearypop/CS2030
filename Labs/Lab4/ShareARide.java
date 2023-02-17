class ShareARide implements Service {
    private static final int PEAK_HOUR_START = 600;
    private static final int PEAK_HOUR_END = 900;
    private static final int SURCHARGE = 500;     
    private static final int RATE = 50;

    ShareARide() {

    }

    @Override
    public int computeFare(Request request) {
        int fare = (request.getTime() >= PEAK_HOUR_START && 
                request.getTime() <= PEAK_HOUR_END) ? SURCHARGE : 0;
        fare = (fare + request.getDistance() * RATE) / request.getNumOfPassengers();
        return fare;
    }

    @Override
    public String toString() {
        return "ShareARide";
    }
}

