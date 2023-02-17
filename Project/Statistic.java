package cs2030.simulator;

public class Statistic {
    private final double totalWaitingTime;
    private final int served;
    private final int left;

    public Statistic() {
        this.totalWaitingTime = 0;
        this.served = 0;
        this.left = 0;
    }

    public Statistic(Statistic statistic) {
        this.totalWaitingTime = statistic.totalWaitingTime;
        this.served = statistic.served;
        this.left = statistic.left;
    }

    public Statistic(double totalWaitingTime, int served, int left) {
        this.totalWaitingTime = totalWaitingTime;
        this.served = served;
        this.left = left;
    }

    public Statistic served() {
        return new Statistic(this.totalWaitingTime, this.served + 1, this.left);
    }

    public Statistic left() {
        return new Statistic(this.totalWaitingTime, this.served, this.left + 1);
    }

    public Statistic addWaitingTime(double waitingTime) {
        return new Statistic(this.totalWaitingTime + waitingTime, this.served, this.left);
    }

    public double getAverageWaitingTime() {
        return this.totalWaitingTime / this.served;
    }

    public int getServed() {
        return this.served;
    }

    public int getLeft() {
        return this.left;
    }
}
