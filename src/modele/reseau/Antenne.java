package modele.reseau;

import modele.physique.ObjetPhysique;
import modele.physique.Position;

import java.util.ArrayList;
import java.util.List;

public class Antenne extends ObjetPhysique implements UniteCellulaire {
    private final GestionnaireReseau reseau;
    private static final List<Cellulaire> cellulaires = new ArrayList<>();


    //Constructeur de l'antenne
    public Antenne(Position position) {
        super(position);
        this.reseau = GestionnaireReseau.getInstance();
    }


    //methode qui permet d'obtenir la distance entre une Cellulaire et une Antenne
    public double distance (Position autre){
        return this.position.distanceE(autre);
    }

    //méthode toString pour afficher la position de l'Antenne
    @Override
    public String toString() {
        return "Antenne{" + position + "}";
    }





    //appelle des méthodes implementer de l'interface UniteCellulaire
    @Override
    public int appeler(String numeroAppele,String numeroAppelant,Antenne antenneConnecte){
        return 0;
    }
    @Override
    public Cellulaire repondre(String numeroAppele,String numeroAppelant,int numeroConnexion){
        return null;
    }

    @Override
    public void finAppelLocal(String numeroAppele,int numeroConnexion){ }

    @Override
    public void finAppelDistant(String numeroAppele,int numeroConnexion){}

    @Override
    public void envoyer(Message message,int numeroConnexion){}

    @Override
    public  void recevoir(Message message){}
}
