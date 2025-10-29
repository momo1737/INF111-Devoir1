package modele.physique;

public abstract class ObjetPhysique {
    protected Position position;

    //constructeur par paramètre de ObjetPhysique
    public ObjetPhysique(Position position){
        this.position = position;
    }

    //getter pour obtenir la Position (objet) de ObjetPhysique
    public Position getPosition() {
        return position;
    }

    //petite vérification a faire puisque le toString ne s'applique pas lors du test
    @Override
    public String toString(){
        return "Position de l'objet = "+position;
    }
}
