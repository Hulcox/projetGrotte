package com.projetgrotte.algorithm.stalactite;

import com.projetgrotte.algorithm.Concretion;

public class Stalactite extends Concretion {

    private double size;
    public Stalactite(double posX, double posY, double diametre, double size) {
        super(posX, posY, diametre);
        this.size = size;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public void evolve(double newWeight, double newLimestone, double newDiameter) {

    }
}
