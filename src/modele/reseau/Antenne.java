package modele.reseau;

import modele.physique.ObjetPhysique;
import modele.physique.Position;
import java.util.ArrayList;
import java.util.List;

public class Antenne extends ObjetPhysique implements UniteCellulaire {
    private final GestionnaireReseau reseau;
    private final List<Cellulaire> cellulaires = new ArrayList<>();

    //Constructeur de l'antenne
    public Antenne(Position position) {
        super(position);
        this.reseau = GestionnaireReseau.getInstance();
    }


    //methode qui permet d'obtenir la distance entre une Cellulaire et une Antenne
    public double distance (Position autre){
        return this.position.distance(autre);
    }

    //méthode toString pour afficher la position de l'Antenne
    @Override
    public String toString() {
        return "Antenne{" + position + "}";
    }

    //===========================================================================================================
    //Méthode 100% chat --pour l'instant a re-vérifier

    // Gestion d’inscription des cellulaires - dans la liste cellulaires de l'antenne(utile pour 3.3.1)
    public void ajouterCellulaire(Cellulaire c) { cellulaires.add(c); }
    public void enleverCellulaire(Cellulaire c) { cellulaires.remove(c); }
    public List<Cellulaire> getCellulaires() { return cellulaires; } // pratique pour 3.3.2 repondre()

    //===========================================================================================================

    //Raytoch---------------

    //Mise à jour connexion (avec this)
    public void mettreAJourConnexion(int numeroConnexion, Antenne ancienne) {
        GestionnaireReseau.getInstance().mettreAJourConnexion(numeroConnexion, ancienne, this);
    }



    //appelle des méthodes implementer de l'interface UniteCellulaire
    @Override
    public int appeler(String numeroAppele,String numeroAppelant,Antenne antenneConnecte){
        return GestionnaireReseau.relayerAppel(numeroAppele,numeroAppelant,antenneConnecte);
    }

    @Override
    public Cellulaire repondre(String numeroAppele,String numeroAppelant,int numeroConnexion){
        int nbCellulaire=cellulaires.size();

        //on parcours les cellulaires enregistre a lantenne
        for (int i=0;i<nbCellulaire;i++){
            Cellulaire cellulairePotentiel =cellulaires.get(i);

            //On compare avec la methode compareNumero si cellulaire potentiel correspond alors on utilise methode repondre sinon retourne nul
            if(cellulairePotentiel.comparerNumero(numeroAppele)){
                return cellulairePotentiel.repondre(numeroAppele,numeroAppelant,numeroConnexion);}
        }
        return null;


    }

    @Override
    public void finAppelLocal(String numeroAppele,int numeroConnexion){ }

    @Override
    public void finAppelDistant(String numeroAppele,int numeroConnexion){}

    @Override
    public boolean envoyer(Message message, int numeroConnexion) {
        return reseau.relayerMessage(this, message, numeroConnexion);
    }
    /*problème identique a la methode envoyer message de la classe Cellulaires
      entre le boolean et la valeur retourner
    */
    @Override
    public void recevoir(Message message) {

        String autrePersonne = message.getNumeroDestination();
        Cellulaire destination = trouverCellulaire(autrePersonne);
        if (destination != null) {
            destination.recevoir(message);


            // PARTIE RAYANE EL AHMADI//

        }
    }
}
