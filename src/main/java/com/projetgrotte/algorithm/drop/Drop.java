package com.projetgrotte.algorithm.drop;

import com.projetgrotte.algorithm.Concretion;
import com.projetgrotte.algorithm.fistulous.Fistulous;
import com.projetgrotte.algorithm.stalactite.Stalactite;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;

import static com.projetgrotte.algorithm.CaveSimulation.CEILING_Y;
import static com.projetgrotte.algorithm.CaveSimulation.SIZE_CAVE;

@Getter
@Setter
public class Drop extends Concretion {

    private static final int DIAMETER = 2;
    private static final double WEIGTH = 4;
    private static final double LIMESTONE_CHARGE = 10.0;
    private double weight;
    private double limestone;
    private boolean isFalling = false;
    private boolean toDestroy = false;

    public static Drop dropBuilder() {
        double posX = Math.round(new Random().nextDouble() * SIZE_CAVE * 100.0) / 100.0;
        return new Drop(posX, CEILING_Y);
    }

    public Drop(double posX, double posY) {
        super(posX, posY, DIAMETER);
        this.weight = WEIGTH;
        this.limestone = LIMESTONE_CHARGE;
    }

    @Override
    public void evolve(double newWeight, double newLimestone, double newDiameter) {
        this.weight += newWeight;
        this.limestone += newLimestone;
        super.setDiameter(getDiameter() + newDiameter);
    }

    public void isDropOnConcretion(List<Fistulous> fistulouses, List<Stalactite> stalactites) {
        //Verifie si la goutte est sur une fistuleuse
        for (Fistulous fistulous : fistulouses) {
            double posXMin = fistulous.getPosX() - fistulous.getDiameter() / 2;
            double posXMax = fistulous.getPosX() + fistulous.getDiameter() / 2;
            if (this.getPosX() > posXMin && this.getPosX() < posXMax) {
                System.out.println("Goutte sur fistuleuse");
                this.setPosY(this.getPosY() - fistulous.getSize());
            }
        }
        //Verifie si la goutte est sur une fistuleuse
        for (Stalactite stalactite : stalactites) {
            double posXMin = stalactite.getPosX() - stalactite.getDiameter() / 2;
            double posXMax = stalactite.getPosX() + stalactite.getDiameter() / 2;
            if (this.getPosY() > posXMin && this.getPosX() < posXMax) {
                System.out.println("Goutte sur Stalactite");
                this.setPosY(this.getPosY() - stalactite.getSize());
            }
        }
    }

    public void isDropOnAnotherDrop(List<Drop> drops) {
        if (drops.isEmpty()) {
            drops.add(this);
        } else {
            boolean matchFound = false;
            for (Drop secondDrop : drops) {
                double posXMin = secondDrop.getPosX() - secondDrop.getDiameter() / 2;
                double posXMax = secondDrop.getPosX() + secondDrop.getDiameter() / 2;

                if (this.getPosX() > posXMin && this.getPosX() < posXMax && !secondDrop.isFalling()) {
                    System.out.println("Goutte doit evoluer");
                    secondDrop.evolve(WEIGTH, LIMESTONE_CHARGE, DIAMETER);
                    matchFound = true;
                }
            }
            if (!matchFound) {
                drops.add(this);
            }
        }
    }

    public void falling() {
        double gravity = 0.98;
        double posY = getPosY() - (weight * gravity);
        setPosY(posY);
        isFalling = true;
    }

    //TODO
    /*public void fuse(Drop otherDrop) {
        double newSize = this.size + otherDrop.getSize();
        double newLimeStone = this.limestone + otherDrop.getLimestone();

        this.size = newSize;
        this.limestone = newLimeStone;
    }*/

    public static String dropsToString(List<Drop> drops) {
        StringBuilder dropsStringified = new StringBuilder();
        final int[] index = {1};
        dropsStringified.append("\n\n---------- GOUTTES ---------- ");
        drops.forEach(drop -> {
            dropsStringified.append("\nGoutte N°").append(index[0])
                    .append("\n\tPosition : (")
                    .append(drop.getPosX()).append(",").append(drop.getPosY())
                    .append(")\n\tPoids : ")
                    .append(drop.getWeight())
                    .append("\n\tDiamètre : ")
                    .append(drop.getDiameter())
                    .append("\n\tCalcaire : ")
                    .append(drop.getLimestone());
            index[0]++;
        });
        return dropsStringified.toString();
    }
}
