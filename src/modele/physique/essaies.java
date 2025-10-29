package modele.physique;

import org.w3c.dom.ls.LSOutput;

import javax.crypto.spec.PSource;

public class essaies {
    public static void main(String[] args) {
        Carte monde1 = new Carte();

        //test carte

        //voir si la carte ce créer
        System.out.println(monde1);
        System.out.println();

        //voir la position aléatoire fonctionne comme prévu
        Position p1 = Carte.positionAleatoire();
        System.out.println("Random "+ p1);


        //tester la méthode ajusterToroidal
        Position p2 = new Position(3000,-6);
        System.out.println("Mauvaise "+p2);
        Position p2a = Carte.ajusterToroidal(p2);
        System.out.println("Reajustement de "+ p2a);

        //test de la classe ObjetPhysique
        ObjetPhysique testOP1 = new ObjetPhysique(new Position(300, 400)) { };
        System.out.println(testOP1.getPosition());
    }
}
