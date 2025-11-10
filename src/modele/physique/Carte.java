package modele.physique;
import java.util.Random;

public class Carte {
    private static final Position  TAILLE_MAP = new Position(1920,1080);
    private static final Random generateur = new Random();

    //Méthode pour retourner une position aléatoire

    public static Position positionAleatoire(){
        double xAleatoire = generateur.nextDouble() * TAILLE_MAP.getPositionX();
        double yAleatoire = generateur.nextDouble() * TAILLE_MAP.getPositionY();
        return new Position(xAleatoire,yAleatoire);
    }

    //Retourner une nouvelle position si la précédente sort de la carte

    public static Position ajusterToroidal(Position autre){
        double x = autre.getPositionX();
        double y = autre.getPositionY();

        //Si x sort de la carte, on le remet dedans
        x = x % TAILLE_MAP.getPositionX();
        if (x < 0) {x = x+ TAILLE_MAP.getPositionX();}

        //Si y sort de la carte, on le remet dedans
        y = y % TAILLE_MAP.getPositionY();
        if (y < 0){y = y+ TAILLE_MAP.getPositionY();}

        return new Position(x,y);
    }
}