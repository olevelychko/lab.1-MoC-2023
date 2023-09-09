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
                    coords.append('+');
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

    public static void probabilityC(HashMap<Integer,ArrayList<StringBuilder>> coordsC){

    }

    public static void main(String[] args) throws Exception {
        ArrayList<Double> a = new ArrayList<>();
        ArrayList<Integer> cipherText = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("C:\\table_01.csv"));
        String line;
        Scanner scanner;
        while ((line = reader.readLine()) != null) {
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String num = scanner.next();
                a.add(Double.parseDouble(num));
                cipherText.add(Integer.parseInt(num));
            }
        }
        reader.close();
        calculationC(cipherText);

    }


}