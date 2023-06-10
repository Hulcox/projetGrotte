package modelisation;

public abstract class ConcretionFragile extends Concretion {
    private boolean isBreak;

    public ConcretionFragile(double posX, double posY, double diameter) {
        super(posX, posY, diameter);
        this.isBreak = false;
    }

    public boolean isBreaked() {
        return isBreak;
    }

    public void setIsBreake(boolean isBreak) {
        this.isBreak = isBreak;
    }

    public abstract void isBreaking();
}
