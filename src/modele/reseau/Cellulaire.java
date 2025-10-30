package modele.reseau;

import modele.physique.ObjetMobile;
import modele.physique.Position;
import java.util.Random;

public class Cellulaire extends ObjetMobile implements UniteCellulaire {

    //Constante demande
    public static final int NON_CONNECTE = -1;
    public static final double PROB_APPELER = 0.05;
    public static final double PROB_ENVOI_MSG = 0.2;
    public static final double PROB_DECONNEXION = 0.1;

    //Attribut de la classe
    private String numeroLocal;
    private int numeroConnexion;
    private String numeroConnecte ;
    private Antenne antenneConnecter;
    private Random generateur ;
    private final GestionnaireReseau reseau;//une copie de l’instance du gestionnaire réseau (voir: getInstance())


    //construteur du Cellulaire
    public Cellulaire (String numeroLocal, Position position,double vitesse,double deviation){
        super(position,vitesse,deviation);
        this.numeroLocal = numeroLocal;
        this.numeroConnexion = NON_CONNECTE;
        this.numeroConnecte = null;
        this.antenneConnecter = null;
        this.generateur = new Random();
        this.reseau = GestionnaireReseau.getInstance();
    }

    //getters pour le numeroLocal et numeroConnexion

    public String getNumeroLocal(){
        return numeroLocal;
    }
    public int getNumeroConnexion(){
        return numeroConnexion;
    }

    //méthode pour savoir si un Cellulaire est connecté
    public boolean siConnecte() {
        return this.numeroConnexion != NON_CONNECTE;
    }

    //méthode pour comparer le numero reçu avec le numero local
    public boolean comparerNumero(String numeroext){
        return this.numeroLocal != null && this.numeroLocal.equals(numeroext);
    }

    //toString pour afficher le numéro local et la position
    @Override
    public String toString() {
        return "Cellulaire {numeroLocal=" + numeroLocal + "," + position + "}";
    }


    //appelle des méthodes implementer de l'interface UniteCellulaire
    @Override
    public int appeler(String numeroAppele,String numeroAppelant,Antenne antenneConnecte){
        return NON_CONNECTE;
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
