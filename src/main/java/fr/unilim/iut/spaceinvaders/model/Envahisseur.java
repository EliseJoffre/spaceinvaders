package fr.unilim.iut.spaceinvaders.model;

public class Envahisseur extends Sprite{

	public  Direction direction;

	public Envahisseur(Dimension dimension, Position positionOrigine, int vitesseEnvahisseur) {
	    super(dimension, positionOrigine, vitesseEnvahisseur);
    }
	
	public Direction sensDeplacement() {
		return this.direction;
	}
	
	public void changerSensDeplacement(Direction nouvelleDirection) {
		this.direction = nouvelleDirection;
	}

}
