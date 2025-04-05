package javiki.course;

public class DistanceCalculator {
    public static double calculateDistance(PointCoordinates a, PointCoordinates b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }
}
