package modele.physique;
import java.util.Random;
public abstract class ObjetMobile extends ObjetPhysique {
    protected double direction ; //mettre a 0 comme demandé dans l'énoncé
    protected double vitesse;
    protected double deviation;
    private static final Random generateurD = new Random();

    //Constructeur avec la direction excluse
    public ObjetMobile(Position position,double vitesse,double deviation){
        super(position);
        this.direction = 0;
        this.vitesse = vitesse;
        this.deviation = deviation;
    }

    //méthode se déplacer
    public void seDeplacer(){
        direction += generateurD.nextGaussian() * deviation;
        double posX = position.getPositionX() + vitesse * Math.cos(direction);
        double posY = position.getPositionY() + vitesse * Math.sin(direction);
        position = Carte.ajusterToroidal(new Position(posX,posY));
    }


}