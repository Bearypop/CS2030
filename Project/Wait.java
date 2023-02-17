package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

public class Wait implements Event {

    private final Customer customer;
    private final double eventTime;
    private final int serverID;
    private final boolean isServer;

    public Wait(Customer customer, double eventTime, int serverID, boolean isServer) {
        this.customer = customer;
        this.eventTime = eventTime;
        this.serverID = serverID;
        this.isServer = isServer;
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
        return 0;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        return Pair.<Optional<Event>, Shop>of(Optional.empty(), shop);
    }

    @Override
    public String toString() {
        if (this.isServer) {
            return String.format("%.3f " + this.customer + " waits at " +
                    this.serverID, this.eventTime);
        } else {
            return String.format("%.3f " + this.customer + " waits at self-check " +
                    this.serverID, this.eventTime);
        }
    }
}
