import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class Population {
    private ArrayList<Kromosom> populasi;

        //ukuran populasi banyaknya kromosom
    public Population(int ukuranPopulasi,int FireStationNum, House[] lokasiLahanKosong,Random rdm){
        populasi = new ArrayList<>();

         for (int i = 0; i < ukuranPopulasi; i++) {
        populasi.add(new Kromosom(lokasiLahanKosong, FireStationNum, rdm));//kenapa random diperlukan untuk memanggil kromosom
            //populasi[i].f();
        }

     
    }
            //mengembalikan kromosom dengan nilai fitness terendah
     public Kromosom getTerbaik() {
        Collections.sort(populasi);
        return populasi.getFirst();
    }
}
