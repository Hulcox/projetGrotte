package modelisation;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CaveSimulation {

    private List<Goutte> gouttes;
    private List<Fistuleuse> fistuleuses;
    private List<Stalactite> stalactites;
    private List<Stalagmite> stalagmites;
    private List<Colonne> colonnes;
    private List<Draperie> draperies;

    public GrotteSimulation() {
        gouttes = new ArrayList<>();
        fistuleuses = new ArrayList<>();
        stalactites = new ArrayList<>();
        stalagmites = new ArrayList<>();
        colonnes = new ArrayList<>();
        draperies = new ArrayList<>();
    }

    public void simuler(int nombrePasTemps) {
        Random random = new Random();

        for (int i = 0; i < nombrePasTemps; i++) {
            // Génération aléatoire de gouttes
            double posX = random.nextDouble() * 400;
            double diametre = DIAMETRE_MIN + random.nextDouble() * (DIAMETRE_MAX - DIAMETRE_MIN);
            double poids = POIDS_MIN + random.nextDouble() * (POIDS_MAX - POIDS_MIN);
            double calcaire = CALCAIRE_MIN + random.nextDouble() * (CALCAIRE_MAX - CALCAIRE_MIN);

            Goutte goutte = new Goutte(posX, PLAFOND_Y, diametre, poids, calcaire);
            gouttes.add(goutte);

            // Simulation de la formation des concrétions
            for (Goutte g : gouttes) {
                g.chuter();

                if (g.getY() + g.getDiametre() >= SOL_Y) {
                    gouttes.remove(g);

                    Fistuleuse fistuleuse = new Fistuleuse(g.getX(), SOL_Y - g.getDiametre(), g.getDiametre(), g.getCalcaire());
                    fistuleuses.add(fistuleuse);
                }
            }

            System.out.println("État de la simulation pour le pas de temps " + (i + 1) + ":");
            afficherConcretions();
            System.out.println("---------------------------------------------");
        }
    }

    public void afficherConcretions() {
        System.out.println("Gouttes:");
        for (Goutte g : gouttes) {
            System.out.println("Goutte - Position: (" + g.getX() + ", " + g.getY() + "), Diamètre: " + g.getDiametre() + ", Calcaire: " + g.getCalcaire());
        }

        System.out.println("Fistuleuses:");
        for (Fistuleuse f : fistuleuses) {
            System.out.println("Fistuleuse - Position: (" + f.getX() + ", " + f.getY() + "), Diamètre: " + f.getDiametre() + ", Calcaire: " + f.getCalcaire());
        }

        // Afficher les autres types de concrétions (stalactites, stalagmites, colonnes, draperies) de la même manière
    }
}
