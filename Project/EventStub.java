package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

public class EventStub implements Event {

    private final Customer customer;
    private final double eventTime;

    public EventStub(Customer customer, double eventTime) {
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
        return 0;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        return Pair.<Optional<Event>, Shop>of(Optional.empty(), shop);
    }   
 
    @Override
    public String toString() {
        return String.format("%.3f", this.eventTime);
    }
}

