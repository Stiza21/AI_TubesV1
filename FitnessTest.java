import java.io.File;
import java.util.Scanner;

public class FitnessTest {
    public static void main(String[] args) {
        File toRead = new File(args[0]);
        Scanner sc;
        try {
            sc = new Scanner(toRead);
            //note input banyak kolom dulu baru banyak baris
            int banyakKolom=sc.nextInt(), banyakBaris=sc.nextInt();
            int banyakFireStation=sc.nextInt(),banyakRumah=sc.nextInt(),banyakPohon=sc.nextInt();
            House[] houses = new House[banyakRumah];
            //matriks layout memang tertranspose dari gambar koordinat yang biasanya agar input mudah
            int [][] layout = new int[banyakKolom][banyakBaris];
            for (int i=0;i<banyakRumah;i++){
                houses[i] = new House(sc.nextInt()-1, sc.nextInt()-1);
                layout[houses[i].xCoordinate][houses[i].yCoordinate]=1;
            }
            System.out.println("done input Rumah");
            for (int i = 0; i < banyakPohon; i++) {
                layout[sc.nextInt()-1][sc.nextInt()-1]=2;
            }
            System.out.println(banyakRumah);
            System.out.println(banyakPohon);
            Fitness hasil = new Fitness(layout, houses);
            hasil.printLayout();
            hasil.printDistances();
        } catch (Exception e) {
        
        }
    }
}
