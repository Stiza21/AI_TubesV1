import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SA {
    static Fitness hasil;
    static int banyakKolom,banyakBaris,banyakFireStation;
    public static void main(String[] args) throws Exception {
        //to run java SA input.txt startingTemp coolingRate stoppingTemp banyakIterasi Seed(opsional)
        //contoh: java SA input.txt 100 0.999 0.0001 20 3394
        File inputFile = new File(args[0]);
        Scanner sc = new Scanner(inputFile);
        hasil = new Fitness(sc);
        banyakBaris=hasil.layout[0].length;
        banyakKolom=hasil.layout.length;
        banyakFireStation=hasil.banyakFireStation;
        sc.close();
        double starting_Temperature = Double.parseDouble(args[1]);
        double cooling_rate = Double.parseDouble(args[2]);
        double stopping_temp = Double.parseDouble(args[3]);
        
        Random seedGenerator = new Random();
        int seed;
        int banyakPercobaan=1;
        int fitness;
        int overallBest=Integer.MAX_VALUE;
        int bestSeed=-1;
        //int freq=0;
        House[] bestLayout=new House[banyakFireStation];
        if (args.length>4) banyakPercobaan=Integer.parseInt(args[4]);
        for(int nomorPercobaan=0;nomorPercobaan<banyakPercobaan;nomorPercobaan++){
            seed = seedGenerator.nextInt(10000);
            if (args.length>5) seed = Integer.parseInt(args[5]);
            Random rd = new Random(seed);
            double temperature = starting_Temperature;
            House[] fireStations = simulatedAnnealing(temperature, cooling_rate, stopping_temp, rd);
            fitness = hasil.f(fireStations);
            if (fitness<overallBest){
                overallBest = fitness;
                bestSeed = seed;
                System.arraycopy(fireStations, 0, bestLayout, 0, banyakFireStation);
                //freq=1;
            }
            // else if (fitness==overallBest){
            //     freq++;
            // }
            System.out.printf("Seed:%d bestFound:%d\n", seed, fitness);
            //uncomment for best layout di percobaan itu
            // System.out.println("optimal found:");
            // for (int i=0;i<banyakFireStation;i++){
            //     System.out.println(fireStations[i].xCoordinate+" "+ fireStations[i].yCoordinate);
            // }
            for (House stations:fireStations){
                hasil.layout[stations.xCoordinate][stations.yCoordinate]=0;
            }
        }
        if (banyakPercobaan>1){
            //System.out.println("best found "+ freq +"times");
            System.out.printf("Best over all tries: %d with seed %d\n",overallBest, bestSeed);
            for (int i=0;i<banyakFireStation;i++){
                System.out.println(bestLayout[i].xCoordinate+" "+ bestLayout[i].yCoordinate);
            }
        }
    }

    public static House findNeighbor(House currentHouse, Random rd){
        ArrayList<House> neighbors = new ArrayList<>();
        int target;
        //bergerak ke kiri
        target = currentHouse.xCoordinate;
        while(target>=0 &&hasil.layout[target][currentHouse.yCoordinate]>0) target--;
        if (target>=0) neighbors.add(new House(target, currentHouse.yCoordinate));
        //bergerak ke kanan
        target = currentHouse.xCoordinate;
        while(target<hasil.layout.length &&hasil.layout[target][currentHouse.yCoordinate]>0) target++;
        if (target<hasil.layout.length) neighbors.add(new House(target, currentHouse.yCoordinate));
        //bergerak ke atas
        target = currentHouse.yCoordinate;
        while(target>=0 &&hasil.layout[currentHouse.xCoordinate][target]>0) target--;
        if (target>=0) neighbors.add(new House(currentHouse.xCoordinate, target));
        //bergerak ke bawah
        target = currentHouse.yCoordinate;
        while(target<hasil.layout[0].length &&hasil.layout[currentHouse.xCoordinate][target]>0) target++;
        if (target<hasil.layout[0].length) neighbors.add(new House(currentHouse.xCoordinate, target));
        //memilih satu
        if (neighbors.isEmpty()) return null;
        return neighbors.get(rd.nextInt(neighbors.size()));
    }

    
    public static House[] simulatedAnnealing(double temperature, double cooling_rate, double stopping_temp, Random rd){
        House[] fireStations = new House[banyakFireStation];
        for (int i=0;i<banyakFireStation;i++){
            // System.out.printf("generating firestation number %d\n", i+1);
            House fireStation = new House(rd.nextInt(banyakKolom), rd.nextInt(banyakBaris));
            while(hasil.layout[fireStation.xCoordinate][fireStation.yCoordinate]>0){
                House neighbor = findNeighbor(fireStation, rd);
                if (neighbor==null){
                    fireStation.xCoordinate=rd.nextInt(banyakKolom);
                    fireStation.yCoordinate=rd.nextInt(banyakBaris);
                }
                else{
                    fireStation = neighbor;
                }
            }
            fireStations[i] = fireStation;
            hasil.layout[fireStation.xCoordinate][fireStation.yCoordinate]=3;
        }
        int currentFitness = hasil.f(fireStations);
        House[] bestArrangement = new House[banyakFireStation];
        int bestScore = currentFitness;
        System.arraycopy(fireStations, 0, bestArrangement, 0, banyakFireStation);
        while(temperature>stopping_temp){
            int changing = rd.nextInt(banyakFireStation);
            House toChange = fireStations[changing];
            House candidate = findNeighbor(toChange, rd);
            while (candidate==null){
                changing = rd.nextInt(banyakFireStation);
                toChange = fireStations[changing];
                candidate = findNeighbor(toChange, rd);
            }
            fireStations[changing]=candidate;
            int deltaE = currentFitness-hasil.f(fireStations);
            if ((deltaE>0) || (rd.nextDouble() > Math.exp(deltaE/temperature))){
                currentFitness = currentFitness-deltaE;
                if (currentFitness>bestScore) {
                    System.arraycopy(fireStations, 0, bestArrangement, 0, banyakFireStation);
                    bestScore = currentFitness;
                }
                hasil.layout[toChange.xCoordinate][toChange.yCoordinate]=0;
                hasil.layout[candidate.xCoordinate][candidate.yCoordinate]=3;
            }
            else{
                fireStations[changing]=toChange;
            }
            temperature *=cooling_rate;
            //System.out.println(temperature);
        }
        return fireStations;
    }
}

