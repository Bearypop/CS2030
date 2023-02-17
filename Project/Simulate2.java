package cs2030.simulator;

import cs2030.util.PQ;
import java.util.List;

public class Simulate2 {
    private final PQ<Event> queue;
    private final Shop shop;

    public Simulate2(int servers, List<Double> arrivalTimes) {
        PQ<Event> newQueue = new PQ<Event>(new EventComparator());
        for (int i = 0; i < arrivalTimes.size(); i++) {
            newQueue = newQueue.add(new EventStub(new Customer(i, 
                            arrivalTimes.get(i)), arrivalTimes.get(i)));
        }
        this.queue = newQueue;
        this.shop = new Shop(servers);
    }

    public String run() {
        String string = "";
        PQ<Event> newQueue = new PQ<Event>(this.queue);
        while (!newQueue.isEmpty()) {
            string += newQueue.poll().first() + "\n";
            newQueue = newQueue.poll().second();
        }
        string += "-- End of Simulation --";
        return string;
    }

    @Override
    public String toString() {
        return "Queue: " + this.queue + "; Shop: " + this.shop;
    }
}
