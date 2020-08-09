import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Point array should not be null");
        }
        checkNullEntries(points);
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        checkDuplicatedEntries(pointsCopy);
        Arrays.sort(pointsCopy);
        ArrayList<LineSegment> segmentsList = new ArrayList<LineSegment>();
        for (int i = 0; i < pointsCopy.length; ++i) {
            Point origin = pointsCopy[i];
            Arrays.sort(pointsCopy);
            Arrays.sort(pointsCopy, origin.slopeOrder());
            int count = 1;
            Point lineBeginning = null;
            for (int j = 0; j < pointsCopy.length - 1; ++j) {
                if (pointsCopy[j].slopeTo(origin) == pointsCopy[j + 1].slopeTo(origin)) {
                    count++;
                    if (count == 2) {
                        lineBeginning = pointsCopy[j];
                        count++;
                    } else if (count >= 4 && j + 1 == pointsCopy.length - 1) {
                        if (lineBeginning.compareTo(origin) > 0) {
                            segmentsList.add(new LineSegment(origin, pointsCopy[j + 1]));
                        }
                        count = 1;
                    }
                } else if (count >= 4) {
                    if (lineBeginning.compareTo(origin) > 0) {
                        segmentsList.add(new LineSegment(origin, pointsCopy[j]));
                    }
                    count = 1;
                } else {
                    count = 1;
                }
            }
        }
        segments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }     // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return segments.length;
    }     // the number of line segments

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }         // the line segments

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
}
