package modele.reseau;

import modele.physique.Position;

public class testCellulaire {
    public static void main(String[] args) {
        Position p6 = new Position(5,4);
        Cellulaire iphone = new Cellulaire("438-878-3426",p6,5,0.02);


        iphone.getNumeroLocal();
        System.out.println();
        iphone.getNumeroConnexion();
        System.out.println();
        System.out.println(iphone);
    }
}
