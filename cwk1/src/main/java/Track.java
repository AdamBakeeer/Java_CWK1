/**
 * Represents a point in space and time, recorded by a GPS sensor.
 *
 * @author Adam Bakeer
 */
import java.io.File;  
import java.time.ZonedDateTime;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Track {
  
  private List<Point> points;
  
  // TODO: Create a stub for the constructor
  public Track() {
    this.points = new ArrayList<Point>();
  }


  public Track (String filename) throws IOException {
    this.points = new ArrayList<Point>();
    readFile(filename);
  }


  // TODO: Create a stub for readFile()
  public void readFile (String filename) throws IOException {
    this.points = new ArrayList<Point>();
    File myObj = new File(filename);

    Scanner myReader = new Scanner(myObj);
    String data = myReader.nextLine();

      while (myReader.hasNextLine()) {
        data = myReader.nextLine();
        String[] parts = data.split(",");

        if (parts.length != 4) {
            throw new GPSException("Invalid record: Each record must have exactly four values.");
          }

        ZonedDateTime timestamp = ZonedDateTime.parse(parts[0]);
        double longitude = Double.parseDouble(parts[1]);
        double latitude = Double.parseDouble(parts[2]);
        double elevation = Double.parseDouble(parts[3]);

        points.add(new Point(timestamp, longitude, latitude, elevation));
      }
      myReader.close(); 
  }

  // TODO: Create a stub for add()
  public void add(Point point) {
     points.add(point);
  }

  // TODO: Create a stub for get()
  public Point get(int index) {
    if (index < 0 || index >= points.size()) {
      throw new GPSException("Invalid index: " + index);
    }

    return points.get(index);
  }

  // TODO: Create a stub for size()
  public int size () {
    return points.size();
  }

  // TODO: Create a stub for lowestPoint()
  public Point lowestPoint() {
    if (points.size() == 0) {
      throw new GPSException("There is no points");
    }


    double max = Integer.MAX_VALUE;
    Point lowest = null;
    
    for (Point i : points) {
      if(i.getElevation() < max) {
        max = i.getElevation();
        lowest = i;
      } 
    }

    return lowest;
  }

  // TODO: Create a stub for highestPoint()
  public Point highestPoint() {
    if (points.size() == 0) {
      throw new GPSException("There is no points");
    }

    double min = Integer.MIN_VALUE;
    Point highest = null;
    
    for (Point i : points) {
      if(i.getElevation() > min) {
        min = i.getElevation();
        highest = i;
      } 
    }

    return highest;
  }

  // TODO: Create a stub for totalDistance()
  public double totalDistance() {
    if (points.size() < 2) {
      throw new GPSException("Not enough points to calculate");
    }

    double total = 0;
    for (int i = 1; i < points.size(); i++) {
      Point p = points.get(i);
      Point q = points.get(i -1);

      total += Point.greatCircleDistance(p, q);
    }

    return total;
  }

  // TODO: Create a stub for averageSpeed()
  public double averageSpeed() {
    if (points.size() == 0) {
      throw new GPSException("There is no points");
    }

    Point start = points.get(0);
    Point end = points.get(points.size() - 1);

    long timeIntervalInSeconds = ChronoUnit.SECONDS.between(start.getTime(), end.getTime());

    double distance = totalDistance();

    double averageSpeed = distance / timeIntervalInSeconds;

    return averageSpeed;
  }

  public void writeKML(String filename) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
        
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        writer.write("<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n");
        writer.write("<Document>\n");

        
        for (Point point : points) {
            writer.write("<Placemark>\n");
            writer.write("<Point>\n");
            writer.write(String.format("<coordinates>%.5f,%.5f</coordinates>\n", 
            point.getLongitude(), point.getLatitude()));
            writer.write("</Point>\n");
            writer.write("</Placemark>\n");
        }

        
        writer.write("</Document>\n");
        writer.write("</kml>\n");

        System.out.println("KML file generated successfully.");
    } catch (IOException e) {
        System.err.println("Error writing KML file: " + e.getMessage());
        e.printStackTrace();
      }
  }
}

