package modele.reseau;

/**
 * Le gestionnaire r�seau est responsable de g�rer les connexions cellulaires et de relayer
 * les appels, messages et fin d'appel.
 *
 * Dans le cadre de la simulation, c'est �galement le gestionnaire r�seau qui instancie Antennes et
 * cellulaire et qui g�re l'ex�cution par tour des cellulaires.
 *
 * Le gestionnaire r�seau est un singleton
 *
 * @author Fred Simard
 * @version Hiver 2021
 */

//===========Ajouts pour les TDA=================
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import tda.ListeOrdonne;

//==========Ajouts des classes===================
import modele.gestionnaires.GestionnaireScenario;
import modele.physique.Carte;
import modele.physique.Position;
import observer.MonObservable;


public class GestionnaireReseau extends MonObservable implements Runnable {

    // Constantes demandées
    public static final int    PERIODE_SIMULATION_MS = 250;
    public static final double VITESSE               = 10.0;
    public static final double DEVIATION_STANDARD    = 0.05;
    public static final int    NB_CELLULAIRES        = 30;
    public static final int    NB_ANTENNES           = 10;
    public static final int    CODE_NON_CONNECTE     = -1;
    public static final int    NB_CRIMINELS          = 10;




    // Attributs demandés
    private boolean mondeEnVie = true;
    private static   GestionnaireReseau instance = new GestionnaireReseau();

    //Générer un numero de connexion unique pour chaque appel
    private static  int prochainNumConnexion = 0;

    //On va stocker le nombre d'antennes crées dans une liste pour utiliser dans notre methode
    private  List<Antenne> antennes = new ArrayList<Antenne>();

    //Liste pour stocker les connexions à partir de listeOrdonnee
    private  ListeOrdonne connexions = new ListeOrdonne(50);
    private final Random generateur = new Random();
    private  final List<Cellulaire> cellulaires = new ArrayList<>();

    /**
     * m�thode permettant d'obtenir une r�f�rence sur le Gestionnaire R�seau
     * @return instance
     */

    public static/**/ GestionnaireReseau getInstance() {
        return instance;
    }


    //Constructeur
    private GestionnaireReseau() {
    }

    /**
     * permet de mettre fin � la simulation
     *@param mondeEnVie
     */

    public void finDeSimulation() {
        this.mondeEnVie = false;
    }

    //Création d'antenne à une position aléatoire (selon NB_ANTENNES)
    private void creeAntennes() {
        antennes.clear();
        for (int i = 0; i < NB_ANTENNES; i++) {
            Position p = Carte.positionAleatoire();
            antennes.add(new Antenne(p));
        }
    }

    //Création de cellulaires à une position aléatoire (selon NB_CELLULAIRES)
    private void creeCellulaires() {
        cellulaires.clear();
        for (int i = 0; i < NB_CELLULAIRES; i++) {
            String numero = GestionnaireScenario.obtenirNouveauNumeroStandard();
            Position position = Carte.positionAleatoire();
            Cellulaire cellulaire = new Cellulaire(numero, position, VITESSE, DEVIATION_STANDARD);
            cellulaires.add(cellulaire);
        }
    }


    // Référence aux antennes
    public List<Antenne> getAntennes() {
        return antennes; // retourne la r�f�rence (pas de copie)
    }

    //Référence aux cellulaires
    public List<Cellulaire> getCellulaires() {
        return cellulaires; // retourne la r�f�rence (pas de copie)
    }

    //Antenne la plus proche par rapport à une position
    public Antenne trouverAntenneLaPlusProche(Position p) {
        Antenne laPlusProche = null;
        double distanceMin = antennes.getFirst().distance(p);
        for (Antenne a : antennes) {
            double distance = a.distance(p);

            if (distance < distanceMin) {
                distanceMin = distance;
                laPlusProche = a;
            }
        }
        return laPlusProche;
    }

    //Générer un numero de connexion unique
    public static int numConnexionUnique(){
        return prochainNumConnexion++;
    }

    //Chercher le destinataire et créer la connexion
    public int relayerAppel(String numeroAppele,String numeroAppelant,Antenne antenneDeBase) {

        //Générer un numero de connexion unique
        int numConnexion = numConnexionUnique();

        //Création des variables vides pour stocker
        Cellulaire destinataire = null;
        Antenne antenneDestination = null;

        //Parcourir toutes les antennes avec la methode
        int nbAntenne = antennes.size();
        for (int i = 0; i < nbAntenne; i++) {
            Antenne antenneCourante = antennes.get(i);

            //Demande à l’antenne si le cellulaire appelé répond
            Cellulaire cellulaireQuiRepond = antenneCourante.repondre(numeroAppele, numeroAppelant, numConnexion);

            if (cellulaireQuiRepond != null) {
                destinataire = cellulaireQuiRepond;
                antenneDestination = antenneCourante;

                //Antenne reference valide donc on crée une connexion
                Connexion connexionCreer = new Connexion(numConnexion, antenneDeBase, antenneCourante);
                connexions.inserer(connexionCreer);
                return numConnexion;
            }
        }
        return Cellulaire.NON_CONNECTE;
    }

    //Récupère une connexion via ListeOrdonne
    private Connexion getConnexion(int numeroConnexion) {
        return connexions.rechercher(numeroConnexion);
    }

    //Mettre à jour la connexion
    public void mettreAJourConnexion(int numeroConnexion, Antenne ancienne, Antenne nouvelle) {
        Connexion connect = getConnexion(numeroConnexion);
        if (connect != null) {
            connect.miseAJourAntenne(ancienne, nouvelle);
        }
    }

    //Relayer un message via la connexion vers l’antenne opposée.
    public boolean relayerMessage(Antenne source, Message message, int numCo) {
        if (source == null || message == null) return false;

        Connexion co = trouverConnexion(numCo);
        if (co == null) return false;

        Antenne destination = co.getAutreAntenne(source);
        if (destination == null) return false;

        destination.recevoir(message);
        return true;
    }

    // Recherche d’une connexion existante
    private Connexion trouverConnexion(int numCo) {
        for (Connexion connexion : connexions.getDonnee()) {
            if (connexion.getNumConnexion() == numCo) return connexion;
        }
        return null;
    }

    //Supprime l'objet "connexion" des connexions
    private void supprimerConnexion(int numeroConnexion) {
        Connexion c = getConnexion(numeroConnexion);
        if (c != null) {
            connexions.supprimer(c.getNumConnexion());
        }
    }

    // Relayer la fin de l'Appel (l'antenne source sait qu'on a raccroché)
    public void relayerFinAppel(int numeroConnexion, Antenne source, String numeroAppele) {

        Connexion connexion = getConnexion(numeroConnexion);
        if (connexion == null) {
            return;
        }

        //  Prendre l'autre antenne
        Antenne autre = connexion.getAutreAntenne(source);
        if (autre != null) {

            // Notifie l'autre côté (le cellulaire destinataire) via son antenne
            autre.finAppelDistant(numeroAppele, numeroConnexion);
        }

        // Enlève la connexion
        supprimerConnexion(numeroConnexion);
    }




    /**
     * s'ex�cute en continue pour simuler le syst�me
     */
    @Override
    public void run() {


        creeAntennes();
        creeCellulaires();
        this.avertirLesObservers();

        while(this.mondeEnVie) {
            for(Cellulaire cell : cellulaires) {
                cell.effectuerTour();
            }
            this.avertirLesObservers();
            try {
                Thread.sleep(PERIODE_SIMULATION_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}