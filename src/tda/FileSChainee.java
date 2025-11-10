package tda;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class FileSChainee {
    private LinkedList<Object> elements;

    public FileSChainee() {
        elements = new LinkedList<>();
    }

    //Ajouter un élément à la fin de la file
    public void enfiler(Object element) {
        elements.addLast(element);
    }

    //Retirer le premier élément de la file
    public String defiler() throws NoSuchElementException {
        if (elements.isEmpty()) {
            throw new NoSuchElementException("File vide");
        }
        return (String) elements.removeFirst();
    }

    // Vérifier si la file est vide
    public boolean estVide() {
        return elements.isEmpty();
    }
}