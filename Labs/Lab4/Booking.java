class Booking implements Comparable<Booking> {
    private final Driver driver;
    private final Request request;
    private final int fare;
    private final Service service;

    Booking(Driver driver, Request request) {
        this.driver = driver;
        this.request = request;
        this.fare = this.driver.getSelectedServiceFare(this.request);
        this.service = this.driver.selectService(this.request);
    }

    Booking(Driver driver, Request request, boolean notSelected) {
        this.driver = driver;
        this.request = request;
        this.fare = this.driver.getNotSelectedServiceFare(this.request);
        this.service = this.driver.notSelectedService(this.request);
    }

    @Override 
    public int compareTo(Booking other) {
        if (this.fare == other.fare) {
            if (this.driver.getWaitingTime() < other.driver.getWaitingTime()) {
                return -1;
            } else if (this.driver.getWaitingTime() == 
                    other.driver.getWaitingTime()) {
                return 0;
            } else {
                return 1;
            }
        }
        if (this.fare < other.fare) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return String.format("$%.2f using %s (%s)", this.fare / 100.0, 
                this.driver, this.service);
    }
}


