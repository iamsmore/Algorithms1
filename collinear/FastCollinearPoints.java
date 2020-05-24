import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {


    //steps 1. make p origin 2. for each other point determine slope with p 3. order according to slope 4. check

    LineSegment[] segments = new LineSegment[1];
    int capacity;
    int counter;
    Point[] points;

    public FastCollinearPoints(Point[] points)    {
        this.points = points;
        this.capacity = segments.length;
        this.counter = 0;

    }


    public int numberOfSegments()  {

        return counter;

    }
    public LineSegment[] segments()     {

        Point origin  = points[0];  //origin point
        double slopeTracker; //slope tracker
        int adjCount = 0; // counts the number of adjacent points have the same slope

        //use slopeOrder method to order according to slope with origin
        Arrays.sort(points,origin.slopeOrder());

        //need to check the slopes of these points
        //if 3 or more adjacent Points have the same slope they are collinear

        //use a for loop with counter. start incrementing evertime adjacent sites have same slope
        // if currentSlope matches add to segments? but if counter does get to < 3 delete from segments?
        // maybe only add in points if counter get above 3. that way dont have to delete

        //possibilities 1. adjCount < 4 slopes match 2. adjCount < 4 slopes dont match

        for(int i =0; i < counter; i++) {

            //grab current slope
            double currentSlope = origin.slopeTo(points[i]);
            ArrayList<Point> list = new ArrayList<Point>();

            if (currentSlope == slopeTracker && adjCount == 4) {

                adjCount++;

                //if adjCount is 4, add the previous 4 points to ArrayList
                list.add(points[i-3]);
                list.add(points[i-2]);
                list.add(points[i-1]);
                list.add(points[i]);
            } else if (currentSlope == slopeTracker && adjCount > 4) {

                adjCount++;

                //if adjCount is greater than 4 and slope is still the same continue to add to list
                list.add(points[i]);
            } else if (currentSlope != slopeTracker && adjCount > 4) {

                //consecutive collinear points have ended. reset adjCount, sort point list, create line segment, add to segments
                adjCount = 0;
                Collections.sort(list);
                LineSegment line = new LineSegment(list.get(0),list.get(list.size()));

                segments[counter] = line;
                counter++;
            } else if (currentSlope != slopeTracker && adjCount == 4) {

            }

        }



    }
}
