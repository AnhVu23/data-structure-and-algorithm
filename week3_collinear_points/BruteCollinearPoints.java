import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Point array should not be null");
        }
        checkNullEntries(points);
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        checkDuplicatedEntries(pointsCopy);
        ArrayList<LineSegment> segmentsList = new ArrayList<LineSegment>();
        for (int i = 0; i < (pointsCopy.length - 3); ++i)
            for (int j = i + 1; j < (pointsCopy.length - 2); ++j)
                for (int k = j + 1; k < (pointsCopy.length - 1); ++k)
                    for (int l = k + 1; l < (pointsCopy.length); ++l) {
                        if (pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i].slopeTo(pointsCopy[l]) &&
                                pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i].slopeTo(pointsCopy[k])) {
                            LineSegment tempLineSegment = new LineSegment(pointsCopy[i], pointsCopy[l]);
                            if (!segmentsList.contains(tempLineSegment))
                                segmentsList.add(tempLineSegment);
                        }
                    }
        segments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }

    public int numberOfSegments() {
        return segments.length;
    }    // the number of line segments

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }              // the line segments

    private void checkNullEntries(Point[] points) {
        for (int i = 0; i <= points.length - 1; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("One of the point in points array is null");
            }
        }
    }

    private void checkDuplicatedEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicated entries in given points");
            }
        }
    }

    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point point1 = new Point(9056, 873);
        Point point2 = new Point(4402, 776);
        Point point3 = new Point(2, 2);
        Point point4 = new Point(4, 4);
        Point point5 = new Point(2, 3);
        Point point6 = new Point(1, 2);
        Point point7 = new Point(9056, 873);
        Point[] array = new Point[]{point1, point2, point3, point4, point5, point6, point7};
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(array);
    }
}
