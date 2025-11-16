import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
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
        File inputFile = new File(args[0]);  
        Scanner sc = new Scanner(inputFile);

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
        //untuk crossrate,mutation rate ,elitismRate,dan ukuranpopulasi
        double crossRate =Double.valueOf(args[1]);
        double mutationRate=Double.valueOf(args[2]);
        double elitismRate=Double.valueOf(args[3]);
        int populationNum=Integer.valueOf(args[4]);
        int generasi=Integer.valueOf(args[4]);
        Random rdm = new Random();
        //mengubah arraylist lokasi kosong menjadi array 
        Fitness fitness = new Fitness(layout, rumah);
        Kromosom.setStorageFit(fitness);
        int seed = rdm.nextInt();//input seed disini
        System.out.println("Seed for current run: " + seed);
        MyGA genetika = new MyGA(fitness,lokasiKosong.toArray(new House[0]), p, crossRate, mutationRate,elitismRate,populationNum,seed);
          Kromosom bestKromosom = null;

        for(int a=0;a<generasi;a++){
            genetika.Genetics();
            bestKromosom = genetika.getBest();

        }

             System.out.printf("jumlah fire stations %d: Best Fitness (Mean) = %.5f\n", p, bestKromosom.getnewFitness());
        //     for (int i = 0; i < bestKromosom.getSize(); i++) {
        //         House house = bestKromosom.getGene(i);
        //         //System.out.println("Firestation " + i +" : x = " + house.xCoordinate +", y = " + house.yCoordinate);
        //        layout[house.xCoordinate][house.yCoordinate]=7;
        // }

        // for(int baris=0;baris<m;baris++){
        //     for(int kolom=0;kolom<n;kolom++){
        //         System.out.print(layout[baris][kolom]+" ");
        //     }
        //     System.out.println();
        // }
}
}