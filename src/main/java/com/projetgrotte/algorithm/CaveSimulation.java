package com.projetgrotte.algorithm;

import com.projetgrotte.algorithm.column.Column;
import com.projetgrotte.algorithm.drapery.Drapery;
import com.projetgrotte.algorithm.drop.Drop;
import com.projetgrotte.algorithm.fistulous.Fistulous;
import com.projetgrotte.algorithm.stalactite.Stalactite;
import com.projetgrotte.algorithm.stalagmite.Stalagmite;

import java.util.*;

public class CaveSimulation {

    private static final int SIZE_CAVE = 100;
    private static final int ROOF_Y = 100;
    private static final int GROUND_Y = 200;
    private static final int DIAMETER = 2;
    private static final double WEIGTH = 4;
    private static final double LIMESTONE_CHARGE = 10.0;
    private List<Drop> drops;
    private List<Fistulous> fistulouses;
    private List<Stalactite> stalactites;
    private List<Stalagmite> stalagmites;
    private List<Column> columns;
    private List<Drapery> draperys;

    public CaveSimulation() {
        drops = new ArrayList<>();
        fistulouses = new ArrayList<>();
        stalactites = new ArrayList<>();
        stalagmites = new ArrayList<>();
        columns = new ArrayList<>();
        draperys = new ArrayList<>();
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Random random = new Random();
            if (random.nextBoolean()) {
                double posX = Math.round(random.nextDouble() * SIZE_CAVE * 100.0) / 100.0;
                Drop drop = new Drop(posX, ROOF_Y, DIAMETER, WEIGTH, LIMESTONE_CHARGE);

                //if a drop already exist the two drop fuse to become an evolved drop
                if (drops.isEmpty()) {
                    drops.add(drop);
                } else {
                    boolean matchFound = false;
                    for (Drop secondDrop : drops) {
                        double posXMin = secondDrop.getPosX() - secondDrop.getDiameter() / 2;
                        double posXMax = secondDrop.getPosX() + secondDrop.getDiameter() / 2;

                        if (posX > posXMin && posX < posXMax && !secondDrop.getIsFalling()) {
                            System.out.println("Goutte doit evoluer");
                            secondDrop.evolve(WEIGTH, LIMESTONE_CHARGE, DIAMETER);
                            matchFound = true;
                        }
                    }
                    if (!matchFound) {
                        drops.add(drop);
                    }
                }
            }

            for(Drop drop: drops){
                if(drop.getWeigth() >= 10 && !drop.getIsFalling()) {
                    drop.falling();
                    System.out.println("Un goutte tombe");

                    Fistulous fistulous = new Fistulous(drop.getPosX(), drop.getPosY(), drop.getDiameter());
                    fistulouses.add(fistulous);
                }
            }

            showConcretions();
            System.out.println("---");
        }
    };


    public void showConcretions() {
        System.out.println("Gouttes:");
        for (Drop drop : drops) {
            System.out.println("Goutte - Position: (" + drop.getPosX() + ", " + drop.getPosY() + "), Poids: " + drop.getWeigth() + " , Diamètre: " + drop.getDiameter() + ", Calcaire: " + drop.getLimestone());
        }

        System.out.println("Fistuleuses:");
        for (Fistulous fistulous : fistulouses) {
            System.out.println("Fistuleuse - Position: (" + fistulous.getPosX() + ", " + fistulous.getPosY() + "), Diamètre: " + fistulous.getDiameter() + ", Taille: "+ fistulous.getSize());
        }

        /*System.out.println("Stalagmites :");
        for (Stalagmite stalagmite : stalagmites) {
            System.out.println(stalagmite);
        }

        System.out.println("Colonnes :");
        for (Column column : columns) {
            System.out.println(column);
        }

        System.out.println("Draperies :");
        for (Drapery drapery : draperys) {
            System.out.println(drapery);
        }*/

        System.out.println("--------------------");
    }

    public static void main(String[] args) {
        CaveSimulation simulation = new CaveSimulation();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(simulation.task, 0, 300);
    }

}
