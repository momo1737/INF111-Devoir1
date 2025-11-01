package modele.reseau;

public class Connexion {

    //Constante nombre dantennes
    public static final int NB_ANTENNES=2;

    //Attributs
    private int numConnexion;

    private Antenne [] tableauAntenne = new Antenne[NB_ANTENNES];

    //Constructeur par parametre
    public Connexion(int numConnexion,Antenne antenne,Antenne antenneDeux) {

        this.numConnexion = numConnexion;

        //valeur de lantenne sera la valeur dans lindex 0
        this.tableauAntenne[0] = antenne;

        //valeur de lantenneAutre sera la valeur dans lindex 1
        this.tableauAntenne[1] = antenneDeux;
    }

    //Passons aux services

    //getter
    public int getNumConnexion(){
        return numConnexion;}

    //Methode equals qui va comparer les numeros de connexion
    @Override
    public boolean equals(Object obj){
        //si les deux objets equivalents return true
        if (this == obj) return true;
        //Verification si lobjet est bien une connexion et comparer
        if (!(obj instanceof Connexion)) return false;
        Connexion autre = (Connexion) obj;
        return this.numConnexion == autre.numConnexion;}



}
