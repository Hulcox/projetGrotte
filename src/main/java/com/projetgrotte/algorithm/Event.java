package com.projetgrotte.algorithm;

import com.projetgrotte.algorithm.column.Column;
import com.projetgrotte.algorithm.drop.Drop;
import com.projetgrotte.algorithm.fistulous.Fistulous;
import com.projetgrotte.algorithm.stalactite.Stalactite;
import com.projetgrotte.algorithm.stalagmite.Stalagmite;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.List;

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
                            if (drop.getPosX() == fistulous.getPosX() && fistulous.isHollow()) {
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
                System.out.println("Un goutte tombe");
            }
            if (drop.isFalling()) {
                drop.falling();
                double posXMin = drop.getPosX() - drop.getDiameter() / 2;
                double posXMax = drop.getPosX() + drop.getDiameter() / 2;
                for (Stalagmite stalagmite : stalagmites) {
                    if (stalagmite.getPosX() > posXMin && stalagmite.getPosX() < posXMax) {
                        if (drop.getPosY() <= (stalagmite.getPosY() + stalagmite.getSize())) {
                            System.out.println("Goutte sur stalagmite");
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
        while (iterator.hasNext()) {
            Fistulous fistulous = iterator.next();
            if (fistulous.getSize() > 10) {
                Stalactite stalactite = new Stalactite(fistulous.getPosX(), fistulous.getPosY(), fistulous.getDiameter(), fistulous.getSize());
                stalactites.add(stalactite);
                iterator.remove();
                //TODO
                //CASSER  LA FISTULEUSE si elle est creuse et > a 10wgt
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
