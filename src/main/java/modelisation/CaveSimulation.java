package modelisation;
import modelisation.column.Column;
import modelisation.drapery.Drapery;
import modelisation.drop.Drop;
import modelisation.fistulous.Fistulous;
import modelisation.stalactite.Stalactite;
import modelisation.stalagmite.Stalagmite;

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
                Drop drop = new Drop(posX, ROOF_Y, DIAMETER, WEIGTH, LIMESTONE_CHARGE);
                drops.add(drop);
                System.out.println("Goutte créée");
            }
            //System.out.println("État de la simulation pour le pas de temps " + (i + 1) + ":");
            //showConcretions();
            System.out.println("---");
        }
    };


    public void showConcretions() {
        System.out.println("Gouttes:");
        for (Drop g : drops) {
            System.out.println("Goutte - Position: (" + g.getPosX() + ", " + g.getPosY() + "), Poids: " + g.getWeigth() + " , Diamètre: " + g.getDiameter() + ", Calcaire: " + g.getLimestone());
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
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(simulation.task, 0, 300);
    }

}
