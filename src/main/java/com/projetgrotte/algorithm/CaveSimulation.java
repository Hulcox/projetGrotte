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
                double posY = ROOF_Y;

                //Check if a concretion are in the drop's position

                for(Fistulous fistulous : fistulouses) {
                    double posXMin = fistulous.getPosX() - fistulous.getDiameter() / 2;
                    double posXMax = fistulous.getPosX() + fistulous.getDiameter() / 2;

                    if (posX > posXMin && posX < posXMax) {
                        System.out.println("Goutte sur fistuleuse");

                        posY -= fistulous.getSize();
                    }
                }

                for(Stalactite stalactite : stalactites) {
                    double posXMin = stalactite.getPosX() - stalactite.getDiameter() / 2;
                    double posXMax = stalactite.getPosX() + stalactite.getDiameter() / 2;

                    if (posX > posXMin && posX < posXMax) {
                        System.out.println("Goutte sur Stalactite");

                        posY -= stalactite.getSize();
                    }
                }

                Drop drop = new Drop(posX, posY, DIAMETER, WEIGTH, LIMESTONE_CHARGE);

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

                    if(drop.getPosY() != 100){
                        double posXMin = drop.getPosX() - drop.getDiameter() / 2;
                        double posXMax = drop.getPosX() + drop.getDiameter() / 2;

                        for(Fistulous fistulous : fistulouses){
                            if(fistulous.getPosX() > posXMin && fistulous.getPosX() < posXMax) {
                                if(drop.getPosX() == fistulous.getPosX() && fistulous.isHollow()){
                                    fistulous.setHollow(false);
                                }
                                    fistulous.setSize(fistulous.getSize() + 1);
                            }
                        }

                        for(Stalactite stalactite : stalactites){
                            if(stalactite.getPosX() > posXMin && stalactite.getPosX() < posXMax) {
                                stalactite.setSize(stalactite.getSize() + 1);
                            }
                        }
                    } else {
                        Fistulous fistulous = new Fistulous(drop.getPosX(), ROOF_Y, drop.getDiameter());
                        fistulouses.add(fistulous);
                    }

                    drop.falling();
                    System.out.println("Un goutte tombe");
                }

                if(drop.getIsFalling()){
                    drop.falling();

                    double posXMin = drop.getPosX() - drop.getDiameter() / 2;
                    double posXMax = drop.getPosX() + drop.getDiameter() / 2;

                    for(Stalagmite stalagmite : stalagmites){
                        if(stalagmite.getPosX() > posXMin && stalagmite.getPosX() < posXMax) {
                            if(drop.getPosY() <= (stalagmite.getPosY() + stalagmite.getSize()))
                            {
                                System.out.println("Goutte sur stalagmite");
                                stalagmite.setSize(stalagmite.getSize() + 1);
                                drop.setToDestroy(true);
                            }

                        }
                    }
                }
            }

            //check to distroy drop :
            checkDrops();
            //check if fistulous become a stalagmite :
            checkFistulouses();
            //check for create Column
            checkForCollones();
            //check for create Drapery

            showConcretions();
            System.out.println("---");
        }
    };

    public void checkDrops () {
        Iterator<Drop> iterator = drops.iterator();
        while (iterator.hasNext()) {
            Drop drop = iterator.next();
            if(drop.isToDestroy()){
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

    public void checkFistulouses() {
        Iterator<Fistulous> iterator = fistulouses.iterator();
        while (iterator.hasNext()) {
            Fistulous fistulous = iterator.next();
            if (fistulous.getSize() > 10) {
                Stalactite stalactite = new Stalactite(fistulous.getPosX(), fistulous.getPosY(), fistulous.getDiameter(), fistulous.getSize());
                stalactites.add(stalactite);
                iterator.remove();
            }
        }
    }

    public void checkForCollones() {
        Iterator<Stalactite> stalactiteIterator = stalactites.iterator();
        while (stalactiteIterator.hasNext()) {
            Stalactite stalactite = stalactiteIterator.next();
            Iterator<Stalagmite> stalagmiteIterator = stalagmites.iterator();
            while (stalagmiteIterator.hasNext()) {
                Stalagmite stalagmite = stalagmiteIterator.next();
                if (checkCollision(stalactite, stalagmite)) {
                    Column column = new Column(stalactite, stalagmite);
                    columns.add(column);
                    stalactiteIterator.remove();
                    stalagmiteIterator.remove();
                }
            }
        }
    }

    public boolean checkCollision(Stalactite stalactite, Stalagmite stalagmite) {

        double stalactitePosXMin = stalactite.getPosY() - stalactite.getDiameter() / 2;
        double stalactitePosXMax = stalactite.getPosY() + stalactite.getDiameter() / 2;
        double stalagmitePosXMin = stalagmite.getPosY() - stalagmite.getSize() / 2;
        double stalagmitePosXMax = stalagmite.getPosY() + stalagmite.getSize() / 2;

        if(stalagmitePosXMin >= stalactitePosXMin && stalagmitePosXMin <= stalactitePosXMax || stalagmitePosXMax >= stalactitePosXMin && stalagmitePosXMax <= stalactitePosXMax  ) {
            if(stalactite.getSize()+stalagmite.getSize() >= ROOF_Y){
                return true;
            }
        }
        return false;
    }

    public void checkForDrapery(double proximityThreshold){
        for (int i = 0; i < stalactites.size() - 1; i++) {
            Stalactite stalactite1 = stalactites.get(i);
            Stalactite stalactite2 = stalactites.get(i + 1);
            if (Math.abs(stalactite1.getPosX() - stalactite2.getPosX()) <= proximityThreshold) {
                System.out.println("TODO - Draperie formation detected");
                // Autres actions à effectuer en cas de formation de draperie...
            }
        }
    }


    public void showConcretions() {
        System.out.println("Gouttes:");
        for (Drop drop : drops) {
            System.out.println("Goutte - Position: (" + drop.getPosX() + ", " + drop.getPosY() + "), Poids: " + drop.getWeigth() + " , Diamètre: " + drop.getDiameter() + ", Calcaire: " + drop.getLimestone());
        }

        System.out.println("Fistuleuses:");
        for (Fistulous fistulous : fistulouses) {
            System.out.println("Fistuleuse - Position: (" + fistulous.getPosX() + ", " + fistulous.getPosY() + "), Diamètre: " + fistulous.getDiameter() + ", Taille: "+ fistulous.getSize());
        }

        System.out.println("Stalagmite:");
        for (Stalagmite stalagmtite : stalagmites) {
            System.out.println("Stalagmite - Position: (" + stalagmtite.getPosX() + ", " + stalagmtite.getPosY() + "), Diamètre: " + stalagmtite.getDiameter() + ", Taille: "+ stalagmtite.getSize());
        }

        System.out.println("Stalactite:");
        for (Stalactite stalactite : stalactites) {
            System.out.println("Stalactite - Position: (" + stalactite.getPosX() + ", " + stalactite.getPosY() + "), Diamètre: " + stalactite.getDiameter() + ", Taille: "+ stalactite.getSize());
        }

        /*
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

        /*
            Simulation simple de la formation de concrétion dans une grotte.
            la simulation se basse sur l'apparition de goutte d'eau ainsi que le chute.
            Pour simplifier le processus quand une goutte d'eau est trop lourde elle tombe est génère directement une fistuleuse.
            Si une goutte se retroube a centre de la fistuleuse alors la fistuleuse continue de grandire (en longeur mais pas en épaisseur pour la simulation non graphique)
            Quand la fistuleuse est bouché a partir d'une certaine taille définie elle devient une stalactite.
            Le meme procéssus sans les fistuleuse est appliqué au stalagmite.
            Si une stalagmite et une stalactite se touche alors on crée une collone.
        */

        CaveSimulation simulation = new CaveSimulation();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(simulation.task, 0, 300);
    }

}
