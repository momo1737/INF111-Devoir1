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



import observer.MonObservable;

//===========Ajouts pour les TDA
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

//==========Ajouts des classes
import modele.reseau.Connexion;
import modele.gestionnaires.GestionnaireScenario;
import modele.physique.Carte;
import modele.physique.Position;
import tda.ListeOrdonne;


public class GestionnaireReseau extends MonObservable implements Runnable {

    // ==================== CONSTANCES (exigées) ====================
    public static final int    PERIODE_SIMULATION_MS = 250;//modification du nombre de periode de simulation pour que sa soit plus lent et donc visible  (ancien:100-nouveau:250) pour tester l'étape 3.3.1
    public static final double VITESSE               = 10.0;
    public static final double DEVIATION_STANDARD    = 0.05;
    public static final int    NB_CELLULAIRES        = 1;//modification du nombre de cellulaire (ancien:30-nouveau:1) pour tester l'étape 3.3.1
    public static final int    NB_ANTENNES           = 30;//modification du nombre d'antennes (ancien:10-nouveau:30) pour tester l'étape 3.3.1
    public static final int    CODE_NON_CONNECTE     = -1;
    public static final int    NB_CRIMINELS          = 10;  // ? constante ajouter suite a la suggestion de chat pour pouvoir lancer le jeu, surement pour une étape future


	private boolean mondeEnVie = true;
	private static GestionnaireReseau instance = new GestionnaireReseau();
    //On creer cet attribut pour pouvoir generer un numero de connexion unique pour chaque appel
    private static  int prochainNumConnexion=0;
    //On va stocker le nombre dantenne creer dans une liste pour utiliser dans notre methode
    private static List<Antenne> antennes = new ArrayList<>();
    //On va creer une liste pour stocker les connexions a partir de listeOrdonnee
    private static ListeOrdonne connexions=new ListeOrdonne(50);
    private final Random generateur = new Random();
    private static final List<Cellulaire> cellulaires = new ArrayList<>();


    //private final ListeOrdonne<Connexion> connexions = new ListeOrdonne<>();   ancien attribut du prof
	/**
	 * méthode permettant d'obtenir une référence sur le Gestionnaire Réseau
	 * @return instance
	 */
	public static GestionnaireReseau getInstance() {
		return instance;
	}
	
	private GestionnaireReseau() {}

	/**
	 * permet de mettre fin à la simulation
	 *@param mondeEnVie
    */

	public void finDeSimulation() {
		this.mondeEnVie = false;
	}



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
            Position p = Carte.positionAleatoire();
            Cellulaire c = new Cellulaire(numero, p, VITESSE, DEVIATION_STANDARD);
            cellulaires.add(c);
            // Si ton constructeur Cellulaire init l'antenne la plus proche, rien à faire ici.
            // Sinon, tu pourras rattacher ici lors de l'étape 3.3.1.
        }
    }


    // ==================== SERVICES (exigés) ====================
    public List<Antenne> getAntennes() {
        return antennes; // retourne la référence (pas de copie)
    }

    public List<Cellulaire> getCellulaires() {
        return cellulaires; // retourne la référence (pas de copie)
    }


    // ==================== Utile dès 3.3.1 ====================
    public Antenne trouverAntenneLaPlusProche(Position p) {
        Antenne laPlusProche = null;
        double dmin = Double.POSITIVE_INFINITY;
        for (Antenne a : antennes) {
            double d = a.distance(p);
            if (d < dmin) {
                dmin = d;
                laPlusProche = a;
            }
        }
        return laPlusProche;
    }

    //Methode pour generer un numero de connexion unique
    public static int numConnexionUnique(){
        return prochainNumConnexion++;}

    //Creation de la méthode GestionnaireReseau::relayerAppel- 3.3.2 - Étape 2
    //je ne suis pas sur
    public static int relayerAppel(String numeroAppele,String numeroAppelant,Antenne antenneDeBase) {

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
            if (cellulaireQuiRepond != null) {destinaire = cellulaireQuiRepond;antenneDestination = antenneCourante;}

            //Si antenne renvoie une valeur alors indique que antenne a repondu donc antenne reference valide
            if (antenneCourante != null) {

                //on creer une connexion a partir des infos valides quon a et on lajoute a la liste(4)
                Connexion connexionCreer = new Connexion(numConnexion, antenneDeBase, antenneCourante);
                connexions.inserer(connexionCreer);
                return numConnexion;}
        }
        return Cellulaire.NON_CONNECTE;


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
