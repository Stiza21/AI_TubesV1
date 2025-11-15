import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MyGA {
private Fitness fitness;
private static House [] lokasiKosong;
private double Crossrate;
private double mutationRate;
private double elitismRate;
private static int fireStationNum;
private int ukuranPopulasi;
private Random rdm;
private ArrayList <Kromosom> populasi;
private Kromosom [] nextPopulation;

//population berisi arrayList
    public MyGA(Fitness fitness,House [] lokasiKosong,int fireStationNum,double Crossrate,double mutationRate,double elitismRate,int ukuranPopulasi){
        this.fitness=fitness;
        this.lokasiKosong=lokasiKosong;
        this.Crossrate=Crossrate;
        this.mutationRate=mutationRate;
        this.rdm = new Random();
        this.ukuranPopulasi=ukuranPopulasi;
        this.fireStationNum=fireStationNum;
        this.elitismRate = elitismRate;
    }

    public void Genetics(){
         Kromosom.setStorageFit(this.fitness);
        if (populasi == null || populasi.isEmpty()) {
        population(); // hitung juga fitness-nya di dalam konstruktor Kromosom
    }
       this.nextPopulation = new Kromosom[populasi.size()];//kromosom child
        Collections.sort(populasi);
        int elitismCount=elitism();
        CrossOver(nextPopulation,elitismCount);
        Mutasi(nextPopulation,elitismCount);
        Collections.sort(populasi);
        //setelah semua anak selesai dilahirkan itung nilai fitnessnya ulang
    for (Kromosom krom : nextPopulation) {
        krom.konversiFitness();
         }
        populasi.clear();
        Collections.addAll(populasi, nextPopulation);
        Collections.sort(populasi);
    }

    public int elitism(){
        // Pastikan tidak melebihi ukuran populasi
        int elitismCount = (int) Math.min(ukuranPopulasi, Math.ceil(ukuranPopulasi * elitismRate));
        if (elitismCount < 1) elitismCount = 1; // Opsi: Pastikan minimal 1 elite
        for(int i = 0; i < elitismCount; i++) {
            // Salin kromosom terbaik (populasi.get(i)) ke posisi awal nextPopulation
            nextPopulation[i] = new Kromosom(populasi.get(i));
        }
        return elitismCount;
    }


    public void population(){
             populasi = new ArrayList<>();//dibuat arraylist karena jumlah firestation pada awalnya tidak diketahui
        for (int i = 0; i < ukuranPopulasi; i++) {
            populasi.add(new Kromosom(lokasiKosong, fireStationNum));
        }
    }

public void CrossOver(Kromosom[] nextPopulation,int startIndex) {
    int index = startIndex; // index 0 = elitism

    while (index < nextPopulation.length) {
        double randomValue = rdm.nextDouble();

        if (randomValue < Crossrate) {
            // Pilih dua parent secara acak lewat tournament
            Kromosom parent1 = Tournament();
            Kromosom parent2 = Tournament();

            // Buat dua anak kosong
            Kromosom anak1 = new Kromosom(new House[fireStationNum]);
            Kromosom anak2 = new Kromosom(new House[fireStationNum]);

            // Titik crossover di antara 1 dan (jumlah gen - 1)
            int point = 1 + rdm.nextInt(fireStationNum - 1);

            // Proses crossover
            for (int b = 0; b < fireStationNum; b++) {
                if (b < point) {
                    anak1.setGene(b, parent1.getGene(b));
                    anak2.setGene(b, parent2.getGene(b));
                } else {
                    anak1.setGene(b, parent2.getGene(b));
                    anak2.setGene(b, parent1.getGene(b));
                }
            }
            //hitung nilai fitness
            anak1.konversiFitness();
            anak2.konversiFitness();
            repairChromosome(anak1);
            repairChromosome(anak2);

            // Simpan anak ke populasi baru
            nextPopulation[index] = anak1;
            if (index + 1 < nextPopulation.length) {
                nextPopulation[index + 1] = anak2;
            }
            index += 2;

        } else {
            // Jika tidak crossover → copy langsung dari parent terbaik
            nextPopulation[index] = new Kromosom(Tournament());
            index++;
        }
    }
}
private int indexOfHouse(House h) {
    for (int i = 0; i < lokasiKosong.length; i++) {
        if (lokasiKosong[i].equals(h)) {
            return i;
        }
    }
    return -1; // tidak ditemukan
}


private void repairChromosome(Kromosom krom) {
    House[] genes = krom.getArrKrom();

    // Menandai lokasi kosong mana yang sudah dipakai
    boolean[] used = new boolean[lokasiKosong.length];

    // 1. Tandai yang sudah dipakai dalam kromosom (deteksi duplikasi)
    for (int i = 0; i < genes.length; i++) {
        House g = genes[i];
        int idx = indexOfHouse(g);

        if (idx != -1) {
            if (!used[idx]) {
                used[idx] = true; // pertama kali muncul → oke
            } else {
                // DUPLIKAT → set null dulu, nanti diisi
                genes[i] = null;
            }
        } else {
            // Jika gene tidak ditemukan di lokasiKosong → invalid → set null
            genes[i] = null;
        }
    }

    // 2. Isi posisi null dengan lokasi kosong yang belum digunakan
    for (int i = 0; i < genes.length; i++) {
        if (genes[i] == null) {
            // cari lokasi kosong yang belum terpakai
            int newIdx;
            do {
                newIdx = rdm.nextInt(lokasiKosong.length);
            } while (used[newIdx]);

            genes[i] = lokasiKosong[newIdx];
            used[newIdx] = true;
        }
    }

    // update kromosom
    for (int i = 0; i < genes.length; i++) {
        krom.setGene(i, genes[i]);
    }

    // 3. Hitung ulang fitness
    krom.konversiFitness();
}



    public Kromosom Tournament(){
        Kromosom best = populasi.get(rdm.nextInt(populasi.size()));
        int participantNum =3;

         //pilih beberapa individu untuk ikut tournament   
        for(int a=0;a<participantNum;a++){
            Kromosom participant = populasi.get(rdm.nextInt(populasi.size()));
            if(participant.getnewFitness() < best.getnewFitness()){//jika nilai fitness nya lebih kecil/lebih baik 
                best=participant;
            }
        }
        return best;
    }

       public void Mutasi(Kromosom [] nextPopulation,int startIndex) {
    for (int i = startIndex; i < nextPopulation.length; i++) { //index 0 untuk elitism
        Kromosom krom = nextPopulation[i];
        for (int j = 0; j < fireStationNum; j++) {
            double r = rdm.nextDouble(); //mengambil nilai 0 sampai 1 untuk perbandingan dengan mutationrate
            if (r < mutationRate) {
                // ganti gen dengan lokasi baru secara acak
                House newGene = lokasiKosong[rdm.nextInt(lokasiKosong.length)];//membuat lokasi firestation baru yang akan digunakan untuk proses mutasi dari lahan kosong yang tersedia sehingga tidak terjadi penumpukan firestation dengan objek lain
                krom.setGene(j, newGene);
            }
        }
         //setelah selesai mutasi gen itung ulang fitness
        krom.konversiFitness();
    }
}


    public Kromosom getBest() {
        return populasi.get(0);
    }

 
       
    }



