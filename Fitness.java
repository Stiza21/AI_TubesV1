
import java.util.LinkedList;
import java.util.Queue;

public class Fitness {
    public final int far=1000;     //arbitrary number, ubah aja kalo mau
    int[][] layout;
    House[] houses;
    int[][][] distances;
    
    public Fitness(int[][] layout, House[] houses){
        this.layout = layout;
        this.houses = houses;
        this.distances =new int[houses.length][layout.length][layout[0].length];
        for (int i=0;i<houses.length;i++){
            for (int j=0;j<layout.length;j++){
                for (int k=0;k<layout[0].length;k++){
                    distances[i][j][k]=160*far;   
                }
            }
        }
        calcDistances();
    }
    public void calcDistances(){
        for (int i=0;i<distances.length;i++){
            bfs(houses[i],distances[i]);
        }
    }
    public void bfs (House house, int distancesFromHouse[][]){
        Queue<House> queue = new LinkedList<>();
        distancesFromHouse[house.xCoordinate][house.yCoordinate]=0;
        queue.offer(house);
        while(!queue.isEmpty()){
            House top = queue.poll();
            int currDistance = distancesFromHouse[top.xCoordinate][top.yCoordinate];
            if (top.xCoordinate+1<layout.length){
                if (distancesFromHouse[top.xCoordinate+1][top.yCoordinate]>currDistance){
                    if (layout[top.xCoordinate+1][top.yCoordinate]==0){
                        distancesFromHouse[top.xCoordinate+1][top.yCoordinate]=currDistance+1;
                        queue.offer(new House(top.xCoordinate+1,top.yCoordinate));
                    }
                    else if (currDistance+far<distancesFromHouse[top.xCoordinate+1][top.yCoordinate]){
                        distancesFromHouse[top.xCoordinate+1][top.yCoordinate]=currDistance+far;
                        queue.offer(new House(top.xCoordinate+1,top.yCoordinate));
                    }
                }
            }
            if (top.xCoordinate-1>=0){
                if (distancesFromHouse[top.xCoordinate-1][top.yCoordinate]>currDistance){
                    if (layout[top.xCoordinate-1][top.yCoordinate]==0){
                        distancesFromHouse[top.xCoordinate-1][top.yCoordinate]=currDistance+1;
                        queue.offer(new House(top.xCoordinate-1,top.yCoordinate));
                    }
                    else if (currDistance+far<distancesFromHouse[top.xCoordinate-1][top.yCoordinate]){
                        distancesFromHouse[top.xCoordinate-1][top.yCoordinate]=currDistance+far;
                        queue.offer(new House(top.xCoordinate-1,top.yCoordinate));
                    }
                }
            }
            if (top.yCoordinate+1<layout[0].length){
                if (distancesFromHouse[top.xCoordinate][top.yCoordinate+1]>currDistance){
                    if (layout[top.xCoordinate][top.yCoordinate+1]==0){
                        distancesFromHouse[top.xCoordinate][top.yCoordinate+1]=currDistance+1;
                        queue.offer(new House(top.xCoordinate,top.yCoordinate+1));
                    }
                    else if (currDistance+far<distancesFromHouse[top.xCoordinate][top.yCoordinate+1]){
                        distancesFromHouse[top.xCoordinate][top.yCoordinate+1]=currDistance+far;
                        queue.offer(new House(top.xCoordinate,top.yCoordinate+1));
                    }
                }
            }
            if (top.yCoordinate-1>=0){
                if (distancesFromHouse[top.xCoordinate][top.yCoordinate-1]>currDistance){
                    if (layout[top.xCoordinate][top.yCoordinate-1]==0){
                        distancesFromHouse[top.xCoordinate][top.yCoordinate-1]=currDistance+1;
                        queue.offer(new House(top.xCoordinate,top.yCoordinate-1));
                    }
                    else if (currDistance+far<distancesFromHouse[top.xCoordinate][top.yCoordinate-1]){
                        distancesFromHouse[top.xCoordinate][top.yCoordinate-1]=currDistance+far;
                        queue.offer(new House(top.xCoordinate,top.yCoordinate-1));
                    }
                }
            }
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
