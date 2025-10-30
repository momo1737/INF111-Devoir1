package modele.reseau;

import modele.physique.ObjetMobile;
import java.util.Random;

public class Cellulaire extends ObjetMobile implements UniteCellulaire {

    //Constante demande
    protected static final int NON_CONNECTE = -1;
    protected static final double PROB_APPELER = 0.05;
    protected static final double PROB_ENVOI_MSG = 0.2;
    protected static final double PROB_DECONNEXION = 0.1;

    //Attribut de la classe
    protected String numeroLocal;
    protected int numeroConnexion;// PAS ENCORE FAIT -----par default NON_CONNECTE)
    protected String numeroConnecte = null;
    protected Antenne antenne;
    protected Random sc = new Random();
    protected //une copie de l’instance du gestionnaire réseau (voir: getInstance())


    //construteur du Cellulaire
    public Cellulaire (){}

    //appelle des méthodes implementer de l'interface UniteCellulaire
    @Override
    int appeler(String numeroAppele,String numeroAppelant,Antenne antenneConnecte){    }

    @Override
    Cellulaire repondre(String numeroAppele,String numeroAppelant,int numeroCellulaire){}

    @Override
    void finAppelLocal(String numeroAppele,int numeroConnexion){}

    @Override
    void finAppelDistant(String numeroAppele,int numeroConnexion){}

    @Override
    void envoyer(Message message,int numeroConnexion){}

    @Override
    void recevoir(Message message){}

}
