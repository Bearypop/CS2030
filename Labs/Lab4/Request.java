class Request {
    private final int distance;
    private final int numOfPassengers;
    private final int time;

    Request(int distance, int numOfPassengers, int time) {
        this.distance = distance;
        this.numOfPassengers = numOfPassengers;
        this.time = time;
    }

    int getDistance() {
        return this.distance;
    }

    int getNumOfPassengers() {
        return this.numOfPassengers;
    }

    int getTime() {
        return this.time;
    }

    @Override
    public String toString() {
        return String.format("%dkm for %dpax @ %dhrs", this.distance, 
                this.numOfPassengers, this.time);
    }
}
