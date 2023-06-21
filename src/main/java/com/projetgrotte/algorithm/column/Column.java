package com.projetgrotte.algorithm.column;

import com.projetgrotte.algorithm.stalactite.Stalactite;
import com.projetgrotte.algorithm.stalagmite.Stalagmite;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static com.projetgrotte.algorithm.CaveSimulation.CEILING_Y;

@Getter
@Setter
@AllArgsConstructor
public class Column {

    private Stalactite stalactite;
    private Stalagmite stalagmite;

    public static Optional<Column> shouldColumnsBeCreated(List<Stalactite> stalactites, List<Stalagmite> stalagmites) {
        Iterator<Stalactite> stalactiteIterator = stalactites.iterator();
        while (stalactiteIterator.hasNext()) {
            Stalactite stalactite = stalactiteIterator.next();
            Iterator<Stalagmite> stalagmiteIterator = stalagmites.iterator();
            while (stalagmiteIterator.hasNext()) {
                Stalagmite stalagmite = stalagmiteIterator.next();
                if (isCollisionHappenedBetweenStalactitesAndStalagmites(stalactite, stalagmite)) {
                    stalactiteIterator.remove();
                    stalagmiteIterator.remove();
                    return Optional.of(new Column(stalactite, stalagmite));
                }
            }
        }
        return Optional.empty();
    }

    public static boolean isCollisionHappenedBetweenStalactitesAndStalagmites(Stalactite stalactite, Stalagmite stalagmite) {
        double stalactitePosXMin = stalactite.getPosY() - stalactite.getDiameter() / 2;
        double stalactitePosXMax = stalactite.getPosY() + stalactite.getDiameter() / 2;
        double stalagmitePosXMin = stalagmite.getPosY() - stalagmite.getSize() / 2;
        double stalagmitePosXMax = stalagmite.getPosY() + stalagmite.getSize() / 2;
        if (stalagmitePosXMin >= stalactitePosXMin && stalagmitePosXMin <= stalactitePosXMax || stalagmitePosXMax >= stalactitePosXMin && stalagmitePosXMax <= stalactitePosXMax) {
            return stalactite.getSize() + stalagmite.getSize() >= CEILING_Y;
        }
        return false;
    }
}
