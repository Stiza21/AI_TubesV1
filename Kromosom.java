import java.util.Arrays;
import java.util.Random;

public class Kromosom implements Comparable<Kromosom>{

    private House [] arrKromo;//Array yang akan memprestasikan Kromosom Yang berisikan gene, dan tiap genennya adlaah lokasi dari Fire Station, Intinya(1 kromosom berisi beberapan fire station)
    private double fitness = 0;
    private static Fitness storageHitunganFit;

    public Kromosom (House [] lokasiLahanKosong, int banyakFireStation, Random rdm) {
        this.arrKromo = new House[banyakFireStation];

        House [] lokasiGanda = lokasiLahanKosong.clone(); // Array buat naro nilai dari lokasikosong, buat jaga jaga
        for (int i = 0; i < lokasiLahanKosong.length; i++) {
            int j = rdm.nextInt(i + 1);
            House storage = lokasiGanda[i];
            lokasiGanda[i] = lokasiGanda[j];
            lokasiGanda[j] = storage;
        }

        System.arraycopy(lokasiGanda, 0, this.arrKromo, 0, banyakFireStation);
    }

    public static void setStorageFit (Fitness n) {
        storageHitunganFit = n;
    }

    public void konversiFitness () {
        int jarakTotal = storageHitunganFit.f(this.arrKromo);
        if (jarakTotal == 1) {
            this.fitness = Double.MAX_VALUE;
        }
        else {
            this.fitness=1.0/jarakTotal;
        }
    }

    public House[] getArrKrom () {
        return this.arrKromo;
    }

    public double getnewFitness () {
        return this.fitness;
    }

    @Override
    public int compareTo(Kromosom o) {
        if (this.fitness > o.fitness) return -1; 
        if (this.fitness < o.fitness) return 1; 
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Kromosom storage = (Kromosom)o;
        return Arrays.equals(arrKromo, storage.arrKromo);
    }

}