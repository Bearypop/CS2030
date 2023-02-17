package cs2030.simulator;

import cs2030.util.PQ;
import cs2030.util.ImList;
import cs2030.util.Pair;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class Simulate7 {
    private final PQ<Event> queue;
    private final Shop shop;
    private final Statistic statistic;
    private final ImList<Pair<Double, Supplier<Double>>> inputTimes;
    private final int qmax;
    private final Supplier<Double> restTimes;

    public Simulate7(int servers, List<Pair<Double, Supplier<Double>>> inputTimes,
            int qmax, Supplier<Double> restTimes) {
        PQ<Event> newQueue = new PQ<Event>(new EventComparator());
        for (int i = 1; i <= inputTimes.size(); i++) {
            newQueue = newQueue.add(new Arrive(new Customer(i, 
                            inputTimes.get(i - 1).first()), inputTimes.get(i - 1).first()));
        }
        this.queue = newQueue;
        this.shop = new Shop(servers, qmax, restTimes);
        this.statistic = new Statistic();
        this.inputTimes = ImList.<Pair<Double, Supplier<Double>>>of(inputTimes);
        this.qmax = qmax;
        this.restTimes = restTimes;
    }

    public Simulate7(Simulate7 simulate7) {
        this.queue = new PQ<Event>(simulate7.queue);
        this.shop = new Shop(simulate7.shop);
        this.statistic = new Statistic(simulate7.statistic);
        this.inputTimes = ImList.<Pair<Double, Supplier<Double>>>of(simulate7.inputTimes);
        this.qmax = simulate7.qmax;
        this.restTimes = simulate7.restTimes;
    }

    public Simulate7(PQ<Event> queue, Shop shop, Statistic statistic,
            ImList<Pair<Double, Supplier<Double>>> inputTimes,
            int qmax, Supplier<Double> restTimes) {
        this.queue = queue;
        this.shop = shop;
        this.statistic = statistic;
        this.inputTimes = inputTimes;
        this.qmax = qmax;
        this.restTimes = restTimes;
    }

    public double getArrivalTime(int customerID) {
        return this.inputTimes.get(customerID - 1).first();
    }

    public String run() {
        String string = "";
        Simulate7 newSimulate = new Simulate7(this);

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

            if (checkEvent.getIdentifier() != 2 + 2) {
                string += checkEvent + "\n";
            }

            Pair<Optional<Event>, Shop> executedPair = checkEvent.execute(newSimulate.shop);
            Optional<Event> newEvent = executedPair.first();
            Shop newShop = executedPair.second();

            ImList<Pair<Double, Supplier<Double>>> newInputTimes = 
                ImList.<Pair<Double, Supplier<Double>>>of(newSimulate.inputTimes);

            int newQmax = newSimulate.qmax;

            Supplier<Double> newRestTimes = newSimulate.restTimes;

            Statistic newStatistic = new Statistic(newSimulate.statistic);

            if (checkEvent.getIdentifier() == 2) {
                newStatistic = newStatistic.served();
            }

            if (checkEvent.getIdentifier() == 2 + 1) {
                newStatistic = newStatistic.left();
            }

            if (checkEvent.getIdentifier() == 2 + 2 && 
                    newShop.checkHasWait(checkEvent.getServerID())) {
                Serve serveWait = new Serve(new Customer(newSimulate.shop
                            .getWaitingCustomer(checkEvent.getServerID()), 0),
                        checkEvent.getEventTime(),
                        newSimulate.shop.serveWait(checkEvent.getServerID()).first().getID());

                newQueue = newQueue.add(serveWait);

                newShop = newSimulate.shop.serveWait(checkEvent.getServerID()).second();

                double waitingTime = serveWait.getEventTime() 
                    - newSimulate.getArrivalTime(serveWait.getCustomerID());

                newStatistic = newStatistic.addWaitingTime(waitingTime); 

            } else if (checkEvent.getIdentifier() == 1 &&
                    newShop.checkHasWait(checkEvent.getServerID())) { 
                Serve serveWait = new Serve(new Customer(newSimulate.shop
                            .getWaitingCustomer(checkEvent.getServerID()), 0),
                        checkEvent.getEventTime()
                        + newSimulate.restTimes.get(), 
                        newSimulate.shop.serveWait(checkEvent.getServerID()).first().getID());

                newQueue = newQueue.add(serveWait);

                newShop = newSimulate.shop.serveWait(checkEvent.getServerID()).second();

                double waitingTime = serveWait.getEventTime() 
                    - newSimulate.getArrivalTime(serveWait.getCustomerID());

                newStatistic = newStatistic.addWaitingTime(waitingTime); 

            } else if (!newEvent.equals(Optional.empty())) {
                newQueue = newQueue.add(newEvent.orElseThrow()); 
            }
            newSimulate = new Simulate7(newQueue, newShop, newStatistic,
                    newInputTimes, newQmax, newRestTimes);
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
