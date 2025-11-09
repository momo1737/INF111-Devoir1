package modele.reseau;

import modele.gestionnaires.GestionnaireScenario;
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
    private String numeroConnecte ;
    private int numeroConnexion ;
    private Antenne antenneConnectee;
    private Random generateur ;
    private final GestionnaireReseau reseau;//une copie de l’instance du gestionnaire réseau (voir: getInstance())
    private String numeroAppelant;


    //construteur du Cellulaire
    public Cellulaire (String numeroLocal, Position position,double vitesse,double deviation){
        super(position,vitesse,deviation);
        this.numeroLocal = numeroLocal;
        this.numeroConnexion = NON_CONNECTE;
        this.numeroConnecte = null;
        this.reseau = GestionnaireReseau.getInstance();

        //pour obtenir l'antenne la plus proche, en initialisant l'antenne connecté
        this.antenneConnectee = reseau.trouverAntenneLaPlusProche(this.getPosition());
        if (antenneConnectee != null) {
            antenneConnectee.ajouterCellulaire(this);
        }
        //générateur
        this.generateur = new Random();

    }

    //getters pour le numeroLocal et numeroConnexion
    public String getNumeroLocal(){
        return numeroLocal;
    }
    public int getNumeroConnexion(){
        return numeroConnexion;
    }

    //méthode pour savoir si un Cellulaire est connecté
    public boolean estConnecte() {
        return this.numeroConnexion != NON_CONNECTE;//(-1)
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


    //==========================================PARTIE 3.3.1====================================================
                               /*cette partie sert uniquement a lire
                          * et a mettre a jour la référence de l'antenne
     */
    //getter et setter demander
    public Antenne getAntenneConnectee() { return antenneConnectee; }
    public void setAntenneConnectee(Antenne a) { this.antenneConnectee = a; }

                //========Méthode pour mettre a jours l'antenne==========
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

        // >>> trace demandée pour la validation <<<------suggestion de chat pour tester comme le prof la demander
        System.out.println("[SWITCH] " + numeroLocal + " -> " + antenneConnectee);
    }


    //Methode pour faire la mise a jour a chaque tour et en fonction de la position a l'aide de seDeplacer()
    //la méthode a été mis en protected pour que je puisse l'utiliser et faire le test dans GestioneReseau pour 3.3.1
    protected void effectuerTour(){
        this.seDeplacer();
        mettreAJourAntenne();
    }
    //==================================================================================================
// PARTIE DE RAYANE EL AHMADI////////
/* concernant la parti de teinté sur le fait de mettre a jour l'antenne a chaque tour,
je ne l'est pas mis puisque on avait déja ce bout de code du coup aucune utilité
vérifier avec l'équipe
*/



    //===================================appelle des méthodes implementer de l'interface UniteCellulaire===============================================================
    // (1) le cellulaire appel la méthode Antenne.appeler() appartenant à son antenne connecté
    @Override
    public int appeler(String numeroAppele,String numeroAppelant,Antenne antenneConnecte){
        return antenneConnectee.appeler(numeroAppele,numeroAppelant,antenneConnecte);
    }
    //Remplir methode cellulaire repondre (5b) JE NE SUIS PAS SUR
    @Override
    public Cellulaire repondre(String numeroAppele,String numeroAppelant,int numeroConnexion){
        //Si cellulaire ne retourne pas quil est connecte
        if(!this.estConnecte()){
            //On enregistre les infos du cellulaire this (numConnexion et num Appelant)
            this.numeroConnexion = numeroConnexion;
            this.numeroAppelant=numeroAppelant;
            return this;}
        //Sinon retourne nul
        return null;
    }

    @Override
    public void finAppelLocal(String numeroAppele,int numeroConnexion){

        // Si c'est pas connecté ou si les numero de connexion sont differents, ca fait rien
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
    public void finAppelDistant(String numeroAppele,int numeroConnexion){
        // Verifie si les numero de connexion correspondent
        if (this.numeroConnexion == numeroConnexion) {

            // Enlève le cellulaire (c'est plus en communication)
            this.numeroConnexion = NON_CONNECTE;
            this.numeroConnecte = null;
        }
    }


    @Override
    public void envoyer(Message message, int numeroConnexion) {
        if (!estConnecte()) return;
        if (this.numeroConnexion != numeroConnexion) return;
        if (antenneConnectee == null) return ;
        if(numeroConnecte == null)return;

        if(message == null){
            String Message = GestionnaireScenario.obtenirMessage(numeroLocal);

        }
        antenneConnectee.envoyer(message, numeroConnexion);
    }
    @Override
    public void recevoir (Message message){
        System.out.println("Numero local est:" +numeroLocal + "et le message est: " +message);
    }

}
