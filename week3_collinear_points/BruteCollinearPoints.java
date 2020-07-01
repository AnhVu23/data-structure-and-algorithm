import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

public class BruteCollinearPoints {
    private int n;
    private Point[] points;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Point array should not be null");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("Point should not be null");
            }
        }
        HashSet<Point> pointSet = new HashSet<Point>(Arrays.asList(points));
        if (pointSet.size() != points.length) {
            throw new IllegalArgumentException("Duplicated points are not allowed");
        }
        this.points = points;
        n = 0;
    }

    public int numberOfSegments() {
        return n;
    }    // the number of line segments

    public LineSegment[] segments() {
        Double[] slopeArray = new Double[points.length - 1];
        LineSegment[] lineSegments = new LineSegment[points.length];
        Point origin = points[0];
        for (int i = 1; i < points.length; i++) {
            double slopeToOrigin = origin.slopeTo(points[i]);
            slopeArray[i - 1] = slopeToOrigin;
        }
        Comparator<Point> pointOrder = origin.slopeOrder();
        for (int i = 1; i < points.length; i++) {
            for (int j = i; j > 0; j--) {
                if (pointOrder.compare(points[j], points[j - 1]) < 0) {
                    exchange(points, j, j - 1);
                    exchangeSlope(slopeArray, j, j - 1);
                }
            }
        }
        for (int u = 0; u < slopeArray.length; u += 3) {
            if (slopeArray[u].equals(slopeArray[u + 1]) && slopeArray[u + 1].equals(slopeArray[u + 2])) {
                lineSegments[u / 3] = new LineSegment(origin, points[u + 2]);
                n++;
            }
        }
        return lineSegments;
    }              // the line segments

    private void exchange(Point[] a, int i, int j) {
        Point old = a[i];
        a[i] = a[j];
        a[j] = old;
    }

    private void exchangeSlope(Double[] a, int i, int j) {
        Double old = a[i];
        a[i] = a[j];
        a[j] = old;
    }

    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point point1 = new Point(1, 1);
        Point point2 = new Point(2, 2);
        Point point3 = new Point(3, 3);
        Point point4 = new Point(4, 4);
        Point[] array = new Point[]{point1, point2, point3, point4};
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(array);
        collinearPoints.segments();
        System.out.println(collinearPoints.numberOfSegments());
    }
}
