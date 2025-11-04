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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import observer.MonObservable;
import tda.ListeOrdonne;
import modele.physique.Carte;
import modele.physique.Position;
import modele.gestionnaires.GestionnaireScenario;

public class GestionnaireReseau extends MonObservable implements Runnable {

    private boolean mondeEnVie = true;
    private static GestionnaireReseau instance = new GestionnaireReseau();

    //Constantes

    public static final int PERIODE_SIMULATION_MS = 100;
    public static final double VITESSE = 10.0;
    public static final double DEVIATION_STANDARD = 0.05;
    public static final int NB_CELLULAIRES = 30;
    public static final int NB_ANTENNES = 5;
    public static final int CODE_NON_CONNECTE = -1;
    public static final int NB_CRIMINELS  = 5;

    //Attributs

    private Random generateur;
    private List<Antenne> antennes;
    private List<Cellulaire> cellulaires;
    private ListeOrdonne connexions;


    //Methodes privées

    /**
     * m�thode permettant d'obtenir une r�f�rence sur le Gestionnaire R�seau
     * @return instance
     */
    public static GestionnaireReseau getInstance() {
        return instance;
    }

    private GestionnaireReseau() {
        this.generateur = new Random();
        this.antennes = new ArrayList<>();
        this.cellulaires = new ArrayList<>();
        this.connexions = new tda.ListeOrdonne(500);
    }

    // Crée des antennes à des positions aléatoires
    private void creeAntennes() {
        //vider la liste
        antennes.clear();
        for (int i = 0; i < NB_ANTENNES; i++) {
            Position position = Carte.positionAleatoire();
            antennes.add(new Antenne(position));
        }
    }

    //Créer des cellulaires

    private void creeCellulaires(){
        //vider la liste
        cellulaires.clear();
        for (int i = 0; i <NB_CELLULAIRES; i++){
            //Obtenir un numero aleatoire
            String num = GestionnaireScenario.obtenirNouveauNumeroStandard();

            //Position aléatoire sur la carte
            Position position = Carte.positionAleatoire();

            //Créer le cellulaire
            Cellulaire cellulaire = new Cellulaire(num, position, VITESSE, DEVIATION_STANDARD);

            cellulaires.add(cellulaire);
        }

    }

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
    // Services
    public List<Antenne> getAntennes() {return antennes;}
    public List<Cellulaire> getCellulaires() {return cellulaires;}

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

    //Supprime l'objet "connexion" des connexions

    private void supprimerConnexion(int numeroConnexion) {
        Connexion c = getConnexion(numeroConnexion);
        if (c != null) {
            connexions.supprimer(c.getNumConnexion());
        }
    }

    // Relayer la fin de l'Appel (l'antenne source sait qu'on a raccrocher)
    public void relayerFinAppel(int numeroConnexion, Antenne source, String numeroAppele) {


        Connexion connexion = getConnexion(numeroConnexion);
        if (connexion == null) {
            return;
        }

        //  Prendre l'Autre antenne
        Antenne autre = connexion.getAutreAntenne(source);
        if (autre != null) {

            // Notifie l’autre côté (le cellulaire destinataire) via son antenne
            autre.finAppelDistant(numeroAppele, numeroConnexion);
        }

        // Enleve la connexion

        supprimerConnexion(numeroConnexion);
    }

    /**
     * permet de mettre fin � la simulation
     * @param mondeEnVie
     */
    public void finDeSimulation() {
        this.mondeEnVie = false;
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