package com.projetgrotte.algorithm.fistulous;

import com.projetgrotte.algorithm.ConcretionFragile;
import com.projetgrotte.algorithm.drop.Drop;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Fistulous extends ConcretionFragile {
    private double size;

    private boolean isHollow = true;

    public Fistulous(double posX, double posY, double diameter) {
        super(posX, posY, diameter);
        this.size = 1;
    }

    public boolean isHollow() {
        return isHollow;
    }

    public void setHollow(boolean hollow) {
        isHollow = hollow;
    }

    @Override
    public void isBreaking() {
        // Logique spécifique à la casse d'une fistuleuse
    }

    @Override
    public void evolve(double newWeight, double newLimestone, double newDiameter) {
        //TODO
    }

    public static String fistulousesToString(List<Fistulous> fistulouses) {
        StringBuilder fistulousesStringified = new StringBuilder();
        final int[] index = {1};
        fistulousesStringified.append("\n\n---------- FISTULEUSES ----------");
        fistulouses.forEach(fistulous -> {
                    fistulousesStringified.append("\nFistuleuse N°").append(index[0])
                            .append("\n\tPosition : (")
                            .append(fistulous.getPosX()).append(",").append(fistulous.getPosY())
                            .append(")\n\tDiamètre : ")
                            .append(fistulous.getDiameter())
                            .append("\n\tTaille : ")
                            .append(fistulous.getSize());
                    index[0]++;
                }
        );
        return fistulousesStringified.toString();
    }
}
