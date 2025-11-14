import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GATester {
    public static void main(String[] args) throws FileNotFoundException {

        int m = 0;
        int n = 0;
        int p = 0;
        int h = 0;
        int t = 0;

        int[][] layout;
        House[] rumah;
        List<House> lokasiKosong = new ArrayList<>();
        Scanner sc = new Scanner(new File("Kode/Input3.txt"));

        if (sc.hasNextInt()) m = sc.nextInt();
        if (sc.hasNextInt()) n = sc.nextInt();
        if (sc.hasNextInt()) p = sc.nextInt();
        if (sc.hasNextInt()) h = sc.nextInt();
        if (sc.hasNextInt()) t = sc.nextInt();

        layout = new int[m][n];
        rumah = new House[h];

        // input Lokasi Rumah
        for (int i = 0; i < h; i++) {
            int x = sc.nextInt() - 1; // Konversi 1-based ke 0-based
            int y = sc.nextInt() - 1;
            rumah[i] = new House(x, y);
            layout[x][y] = 1;
        }

        // input lokasi pohon
        for (int i = 0; i < t; i++) {
            int x = sc.nextInt() - 1; // Konversi 1-based ke 0-based
            int y = sc.nextInt() - 1;
            layout[x][y] = 2; 
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (layout[i][j] == 0) {
                    lokasiKosong.add(new House(i, j));
                }
            }
        }
        //mengubah arraylist lokasi kosong menjadi array 
        Fitness fitness = new Fitness(layout, rumah);
        Kromosom.setStorageFit(fitness);
        MyGA genetika = new MyGA(fitness,lokasiKosong.toArray(new House[0]), p, 0.8, 0.0015, 500);
          Kromosom bestKromosom = null;

        for(int a=0;a<100;a++){
            genetika.Genetics();
            bestKromosom = genetika.getBest();
        }
            System.out.printf("jumlah fire stations %d: Best Fitness (Mean) = %.5f\n", p, bestKromosom.getnewFitness());
            for (int i = 0; i < bestKromosom.getSize(); i++) {
                House he = bestKromosom.getGene(i);
                System.out.println("Firestation " + i +" : x = " + he.xCoordinate +", y = " + he.yCoordinate);
}
        
        
        
    
}
}