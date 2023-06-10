package modelisation;

public class Drop {
    private double size;
    private double limestone;

    public Drop(double size, double limestone) {
        this.size = size;
        this.limestone = limestone;
    }

    public double getSize() {
        return size;
    }

    public double getLimestone() {
        return limestone;
    }

    public void fuse(Drop otherDrop) {
        double newSize = this.size + otherDrop.getSize();
        double newLimeStone = this.limestone + otherDrop.getLimestone();

        this.size = newSize;
        this.limestone = newLimeStone;
    }
}
