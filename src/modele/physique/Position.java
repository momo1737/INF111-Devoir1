package modele.physique;

public class Position {
    private double positionX;
    private double positionY;

    //Creation du constructeur par parametre
    public Position (double positionX,double positionY){
        this.positionX=positionX;
        this.positionY=positionY;
    }

//Creation informateur et mutateur

    //getter
    public double getPositionX(){
        return positionX;}

    public double getPositionY(){
        return positionY;}

    //setter
    public void setPositionX(double positionX){
        this.positionX=positionX;}

    public void setPositionY(double positionY){
        this.positionY=positionY;}

    //Methode qui retourne la distance entre deux points
    public double distancePoint(Position pointRecu){
        double distanceX= positionX-pointRecu.getPositionX();
        double distanceY= positionY-pointRecu.getPositionY();
        return Math.sqrt((distanceX*distanceX) + (distanceY*distanceY));}

    //Methode toString??
    @Override
    public String toString(){
        return String.format("(%.2f, %.2f)", positionX, positionY);
    }
}
