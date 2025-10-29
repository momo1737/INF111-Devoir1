package modele.physique;
import java.util.Random;
public class Carte {

    //la carte est toroïdale (wrap X et Y)
    private static final Position  TAILLE_MAP = new Position(1920,1080);
    private static final Random generateur = new Random();

    //méthode pour retourner une position aléatoire

    public static Position positionAleatoire(){
        double xAleatoire = generateur.nextDouble() * TAILLE_MAP.getPositionX();
        double yAleatoire = generateur.nextDouble() * TAILLE_MAP.getPositionY();
        return new Position(xAleatoire,yAleatoire);
    }

    //retourner une nouvelle position si la précedente sort de la map

    public static Position ajusterToroidal(Position autre){
        double x = autre.getPositionX();
        double y = autre.getPositionY();

        x = x % TAILLE_MAP.getPositionX();
        if (x<0) {x = x+ TAILLE_MAP.getPositionX();}

        y = y % TAILLE_MAP.getPositionY();
        if (y<0){y = y+ TAILLE_MAP.getPositionY();}

        return new Position(x,y);
    }
}
