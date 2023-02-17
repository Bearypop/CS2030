package cs2030.simulator;

import cs2030.util.PQ;
import cs2030.util.ImList;
import cs2030.util.Pair;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class Simulate6 {
    private final PQ<Event> queue;
    private final Shop shop;
    private final Statistic statistic;
    private final ImList<Pair<Double, Supplier<Double>>> inputTimes;
    private final int qmax;
   
    public Simulate6(int servers, List<Pair<Double, Supplier<Double>>> inputTimes, int qmax) {
        PQ<Event> newQueue = new PQ<Event>(new EventComparator());
        for (int i = 1; i <= inputTimes.size(); i++) {
            newQueue = newQueue.add(new Arrive(new Customer(i, 
                            inputTimes.get(i - 1).first()), inputTimes.get(i - 1).first()));
        }
        this.queue = newQueue;
        this.shop = new Shop(servers, qmax);
        this.statistic = new Statistic();
        this.inputTimes = ImList.<Pair<Double, Supplier<Double>>>of(inputTimes);
        this.qmax = qmax;
    }

    public Simulate6(Simulate6 simulate6) {
        this.queue = new PQ<Event>(simulate6.queue);
        this.shop = new Shop(simulate6.shop);
        this.statistic = new Statistic(simulate6.statistic);
        this.inputTimes = ImList.<Pair<Double, Supplier<Double>>>of(simulate6.inputTimes);
        this.qmax = simulate6.qmax;
    }

    public Simulate6(PQ<Event> queue, Shop shop, Statistic statistic,
            ImList<Pair<Double, Supplier<Double>>> inputTimes, int qmax) {
        this.queue = queue;
        this.shop = shop;
        this.statistic = statistic;
        this.inputTimes = inputTimes;
        this.qmax = qmax;
    }

    public double getArrivalTime(int customerID) {
        return this.inputTimes.get(customerID - 1).first();
    }

    public String run() {
        String string = "";
        Simulate6 newSimulate = new Simulate6(this);

        while (!newSimulate.queue.isEmpty()) {
            PQ<Event> newQueue = new PQ<Event>(newSimulate.queue);

            // remove priority event 
            newQueue = newSimulate.queue.poll().second();

            // checkEvent is the polled event
            Event checkEvent = newSimulate.queue.poll().first();

            // check if the polled event is a serve event
            if (checkEvent.getIdentifier() == 2) {
                checkEvent = new Serve(checkEvent, newSimulate.inputTimes
                        .get(checkEvent.getCustomerID() - 1).second().get());
            }

            string += checkEvent + "\n";

            // Event that is generated from execution of the polled event
            Optional<Event> newEvent = checkEvent.execute(newSimulate.shop).first();

            Shop newShop = newSimulate.queue.poll()
                .first().execute(newSimulate.shop).second();

            ImList<Pair<Double, Supplier<Double>>> newInputTimes = 
                ImList.<Pair<Double, Supplier<Double>>>of(newSimulate.inputTimes);

            int newQmax = newSimulate.qmax;

            Statistic newStatistic = new Statistic(newSimulate.statistic);

            if (checkEvent.getIdentifier() == 2) {
                newStatistic = newStatistic.served();
            }

            if (checkEvent.getIdentifier() == 2 + 1) {
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
            newSimulate = new Simulate6(newQueue, newShop, newStatistic, newInputTimes, newQmax);
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
