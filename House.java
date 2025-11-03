public class House implements Comparable<House>{
    int x,y;

    public House(int x, int y){
        this.x=x;
        this.y=y;
    }
    @Override
    public boolean equals(Object other){
        if (!(other instanceof House)) return false;

        else return this.x==((House)other).x&&this.y==((House)other).y;
    }
    @Override
    public int compareTo(House other){
        return (this.x-other.x)*80 + (this.y-other.y);
    }
}
