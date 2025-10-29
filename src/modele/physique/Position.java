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
        double Dx = Math.pow(autre.positionX,2) - Math.pow(this.positionX,2);
        double Dy = Math.pow(autre.positionY,2) - Math.pow(this.positionY,2);
        double sommeDXY = Dx + Dy;
        double DE = Math.sqrt(sommeDXY);
        return DE;
    }

    //toString pour afficher la position
    @Override
    public String toString(){
        return String.format("Postion = (%.2f;%.2f)",positionX,positionY);
    }
}
