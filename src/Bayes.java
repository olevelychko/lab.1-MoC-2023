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
        //System.out.println(allMC);
        showTable(allMC);
        return allMC;
    }


    public static ArrayList<Double> probabilityMIC(ArrayList<Double> probabC, ArrayList<Double> probabMC) {
        System.out.println("P(M|C)");
        ArrayList<Double> MC = new ArrayList<>();
        for (int i = 0; i < probabMC.size(); i++) {
            MC.add(probabMC.get(i) / probabC.get(i % 20));
        }
        showTable(MC);
        return MC;
    }


    private static void showTable(ArrayList<Double> MC) {
        for (int i = 0; i < MC.size(); i++) {
            System.out.print(String.format("%.2f", MC.get(i)) + " ");
            if ((i + 1) % 20 == 0) {
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("______________________________________________________________________________");
    }


    public static ArrayList<Double> deterministic(ArrayList<Double> MC) {
        System.out.println("Deterministic function");
        ArrayList<Double> Det = new ArrayList<>();
        double maxEl = -1;
        int prevMaxPos = 0;
        int maxPos;
        int j = 0;
        int p = 0;
        while (j < 20) {
            for (int i = j ; i < MC.size(); i+=20) {
                if (MC.get(i) > maxEl) {
                    maxEl = MC.get(i);
                    maxPos = p;
                    Det.add(maxPos, 1.0);
                    if (prevMaxPos != maxPos) {
                        Det.set(prevMaxPos, 0.0);
                        prevMaxPos = p;
                    }
                } else Det.add(p, 0.0);
                p++;
            }
            maxEl = -1;
            prevMaxPos = 0;
            j++;
        }
        showTable(Det);
        return Det;
    }


    public static ArrayList<Double> stochastic(ArrayList<Double> MC) {
        System.out.println("Stochastic function");
        ArrayList<Double> Stoch = new ArrayList<>();
        ArrayList<Integer> sameMaxPos = new ArrayList<>();
        double maxEl = -1;
        int prevMaxPos = 0;
        int maxPos;
        int j = 0;
        int p = 0;
        while (j < 20) {
            for (int i = j ; i < MC.size(); i+=20) {
                if (MC.get(i) > maxEl) {
                    sameMaxPos = new ArrayList<>();
                    maxEl = MC.get(i);
                    maxPos = p;
                    Stoch.add(maxPos, 1.0);
                    if (prevMaxPos != maxPos) {
                        Stoch.set(prevMaxPos, 0.0);
                        prevMaxPos = p;
                    }
                } else Stoch.add(p, 0.0);
                if (MC.get(i) == maxEl) {
                    sameMaxPos.add(p);
                }
                p++;
            }
            int numbSameMax = sameMaxPos.size();
            for (Integer sameMaxPo : sameMaxPos) {
                Stoch.set(sameMaxPo, 1.0 / numbSameMax);
            }
            sameMaxPos = new ArrayList<>();
            maxEl = -1;
            prevMaxPos = 0;
            j++;
        }
        showTable(Stoch);
        return Stoch;
    }

    public static double Loss(ArrayList<Double> MC, ArrayList<Double> function)
    {
        double loss = 0.0;
        int i = 0;
        int j = 0;
        while(i < function.size()) {
            while (j < MC.size()) {
                if (function.get(i) == 0) {
                    loss = loss + MC.get(j);
                }
                i++;
                j+=20;
            }
            j= i /20;
        }
        System.out.println("Loss in function = " + loss);
        return loss;
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
        ArrayList<Double> MC = probabilityMIC(pC, pMC);
        ArrayList<Double> Det = deterministic(MC);
        Loss(pMC, Det);
        ArrayList<Double> Stoch = stochastic(MC);
        Loss(pMC, Stoch);
    }
}