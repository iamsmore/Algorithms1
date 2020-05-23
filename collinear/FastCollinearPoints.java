import java.util.Arrays;

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

        //use slopeOrder method to order according to slope with origin
        Arrays.sort(points,origin.slopeOrder());

        //need to check the slopes of these points
        //if 3 or more adjacent Points have the same slope they are collinear

        //use a for loop with counter. start incrementing evertime adjacent sites have same slope
        // if currentSlope matches add to segments? but if counter does get to < 3 delete from segments?
        // maybe only add in points if counter get above 3. that way dont have to delete
        

        for(int i =0; i < counter; i++) {

            double currentSlope = origin.slopeTo(points[i]);

            //update slopeTracker if current slope is not the same
            if (currentSlope != slopeTracker) {
                slopeTracker = currentSlope;
            }

        }



    }
}
