package com.projetgrotte.algorithm.stalagmite;

import com.projetgrotte.algorithm.Concretion;

public class Stalagmite extends Concretion {

    private double size;
    public Stalagmite(double posX, double posY, double diametre, double size) {
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
