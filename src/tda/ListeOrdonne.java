package tda;

import modele.reseau.Connexion;

import javax.sql.ConnectionPoolDataSource;

public class ListeOrdonne {
    private final Connexion[] donnee;
    private int n = 0;

    //constructeur

    public ListeOrdonne(int capacite) {
        donnee = new Connexion[capacite];
    }

    public  Connexion[] getDonnee(){return this.donnee;}
    // recherche binaire par numero

    public Connexion rechercher(int numero) {
        int gauche = 0, droite = n - 1;
        while (gauche <= droite) {
            int centre = (gauche + droite) / 2;
            int numeroCentre = donnee[centre].getNumConnexion();

            if (numeroCentre == numero) return donnee[centre];
            if (numeroCentre < numero) gauche = centre + 1;
            else droite = centre -1;
        }
        return null;
    }


    public boolean inserer(Connexion connexion) {
        if (connexion == null) return false;
        if (n == donnee.length) return false;

        int numConnexion = connexion.getNumConnexion();

        //Voir si le numero existe
        if (rechercher(numConnexion) != null) return false;

        //Trouver la position ou inserer
        int index = 0;
        while (index <n && donnee[index].getNumConnexion() < numConnexion) {
            index ++;
        }

        // Décaler à droite
        for (int i = n; i > index; i--) {
            donnee[i] = donnee[i - 1];
        }

        //Inserer
        donnee[index] = connexion;
        n++;
        return true;
    }

    //nom de méthode a revoir... anglais/francais
    // Supprime avec le numéro de connexion
    public boolean supprimer(int numero) {
        int g = 0, d = n - 1;

        // Recherche binaire pour trouver l'index du numéro
        while (g <= d) {
            int m = (g + d) / 2;
            int numM = data[m].getNumConnexion();//surement a remplacer par donnee dans les attribut de la liste

            if (numM == numero) {
                // Décaler à gauche à partir de m
                for (int i = m; i < n - 1; i++) {
                    data[i] = data[i + 1];
                }

                // Nettoyer la dernière case et réduire la taille
                data[n - 1] = null;
                n--;
                return true;
            }
            if (numM < numero) g = m + 1;
            else d = m - 1;
        }

        return false;
    }



}
