import java.util.Arrays;
import java.util.Random;

public class Population {
    private Kromosom[] populasi;

    public Population(int ukuranPopulasi,int FireStationNum, House[] lokasiLahanKosong,Random rdm){
        populasi = new Kromosom[ukuranPopulasi];
         for (int i = 0; i < ukuranPopulasi; i++) {
            populasi[i] = new Kromosom(lokasiLahanKosong, FireStationNum, rdm);//kenapa random diperlukan untuk memanggil kromosom
            //populasi[i].f();
        }

        //mengembalikan kromosom dengan nilai fitness terendah
         
    }

     public Kromosom getTerbaik() {
        Arrays.sort(populasi); // karena Kromosom sudah implement compareTo
        return populasi[0];
    }
}
