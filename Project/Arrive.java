package cs2030.simulator;

import cs2030.util.Pair;
import java.util.Optional;

class Arrive implements Event {
    private final Customer customer;
    private final double eventTime;

    public Arrive(Customer customer, double eventTime) {
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
        if (shop.hasQueue()) {
            return Pair.<Optional<Event>, Shop>of(Optional
                    .<Event>of(new Leave(this.customer, this.eventTime)), shop);
        }

        if (!shop.hasAvailableServer()) {
            int newServerID = shop.waits(this.customer.getID()).first().getID();
            return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(new 
                        Wait(this.customer, this.eventTime,
                              newServerID, shop.checkIsServer(newServerID))), 
                    shop.waits(this.customer.getID()).second());
        }

        return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(new Serve(this.customer,
                        this.eventTime, shop.arrive().first().getID())), shop.arrive().second());
    }

    @Override
    public String toString() {
        return String.format("%.3f " + this.customer + " arrives", this.eventTime);
    }
}
