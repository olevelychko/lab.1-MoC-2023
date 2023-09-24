import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Bayes {

    public static ArrayList<Double> calculationC(ArrayList<Integer> C, ArrayList<Double> M, ArrayList<Double> K) {
        ArrayList<Double> probabC = new ArrayList<>();
        int count = 0;
        double probC = 0;
        while (count < 20) {
            for (int k = 0; k < C.size(); k++) {
                if (C.get(k) == count) {
                    int i = k / 20;
                    int j = k % 20;
                    probC = (M.get(j) * K.get(i)) + probC;
                }
            }
            count++;
            probabC.add(probC);
            System.out.printf("%.2f%n", probC);
            probC = 0.0;
        }
        return probabC;
    }

    public static ArrayList<Double> calculationMC(ArrayList<Integer> C, ArrayList<Double> M, ArrayList<Double> K) {
        ArrayList<Double> allMC = new ArrayList<>();
        int countC = 0;
        int countM = 0;
        double sumC = 0.0;
        while (countM < 20) {
            while (countC < 20) {
                for (int k = 0; k < C.size(); k++) {
                    if (C.get(k) == countC && k % 20 == countM) {
                        //  System.out.println(" M " + countM + " C " + countC + " K " + k / 20);
                        sumC = (M.get(countM) * K.get(k / 20)) + sumC;
                    }
                }
                allMC.add(sumC);
                sumC = 0.0;
                countC++;
            }
            countC = 0;
            countM++;
        }
        System.out.println(allMC);
        return allMC;
    }

    public static ArrayList<Double> probabilityMIC(ArrayList<Double> probabC, ArrayList<Double> probabMC) {
        ArrayList<Double> MC = new ArrayList<>();
        for (int i = 0; i < probabMC.size(); i++) {
            MC.add(probabMC.get(i) / probabC.get(i % 20));
        }
        System.out.println(MC);
        return MC;
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
        ArrayList<Double> pC = calculationC(cipherText, plainText, key);
        ArrayList<Double> pMC = calculationMC(cipherText, plainText, key);
        probabilityMIC(pC, pMC);
    }
}