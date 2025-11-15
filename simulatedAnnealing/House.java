public class House implements Comparable<House>{
    int xCoordinate,yCoordinate, distance;

    public House(int xCoordinate, int yCoordinate){
        this.xCoordinate=xCoordinate;
        this.yCoordinate=yCoordinate;
        this.distance=0;
    }
    public House(int xCoordinate, int yCoordinate, int distance){
        this.xCoordinate=xCoordinate;
        this.yCoordinate=yCoordinate;
        this.distance=distance;
    }
    @Override
    public boolean equals(Object other){
        if (!(other instanceof House)) return false;

        else return this.xCoordinate==((House)other).xCoordinate&&this.yCoordinate==((House)other).yCoordinate;
    }
    @Override
    public int compareTo(House other){
        return this.distance-other.distance;
    }
}
