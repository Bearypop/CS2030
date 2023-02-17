class Loader {
    private final int identifier;
    private final Cruise cruise;

    Loader(int identifier, Cruise cruise) {
        this.identifier = identifier;
        this.cruise = cruise;
    }

    boolean canServe(Cruise otherCruise) {
        return otherCruise.getArrivalTime() >= this.cruise.getServiceCompletionTime();
    }

    Loader serve(Cruise otherCruise) {
        if (canServe(otherCruise)) {
            return new Loader(this.identifier, otherCruise);
        } else {
            return this;
        }
    }

    int getIdentifier() {
        return this.identifier;
    }

    int getNextAvailableTime() {
        return this.cruise.getServiceCompletionTime();
    }

    public String toString() {
        return String.format("Loader %d serving %s", this.identifier, this.cruise);
    }
}


