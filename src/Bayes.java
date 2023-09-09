import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
public class Bayes {

    public static void calculationC(ArrayList C)
    {
        HashMap<Integer,String> probC = new HashMap<>();

    }

    public static void main(String[] args) throws Exception {
        ArrayList<Integer> cipherText = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("C:\\table_01.csv"));
        String line = null;
        Scanner scanner = null;
        while ((line = reader.readLine()) != null) {
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String num = scanner.next();
                cipherText.add(Integer.parseInt(num));
            }
        }
        reader.close();
        //System.out.println(a);
        for(int i = 0; i < cipherText.size(); i++)
        {
            System.out.print(cipherText.get(i) + " ");
            if(i % 20 == 0 && i != 0)
            {
                System.out.println("   ");
            }
        }
    }

}
