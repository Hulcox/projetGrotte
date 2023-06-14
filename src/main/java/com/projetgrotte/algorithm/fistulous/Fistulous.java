package com.projetgrotte.algorithm.fistulous;

import com.projetgrotte.algorithm.ConcretionFragile;

public class Fistulous extends ConcretionFragile {
    private double size;

    private boolean isHollow = true;
    public Fistulous(double posX, double posY, double diameter) {
        super(posX, posY, diameter);
        this.size = 1;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public boolean isHollow() {
        return isHollow;
    }

    public void setHollow(boolean hollow) {
        isHollow = hollow;
    }

    @Override
    public void isBreaking() {
        // Logique spécifique à la casse d'une fistuleuse
    }

    @Override
    public void evolve(double newWeight, double newLimestone, double newDiameter) {

    }
}
