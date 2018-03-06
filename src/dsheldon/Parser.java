package dsheldon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    public static DataSet fromCSV(String text) {
        String[] lines = text.split("\n");
        Map<Integer, Klass> klasses = new HashMap<>();
        Map<String, Person> people = new HashMap<>();
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
                Person person = new Person();
                person.key = cells[0];
                for (int col = 2; col < cells.length; col++) {
                    if (klasses.containsKey(col)) {
                        person.requests.put(klasses.get(col), numerize(cells[col]));
                        klasses.get(col).people.add(person);
                    }
                }
                people.put(person.key, person);
            }
        }
        return new DataSet(klasses, people);
    }

    private static Integer numerize(String cell) {
        if (cell.equals("H"))
            return 75;
        if (cell.equals("M"))
            return 50;
        if (cell.equals("L"))
            return 25;
        if (cell.equals("X"))
            return -1000;
        if (cell.equals("W"))
            return 1000;
        if (cell.equals("Y"))
            return 1000;
        if (cell.equals(""))
            return 0;
        return 10;
    }
}

class DataSet {

    Map<Integer, Klass> klasses;
    Map<String, Person> people;

    public DataSet(Map<Integer, Klass> klasses, Map<String, Person> people) {
        this.klasses = klasses;
        this.people = people;
    }
}

class Klass {
    String key;
    int capacity;

    // A or B
    String session;

    List<Person> people = new ArrayList<>();
    public boolean full = false;
}

class Person {
    String key;

    Map<Klass, Integer> requests = new HashMap<>();
}