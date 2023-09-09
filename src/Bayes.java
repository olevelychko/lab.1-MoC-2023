import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
public class Bayes {

    public static HashMap<Integer,ArrayList<StringBuilder>> calculationC(ArrayList<Integer> C)
    {
        HashMap<Integer,ArrayList<StringBuilder>> probC = new HashMap<>();
        StringBuilder coords = new StringBuilder();
        int count=0;
        while(count<20) {
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

    private static ArrayList<Integer> deformat(StringBuilder s)
    {
        ArrayList<Integer> coordinates = new ArrayList<>();
        StringBuilder temp;
        int l = 0;
        l = s.indexOf("+");
        coordinates.add(0, Integer.valueOf(s.substring(0,l)));
        coordinates.add(1,Integer.valueOf(s.substring(l+1)));
        return coordinates;
    }

    public static void probabilityC(HashMap<Integer,ArrayList<StringBuilder>> coordsC, ArrayList<Double> m, ArrayList<Double> k)
    {
        int count = 0;
        while (count < 20) {
            ArrayList<StringBuilder> coords = coordsC.get(count);
            for(int l = 0; l < coords.size(); l++)
            {
                StringBuilder a = coords.get(l);
                System.out.println(a);
                ArrayList<Integer> intcoord = deformat(a);
                int i = intcoord.get(0);
                int j = intcoord.get(1);
                System.out.println(i + " " + j);
            }
            count++;
        }
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
        while ((line=reader1.readLine()) != null)
        {
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String num = scanner.next();
                plainText.add(Double.parseDouble(num));
            }
        }
        reader.close();
        for(int i = 20; i < 40; i++)
        {
            key.add(plainText.get(i));
        }
        plainText.removeAll(key);
        System.out.println(plainText);
        System.out.println(key);
        HashMap m = calculationC(cipherText);
        probabilityC(m, plainText, key);
    }


}