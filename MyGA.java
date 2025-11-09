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
         nextPopulation[0]= best ;
         
        CrossOver(nextPopulation);
    }


    public void population(){
             populasi = new ArrayList<>();
        for (int i = 0; i < ukuranPopulasi; i++) {
            populasi.add(new Kromosom(lokasiKosong, fireStationNum));
        }
       

    }

    public void CrossOver(Kromosom [] nextPopulation){
        Kromosom parent1 = populasi.get(rdm.nextInt(populasi.size()));
        Kromosom parent2 = populasi.get(rdm.nextInt(populasi.size()));
         int point = rdm.nextInt(populasi.size());//poin untuk crossover 
         for(int a= 1;a<nextPopulation.length;a++){
                if(a<point){
                   House gene = parent1.getGene(a); 
                    nextPopulation[a].setGene(a,gene);
                }else{
                     House gene = parent2.getGene(a); 
                    nextPopulation[a].setGene(a,gene);
                }
         }


    }
       
    }



