import java.util.PriorityQueue;
import java.util.Scanner;

public class Fitness {
    public final int far=100000;     //arbitrary number, ubah aja kalo mau
    public int[][] layout;
    public House[] houses;
    public int banyakFireStation;
    private int[][][] distances;    
    
    public Fitness(int[][] layout, House[] houses){
        this.layout = layout;
        this.houses = houses;
        this.calcDistances();
    }
    public Fitness(Scanner sc){
        int banyakKolom,banyakBaris,banyakRumah,banyakPohon;
        banyakKolom=sc.nextInt();
        banyakBaris=sc.nextInt();
        this.banyakFireStation=sc.nextInt();
        banyakRumah=sc.nextInt();
        banyakPohon=sc.nextInt();
        this.houses = new House[banyakRumah];
        //matriks layout memang tertranspose dari gambar koordinat yang biasanya agar input mudah
        this.layout = new int[banyakKolom][banyakBaris];
        for (int i=0;i<banyakRumah;i++){
            this.houses[i] = new House(sc.nextInt()-1, sc.nextInt()-1);
            this.layout[this.houses[i].xCoordinate][this.houses[i].yCoordinate]=1;
        }
        for (int i = 0; i < banyakPohon; i++) {
            this.layout[sc.nextInt()-1][sc.nextInt()-1]=2;
        }
        this.calcDistances();
    }
    public void calcDistances(){
        this.distances =new int[houses.length][layout.length][layout[0].length];
        for (int i=0;i<houses.length;i++){
            for (int j=0;j<layout.length;j++){
                for (int k=0;k<layout[0].length;k++){
                    distances[i][j][k]=160*far;   
                }
            }
        }
        // for (int i=0;i<distances.length;i++){
        //     bfs(houses[i],distances[i]);
        // }
        for (int i = 0; i < distances.length; i++) {
            djikstra(houses[i], distances[i]);
        }
    }
    public int f(House[] firestations){
        int total=0;
        for (int i=0;i<houses.length;i++){
            int min = far;
            for (int j=0;j<firestations.length;j++){
                min = Math.min(min,distances[i][firestations[j].xCoordinate][firestations[j].yCoordinate]);
            }
            total+=min;
        }
        return total;
    }

    public void djikstra(House source, int distancesFromHouse[][]){
        PriorityQueue<House> pq = new PriorityQueue<>();
        pq.add(new House(source.xCoordinate,source.yCoordinate,0));
        while(!pq.isEmpty()){
            House top = pq.poll();
            // System.out.println(top.xCoordinate+" "+top.yCoordinate);
            if (distancesFromHouse[top.xCoordinate][top.yCoordinate]<=top.distance) continue;
            distancesFromHouse[top.xCoordinate][top.yCoordinate]=top.distance;
            if (top.xCoordinate>0) pq.offer(new House(top.xCoordinate-1, top.yCoordinate, top.distance+1+(layout[top.xCoordinate-1][top.yCoordinate]==0?0:far)));
            if (top.xCoordinate<layout.length-1) pq.offer(new House(top.xCoordinate+1, top.yCoordinate, top.distance+1+(layout[top.xCoordinate+1][top.yCoordinate]==0?0:far)));
            if (top.yCoordinate>0) pq.offer(new House(top.xCoordinate, top.yCoordinate-1, top.distance+1+(layout[top.xCoordinate][top.yCoordinate-1]==0?0:far)));
            if (top.yCoordinate<layout[0].length-1) pq.offer(new House(top.xCoordinate, top.yCoordinate+1, top.distance+1+(layout[top.xCoordinate][top.yCoordinate+1]==0?0:far)));
        }
    }
    //for debugging
    public void printLayout(){
        for (int i=layout[0].length-1;i>=0;i--){
            for (int j=0;j<layout.length;j++){
                System.out.printf("%d ", layout[j][i]);
            }
            System.out.println();
        }
    }
    public void printDistances(){
        for (int i=0;i<houses.length;i++){
            System.out.printf("distance from %d %d \n", houses[i].xCoordinate+1, houses[i].yCoordinate+1);
            for (int j=layout[0].length-1;j>=0;j--){
                for (int k=0;k<layout.length;k++){
                    System.out.printf("%6d ", distances[i][k][j]);
                }
                System.out.println();
            }
        }
    }
}
