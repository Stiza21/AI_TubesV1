
import java.util.LinkedList;
import java.util.Queue;

public class Fitness {
    public final int far=80085;     //arbitrary number, ubah aja kalo mau
    int layout[][];
    House houses[];
    int distances[][][];
    
    public Fitness(int layout[][], House houses[]){
        this.layout = layout;
        this.houses = houses;
        this.distances =new int[houses.length][layout.length][layout[0].length];
        for (int i=0;i<houses.length;i++){
            for (int j=0;j<layout.length;j++){
                for (int k=0;k<layout[0].length;k++){
                    distances[i][j][k]=far;   
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
        distancesFromHouse[house.x][house.y]=0;
        queue.offer(house);
        for (int i=queue.size();i>0;i--){
            House top = queue.poll();
            if (top.x+1<layout.length){
                if (distancesFromHouse[top.x+1][top.y]!=far){
                    if (layout[top.x+1][top.y]==0){
                        distancesFromHouse[top.x+1][top.y]=distancesFromHouse[top.x][top.y]+1;
                        queue.offer(new House(top.x+1,top.y));
                    }
                }
            }
            if (top.x-1>=0){
                if (distancesFromHouse[top.x-1][top.y]!=far){
                    if (layout[top.x-1][top.y]==0){
                        distancesFromHouse[top.x-1][top.y]=distancesFromHouse[top.x][top.y]+1;
                        queue.offer(new House(top.x-1,top.y));
                    }
                }
            }
            if (top.y+1<layout[0].length){
                if (distancesFromHouse[top.x][top.y+1]!=far){
                    if (layout[top.x][top.y+1]==0){
                        distancesFromHouse[top.x][top.y+1]=distancesFromHouse[top.x][top.y]+1;
                        queue.offer(new House(top.x,top.y+1));
                    }
                }
            }
            if (top.y-1>=0){
                if (distancesFromHouse[top.x][top.y-1]!=far){
                    if (layout[top.x][top.y-1]==0){
                        distancesFromHouse[top.x][top.y-1]=distancesFromHouse[top.x][top.y]+1;
                        queue.offer(new House(top.x,top.y-1));
                    }
                }
            }
        }
    }
    public int f(House fireStation1, House fireStation2){
        int total=0;
        for (int i=0;i<houses.length;i++){
            total+=Math.min(distances[i][fireStation1.x][fireStation1.y],distances[i][fireStation2.x][fireStation2.y]);
        }
        return total;
    }

}
