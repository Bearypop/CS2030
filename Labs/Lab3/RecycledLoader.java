class RecycledLoader extends Loader { 
    private static final int MAINTENANCE = 60;

    RecycledLoader(int identifier, Cruise cruise) {
        super(identifier, cruise);
    }

    @Override
    boolean canServe(Cruise otherCruise) {
        return otherCruise.getArrivalTime() >= super.getNextAvailableTime() + MAINTENANCE;
    }

    @Override
    Loader serve(Cruise otherCruise) {
        if (canServe(otherCruise)) {
            return new RecycledLoader(super.getIdentifier(), otherCruise);
        } else {
            return this;
        }
    }

    @Override
    public String toString() {
        return "Recycled " + super.toString();
    }
}
