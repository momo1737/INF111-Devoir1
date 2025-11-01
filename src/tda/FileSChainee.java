package tda;

//classe temporaire selon chat uniquement pour pouvoir lancé le jeu
//mais elle devra être modifier pour répondre aux exigence du prof dans l'annexxe A
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class FileSChainee {
    private LinkedList<Object> elements;

    public FileSChainee() {
        elements = new LinkedList<>();
    }

    // ajoute un élément à la file
    public void enfiler(Object element) {
        elements.addLast(element);
    }

    // retire le premier élément de la file
    public String defiler() throws NoSuchElementException {
        if (elements.isEmpty()) {
            throw new NoSuchElementException("File vide");
        }
        return (String) elements.removeFirst();
    }

    // utile pour debug
    public boolean estVide() {
        return elements.isEmpty();
    }
}
