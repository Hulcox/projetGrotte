package com.projetgrotte.algorithm;

import com.projetgrotte.algorithm.column.Column;
import com.projetgrotte.algorithm.drapery.Drapery;
import com.projetgrotte.algorithm.drop.Drop;
import com.projetgrotte.algorithm.fistulous.Fistulous;
import com.projetgrotte.algorithm.stalactite.Stalactite;
import com.projetgrotte.algorithm.stalagmite.Stalagmite;
import lombok.NoArgsConstructor;

import java.util.*;

import static com.projetgrotte.algorithm.CaveSimulation.CEILING_Y;

@NoArgsConstructor
public class Event {

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

    public static void fistulousIsBecomeStalagmite(List<Fistulous> fistulouses, List<Stalactite> stalactites, List<Stalagmite> stalagmites) {
        Iterator<Fistulous> iterator = fistulouses.iterator();
        int counter = 0;
        while (iterator.hasNext()) {
            Fistulous fistulous = iterator.next();
            counter++;
            if (fistulous.getSize() > 10) {
                if (!fistulous.isHollow()) {
                    Stalactite stalactite = new Stalactite(fistulous.getPosX(), fistulous.getPosY(), fistulous.getDiameter(), fistulous.getSize());
                    stalactites.add(stalactite);
                    iterator.remove();
                } else {
                    System.out.println("\nFistuleuse " + counter + " détruite\n");
                    iterator.remove();
                }
            }
        }
    }

    public static void shouldColumnsBeCreated(List<Stalactite> stalactites, List<Stalagmite> stalagmites, List<Column> columns) {
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

    public static List<Drapery> shouldDraperyBeCreated(List<Stalactite> stalactites) {
        List<Drapery> draperies = new ArrayList<>();
        for (Stalactite stalactite : stalactites) {
            for (int i = 0; i < stalactites.size(); i++) {
                if (stalactite.hashCode() != stalactites.get(i).hashCode()) {
                    if (!stalactites.get(i).isOnDrapery()) {
                        boolean isTwoStalactitesAreTouching = isTwoStalactitesAreTouching(stalactite, stalactites.get(i));
                        if (isTwoStalactitesAreTouching) {
                            stalactite.setOnDrapery(true);
                            stalactites.get(i).setOnDrapery(true);
                            draperies.add(new Drapery(List.of(stalactite, stalactites.get(i))));
                        }
                    }
                }
            }
        }
        return draperies;
    }

    private static boolean isTwoStalactitesAreTouching(Stalactite stalactite1, Stalactite stalactite2) {
        double[] stalactiteFirstPosition = getPositionStalactite(stalactite1);
        double[] stalactiteSecondPosition = getPositionStalactite(stalactite2);
        if (stalactiteFirstPosition[0] == stalactiteSecondPosition[1]
                || stalactiteFirstPosition[1] == stalactiteSecondPosition[0]) {
            return true;
        }
        return false;
    }

    private static double[] getPositionStalactite(Stalactite stalactite) {
        double positionMin = stalactite.getPosX() - stalactite.getDiameter() / 2;
        double positionMax = stalactite.getPosX() + stalactite.getDiameter() / 2;
        return new double[]{positionMin, positionMax};
    }


    public static boolean checkCollision(Stalactite stalactite, Stalagmite stalagmite) {
        double stalactitePosXMin = stalactite.getPosY() - stalactite.getDiameter() / 2;
        double stalactitePosXMax = stalactite.getPosY() + stalactite.getDiameter() / 2;
        double stalagmitePosXMin = stalagmite.getPosY() - stalagmite.getSize() / 2;
        double stalagmitePosXMax = stalagmite.getPosY() + stalagmite.getSize() / 2;
        if (stalagmitePosXMin >= stalactitePosXMin && stalagmitePosXMin <= stalactitePosXMax || stalagmitePosXMax >= stalactitePosXMin && stalagmitePosXMax <= stalactitePosXMax) {
            if (stalactite.getSize() + stalagmite.getSize() >= CEILING_Y) {
                return true;
            }
        }
        return false;
    }
}
