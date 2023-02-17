package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

public class Done implements Event {
    private final Customer customer;
    private final double eventTime;
    private final int serverID;
    private static final int identifier = 1;

    public Done(Customer customer, double eventTime, int serverID) {
        this.customer = customer;
        this.eventTime = eventTime;
        this.serverID = serverID;
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
        if (!shop.checkHasWait(this.serverID) && shop.checkIsServer(this.serverID)) {
            double restTime = shop.getRestTime(this.serverID);
            if (restTime > 0) {
                return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(new Rest(this.serverID,
                                this.eventTime + restTime)), shop);
            } else {
                return Pair.<Optional<Event>, Shop>of(Optional.empty(), shop.done(this.serverID));
            }
        } else {
            return Pair.<Optional<Event>, Shop>of(Optional.empty(), shop.done(this.serverID));
        }
    }

    @Override
    public String toString() {
        return String.format("%.3f " + this.customer + " done serving by " 
                + this.serverID, this.eventTime);
    }
}
