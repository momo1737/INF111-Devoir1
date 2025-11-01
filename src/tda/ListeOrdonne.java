package tda;

import modele.reseau.Connexion;

public class ListeOrdonne {
    private final Connexion[] data;
    private int n = 0;

    //constructeur

    public ListeOrdonne(int capacite) {
        data = new Connexion[capacite];
    }

    /* recherche binaire par numero
    g = gauche, d = droite, m = milieu
    */

    public Connexion rechercher(int numero) {
        int g = 0, d = n - 1;
        while (g <= d) {
            int m = (g + d) / 2;
            int numM = data[m].getNumConnexion();

            if (numM == numero) return data[m];
            if (numM < numero) g = m + 1;
            else d = m -1;
        }
        return null;
    }


    public boolean inserer(Connexion connexion) {
        if (connexion == null) return false;
        if (n == data.length) return false;

        int numConnexion = connexion.getNumConnexion();

        //Voir si le numero existe
        if (rechercher(numConnexion) != null) return false;

        //Trouver la position ou inserer
        int index = 0;
        while (index <n && data[index].getNumConnexion() < numConnexion) {
            index ++;
        }

        // Décaler à droite
        for (int i = n; i > index; i--) {
            data[i] = data[i - 1];
        }

        //Inserer
        data[index] = connexion;
        n++;
        return true;
    }


}
