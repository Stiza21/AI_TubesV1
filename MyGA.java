import java.util.ArrayList;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyGA {
private Fitness fitness;
private static House [] lokasiKosong;
private double Crossrate;
private double mutationRate;
private static int fireStationNum;
private int ukuranPopulasi;
private Random rdm;
private ArrayList <Kromosom> populasi;

//population berisi arrayList
    public MyGA(Fitness fitness,House [] lokasiKosong,int fireStationNum,double Crossrate,double mutationRate,int ukuranPopulasi){
        this.fitness=fitness;
        this.lokasiKosong=lokasiKosong;
        this.Crossrate=Crossrate;
        this.mutationRate=mutationRate;
        this.rdm = new Random();
        this.ukuranPopulasi=ukuranPopulasi;
    }

    public void Genetics(){
        population();
        Kromosom best = populasi.get(0);//ELITISM AMBIL YANG TERBAIK
         Kromosom [] nextPopulation = new Kromosom[populasi.size()];//kromosom child
         nextPopulation[0]= best ;//elitism
        CrossOver(nextPopulation);
        Mutasi(nextPopulation);
    }


    public void population(){
             populasi = new ArrayList<>();//dibuat arraylist karena jumlah firestation pada awalnya tidak diketahui
        for (int i = 0; i < ukuranPopulasi; i++) {
            populasi.add(new Kromosom(lokasiKosong, fireStationNum));
        }
       

    }

    public void CrossOver(Kromosom [] nextPopulation){

         for(int a= 1;a<nextPopulation.length;a++){
        double randomValue = rdm.nextDouble();//untuk menentukan apakah akan crossover atau tidak && double.rdmvalue diantara 0 sampai 1
        if(randomValue<Crossrate){
        Kromosom parent1 = Tournament();
        Kromosom parent2 = Tournament();
        Kromosom anak1 = new Kromosom(new House[fireStationNum]);
        Kromosom anak2 = new Kromosom(new House[fireStationNum]);
         int point = rdm.nextInt(parent1.getSize());//poin untuk crossover 
                if(a<point){
                   anak1.setGene(a,parent1.getGene(a));
                   anak2.setGene(a,parent2.getGene(a));
                }else{
                   anak1.setGene(a,parent2.getGene(a));
                   anak2.setGene(a,parent1.getGene(a));
                }
        }
         }
    }

    public Kromosom Tournament(){
        Kromosom best = null;
        int participantNum =3;

         //pilih beberapa individu untuk ikut tournament   
        for(int a=0;a<participantNum;a++){
            Kromosom participant = populasi.get(rdm.nextInt(populasi.size()));
            if(participant.compareTo(best)>0){//jika nilai fitness nya lebih kecil/lebih baik 
                best=participant;
            }
        }
        return best;
    }

       public void Mutasi(Kromosom [] nextPopulation) {
    for (int i = 1; i < nextPopulation.length; i++) { //index 0 untuk elitism
        Kromosom krom = nextPopulation[i];
        for (int j = 0; j < fireStationNum; j++) {
            double r = rdm.nextDouble(); //mengambil nilai 0 sampai 1 untuk perbandingan dengan mutationrate
            if (r < mutationRate) {
                // ganti gen dengan lokasi baru secara acak
                House newGene = lokasiKosong[rdm.nextInt(lokasiKosong.length)];//membuat lokasi firestation baru yang akan digunakan untuk proses mutasi dari lahan kosong yang tersedia sehingga tidak terjadi penumpukan firestation dengan objek lain
                krom.setGene(j, newGene);
            }
        }
    }
}


    public Kromosom getBest() {
        Kromosom best = null;
        for (Kromosom k : populasi) {
            if (best == null ||k.getnewFitness() < best.getnewFitness()) {
                best = k;
            }
        }
        return best;
    }

 
       
    }



