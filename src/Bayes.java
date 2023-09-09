import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
public class Bayes {

    public static void main(String[] args) throws Exception {
        ArrayList<Double> a = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("C:\\table_01.csv"));
        String line = null;
        Scanner scanner = null;
        int r = 1;
        while ((line = reader.readLine()) != null) {
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String num = scanner.next();
                a.add(Double.parseDouble(num));
            }
        }
        reader.close();
        System.out.println(a);
    }

}
