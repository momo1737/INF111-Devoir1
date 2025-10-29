package modele.physique.test;

import modele.physique.Position;

public class TestOB {
    public static void main(String[] args) {
        TestObjetMobile m1 = new TestObjetMobile(new Position(100,100),10,0.1);

        for (int i = 0; i < 5; i++){
            System.out.println("Avant déplacement : " + m1);
            m1.seDeplacer();
            System.out.println("Après déplacement : " + m1);
            System.out.println("--------------------------------------");
        }
    }
}
