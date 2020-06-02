import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private Point[] points;
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        this.points = points;
        Arrays.sort(points);
        checkForDuplicates(points);
        checkForNull(points);

    }

    // the number of line segments
    public int numberOfSegments()      {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {

        ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();

        //for every points in points
        for (int i = 0; i < points.length - 1; i++) {

            double pq = points[i].slopeTo(points[(i + 1) % points.length]);
            double pr = points[i].slopeTo(points[(i + 2) % points.length]);
            double ps = points[i].slopeTo(points[(i + 3) % points.length]);
            //use array list instead of an array to store segments



            if (pq == pr && pr == ps ) {


                Point[] sorted = new Point[]{points[i],points[(i + 1) % points.length],points[(i + 2) % points.length],points[(i + 3) % points.length]};

                Arrays.sort(sorted);

                //create line segment
                LineSegment line = new LineSegment(sorted[0],sorted[3]);

                lineSegments.add(line);



            }
        }

        segments = lineSegments.toArray(new LineSegment[lineSegments.size()]);
        return segments;
    }

    //helper method to check for null arguments
    private void checkForNull(Point[] points) {

        for(int i = 0;i < points.length;i++) {
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
