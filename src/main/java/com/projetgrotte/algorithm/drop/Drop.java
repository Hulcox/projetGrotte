package com.projetgrotte.algorithm.drop;

import com.projetgrotte.algorithm.Concretion;

public class Drop extends Concretion {
    private double weight;
    private double limestone;
    private boolean isFalling = false;

    private boolean toDestroy = false;

    public Drop(double posX, double posY, double diameter, double weight, double limestone) {
        super(posX, posY, diameter);
        this.weight = weight;
        this.limestone = limestone;
    }

    public double getWeigth() {
        return weight;
    }

    public double getLimestone() {
        return limestone;
    }

    public void falling() {
        double gravity = 0.98;

        double posY = getPosY() - (weight * gravity);
        setPosY(posY);

        isFalling = true;
    }
    public boolean getIsFalling() {
        return isFalling;
    }

    public boolean isToDestroy() {
        return toDestroy;
    }

    public void setToDestroy(boolean toDestroy) {
        this.toDestroy = toDestroy;
    }

    @Override
    public void evolve(double newWeight, double newLimestone, double newDiameter) {
        this.weight += newWeight;
        this.limestone += newLimestone;
        super.setDiameter(getDiameter()+newDiameter);
    }

    /*public void fuse(Drop otherDrop) {
        double newSize = this.size + otherDrop.getSize();
        double newLimeStone = this.limestone + otherDrop.getLimestone();

        this.size = newSize;
        this.limestone = newLimeStone;
    }*/
}
