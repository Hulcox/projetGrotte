package modelisation;
import modelisation.column.Column;
import modelisation.drapery.Drapery;
import modelisation.drop.Drop;
import modelisation.fistulous.Fistulous;
import modelisation.stalactite.Stalactite;
import modelisation.stalagmite.Stalagmite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CaveSimulation {

    private static final int ROOF_Y = 100;
    private static final int GROUND_Y = 200;
    private static final int DIAMETER_MIN = 5;
    private static final int DIAMETER_MAX = 20;
    private static final double WEIGTH_MIN = 0.1;
    private static final double WEIGTH_MAX = 1.0;
    private static final double LIMESTONE_MIN = 1.0;
    private static final double LIMESTONE_MAX = 10.0;
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

    public void simulate(int nombrePasTemps) {
        Random random = new Random();

        for (int i = 0; i < nombrePasTemps; i++) {
            // Génération aléatoire de gouttes
            double posX = random.nextDouble() * 400;
            double diameter = DIAMETER_MIN + random.nextDouble() * (DIAMETER_MAX - DIAMETER_MIN);
            double weigth = WEIGTH_MIN + random.nextDouble() * (WEIGTH_MAX - WEIGTH_MIN);
            double limestone = LIMESTONE_MIN + random.nextDouble() * (LIMESTONE_MAX - LIMESTONE_MIN);

            Drop drop = new Drop(posX, ROOF_Y, diameter, weigth, limestone);
            drops.add(drop);

            // Simulation de la formation des concrétions
            for (Drop g : drops) {
                g.falling();

                if (g.getPosY() + g.getDiameter() >= GROUND_Y) {
                    drops.remove(g);

                    Fistulous fistulous = new Fistulous(g.getPosX(), GROUND_Y - g.getDiameter(), g.getDiameter());
                    fistulouses.add(fistulous);
                }
            }

            System.out.println("État de la simulation pour le pas de temps " + (i + 1) + ":");
            showConcretions();
            System.out.println("---------------------------------------------");
        }
    }

    public void showConcretions() {
        System.out.println("Gouttes:");
        for (Drop g : drops) {
            System.out.println("Goutte - Position: (" + g.getPosX() + ", " + g.getPosY() + "), Diamètre: " + g.getWeigth() + ", Calcaire: " + g.getLimestone());
        }

        System.out.println("Fistuleuses:");
        for (Fistulous f : fistulouses) {
            System.out.println("Fistuleuse - Position: (" + f.getPosX() + ", " + f.getPosY() + "), Diamètre: " + f.getDiameter());
        }

        System.out.println("Stalagmites :");
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
        }

        System.out.println("--------------------");
    }

    public static void main(String[] args) {
        CaveSimulation simulation = new CaveSimulation();
        simulation.simulate(10);
    }

}
