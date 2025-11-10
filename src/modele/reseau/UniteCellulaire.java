package modele.reseau;

public interface UniteCellulaire {

    //Lance un appel et retourne le numéro de connexion créé
    int appeler(String numeroAppele,String numeroAppelant,Antenne antenneConnecte);

    //Répond à un appel et retourne le cellulaire qui accepte l'appel
    Cellulaire repondre(String numeroAppele,String numeroAppelant,int numeroConnexion);

    //Terminer un appel localement
    void finAppelLocal(String numeroAppele,int numeroConnexion);

    //Terminer un appel de l'autre côté
    void finAppelDistant(String numeroAppele,int numeroConnexion);

    //Envoyer un message
    void envoyer(Message message,int numeroConnexion);

    //Recevoir un message
    void recevoir(Message message);

}