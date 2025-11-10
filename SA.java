import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SA {
    public static Fitness hasil;
    public static void main(String[] args) {
        int banyakKolom=0,banyakBaris=0,banyakFireStation=0,banyakRumah=0,banyakPohon=0;
        File inputFile = new File(args[0]);
        int[][] layout = new int[1][1];
        try{
            Scanner sc = new Scanner(inputFile);
            banyakKolom=sc.nextInt();
            banyakBaris=sc.nextInt();
            banyakFireStation=sc.nextInt();
            banyakRumah=sc.nextInt();
            banyakPohon=sc.nextInt();
            House[] houses = new House[banyakRumah];
            //matriks layout memang tertranspose dari gambar koordinat yang biasanya agar input mudah
            layout = new int[banyakKolom][banyakBaris];
            for (int i=0;i<banyakRumah;i++){
                houses[i] = new House(sc.nextInt()-1, sc.nextInt()-1);
                layout[houses[i].xCoordinate][houses[i].yCoordinate]=1;
            }
            System.out.println("done input Rumah");
            for (int i = 0; i < banyakPohon; i++) {
                layout[sc.nextInt()-1][sc.nextInt()-1]=2;
            }
        }
        catch(Exception e){
            
            //note input banyak kolom dulu baru banyak baris
        }
        double starting_Temperature = Double.valueOf(args[1]);
        double cooling_rate = Double.valueOf(args[2]);
        double stopping_temp = Double.valueOf(args[3]);
        
        Random seedGenerator = new Random();
        int seed;
        for (int nomorPercobaan = 1;nomorPercobaan<10;nomorPercobaan++){
            double temperature = starting_Temperature;
            seed = seedGenerator.nextInt(10000);
            Random rd = new Random(seed);
            House[] fireStations = new House[banyakFireStation];
            for (int i=0;i<banyakFireStation;i++){
                House fireStation = new House(rd.nextInt(banyakKolom), rd.nextInt(banyakBaris));
                while(layout[fireStation.xCoordinate][fireStation.yCoordinate]>0){
                    if (findNeighbor(fireStation, rd)==null){
                        fireStation.xCoordinate=rd.nextInt(banyakKolom);
                        fireStation.yCoordinate=rd.nextInt(banyakBaris);
                    }
                }
                fireStations[i] = fireStation;
                layout[fireStation.xCoordinate][fireStation.yCoordinate]=3;
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
                int deltaE = hasil.f(fireStations)-currentFitness;
                if ((deltaE<0) && (rd.nextDouble() > Math.exp(deltaE/temperature))){
                    currentFitness = deltaE+currentFitness;
                    if (currentFitness>bestScore) {
                        System.arraycopy(fireStations, 0, bestArrangement, 0, banyakFireStation);
                        bestScore = currentFitness;
                    }
                }
                else{
                    fireStations[changing]=toChange;
                }
                temperature *=cooling_rate;
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
        target = currentHouse.xCoordinate;
        while(target>=0 &&hasil.layout[currentHouse.xCoordinate][target]>0) target--;
        if (target>=0) neighbors.add(new House(currentHouse.xCoordinate, target));
        //bergerak ke bawah
        target = currentHouse.xCoordinate;
        while(target<hasil.layout[0].length &&hasil.layout[currentHouse.xCoordinate][target]>0) target++;
        if (target<hasil.layout[0].length) neighbors.add(new House(currentHouse.xCoordinate, target));
        //memilih satu
        if (neighbors.isEmpty()) return null;
        return neighbors.get(rd.nextInt(neighbors.size()));
    }
}
