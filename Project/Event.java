package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

interface Event {
    Pair<Optional<Event>, Shop> execute(Shop shop);
    
    int getCustomerID();

    int getServerID();

    Customer getCustomer();

    int getIdentifier();

    double getEventTime();
}

