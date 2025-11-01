package modele.reseau;

public interface UniteCellulaire {

    int appeler(String numeroAppele,String numeroAppelant,Antenne antenneConnecte);
    //entier, indiquant le numéro de connexion

    Cellulaire repondre(String numeroAppele,String numeroAppelant,int numeroConnexion);
    //Cellulaire, référence au cellulaire qui répond

    void finAppelLocal(String numeroAppele,int numeroConnexion);
    //aucune sortie

    void finAppelDistant(String numeroAppele,int numeroConnexion);
    //aucune sortie

    void envoyer(Message message,int numeroConnexion);
    //aucune sortie

    void recevoir(Message message);
    //aucune sortie
}
