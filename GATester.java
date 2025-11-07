import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GATester {
    public static void main(String[] args) throws FileNotFoundException {
        String input = "Input.txt";

        int m=0;
        int n=0;
        int p=0;
        int h=0;
        int t=0;

        int [][] layout;
        House [] rumah;
        List<House> lokasiKosong = new ArrayList<>();

        Scanner sc = new Scanner(new File(input));
        if (sc.hasNextInt()) m = sc.nextInt();
            if (sc.hasNextInt()) n = sc.nextInt();
            
            if (sc.hasNextInt()) p = sc.nextInt();
            if (sc.hasNextInt()) h = sc.nextInt();
            if (sc.hasNextInt()) t = sc.nextInt();

            layout = new int[m][n];
            rumah= new House[h];

            //input Lokasi Rumah
            for (int i = 0; i < h; i++) {
                int x = sc.nextInt() - 1; // Konversi 1-based ke 0-based
                int y = sc.nextInt() - 1;
                rumah[i] = new House(x, y);
            }

            //input lokasi pohon
            for (int i = 0; i < t; i++) {
                int x = sc.nextInt() - 1; // Konversi 1-based ke 0-based
                int y = sc.nextInt() - 1;
            }
            
    }
}
