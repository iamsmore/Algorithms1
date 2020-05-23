import java.util.Arrays;

public class BruteCollinearPoints {

    Point[] points;
    int counter; //counts the number of segments
    LineSegment[] segments = new LineSegment[1]; //holds line segments made of up of 4 collinear points
    int capacity; // capcaity of segments array

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        this.points = points;

        this.capacity = segments.length;
        Arrays.sort(points);
    }

    // the number of line segments
    public int numberOfSegments()      {
        return counter;
    }

    // the line segments
    public LineSegment[] segments() {

        int segLength = 0;

        //for every points in points
        for (int i = 0; i < points.length - 1; i++) {

            double pq = points[i].slopeTo(points[(i + 1) % points.length]);
            double pr = points[i].slopeTo(points[(i + 2) % points.length]);
            double ps = points[i].slopeTo(points[(i + 3) % points.length]);


            if (pq == pr && pr == ps ) {

                //increments number of collinear segments
                counter++;

                Point[] sorted = new Point[]{points[i],points[(i + 1) % points.length],points[(i + 2) % points.length],points[(i + 3) % points.length]};

                Arrays.sort(sorted);

                //create line segment
                LineSegment line = new LineSegment(sorted[0],sorted[3]);

                if (counter == capacity) {
                    resize(2 * capacity);
                }


                segments[segLength] = line;
                segLength++;


            }
        }

        //maybe before return array resize again to get rid of null arguments
        return segments;
    }

    //helper method for resizing dynamic array
    private void resize(int newSize) {

        LineSegment[] copy = new LineSegment[newSize]; //array to copy data

        for (int i = 0; i < counter; i++) {
            copy[i] = segments[i];
        }

        //update
        segments = copy;
        capacity = segments.length;





    }


    public static void main(String[] args) {

        Point test = new Point(1,1);
        Point test1 = new Point(2,2);
        Point test2 = new Point(4,4);
        Point test3 = new Point(3,3);
        Point test4 = new Point(1,2);

        Point[] testPoints = new Point[]{test,test1,test2,test3,test4};


        System.out.println(testPoints[4].slopeTo(testPoints[1]));

        System.out.println(test4.slopeTo(test1));

        System.out.println(testPoints[4].slopeTo(testPoints[3]));

        System.out.println(test4.slopeTo(test3));

        System.out.println(testPoints[4].slopeTo(testPoints[2]));

        System.out.println(testPoints[4].slopeTo(testPoints[0]));




        BruteCollinearPoints brute = new BruteCollinearPoints(testPoints);

        LineSegment[] lines = brute.segments();

        System.out.println(brute.numberOfSegments());

        System.out.println((brute.capacity));

        for(int i = 0; i < lines.length;i++) {

            if(lines[i] == null) {
                break;
            }

            System.out.println(lines[i].toString());

        }






    }

    }
