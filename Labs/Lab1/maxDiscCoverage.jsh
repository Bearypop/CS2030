
Circle createUnitCircle(Point p, Point q) {
    Point m = p.midPoint(q);
    double theta = p.angleTo(q);
    double mq = m.distanceTo(q);
    double d = Math.sqrt(1 - Math.pow(mq, 2));
    Point centre = m.moveTo(theta + Math.PI / 2, d);
    return new Circle(centre, 1.0);
}

int findMaxDiscCoverage(List<Point> points) {
    int maxDiscCoverage = 0;
    int numOfPoints = points.size();

    for (int i = 0; i < numOfPoints; i++) {
        for (int j = i + 1; j < numOfPoints; j++) {
            if (points.get(i).distanceTo(points.get(j)) <= 2) {
                int coverage = 0;
                Circle unitCircle = createUnitCircle(points.get(i), points.get(j));
                for (int h = 0; h < numOfPoints; h++) {
                    if (unitCircle.contains(points.get(h))) {
                        coverage++;
                    }
                }
                if (coverage > maxDiscCoverage) {
                    maxDiscCoverage = coverage;
                }
            }
        }
    }
    return maxDiscCoverage;
}


