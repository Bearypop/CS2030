package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

public class Rest implements Event {
    private final double eventTime;
    private final int serverID;
    private static final int identifier = 4;

    public Rest(int serverID, double eventTime) {
        this.serverID = serverID;
        this.eventTime = eventTime;
    }

    @Override
    public double getEventTime() {
        return this.eventTime;
    }

    @Override
    public int getCustomerID() {
        return 0;
    }

    @Override
    public int getServerID() {
        return this.serverID;
    }

    @Override
    public Customer getCustomer() {
        return new Customer(0, 0);
    }

    @Override
    public int getIdentifier() {
        return this.identifier;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        return Pair.<Optional<Event>, Shop>of(Optional.empty(), shop.done(this.serverID));
    }

    @Override
    public String toString() {
        return "" + this.eventTime + " " + this.serverID + " ends resting ";
    }
}
