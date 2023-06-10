package modelisation;

public class Fistulous extends ConcretionFragile {
    public Fistulous(double posX, double posY, double diameter) {
        super(posX, posY, diameter);
    }

    @Override
    public void toBrake() {
        // Logique spécifique à la casse d'une fistuleuse
    }

    @Override
    public void evolve() {
        // Logique spécifique à l'évolution d'une fistuleuse
    }
}
