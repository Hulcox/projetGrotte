package com.projetgrotte.algorithm.drop;

import com.projetgrotte.algorithm.Concretion;
import com.projetgrotte.algorithm.fistulous.Fistulous;
import com.projetgrotte.algorithm.stalactite.Stalactite;
import com.projetgrotte.algorithm.stalagmite.Stalagmite;
import lombok.Getter;
import lombok.Setter;

import java.util.Iterator;
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
        double posX = Math.round(new Random().nextDouble() * SIZE_CAVE * 100.0 / 100.0);
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
        
        double posXMinCurrentDrop = this.getPosX() - this.getDiameter() / 2;
        double posXMaxCurrentDrop = this.getPosX() + this.getDiameter() / 2;
        //Verifie si la goutte est sur une fistuleuse
        for (Fistulous fistulous : fistulouses) {
            checkDropOnConcretions(posXMinCurrentDrop, posXMaxCurrentDrop, fistulous.getPosX(), fistulous.getDiameter(), fistulous.getSize());
        }
        //Verifie si la goutte est sur une stalactite
        for (Stalactite stalactite : stalactites) {
            checkDropOnConcretions(posXMinCurrentDrop, posXMaxCurrentDrop, stalactite.getPosX(), stalactite.getDiameter(), stalactite.getSize());
        }
    }

    private void checkDropOnConcretions(double posXMinCurrentDrop, double posXMaxCurrentDrop, double posX, double diameter, double size) {
        double posXMin = posX - diameter / 2;
        double posXMax = posX + diameter / 2;

        if ((posXMinCurrentDrop > posXMin && posXMinCurrentDrop < posXMax) || (posXMaxCurrentDrop > posXMin && posXMaxCurrentDrop < posXMax)) {
            //System.out.println("Goutte sur fistuleuse");
            this.setPosY(this.getPosY() - size);
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

                double posXMinCurrentDrop = this.getPosX() - this.getDiameter() / 2;
                double posXMaxCurrentDrop = this.getPosX() + this.getDiameter() / 2;

                if ((posXMinCurrentDrop >= posXMin && posXMinCurrentDrop <= posXMax) || (posXMaxCurrentDrop >= posXMin && posXMaxCurrentDrop <= posXMax) && !secondDrop.isFalling()) {
                    //System.out.println("Goutte doit evoluer");
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

    public static void fallenDropIsDestroyed(List<Drop> drops, List<Stalagmite> stalagmites) {
        Iterator<Drop> iterator = drops.iterator();
        while (iterator.hasNext()) {
            Drop drop = iterator.next();
            if (drop.isToDestroy()) {
                iterator.remove();
            } else {
                if (drop.getPosY() <= 0) {
                    Stalagmite stalagmite = new Stalagmite(drop.getPosX(), 0, drop.getDiameter(), 1);
                    stalagmites.add(stalagmite);
                    iterator.remove();
                }
            }
        }
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

    public static void tooHeavyDropsFall(List<Drop> drops, List<Fistulous> fistulouses, List<Stalactite> stalactites, List<Stalagmite> stalagmites) {
        for (Drop drop : drops) {
            if (drop.getWeight() >= 10 && !drop.isFalling()) {
                if (drop.getPosY() != 100) {
                    double posXMin = drop.getPosX() - drop.getDiameter() / 2;
                    double posXMax = drop.getPosX() + drop.getDiameter() / 2;
                    for (Fistulous fistulous : fistulouses) {
                        if (fistulous.getPosX() > posXMin && fistulous.getPosX() < posXMax) {
                            if (Math.round(drop.getPosX()) == Math.round(fistulous.getPosX()) && fistulous.isHollow()) {
                                fistulous.setHollow(false);
                            }
                            fistulous.setSize(fistulous.getSize() + 1);
                        }
                    }
                    for (Stalactite stalactite : stalactites) {
                        if (stalactite.getPosX() > posXMin && stalactite.getPosX() < posXMax) {
                            stalactite.setSize(stalactite.getSize() + 1);
                        }
                    }
                } else {
                    Fistulous fistulous = new Fistulous(drop.getPosX(), CEILING_Y, drop.getDiameter());
                    fistulouses.add(fistulous);
                }
                drop.falling();
                //System.out.println("Un goutte tombe");
            }
            if (drop.isFalling()) {
                drop.falling();
                double posXMin = drop.getPosX() - drop.getDiameter() / 2;
                double posXMax = drop.getPosX() + drop.getDiameter() / 2;
                for (Stalagmite stalagmite : stalagmites) {
                    if (stalagmite.getPosX() > posXMin && stalagmite.getPosX() < posXMax) {
                        if (drop.getPosY() <= (stalagmite.getPosY() + stalagmite.getSize())) {
                            //System.out.println("Goutte sur stalagmite");
                            stalagmite.setSize(stalagmite.getSize() + 1);
                            drop.setToDestroy(true);
                        }
                    }
                }
            }
        }
    }
}
