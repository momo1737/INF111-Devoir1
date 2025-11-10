package modele.reseau;

import modele.gestionnaires.GestionnaireScenario;
import modele.physique.ObjetMobile;
import modele.physique.Position;
import java.util.Random;

public class Cellulaire extends ObjetMobile implements UniteCellulaire {

    //Constantes
    public static final int NON_CONNECTE = -1;
    public static final double PROB_APPELER = 0.05;
    public static final double PROB_ENVOI_MSG = 0.2;
    public static final double PROB_DECONNEXION = 0.1;

    //Attributs
    private String numeroLocal;
    private String numeroConnecte ;
    private int numeroConnexion ;
    private Antenne antenneConnectee;
    private Random generateur ;
    private final GestionnaireReseau reseau;//une copie de l’instance du gestionnaire réseau (voir: getInstance())
    private String numeroAppelant;


    //Constructeur
    public Cellulaire (String numeroLocal, Position position,double vitesse,double deviation){
        super(position,vitesse,deviation);
        this.numeroLocal = numeroLocal;
        this.numeroConnexion = NON_CONNECTE;
        this.numeroConnecte = null;
        this.reseau = GestionnaireReseau.getInstance();

        //Trouve l'Antenne la plus proche pour se connecter (si possible)
        this.antenneConnectee = reseau.trouverAntenneLaPlusProche(this.getPosition());
        if (antenneConnectee != null) {
            antenneConnectee.ajouterCellulaire(this);
        }

        //Générateur
        this.generateur = new Random();
    }

    //Getters pour numeroLocal et numeroConnexion
    public String getNumeroLocal(){
        return numeroLocal;
    }

    public int getNumeroConnexion(){
        return numeroConnexion;
    }

    //Méthode pour savoir si un Cellulaire est connecté
    public boolean estConnecte() {
        return this.numeroConnexion != NON_CONNECTE;//(-1)
    }

    //Comparer un numéro externe avec le numéro local
    public boolean comparerNumero(String numeroext){
        return this.numeroLocal != null && this.numeroLocal.equals(numeroext);
    }

    //toString pour afficher le numéro local et la position
    @Override
    public String toString() {
        return "Cellulaire {numeroLocal=" + numeroLocal + "," + position + "}";
    }


    //==========================================PARTIE 3.3.1====================================================

    public Antenne getAntenneConnectee() { return antenneConnectee; }
    public void setAntenneConnectee(Antenne a) { this.antenneConnectee = a; }

    //Basculer vers l'antenne la plus proche
    private void mettreAJourAntenne(){
        Antenne NvAntenne = reseau.trouverAntenneLaPlusProche(this.getPosition());
        if (NvAntenne == null){
            return;
        }

        else if (NvAntenne == antenneConnectee){
            return;
        }

        else if (antenneConnectee != null){
            antenneConnectee.enleverCellulaire(this);
        }

        NvAntenne.ajouterCellulaire(this);
        antenneConnectee = NvAntenne;

        //Validation
        System.out.println("[SWITCH] " + numeroLocal + " -> " + antenneConnectee);
    }


    // Un “tour” = déplacement + changement d’antenne (si necessaire)
    protected void effectuerTour(){
        this.seDeplacer();
        mettreAJourAntenne();
    }

    // ===== Implémentation UniteCellulaire =====

    // Demande d’appel
    @Override
    public int appeler(String numeroAppele, String numeroAppelant, Antenne antenneConnecte){
        return antenneConnectee.appeler(numeroAppele,numeroAppelant,antenneConnecte);
    }

    //Répondre à un appel entrant (enregistre la connexion si libre)
    @Override
    public Cellulaire repondre(String numeroAppele, String numeroAppelant, int numeroConnexion) {

        if(!this.estConnecte()){

            //On enregistre les infos du cellulaire this (numConnexion et num Appelant)
            this.numeroConnexion = numeroConnexion;
            this.numeroAppelant=numeroAppelant;
            return this;}

        return null;
    }

    @Override
    public void finAppelLocal(String numeroAppele, int numeroConnexion){

        // Si ce n'est pas connecté ou si les numero de connexion sont différents, ça ne fait rien
        if (!estConnecte() || this.numeroConnexion != numeroConnexion) {
            return;
        }

        //Si c'est connecté, fini l'appel
        if (antenneConnectee != null) {
            antenneConnectee.finAppelLocal(numeroAppele, numeroConnexion);
        }

        // Enlève le cellulaire (c'est plus en communication)
        this.numeroConnexion = NON_CONNECTE;
        this.numeroConnecte = null;
    }

    @Override
    public void finAppelDistant(String numeroAppele, int numeroConnexion){

        // Vérifie si les numero de connexion correspondent
        if (this.numeroConnexion == numeroConnexion) {

            // Enlève le cellulaire (c'est plus en communication)
            this.numeroConnexion = NON_CONNECTE;
            this.numeroConnecte = null;
        }
    }

    // Envoyer un message si la connexion est active
    @Override
    public void envoyer(Message message, int numeroConnexion) {
        if (!estConnecte()) return;
        if (this.numeroConnexion != numeroConnexion) return;
        if (antenneConnectee == null) return ;
        if(numeroConnecte == null)return;

        if(message == null){
            String Message = GestionnaireScenario.obtenirMessage(numeroLocal); //Récupérer un message par défaut
        }

        antenneConnectee.envoyer(message, numeroConnexion);
    }

    //Recevoir un message
    @Override
    public void recevoir (Message message){
        System.out.println("Numero local est:" +numeroLocal + "et le message est: " +message);
    }
}