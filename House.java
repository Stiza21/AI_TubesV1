public class House implements Comparable<House>{
    int xCoordinate,yCoordinate;

    public House(int xCoordinate, int yCoordinate){
        this.xCoordinate=xCoordinate;
        this.yCoordinate=yCoordinate;
    }
    @Override
    public boolean equals(Object other){
        if (!(other instanceof House)) return false;

        else return this.xCoordinate==((House)other).xCoordinate&&this.yCoordinate==((House)other).yCoordinate;
    }
    @Override
    public int compareTo(House other){
        return (this.xCoordinate-other.xCoordinate)*80 + (this.yCoordinate-other.yCoordinate);
    }
}
