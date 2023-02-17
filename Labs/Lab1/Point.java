class Point {

    private final double x;
    private final double y;
    
    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("point (%.3f, %.3f)", this.x, this.y);
    }

    Point midPoint(Point q) {
        return new Point((this.x + q.x) / 2, (this.y + q.y) / 2);
    }

    double angleTo(Point q) {
        return Math.atan2(q.y - this.y, q.x - this.x);

    }

    Point moveTo(double theta, double d) {
        return new Point(this.x + d * Math.cos(theta), this.y + d * Math.sin(theta));
    }
    
    double distanceTo(Point otherpoint) {
        return Math.sqrt(Math.pow(this.x - otherpoint.x, 2) + Math.pow(this.y - otherpoint.y, 2));
    }
}
