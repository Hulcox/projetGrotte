package modelisation.fistulous;

import modelisation.ConcretionFragile;

public class Fistulous extends ConcretionFragile {
    public Fistulous(double posX, double posY, double diameter) {
        super(posX, posY, diameter);
    }

    @Override
    public void isBreaking() {
        // Logique spécifique à la casse d'une fistuleuse
    }

    @Override
    public void evolve() {
        // Logique spécifique à l'évolution d'une fistuleuse
    }
}
