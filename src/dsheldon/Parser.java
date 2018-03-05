package dsheldon;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    public static DataSet fromCSV(String text) {
        String[] lines = text.split("\n");
        Map<Integer, Klass> klasses = new HashMap<>();
        Map<String, Row> people = new HashMap<>();
        for (int row = 0; row < lines.length; row++) {
            String[] cells = lines[row].split(",");

            if (row == 0) { // Capacity
                for (int col = 2; col < cells.length; col++) {
                    if (!cells[col].equals("")) {
                        Klass klass = new Klass();
                        klass.capacity = Integer.valueOf(cells[col]);
                        klasses.put(col, klass);
                    }
                }
            }else if (row == 1) { // Session
                for (int col = 2; col < cells.length; col++) {
                    if (klasses.containsKey(col))
                    klasses.get(col).session = cells[col];
                }
            }else if (row == 3) {
                for (int col = 2; col < cells.length; col++) {
                    System.out.println(col + " " + klasses.get(col));
                    if (klasses.containsKey(col))
                    klasses.get(col).key = cells[col];
                }
            }else if (row >= 4) {
                Row person = new Row();
                person.key = cells[0];
                for (int col = 2; col < cells.length; col++) {
                    if (klasses.containsKey(col))
                    person.requests.put(klasses.get(col), cells[col]);
                }
                people.put(person.key, person);
            }
        }
        return new DataSet(klasses, people);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("test.txt")));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line + "\n");
        }
        DataSet set = fromCSV(builder.toString());
        System.out.println(set);
        }
}

class DataSet {

    Map<Integer, Klass> klasses;
    Map<String, Row> people;

    public DataSet(Map<Integer, Klass> klasses, Map<String, Row> people) {
        this.klasses = klasses;
        this.people = people;
    }
}

class Klass {
    String key;
    int capacity;

    // A or B
    String session;
}

class Row {
    String key;

    Map<Klass, String> requests = new HashMap<>();
}