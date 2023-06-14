package com.projetgrotte.algorithm;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Concretion {
    private double posX;
    private double posY;
    private double diameter;

    public Concretion(double posX, double posY, double diameter) {
        this.posX = posX;
        this.posY = posY;
        this.diameter = diameter;
    }

    public abstract void evolve(double newWeight, double newLimestone, double newDiameter);

}
