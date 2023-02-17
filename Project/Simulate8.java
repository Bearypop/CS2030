package cs2030.simulator;

import cs2030.util.PQ;
import cs2030.util.ImList;
import cs2030.util.Pair;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class Simulate8 {
    private final PQ<Event> queue;
    private final Shop shop;
    private final Statistic statistic;
    private final ImList<Pair<Double, Supplier<Double>>> inputTimes;
    private final int qmax;
    private final Supplier<Double> restTimes;

    public Simulate8(int servers, int selfChecks,
            List<Pair<Double, Supplier<Double>>> inputTimes,
            int qmax, Supplier<Double> restTimes) {
        PQ<Event> newQueue = new PQ<Event>(new EventComparator());
        for (int i = 1; i <= inputTimes.size(); i++) {
            newQueue = newQueue.add(new Arrive(new Customer(i, 
                            inputTimes.get(i - 1).first()), inputTimes.get(i - 1).first()));
        }
        this.queue = newQueue;
        this.shop = new Shop(servers, selfChecks, qmax, restTimes);
        this.statistic = new Statistic();
        this.inputTimes = ImList.<Pair<Double, Supplier<Double>>>of(inputTimes);
        this.qmax = qmax;
        this.restTimes = restTimes;
    }

    public Simulate8(Simulate8 simulate8) {
        this.queue = new PQ<Event>(simulate8.queue);
        this.shop = new Shop(simulate8.shop);
        this.statistic = new Statistic(simulate8.statistic);
        this.inputTimes = ImList.<Pair<Double, Supplier<Double>>>of(simulate8.inputTimes);
        this.qmax = simulate8.qmax;
        this.restTimes = simulate8.restTimes;
    }

    public Simulate8(PQ<Event> queue, Shop shop, Statistic statistic,
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
        Simulate8 newSimulate = new Simulate8(this);

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
                if (checkEvent.getIdentifier() == 2) {
                    if (newSimulate.shop.checkIsServer(checkEvent.getServerID())) {
                        string += checkEvent + "\n";
                    } else {
                        string += String.format("%.3f", checkEvent.getEventTime()) + " " 
                            + checkEvent.getCustomerID() + " serves by self-check " 
                            + checkEvent.getServerID() + "\n";
                    }
                } else if (checkEvent.getIdentifier() == 1) {
                    if (newSimulate.shop.checkIsServer(checkEvent.getServerID())) {
                        string += checkEvent + "\n";
                    } else {
                        string += String.format("%.3f", checkEvent.getEventTime()) + " "
                            + checkEvent.getCustomerID() + " done serving by self-check "
                            + checkEvent.getServerID() + "\n";
                    }
                } else if (checkEvent.getIdentifier() == 2 + 2 + 1) {
                    if (newSimulate.shop.checkIsServer(checkEvent.getServerID())) {
                        string += checkEvent + "\n";
                    } else {
                        string += String.format("%.3f", checkEvent.getEventTime()) + " "
                            + checkEvent.getCustomerID() + " waits at self-check "
                            + checkEvent.getServerID() + "\n";
                    }
                } else {
                    string += checkEvent + "\n";
                }
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
            //check whether polled event is a rest event
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
                // check whether polled event is a done event
            } else if (checkEvent.getIdentifier() == 1 &&
                    newShop.checkHasWait(checkEvent.getServerID())) {

                double newEventTime;
                Event addEvent = new EventStub(new Customer(1, 1.0), 1.0);

                if (newShop.checkIsServer(checkEvent.getServerID())) {
                    newEventTime = checkEvent.getEventTime() + newSimulate.restTimes.get();
                } else {
                    newEventTime = checkEvent.getEventTime();
                }

                if (newEventTime > checkEvent.getEventTime()) {
                    addEvent = new Rest(checkEvent.getServerID(), newEventTime);
                    newShop = newSimulate.shop.serve(checkEvent.getServerID()).second();

                } else {
                    addEvent = new Serve(new Customer(newSimulate.shop
                                .getWaitingCustomer(checkEvent.getServerID()), 0), newEventTime, 
                            newSimulate.shop.serveWait(checkEvent.getServerID()).first().getID());
                    newShop = newSimulate.shop.serveWait(checkEvent.getServerID()).second();

                    double waitingTime = addEvent.getEventTime() 
                        - newSimulate.getArrivalTime(addEvent.getCustomerID());

                    newStatistic = newStatistic.addWaitingTime(waitingTime); 
                }
                newQueue = newQueue.add(addEvent); 

            } else if (!newEvent.equals(Optional.empty())) {
                newQueue = newQueue.add(newEvent.orElseThrow()); 
            }

            newSimulate = new Simulate8(newQueue, newShop, newStatistic,
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
