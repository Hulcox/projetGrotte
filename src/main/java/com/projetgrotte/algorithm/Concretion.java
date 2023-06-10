package com.projetgrotte.algorithm;

public abstract class Concretion {
    private double posX;
    private double posY;

    private double diameter;

    public Concretion(double posX, double posY, double diameter) {
        this.posX = posX;
        this.posY = posY;
        this.diameter = diameter;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double y) {
        this.posY = y;
    }

    public double getDiameter() {
        return diameter;
    }
    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }
    public abstract void evolve(double newWeight, double newLimestone, double newDiameter);
}