import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Bayes {

    public static HashMap<Integer, ArrayList<StringBuilder>> calculationC(ArrayList<Integer> C) {
        HashMap<Integer, ArrayList<StringBuilder>> probC = new HashMap<>();
        StringBuilder coords = new StringBuilder();
        int count = 0;
        while (count < 20) {
            ArrayList<StringBuilder> allCoords = new ArrayList<>();
            for (int k = 0; k < C.size(); k++) {
                if (C.get(k) == count) {
                    int i = k / 20;
                    int j = k % 20;
                    coords.append(i);
                    coords.append("+");
                    coords.append(j);
                    allCoords.add(coords);
                    coords = new StringBuilder();
                }
            }
            probC.put(count, allCoords);
            count++;
        }
        return probC;
    }

    public static ArrayList<Double> calculationMC(ArrayList<Integer> C, ArrayList<Double> M, ArrayList<Double> K) {
        ArrayList<Double> allMC = new ArrayList<>();
        int countC = 0;
        int countM = 0;
        while (countM < 20) {
            while (countC < 20) {
                for (int k = 0; k < C.size(); k++) {
                    if (C.get(k) == countC && k / 20 == countM) {
                        System.out.println(" M " + countM + " C " + countC + " K " + k % 20);
                        allMC.add((double) (M.get(countM) * K.get(k % 20)));
                    }
                }
                countC++;
            }
            countC = 0;
            countM++;
        }
        System.out.println(allMC);
        return allMC;
    }

    private static ArrayList<Integer> deformat(StringBuilder s) {
        ArrayList<Integer> coordinates = new ArrayList<>();
        int l = s.indexOf("+");
        coordinates.add(0, Integer.valueOf(s.substring(0, l)));
        coordinates.add(1, Integer.valueOf(s.substring(l + 1)));
        return coordinates;
    }

    private static double probabilityMC(HashMap<Integer, ArrayList<StringBuilder>> coordsC, ArrayList<Double> m, ArrayList<Double> k) {
        int count = 0;
        double probC = 0; //put on line 55, if we will need separate probabilities for different C
        while (count < 20) {
            ArrayList<StringBuilder> coords = coordsC.get(count);
            for (StringBuilder a : coords) {
                ArrayList<Integer> intcoord = deformat(a);
                int i = intcoord.get(0);
                int j = intcoord.get(1);
                probC = (m.get(j) * k.get(i)) + probC;
            }
            count++;
            System.out.println(probC);
            probC = 0.0;
        }
        return probC;
    }

    public static ArrayList<Double> probabilityC(HashMap<Integer, ArrayList<StringBuilder>> coordsC, ArrayList<Double> m, ArrayList<Double> k) {
        ArrayList<Double> probabC = new ArrayList<>();
        int count = 0;
        double probC = 0; //put on line 55, if we will need separate probabilities for different C
        while (count < 20) {
            ArrayList<StringBuilder> coords = coordsC.get(count);
            for (StringBuilder a : coords) {
                ArrayList<Integer> intcoord = deformat(a);
                int i = intcoord.get(0);
                int j = intcoord.get(1);
                probC = (m.get(j) * k.get(i)) + probC;
            }
            count++;
            probabC.add(probC);
            System.out.printf("%.2f%n", probC);
            probC = 0.0;
        }
        return probabC;
    }

    public static void main(String[] args) throws Exception {
        ArrayList<Double> plainText = new ArrayList<>();
        ArrayList<Double> key = new ArrayList<>();
        ArrayList<Integer> cipherText = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("C:\\table_01.csv"));
        String line;
        Scanner scanner;
        while ((line = reader.readLine()) != null) {
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String num = scanner.next();
                cipherText.add(Integer.parseInt(num));
            }
        }
        BufferedReader reader1 = new BufferedReader(new FileReader("C:\\prob_01.csv"));
        while ((line = reader1.readLine()) != null) {
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String num = scanner.next();
                plainText.add(Double.parseDouble(num));
            }
        }
        reader.close();
        for (int i = 20; i < 40; i++) {
            key.add(plainText.get(i));
        }
        plainText.removeAll(key);
        System.out.println(plainText);
        System.out.println(key);
        HashMap<Integer, ArrayList<StringBuilder>> m = calculationC(cipherText);
        ArrayList<Double> pC = probabilityC(m, plainText, key);
        //System.out.println(pC);
        calculationMC(cipherText, plainText, key);
    }
}