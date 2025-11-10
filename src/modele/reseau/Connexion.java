package modele.reseau;

public class Connexion {

    //Constante nombre d'antennes
    public static final int NB_ANTENNES = 2;

    //Attributs
    private int numConnexion;

    private Antenne [] tableauAntenne = new Antenne[NB_ANTENNES];

    // Associe la connexion à deux antennes
    public Connexion(int numConnexion,Antenne antenneSource,Antenne antenneDestination) {
        this.numConnexion = numConnexion;

        //La valeur de l'antenne sera la valeur dans l'index (0)
        this.tableauAntenne[0] = antenneSource;

        //La valeur de l'autre antenne sera la valeur dans l'index (1)
        this.tableauAntenne[1] = antenneDestination;
    }

    // Retourne l’autre antenne de la connexion (si présente)
    public Antenne getAutreAntenne(Antenne a) {
        if (a == null) return null;
        if(tableauAntenne[0] == a) return tableauAntenne[1];
        if(tableauAntenne[1] == a) return tableauAntenne[0];
        return null;
    }

    //Getter
    public int getNumConnexion(){
        return numConnexion;
    }

    //Methode equals qui va comparer les numéros de connexion
    @Override
    public boolean equals(Object obj){

        //si les deux objets equivalents return true
        if (this == obj) return true;

        //Verification si l'objet est une connexion et compare
        if (!(obj instanceof Connexion)) return false;
        Connexion autre = (Connexion) obj;
        return this.numConnexion == autre.numConnexion;
    }

    //Mettre à jour une antenne
    public void miseAJourAntenne(Antenne ancienne, Antenne nouvelle) {
        if (tableauAntenne[0] == ancienne) {
            tableauAntenne[0] = nouvelle;
        }

        else if (tableauAntenne[1] == ancienne) {
            tableauAntenne[1] = nouvelle;
        }
    }
}
