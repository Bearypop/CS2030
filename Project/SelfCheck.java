package cs2030.simulator;

import cs2030.util.ImList;
import java.util.function.Supplier;

class SelfCheck extends Server {

    public SelfCheck(int id) {
        super(id);
    }

    public SelfCheck(int id, int qmax) {
        super(id, qmax);
    }

    public SelfCheck(int id, int qmax, Supplier<Double> restTimes) {
        super(id, qmax, restTimes);
    }

    public SelfCheck(int id, boolean busy, boolean hasWait,
            ImList<Integer> waitingQueue, int qmax, Supplier<Double> restTimes) {
        super(id, busy, hasWait, waitingQueue, qmax, restTimes);
    }

    public SelfCheck addWait(int customerID) {
        ImList<Integer> newList = ImList.<Integer>of(super.getWaitingQueue());
        newList = newList.add(customerID);
        return new SelfCheck(super.getID(), super.isBusy(), super.hasWait(),
                newList, super.getQmax(), super.getRestTimes());
    }

    public SelfCheck removeWait() {
        ImList<Integer> newList = ImList.<Integer>of(super.getWaitingQueue());
        newList = newList.remove(0).second();
        return new SelfCheck(super.getID(), true, newList.size() > 0, newList,
                super.getQmax(), super.getRestTimes());
    }

    public SelfCheck updateSharedQueue(Server server) {
        ImList<Integer> newList = ImList.<Integer>of(server.getWaitingQueue());
        return new SelfCheck(super.getID(), super.isBusy(), super.hasWait(),
                newList, super.getQmax(), super.getRestTimes());
    }

    @Override
    public boolean isServer() {
        return false;
    }

    @Override
    public String toString() {
        return "Self-check " + super.getID(); 
    }
}
