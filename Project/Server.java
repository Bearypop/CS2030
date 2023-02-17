package cs2030.simulator;

import cs2030.util.ImList;
import java.util.function.Supplier;

public class Server {
    private final int id;
    private final boolean busy;
    private final boolean hasWait;
    private final ImList<Integer> waitingQueue;
    private final int qmax;
    private final Supplier<Double> restTimes;

    public Server(int id) {
        this.id = id;
        this.busy = false;
        this.hasWait = false;
        this.waitingQueue = ImList.<Integer>of();
        this.qmax = 1;
        this.restTimes = () -> 0.0;
    }

    public Server(int id, int qmax) {
        this.id = id;
        this.busy = false;
        this.hasWait = false;
        this.waitingQueue = ImList.<Integer>of();
        this.qmax = qmax;
        this.restTimes = () -> 0.0;
    }

    public Server(int id, int qmax, Supplier<Double> restTimes) {
        this.id = id;
        this.busy = false;
        this.hasWait = false;
        this.waitingQueue = ImList.<Integer>of();
        this.qmax = qmax;
        this.restTimes = restTimes;
    }

    public Server(int id, boolean busy, boolean hasWait,
            ImList<Integer> waitingQueue, int qmax, Supplier<Double> restTimes) {
        this.id = id;
        this.busy = busy;
        this.hasWait = hasWait;
        this.waitingQueue = ImList.<Integer>of(waitingQueue);
        this.qmax = qmax;
        this.restTimes = restTimes;
    }

    public int getID() {
        return this.id;
    }

    public boolean isBusy() {
        return this.busy;
    }

    public boolean hasWait() {
        return this.waitingQueue.size() > 0;
    }

    public boolean hasFullQueue() {
        return this.waitingQueue.size() == qmax;
    }

    public Server addWait(int customerID) {
        ImList<Integer> newList = ImList.<Integer>of(this.waitingQueue);
        newList = newList.add(customerID);
        return new Server(this.id, this.busy, this.hasWait, newList, this.qmax, this.restTimes);
    }

    public Server removeWait() {
        ImList<Integer> newList = ImList.<Integer>of(this.waitingQueue);
        newList = newList.remove(0).second();
        return new Server(this.id, true, newList.size() > 0, newList, this.qmax, this.restTimes);
    }

    public Server updateSharedQueue(Server server) {
        ImList<Integer> newList = ImList.<Integer>of(server.waitingQueue);
        return new Server(this.id, this.busy, this.hasWait(), newList, this.qmax, this.restTimes);
    }

    public int getWaitingCustomer() {
        return this.waitingQueue.get(0);
    }

    public ImList<Integer> getWaitingQueue() {
        return this.waitingQueue;
    }

    public int getQmax() {
        return this.qmax;
    }

    public Supplier<Double> getRestTimes() {
        return this.restTimes;
    }

    public boolean isServer() {
        return true;
    }

    @Override 
    public String toString() {
        return "" + this.id;
    }
}
