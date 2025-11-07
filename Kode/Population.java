import java.util.Random;

public class Population {
    private Kromosom[] populasi;

    public Population(int ukuranPopulasi,int FireStationNum, House[] lokasiLahanKosong){
        populasi = new Kromosom[ukuranPopulasi];
         for (int i = 0; i < ukuranPopulasi; i++) {
            populasi[i] = new Kromosom(FireStationNum, lokasiLahanKosong,Random rdm);//kenapa random diperlukan untuk memanggil kromosom
            populasi[i].f;
        }
          public Kromosom getTerbaik() {
        Arrays.sort(populasi); // karena Kromosom sudah implement compareTo
        return populasi[0];
    }
    }
}
