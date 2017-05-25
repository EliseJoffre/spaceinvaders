package fr.unilim.iut.spaceinvaders.model;

public class Collision {

	public boolean detecterCollision(Sprite sprite1, Sprite sprite2) {

		return sprite1.abscisseLaPlusADroite() < sprite2.abscisseLaPlusAGauche() + sprite2.longueur()
				&& sprite1.abscisseLaPlusADroite() + sprite1.longueur() > sprite2.abscisseLaPlusAGauche()
				&& sprite1.ordonneeLaPlusHaute() < sprite2.ordonneeLaPlusBasse() + sprite2.hauteur()
				&& sprite1.hauteur() + sprite1.ordonneeLaPlusHaute() > sprite2.ordonneeLaPlusBasse();

	}

}
