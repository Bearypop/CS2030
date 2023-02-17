package cs2030.simulator;

import cs2030.util.PQ;
import java.util.List;
import java.util.Optional;

public class Simulate3 {
    private final PQ<Event> queue;
    private final Shop shop;

    public Simulate3(int servers, List<Double> arrivalTimes) {
        PQ<Event> newQueue = new PQ<Event>(new EventComparator());
        for (int i = 1; i <= arrivalTimes.size(); i++) {
            newQueue = newQueue.add(new Arrive(new Customer(i, 
                            arrivalTimes.get(i - 1)), arrivalTimes.get(i - 1)));
        }
        this.queue = newQueue;
        this.shop = new Shop(servers);
    }

    public Simulate3(Simulate3 simulate3) {
        this.queue = new PQ<Event>(simulate3.queue);
        this.shop = new Shop(simulate3.shop);
    }

    public Simulate3(PQ<Event> queue, Shop shop) {
        this.queue = queue;
        this.shop = shop;
    }

    public String run() {
        String string = "";
        Simulate3 newSimulate = new Simulate3(this);
        while (!newSimulate.queue.isEmpty()) {
            PQ<Event> newQueue = new PQ<Event>(newSimulate.queue);
            string += newSimulate.queue.poll().first() + "\n";
            Optional<Event> newEvent = newSimulate.queue.poll()
                .first().execute(newSimulate.shop).first();

            // remove priority event 
            newQueue = newSimulate.queue.poll().second();
            Shop newShop = newSimulate.queue.poll()
                .first().execute(newSimulate.shop).second();

            Event checkEvent = newSimulate.queue.poll().first();
            if (checkEvent.getIdentifier() == 1 && newShop.checkHasWait(checkEvent.getServerID())) {
                Serve serveWait = new Serve(new Customer(newSimulate.shop
                            .getWaitingCustomer(checkEvent.getServerID()), 0),
                        checkEvent.getEventTime(), 
                        newSimulate.shop.serveWait(checkEvent.getServerID()).first().getID());

                newQueue = newQueue.add(serveWait);

                newShop = newSimulate.shop.serveWait(checkEvent.getServerID()).second();

            } else if (!newEvent.equals(Optional.empty())) {
                newQueue = newQueue.add(newEvent.orElseThrow()); 
            }
            newSimulate = new Simulate3(newQueue, newShop);
        }
        string += "-- End of Simulation --";
        return string;
    }

    @Override
    public String toString() {
        return "Queue: " + this.queue + "; Shop: " + this.shop;
    }
}
