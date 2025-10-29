package modele.physique.test;
import modele.physique.ObjetMobile;
import modele.physique.Position;
public class TestObjetMobile extends ObjetMobile {

    //Constructeur
    public TestObjetMobile(Position position, double vitesse, double deviation){
        super(position, vitesse, deviation);
    }

    //méthode toString pour le déverminage
    @Override
    public String toString(){
        return "MobileDeTest -> Position: " + position +
                ", direction: " + direction +
                ", vitesse: " + vitesse;
    }
}
