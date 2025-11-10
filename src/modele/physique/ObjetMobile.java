package modele.physique;
import java.util.Random;


public abstract class ObjetMobile extends ObjetPhysique {
    protected double direction;
    protected double vitesse;
    protected double deviation;
    private static final Random generateurD = new Random();

    //Constructeur avec la direction excluse (elle commence toujours à 0)
    public ObjetMobile(Position position,double vitesse,double deviation){
        super(position);
        this.direction = 0;
        this.vitesse = vitesse;
        this.deviation = deviation;
    }

    //Mettre à jour la position selon la direction et la vitesse
    public void seDeplacer(){
        direction += generateurD.nextGaussian() * deviation;
        double posX = position.getPositionX() + vitesse * Math.cos(direction);
        double posY = position.getPositionY() + vitesse * Math.sin(direction);
        position = Carte.ajusterToroidal(new Position(posX,posY));
    }


}