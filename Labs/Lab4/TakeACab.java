class TakeACab implements Service {
    private static final int BOOKING_FEE = 200;
    private static final int RATE = 33;

    TakeACab() {

    }

    @Override
    public int computeFare(Request request) {
        return BOOKING_FEE + request.getDistance() * RATE;
    }

    @Override 
    public String toString() {
        return "TakeACab";
    }
}
