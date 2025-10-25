package modele.physique;

public class Carte {

    // Dimensions fixes de la carte
    public static final double LARGEUR = 1920.0;
    public static final double HAUTEUR = 1080.0;

    // --- MÉTHODES ---

    /**
     * Donne une position aléatoire dans la carte.
     * On utilise Math.random() (simple et suffisant pour ce TP).
     */
    public static Position positionAleatoire() {
        double x = Math.random() * LARGEUR; // entre 0 inclus et LARGEUR exclu
        double y = Math.random() * HAUTEUR; // entre 0 inclus et HAUTEUR exclu
        return new Position(x, y);
    }

    /**
     * Ajuste la position p pour qu'elle reste dans la carte (effet "tore"):
     * - si on sort à gauche, on réapparaît à droite
     * - si on sort en haut, on réapparaît en bas, etc.
     * Version simple avec des boucles while et des if.
     */
    public static void ajuster(Position p) {
        double x = p.getPositionX();
        double y = p.getPositionY();

        // Ramener X dans [0, LARGEUR)
        while (x < 0) {
            x += LARGEUR;
        }
        while (x >= LARGEUR) {
            x -= LARGEUR;
        }

        // Ramener Y dans [0, HAUTEUR)
        while (y < 0) {
            y += HAUTEUR;
        }
        while (y >= HAUTEUR) {
            y -= HAUTEUR;
        }

        p.setPositionX(x);
        p.setPositionY(y);
    }
}
