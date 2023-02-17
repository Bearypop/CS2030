package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

public class Serve implements Event {

    private final Customer customer;
    private final double eventTime;
    private final int serverID;
    private static final int identifier = 2;
    private final double serviceTime;

    public Serve(Customer customer, double eventTime, int serverID) {
        this.customer = customer;
        this.eventTime = eventTime;
        this.serverID = serverID;
        this.serviceTime = 1;
    }

    public Serve(Event serve, double serviceTime) {
        this.customer = serve.getCustomer();
        this.eventTime = serve.getEventTime();
        this.serverID = serve.getServerID();
        this.serviceTime = serviceTime;
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
        return this.serverID;
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
        return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(new Done(this.customer,
                        this.eventTime + this.serviceTime, 
                          shop.serve(this.serverID).first().getID())), 
                shop.serve(this.serverID).second());
    }  
 
    @Override
    public String toString() {
        return String.format("%.3f " + this.customer 
                + " serves by " + this.serverID, this.eventTime);
    }
}

