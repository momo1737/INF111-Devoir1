package modele.reseau;

/**
 * Le gestionnaire réseau est responsable de gérer les connexions cellulaires et de relayer
 * les appels, messages et fin d'appel.
 * 
 * Dans le cadre de la simulation, c'est également le gestionnaire réseau qui instancie Antennes et
 * cellulaire et qui gère l'exécution par tour des cellulaires.
 * 
 * Le gestionnaire réseau est un singleton
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

    // ==================== CONSTANCES (exigées) ====================
    public static final int    PERIODE_SIMULATION_MS = 250;//modification du nombre de periode de simulation pour que sa soit plus lent et donc visible  (ancien:100-nouveau:250) pour tester l'étape 3.3.1
    public static final double VITESSE               = 10.0;
    public static final double DEVIATION_STANDARD    = 0.05;
    public static final int    NB_CELLULAIRES        = 2;//modification du nombre de cellulaire (ancien:30-nouveau:1) pour tester l'étape 3.3.1
    public static final int    NB_ANTENNES           = 30;//modification du nombre d'antennes (ancien:10-nouveau:30) pour tester l'étape 3.3.1
    public static final int    CODE_NON_CONNECTE     = -1;
    public static final int    NB_CRIMINELS          = 10;  // ? constante ajouter suite a la suggestion de chat pour pouvoir lancer le jeu, surement pour une étape future

    // ==================== ATTRIBUTS (exigées) ====================
	private boolean mondeEnVie = true;
	private static GestionnaireReseau instance = new GestionnaireReseau();
    //On creer cet attribut pour pouvoir generer un numero de connexion unique pour chaque appel
    private static  int prochainNumConnexion=0;
    //On va stocker le nombre dantenne creer dans une liste pour utiliser dans notre methode
    private  List<Antenne> antennes = new ArrayList<Antenne>();
    //On va creer une liste pour stocker les connexions a partir de listeOrdonnee
    private  ListeOrdonne connexions = new ListeOrdonne(50);
    private final Random generateur = new Random();
    private  final List<Cellulaire> cellulaires = new ArrayList<>();

    //private final ListeOrdonne<Connexion> connexions = new ListeOrdonne<>();   ancien attribut du prof


	/**
	 * méthode permettant d'obtenir une référence sur le Gestionnaire Réseau
	 * @return instance
	 */

	public static/**/ GestionnaireReseau getInstance() {
		return instance;
	}


   //=====================CONSTRUCTEUR=======================================================
	private GestionnaireReseau() {
        /*le constructeur reste vide, puisque nous avons déjà attribut les valeurs
        des attributs dans l'implémentation de ceux-ci
        */
    }

	/**
	 * permet de mettre fin à la simulation
	 *@param mondeEnVie
    */

	public void finDeSimulation() {
		this.mondeEnVie = false;
	}


//=============================création d'antenne et de cellulaires===========================
    private void creeAntennes() {
        antennes.clear();
        for (int i = 0; i < NB_ANTENNES; i++) {
            Position p = Carte.positionAleatoire();
            antennes.add(new Antenne(p));
        }
    }

    private void creeCellulaires() {
        cellulaires.clear();
        for (int i = 0; i < NB_CELLULAIRES; i++) {
            String numero = GestionnaireScenario.obtenirNouveauNumeroStandard();
            Position position = Carte.positionAleatoire();
            Cellulaire cellulaire = new Cellulaire(numero, position, VITESSE, DEVIATION_STANDARD);
            cellulaires.add(cellulaire);
            // Si ton constructeur Cellulaire init l'antenne la plus proche, rien à faire ici.
            // Sinon, tu pourras rattacher ici lors de l'étape 3.3.1.
        }
    }


    // ==================== SERVICES  ====================
    public List<Antenne> getAntennes() {
        return antennes; // retourne la référence (pas de copie)
    }

    public List<Cellulaire> getCellulaires() {
        return cellulaires; // retourne la référence (pas de copie)
    }


    // ==================== Utile pour l'étape 3.3.1 ====================
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

//====================================Walid================================
    //Methode pour generer un numero de connexion unique
    public static int numConnexionUnique(){
        return prochainNumConnexion++;} //a revoir le prochainNumConnexion

    //Creation de la méthode GestionnaireReseau::relayerAppel- 3.3.2 - Étape 2
    //je ne suis pas sur
    public int relayerAppel(String numeroAppele,String numeroAppelant,Antenne antenneDeBase) {

        //On va gener un numero de connexion unique avec la methode creer plus haut (numConnexionUnique)
        int numConnexion = numConnexionUnique();

        //On va creer des variables vides pour stocker
        Cellulaire destinaire = null;
        Antenne antenneDestination = null;

        //(3) on va parcourir toutes les antennes avec la methode
        int nbAntenne = antennes.size();
        for (int i = 0; i < nbAntenne; i++) {
            Antenne antenneCourante = antennes.get(i);

            //On demande a lantenne si elle a le cellulaire qui est cense repondre
            Cellulaire cellulaireQuiRepond = antenneCourante.repondre(numeroAppele, numeroAppelant, numConnexion);

            //reference valide donc non null alors ok(5)
            if (cellulaireQuiRepond != null) {
                destinaire = cellulaireQuiRepond;
                antenneDestination = antenneCourante;
                //Antenne reference valide donc on creer une connexion
                Connexion connexionCreer = new Connexion(numConnexion, antenneDeBase, antenneCourante);
                connexions.inserer(connexionCreer);
                return numConnexion;}}
        return Cellulaire.NON_CONNECTE;


    }
//==============================Raytoch=============================
    // Récupérer une connexion par son numéro (via ListeOrdonne)
    private Connexion getConnexion(int numeroConnexion) {
        return connexions.rechercher(numeroConnexion);
    }

    //Mettre a jour la connexion
    public void mettreAJourConnexion(int numeroConnexion, Antenne ancienne, Antenne nouvelle) {
        Connexion connect = getConnexion(numeroConnexion);
        if (connect != null) {
            connect.miseAJourAntenne(ancienne, nouvelle);
        }
    }

    //=========================PARTIE RAYANE EL AHMADI==================================//

    public boolean relayerMessage(Antenne source, Message message, int numCo) {
        if (source == null || message == null) return false;

        Connexion co = trouverConnexion(numCo);
        if (co == null) return false;

        Antenne destination = co.getAutreAntenne(source);
        if (destination == null) return false;

        destination.recevoir(message);
        return true;
    }

//revoir le nom des variables pour que sa concorde avec tout le reste du projet
    // Recherche dans la liste de connexion
    private Connexion trouverConnexion(int numCo) {
        for (Connexion c : connexions.getDonnee()) {
            if (c.getNumConnexion() == numCo) return c;
        }
        return null;
    }


    /**
	 * s'exécute en continue pour simuler le système
	 */
	@Override
	public void run() {
		

		creeAntennes();
		creeCellulaires();
		this.avertirLesObservers();

//j'ai décocher cette section pour pouvoir faire le test pour le 3.3.1
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
