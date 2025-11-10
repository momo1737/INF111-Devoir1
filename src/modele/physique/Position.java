package modele.physique;

public class Position {
    private double positionX;
    private double positionY;


    //Constructeur
    public Position(double positionX,double positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }

    //Getter et Setter (position X)

    public double getPositionX(){
        return positionX;
    }

    public void setPositionX(double positionX){
        this.positionX=positionX;
    }

    //Getter et Setter (position Y)

    public double getPositionY(){
        return positionY;
    }

    public void setPositionY(double positionY){
        this.positionY = positionY;
    }

    //Calculer la distance euclidienne
    public double distance(Position autre){
        double Dx = autre.positionX- this.positionX;
        double Dy = autre.positionY - this.positionY;
        double sommeDXY = Math.pow(Dx,2) + Math.pow(Dy,2);
        return Math.sqrt(sommeDXY);
    }

    //toString pour afficher la position en format (x; y)
    @Override
    public String toString(){
        return String.format("Postion = (%.2f;%.2f)",positionX,positionY);
    }
}