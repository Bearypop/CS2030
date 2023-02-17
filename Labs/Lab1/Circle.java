class Circle {

    private final double radius;
    private final Point centre;

    Circle(Point centre, double radius) {
        this.centre = centre;
        this.radius = radius;
    }
    
    boolean contains(Point point) {
        return this.centre.distanceTo(point) <= this.radius;
    }

    @Override
    public String toString() {
        return "circle of radius " + radius + " centered at " + this.centre;
    }
}

