package modele.physique;

public class Position {
    private double positionX;
    private double positionY;


    //Constructeur
    public Position(double positionX,double positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }

    //getter et setter -positionX

    public double getPositionX(){
        return positionX;
    }

    public void setPositionX(double positionX){
        this.positionX=positionX;
    }

    //getter et setter -positionY

    public double getPositionY(){
        return positionY;
    }

    public void setPositionY(double positionY){
        this.positionY = positionY;
    }

    //méthode de la distance euclidienne
    public double distanceE(Position autre){
        double Dx = autre.positionX- this.positionX;
        double Dy = autre.positionY - this.positionY;
        double sommeDXY = Math.pow(Dx,2) + Math.pow(Dy,2);
        return Math.sqrt(sommeDXY);
    }

    //toString pour afficher la position
    @Override
    public String toString(){
        return String.format("Postion = (%.2f;%.2f)",positionX,positionY);
    }
}
