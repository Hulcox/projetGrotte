package com.projetgrotte.algorithm.stalactite;

import com.projetgrotte.algorithm.Concretion;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Stalactite extends Concretion {

    private double size;

    private boolean isOnDrapery = false;

    public Stalactite(double posX, double posY, double diametre, double size) {
        super(posX, posY, diametre);
        this.size = size;
    }

    public static String stalactitesToString(List<Stalactite> stalactites) {
        StringBuilder stalactitesStringified = new StringBuilder();
        final int[] index = {1};
        stalactitesStringified.append("\n\n---------- STALACTITES ----------");
        stalactites.forEach(stalactite -> {
                    stalactitesStringified.append("\nStalactite N°").append(index[0]).append("\n\tPosition : (")
                            .append(stalactite.getPosX()).append(",").append(stalactite.getPosY())
                            .append(")\n\tDiamètre : ")
                            .append(stalactite.getDiameter())
                            .append("\n\tTaille : ")
                            .append(stalactite.getSize());
                    if (stalactite.isOnDrapery()) {
                        stalactitesStringified.append("\n\tEst dans une draperie");
                    }
                    index[0]++;
                }
        );
        return stalactitesStringified.toString();
    }

    @Override
    public void evolve(double newWeight, double newLimestone, double newDiameter) {
        //TODO
    }
}
