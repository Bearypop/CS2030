package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

public class Leave implements Event {

    private final Customer customer;
    private final double eventTime;
    private static final int identifier = 3;

    public Leave(Customer customer, double eventTime) {
        this.customer = customer;
        this.eventTime = eventTime;
    }

    @Override
    public double getEventTime() {
        return this.eventTime;
    }

    @Override
    public int getCustomerID() {
        return this.customer.getID();
    }

    @Override
    public int getServerID() {
        return 0;
    }

    @Override
    public Customer getCustomer() {
        return this.customer;
    }

    @Override
    public int getIdentifier() {
        return this.identifier;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        return Pair.<Optional<Event>, Shop>of(Optional.empty(), shop);
    }  
 
    @Override
    public String toString() {
        return String.format("%.3f " + this.customer + " leaves", this.eventTime);
    }
}

