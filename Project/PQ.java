package cs2030.util;

import java.util.PriorityQueue;
import java.util.Comparator;

public class PQ<T> {
    private final PriorityQueue<T> priorityQueue;

    public PQ(Comparator<? super T> cmp) {
        this.priorityQueue = new PriorityQueue<T>(cmp);
    }

    public PQ(PriorityQueue<T> priorityQueue) {
        this.priorityQueue = new PriorityQueue<T>(priorityQueue);
    }

    public PQ(PQ<T> queue) {
        this(queue.priorityQueue);
    }

    public PQ<T> add(T element) {
        PQ<T> newQueue = new PQ<T>(this.priorityQueue);
        newQueue.priorityQueue.add(element);
        return newQueue;
    }

    public Pair<T, PQ<T>> poll() {
        PQ<T> newQueue = new PQ<T>(this.priorityQueue);
        T removedElement = newQueue.priorityQueue.poll();
        return Pair.<T, PQ<T>>of(removedElement, newQueue);
    }

    public boolean isEmpty() {
        return this.priorityQueue.isEmpty();
    }

    @Override
    public String toString() {
        return this.priorityQueue.toString();
    }
}
