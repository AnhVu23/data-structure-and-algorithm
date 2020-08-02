import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private int n;
    private Point[] points;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Point array should not be null");
        }
        checkNullEntries(points);
        checkNullEntries(points);
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        checkDuplicatedEntries(pointsCopy);
        this.points = points;
        n = 0;
    }     // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return n;
    }     // the number of line segments

    public LineSegment[] segments() {
        ArrayList<LineSegment> segmentsList = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length; ++i) {
            Point origin = points[i];
            Arrays.sort(points);
            Arrays.sort(points, origin.slopeOrder());
            int count = 1;
            Point lineBeginning = null;
            for (int j = 0; j < points.length - 1; ++j) {
                if (points[j].slopeTo(origin) == points[j + 1].slopeTo(origin)) {
                    count++;
                    if (count == 2) {
                        lineBeginning = points[j];
                        count++;
                    } else if (count >= 4 && j + 1 == points.length - 1) {
                        if (lineBeginning.compareTo(origin) > 0) {
                            segmentsList.add(new LineSegment(origin, points[j + 1]));
                        }
                        count = 1;
                    }
                } else if (count >= 4) {
                    if (lineBeginning.compareTo(origin) > 0) {
                        segmentsList.add(new LineSegment(origin, points[j]));
                    }
                    count = 1;
                } else {
                    count = 1;
                }
            }
        }
        return segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }         // the line segments

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
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("Duplicated entries in given points");
            }
        }
    }
}
