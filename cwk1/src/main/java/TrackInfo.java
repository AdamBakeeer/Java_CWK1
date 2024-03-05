import java.io.IOException;

/**
 * Program to provide information on a GPS track stored in a file.
 *
 * @author YOUR NAME HERE
 */
public class TrackInfo {
  public static void main(String[] args) throws IOException {
    
    if (args.length == 0) {
      System.err.println("file name is not specified at command line");
      System.exit(0);
    }

    try {
      Track myTrack = new Track(args[0]);

      double distance = myTrack.totalDistance() / 1000;

      System.out.println(myTrack.size());
      System.out.println("Lowest point is " + myTrack.lowestPoint());
      System.out.println("highest point point is " + myTrack.highestPoint());
      System.out.printf("Total distance = %.3f km\n", distance);
      System.out.println("average speed = " + String.format("%.3f m/s",myTrack.averageSpeed()));
    } catch (IOException e) {
      System.err.println("Error accessing the file" + e.getMessage());
      System.exit(1);
    } catch (GPSException e) {
      System.err.println("Error processing data" + e.getMessage());
      System.exit(1);
    }
    



  }
}
