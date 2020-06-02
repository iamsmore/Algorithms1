import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private LineSegment[] segments; //array for line segments
    private int counter; //counts the number of segments
    private Point[] points;

    public FastCollinearPoints(Point[] points) {

        this.points = points;
        this.counter = 0;
    }


    public int numberOfSegments() {

        return segments.length;
    }

    public LineSegment[] segments() {

        Point[] copySO = Arrays.copyOf(points, points.length); //copy of points to be sorted in natural order
        Point[] copyNO = Arrays.copyOf(points, points.length); //copy of point to be sorted in slope order
        ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>(); //list to hold line segments
        Arrays.sort(copyNO);
        checkForDuplicates(copyNO);
        checkForNull(copyNO);

        for (int i = 0; i < copyNO.length; i++) {

            Point origin = copyNO[i];// origin point
            Arrays.sort(copySO);
            Arrays.sort(copySO, origin.slopeOrder());
            int adjCount = 1; //counts adjacent point with same slope wrt the origin
            Point firstPoint = null; //hold the first point in a line

            for (int j = 0; j < copySO.length-1; j++) {


                if (origin.slopeTo(copySO[j]) == origin.slopeTo(copySO[j + 1])) {

                    adjCount++;
                    if (adjCount == 2) {
                        firstPoint = copySO[j];
                        adjCount++;
                    } else if (adjCount >= 4 && j + 1 == copySO.length - 1) {

                        //check to avoid duplicates of the same line being added
                        if (firstPoint.compareTo(origin) > 0) {
                            lineSegments.add(new LineSegment(origin, copySO[j+1]));
                        }
                        adjCount = 1;
                    }
                } else if (adjCount >= 4) {

                    if (firstPoint.compareTo(origin) > 0) {
                        lineSegments.add(new LineSegment(origin, copySO[j]));
                    }
                    adjCount = 1;
                } else {
                    adjCount = 1;
                }
            }
        }

        segments = lineSegments.toArray(new LineSegment[lineSegments.size()]);
        return segments;
    }


    private void checkForNull(Point[] points) {

        for(int i = 0;i < points.length-1;i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("Null Argument");
            }
        }
    }

    private void checkForDuplicates(Point[] points) {

        for (int i = 0;i < points.length-1;i++) {
            if (points[i].compareTo(points[i+1]) == 0){
                throw new IllegalArgumentException("Duplicate Points");
            }
        }
    }


    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        System.out.println(collinear.numberOfSegments());
    }
}
