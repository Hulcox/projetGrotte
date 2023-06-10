import modelisation.Column;
import modelisation.Drapery;
import modelisation.Stalactite;
import modelisation.Stalagmite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CaveSimulation {
    private List<Goutte> gouttes;
    private List<Fistuleuse> fistuleuses;
    private List<Stalactite> stalactites;
    private List<Stalagmite> stalagmites;
    private List<Column> colonnes;
    private List<Drapery> draperies;

    public CaveSimulation() {
        gouttes = new ArrayList<>();
        fistuleuses = new ArrayList<>();
        stalactites = new ArrayList<>();
        stalagmites = new ArrayList<>();
        colonnes = new ArrayList<>();
        draperies = new ArrayList<>();
    }

    public void simule() {
        Random random = new Random();
        int nombreGouttes = 10;

        // Génération aléatoire des gouttes
        for (int i = 0; i < nombreGouttes; i++) {
            double posX = random.nextDouble() * 400;
            double diametre = DIAMETRE_MIN + random.nextDouble() * (DIAMETRE_MAX - DIAMETRE_MIN);
            double poids = POIDS_MIN + random.nextDouble() * (POIDS_MAX - POIDS_MIN);
            double calcaire = CALCAIRE_MIN + random.nextDouble() * (CALCAIRE_MAX - CALCAIRE_MIN);

            Goutte goutte = new Goutte(posX, PLAFOND_Y, diametre, poids, calcaire);
            gouttes.add(goutte);
        }

        int nombrePasDeTemps = 100;

        // Simulation pour chaque pas de temps
        for (int t = 0; t < nombrePasDeTemps; t++) {
            System.out.println("Pas de temps : " + t);

            // Simulation des gouttes
            for (Goutte goutte : gouttes) {
                goutte.chuter();

                // Vérification de la formation d'une fistuleuse
                if (goutte.getY() + goutte.getDiametre() >= SOL_Y) {
                    fistuleuses.add(new Fistuleuse(goutte.getX(), SOL_Y - goutte.getDiametre(), goutte.getDiametre(), goutte.getCalcaire()));
                }
            }

            // Affichage de l'état des concrétions
            afficherConcretions();
        }
    }

    private void afficherConcretions() {
        System.out.println("Liste des concrétions :");

        System.out.println("Fistuleuses :");
        for (Fistuleuse fistuleuse : fistuleuses) {
            System.out.println(fistuleuse);
        }

        System.out.println("Stalactites :");
        for (Stalactite stalactite : stalactites) {
            System.out.println(stalactite);
        }

        System.out.println("Stalagmites :");
        for (Stalagmite stalagmite : stalagmites) {
            System.out.println(stalagmite);
        }

        System.out.println("Columns :");
        for (Column colonne : colonnes) {
            System.out.println(colonne);
        }

        System.out.println("Draperies :");
        for (Drapery draperie : draperies) {
            System.out.println(draperie);
        }

        System.out.println("--------------------");
    }

    public static void main(String[] args) {
        CaveSimulation simulation = new CaveSimulation();
        simulation.simule();
    }
}
