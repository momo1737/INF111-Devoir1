package modele.physique;

public abstract class ObjetPhysique {
    protected Position position;

    //Constructeur par paramètre de ObjetPhysique
    public ObjetPhysique(Position position){
        this.position = position;
    }

    //Getter pour obtenir la position de l'objet
    public Position getPosition() {
        return position;
    }

    //Affiche la position
    @Override
    public String toString(){
        return "Position de l'objet = "+position;
    }
}