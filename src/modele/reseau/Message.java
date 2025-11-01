package modele.reseau;

/**
 * Message échangé dans le réseau
 * - numeroDestination : à qui l'envoyer
 * - message : contenu texte
 */

public class Message {
    private  String numeroDestination;
    private  String message;

    public Message(String numeroDestination, String message) {
        this.numeroDestination=numeroDestination;
        this.message=message;

    }
    //Accesseur qui permet d'obtenir la destination du numero
    public String getNumeroDestination() {
        return numeroDestination;
    }
    //Accesseur qui permet d'obtenir le contenu d'un message
    public String getMessage() {
        return message;

    }
    //Phase de test qui retourne le numeroDestination et le message
    @Override
    public String toString(){
        return "Message destination = " + numeroDestination+","+"Message:"+message;
    }

}
