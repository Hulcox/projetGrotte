package com.projetgrotte.algorithm.stalagmite;

import com.projetgrotte.algorithm.Concretion;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Stalagmite extends Concretion {

    private double size;

    public Stalagmite(double posX, double posY, double diametre, double size) {
        super(posX, posY, diametre);
        this.size = size;
    }

    public static String stalagmitesToString(List<Stalagmite> stalagmites) {
        StringBuilder stalagmitesStringified = new StringBuilder();
        final int[] index = {1};
        stalagmitesStringified.append("\n\n---------- STALAGMITES ----------");
        stalagmites.forEach(stalagmite -> {
                    stalagmitesStringified.append("\nStalagmite N°").append(index[0])
                            .append("\n\tPosition : (")
                            .append(stalagmite.getPosX()).append(",").append(stalagmite.getPosY())
                            .append(")\n\tDiamètre : ")
                            .append(stalagmite.getDiameter())
                            .append("\n\tTaille : ")
                            .append(stalagmite.getSize());
                    index[0]++;
                }
        );
        return stalagmitesStringified.toString();
    }

    @Override
    public void evolve(double newWeight, double newLimestone, double newDiameter) {
        //TODO
    }
}
