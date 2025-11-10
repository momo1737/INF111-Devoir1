package modele.reseau;

import modele.physique.ObjetPhysique;
import modele.physique.Position;
import java.util.ArrayList;
import java.util.List;

public class Antenne extends ObjetPhysique implements UniteCellulaire {
    private GestionnaireReseau reseau;
    private final List<Cellulaire> cellulaires = new ArrayList<>();

    //Constructeur de l'antenne
    public Antenne(Position position) {
        super(position);
        this.reseau = GestionnaireReseau.getInstance();
    }


    //Distance entre l'antenne et une position
    public double distance (Position autre){
        return this.position.distance(autre);
    }

    //méthode toString pour afficher la position de l'Antenne
    @Override
    public String toString() {
        return "Antenne{" + position + "}";
    }

    // Ajouter un cellulaire
    public void ajouterCellulaire(Cellulaire c) { cellulaires.add(c); }

    // Ajouter un cellulaire
    public void enleverCellulaire(Cellulaire c) { cellulaires.remove(c); }

    //Retourne les cellulaires connectés
    public List<Cellulaire> getCellulaires() { return cellulaires; } // pratique pour 3.3.2 repondre()

    //Rechercher un cellulaire avec un numéro
    private Cellulaire trouverCellulaire(String numero) {
        for (Cellulaire c : cellulaires) {
            if (c.comparerNumero(numero)) return c;
        }
        return null;
    }


    //Mettre à jour la connexion d'une antenne à l'autre
    public void mettreAJourConnexion(int numeroConnexion, Antenne ancienne) {
        GestionnaireReseau.getInstance().mettreAJourConnexion(numeroConnexion, ancienne, this);
    }



    //Relayer l'appel vers gestionnaire reseau
    @Override
    public int appeler(String numeroAppele,String numeroAppelant,Antenne antenneConnecte){
        return reseau.relayerAppel(numeroAppele,numeroAppelant,antenneConnecte);
    }

    @Override
    public Cellulaire repondre(String numeroAppele,String numeroAppelant,int numeroConnexion){
        int nbCellulaire=cellulaires.size();

        //Parcourt les cellulaires connnectés à l'antenne
        for (int i = 0; i < nbCellulaire; i++) {
            Cellulaire cellulairePotentiel =cellulaires.get(i);

            // Si le numéro correspond, on laisse le cellulaire repondre
            if(cellulairePotentiel.comparerNumero(numeroAppele)){
                return cellulairePotentiel.repondre(numeroAppele,numeroAppelant,numeroConnexion);}
        }
        return null;
    }

    @Override
    public void finAppelLocal(String numeroAppele,int numeroConnexion) {

        // Signale au réseau qu’on veut terminer l’appel depuis cette antenne
        GestionnaireReseau.getInstance().relayerFinAppel(numeroConnexion, this, numeroAppele);
    }

    @Override
    public void finAppelDistant(String numeroAppele,int numeroConnexion) {

        // Parcourt les cellulaires connectés pour raccrocher
        for (Cellulaire c : cellulaires) {
            if (c != null && c.comparerNumero(numeroAppele)) {
                c.finAppelDistant(numeroAppele, numeroConnexion);
                break;
            }
        }
    }


    @Override
    public void envoyer(Message message, int numeroConnexion) {

        // Envoie le message au réseau
        reseau.relayerMessage(this, message, numeroConnexion);
        return ;

    }

    @Override
    public void recevoir(Message message) {

        String autrePersonne = message.getNumeroDestination();

        // Trouve le cellulaire s'il est connecté à cette antenne
        Cellulaire destination = trouverCellulaire(autrePersonne);
        if (destination != null) {
            destination.recevoir(message);
        }
    }
}