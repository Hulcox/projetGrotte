package com.projetgrotte.algorithm.drop;

import com.projetgrotte.algorithm.Concretion;

public class Drop extends Concretion {
    private double weight;
    private double limestone;

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
        setPosY(getPosY() + weight); // Augmente la position Y en fonction du poids de la goutte
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
