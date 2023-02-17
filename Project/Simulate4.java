package cs2030.simulator;

import cs2030.util.PQ;
import cs2030.util.ImList;
import java.util.List;
import java.util.Optional;

public class Simulate4 {
    private final PQ<Event> queue;
    private final Shop shop;
    private final Statistic statistic;
    private final ImList<Double> arrivalTimes;

    public Simulate4(int servers, List<Double> arrivalTimes) {
        PQ<Event> newQueue = new PQ<Event>(new EventComparator());
        for (int i = 1; i <= arrivalTimes.size(); i++) {
            newQueue = newQueue.add(new Arrive(new Customer(i, 
                            arrivalTimes.get(i - 1)), arrivalTimes.get(i - 1)));
        }
        this.queue = newQueue;
        this.shop = new Shop(servers);
        this.statistic = new Statistic();
        this.arrivalTimes = ImList.<Double>of(arrivalTimes);
    }

    public Simulate4(Simulate4 simulate4) {
        this.queue = new PQ<Event>(simulate4.queue);
        this.shop = new Shop(simulate4.shop);
        this.statistic = new Statistic(simulate4.statistic);
        this.arrivalTimes = ImList.<Double>of(simulate4.arrivalTimes);
    }

    public Simulate4(PQ<Event> queue, Shop shop, Statistic statistic, ImList<Double> arrivalTimes) {
        this.queue = queue;
        this.shop = shop;
        this.statistic = statistic;
        this.arrivalTimes = arrivalTimes;
    }

    public double getArrivalTime(int customerID) {
        return this.arrivalTimes.get(customerID - 1);
    }

    public String run() {
        String string = "";
        Simulate4 newSimulate = new Simulate4(this);

        while (!newSimulate.queue.isEmpty()) {
            PQ<Event> newQueue = new PQ<Event>(newSimulate.queue);
            string += newSimulate.queue.poll().first() + "\n";
            Optional<Event> newEvent = newSimulate.queue.poll()
                .first().execute(newSimulate.shop).first();

            // remove priority event 
            newQueue = newSimulate.queue.poll().second();

            Shop newShop = newSimulate.queue.poll()
                .first().execute(newSimulate.shop).second();

            ImList<Double> newArrivalTimes = ImList.<Double>of(newSimulate.arrivalTimes);

            Statistic newStatistic = new Statistic(newSimulate.statistic);

            Event checkEvent = newSimulate.queue.poll().first();

            if (checkEvent.getIdentifier() == 2) {
                newStatistic = newStatistic.served();
            }

            if (checkEvent.getIdentifier() == 1 + 1 + 1) {
                newStatistic = newStatistic.left();
            }

            if (checkEvent.getIdentifier() == 1 && newShop.checkHasWait(checkEvent.getServerID())) {
                Serve serveWait = new Serve(new Customer(newSimulate.shop
                            .getWaitingCustomer(checkEvent.getServerID()), 0),
                        checkEvent.getEventTime(), 
                        newSimulate.shop.serveWait(checkEvent.getServerID()).first().getID());

                newQueue = newQueue.add(serveWait);

                newShop = newSimulate.shop.serveWait(checkEvent.getServerID()).second();

                double waitingTime = serveWait.getEventTime() 
                    - newSimulate.getArrivalTime(serveWait.getCustomerID());

                newStatistic = newStatistic.addWaitingTime(waitingTime); 

            } else if (!newEvent.equals(Optional.empty())) {
                newQueue = newQueue.add(newEvent.orElseThrow()); 
            }
            newSimulate = new Simulate4(newQueue, newShop, newStatistic, newArrivalTimes);
        }

        string += String.format("[%.3f" 
                + " " + newSimulate.statistic.getServed() + " " 
                + newSimulate.statistic.getLeft() + "]",
                newSimulate.statistic.getAverageWaitingTime());
        return string;
    }

    @Override
    public String toString() {
        return "Queue: " + this.queue + "; Shop: " + this.shop;
    }
}
