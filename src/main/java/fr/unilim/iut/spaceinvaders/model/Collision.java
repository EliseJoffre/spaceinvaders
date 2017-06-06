package fr.unilim.iut.spaceinvaders.model;

public class Collision {
	public boolean detecterCollision(Sprite sprite1, Sprite sprite2) {

		return abscisseSprite1EgaleAbscisseSprite2(sprite1, sprite2)
				&& ordonneeSprite1EgaleOrdonneeSprite2(sprite1, sprite2);

	}

	public boolean abscisseSprite1EgaleAbscisseSprite2(Sprite sprite1, Sprite sprite2) {
		return sprite1.abscisseLaPlusADroite() < sprite2.abscisseLaPlusAGauche() + sprite2.longueur()
				&& sprite1.abscisseLaPlusADroite() + sprite1.longueur() > sprite2.abscisseLaPlusAGauche();
	}

	public boolean ordonneeSprite1EgaleOrdonneeSprite2(Sprite sprite1, Sprite sprite2) {
		return sprite1.ordonneeLaPlusHaute() < sprite2.ordonneeLaPlusBasse() + sprite2.hauteur()
				&& sprite1.hauteur() + sprite1.ordonneeLaPlusHaute() > sprite2.ordonneeLaPlusBasse();
	}

}