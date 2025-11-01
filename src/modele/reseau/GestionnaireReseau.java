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

    // ==================== ATTRIBUTS (exigés) ====================
    private final Random rand = new Random();
    private final List<Antenne> antennes = new ArrayList<>();
    private final List<Cellulaire> cellulaires = new ArrayList<>();
    private final List<Connexion> connexions = new ArrayList<>();//attribut temporaire puisque le TDA n'est pas encore prêt

    //
    // private final ListeOrdonne<Connexion> connexions = new ListeOrdonne<>();


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
            // Si ton constructeur Cellulaire init l?antenne la plus proche, rien à faire ici.
            // Sinon, tu pourras rattacher ici lors de l?étape 3.3.1.
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
        Antenne best = null;
        double dmin = Double.POSITIVE_INFINITY;
        for (Antenne a : antennes) {
            double d = a.distance(p);
            if (d < dmin) {
                dmin = d;
                best = a;
            }
        }
        return best;
    }
	/**
	 * s'exécute en continue pour simuler le système
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
