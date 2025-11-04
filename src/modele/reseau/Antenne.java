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

    //Méthode 100% chat --pour l'instant a re-vérifier

    // Gestion d’inscription des cellulaires - dans la liste cellulaires de l'antenne(utile pour 3.3.1)
    public void ajouterCellulaire(Cellulaire c) { cellulaires.add(c); }
    public void enleverCellulaire(Cellulaire c) { cellulaires.remove(c); }
    public List<Cellulaire> getCellulaires() { return cellulaires; } // pratique pour 3.3.2 repondre()

    //Mise à jour connexion (avec this)
    public void mettreAJourConnexion(int numeroConnexion, Antenne ancienne) {
        GestionnaireReseau.getInstance().mettreAJourConnexion(numeroConnexion, ancienne, this);
    }



    //appelle des méthodes implementer de l'interface UniteCellulaire
    @Override
    public int appeler(String numeroAppele,String numeroAppelant,Antenne antenneConnecte){
        return GestionnaireReseau.CODE_NON_CONNECTE;
    }
    @Override
    public Cellulaire repondre(String numeroAppele,String numeroAppelant,int numeroConnexion){
        return null;
    }

    @Override
    public void finAppelLocal(String numeroAppele,int numeroConnexion) {

        // Recoit la demande de fin d'appel
        GestionnaireReseau.getInstance().relayerFinAppel(numeroConnexion, this, numeroAppele);
    }

    @Override
    public void finAppelDistant(String numeroAppele,int numeroConnexion) {

        // Parcourt les cellulaires connectés pour trouver celui avec le meme numero et ensuite signale que le cell a racroché
        for (Cellulaire c : cellulaires) {
            if (c != null && c.comparerNumero(numeroAppele)) {
                c.finAppelDistant(numeroAppele, numeroConnexion);
                break;
            }
        }
    }

    @Override
    public void envoyer(Message message,int numeroConnexion){}

    @Override
    public  void recevoir(Message message){}





}