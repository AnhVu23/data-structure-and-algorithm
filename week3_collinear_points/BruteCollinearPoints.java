import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[] points;
    private int n;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Point array should not be null");
        }
        checkNullEntries(points);
        Arrays.sort(points);
        checkDuplicatedEntries(points);
        this.points = points;
        this.n = 0;
    }

    public int numberOfSegments() {
        return n;
    }    // the number of line segments

    public LineSegment[] segments() {
        Double[] slopeArray = new Double[points.length];
        ArrayList<LineSegment> segmentsList = new ArrayList<LineSegment>();
        for (int i = 0; i < (points.length - 3); ++i)
            for (int j = i + 1; j < (points.length - 2); ++j)
                for (int k = j + 1; k < (points.length - 1); ++k)
                    for (int l = k + 1; l < (points.length); ++l) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[l]) &&
                                points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])) {
                            LineSegment tempLineSegment = new LineSegment(points[i], points[l]);
                            if (!segmentsList.contains(tempLineSegment))
                                segmentsList.add(tempLineSegment);
                        }
                    }
        return segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }              // the line segments

    private void exchange(Point[] a, int i, int j) {
        Point old = a[i];
        a[i] = a[j];
        a[j] = old;
    }

    private void exchangeSlope(Double[] a, int i, int j) {
        double old = a[i];
        a[i] = a[j];
        a[j] = old;
    }

    private void checkNullEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null) {
                throw new java.lang.NullPointerException("One of the point in points array is null");
            }
        }
    }

    private void checkDuplicatedEntries(Point[] points) {
        for (int i = 1; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("Duplicated entries in given points");
            }
        }
    }

    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point point1 = new Point(1, 1);
        Point point2 = new Point(2, 2);
        Point point3 = new Point(3, 3);
        Point point4 = new Point(4, 4);
        Point point5 = new Point(2, 3);
        Point point6 = new Point(1, 2);
        Point point7 = new Point(1, 6);
        Point[] array = new Point[]{point1, point2, point3, point4, point5, point6, point7};
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(array);
        collinearPoints.segments();
        System.out.println(collinearPoints.numberOfSegments());
    }
}
