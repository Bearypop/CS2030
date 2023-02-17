class JustRide implements Service {
    private static final int PEAK_HOUR_START = 600;
    private static final int PEAK_HOUR_END = 900;
    private static final int SURCHARGE = 500;
    private static final int RATE = 22;

    JustRide() {

    }

    @Override
    public int computeFare(Request request) {
        int fare = (request.getTime() >= PEAK_HOUR_START && 
                request.getTime() <= PEAK_HOUR_END) ? SURCHARGE : 0;
        fare += request.getDistance() * RATE;
        return fare;
    }

    @Override
    public String toString() {
        return "JustRide";
    }
}

