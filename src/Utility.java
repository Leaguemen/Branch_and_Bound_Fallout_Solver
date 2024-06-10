import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Utility {
    public static ArrayList<String> parseText(String _path) {
        ArrayList<String> retVal = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(_path))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line by semicolon and convert to ArrayList
                String[] items = line.split(";");
                // Remove the last empty element if the line ends with a semicolon
                if (items.length > 0 && items[items.length - 1].isEmpty()) {
                    retVal.addAll(Arrays.asList(items).subList(0, items.length - 1));
                } else {
                    retVal.addAll(Arrays.asList(items));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retVal;
    }
}
