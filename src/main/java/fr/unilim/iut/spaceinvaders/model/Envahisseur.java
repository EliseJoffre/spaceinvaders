package fr.unilim.iut.spaceinvaders.model;

public class Envahisseur extends Sprite{

	public  Direction direction;

	public Envahisseur(Dimension dimension, Position positionOrigine, int vitesseEnvahisseur) {
	    super(dimension, positionOrigine, vitesseEnvahisseur);
	    direction = Direction.GAUCHE;
		direction = Direction.DROITE;
	    
    }
	
	public Direction sensDeplacement() {
		return this.direction;
		
	}
	
	public void changerSensDeplacement(Direction nouvelleDirection) {
		this.direction = nouvelleDirection;
	}

}
