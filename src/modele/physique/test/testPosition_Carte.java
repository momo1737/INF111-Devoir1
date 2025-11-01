package modele.physique.test;
import modele.physique.Carte;
import modele.physique.Position;
public class testPosition_Carte {
        public static void main(String[] args) {
            // Test Position.distance
            Position a = new Position(0, 0);
            Position b = new Position(3, 4);
            System.out.println("distance attendue 5 -> " + a.distance(b));

            // Test positionAleatoire + ajustement toroïdal
            Position p = Carte.positionAleatoire();
            System.out.println("Init : " + p);

            // On le pousse hors carte
            p.setPositionX(-10);
            p.setPositionY(2000);
            Carte.ajusterToroidal(p);
            System.out.println("Après ajustement (doit être dans [0,1920)×[0,1080)) : " + p);
        }
}
