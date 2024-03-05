/**
 * Program to general a KML file from GPS track data stored in a file,
 * for the Advanced task of COMP1721 Coursework 1.
 *
 * @author Adam Bakeer
 */

 import java.io.IOException;

  public class ConvertToKML {
    public static void main(String[] args) {
      if (args.length != 2) {
        System.err.println("Usage: java ConvertToKML <inputCSVFile> <outputKMLFile>");
        System.exit(1);
      }

    String inputCSVFile = args[0];
    String outputKMLFile = args[1];
    
    try {
      Track myTrack = new Track(inputCSVFile);
      myTrack.writeKML(outputKMLFile);
      System.out.println("KML file generated successfully.");
    } catch (IOException e) {
      System.err.println("Error processing files: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
