package fr.unilim.iut.spaceinvaders.model;

import java.util.ArrayList;
import java.util.List;

import fr.unilim.iut.spaceinvaders.moteurjeu.Commande;
import fr.unilim.iut.spaceinvaders.moteurjeu.Jeu;
import fr.unilim.iut.spaceinvaders.utils.DebordementEspaceJeuException;
import fr.unilim.iut.spaceinvaders.utils.HorsEspaceJeuException;
import fr.unilim.iut.spaceinvaders.utils.MissileException;

public class SpaceInvaders implements Jeu {

	int longueur;
	int hauteur;
	boolean partieFinie;
	Vaisseau vaisseau;
	List<Missile> missiles;
	int nombreDeMissiles;
	Envahisseur envahisseur;
	Collision collision;

	public SpaceInvaders(int longueur, int hauteur) {
		this.longueur = longueur;
		this.hauteur = hauteur;
		this.collision = new Collision();
		this.partieFinie = false;
		this.missiles = new ArrayList<>();
		this.nombreDeMissiles = 0;
	}

	public void initialiserJeu() {
		Position positionVaisseau = new Position(this.longueur / 2, this.hauteur - 1);
		Dimension dimensionVaisseau = new Dimension(Constante.VAISSEAU_LONGUEUR, Constante.VAISSEAU_HAUTEUR);
		Position positionEnvahisseur = new Position(this.longueur / 5 - 140, this.hauteur / 5 - 20);
		Dimension dimensionEnvahisseur = new Dimension(Constante.ENVAHISSEUR_LONGUEUR, Constante.ENVAHISSEUR_HAUTEUR);
		
		positionnerUnNouvelEnvahisseur(dimensionEnvahisseur, positionEnvahisseur, Constante.ENVAHISSEUR_VITESSE);
		positionnerUnNouveauVaisseau(dimensionVaisseau, positionVaisseau, Constante.VAISSEAU_VITESSE);
	}

	public String recupererEspaceJeuDansChaineASCII() {
		StringBuilder espaceDeJeu = new StringBuilder();
		for (int y = 0; y < hauteur; y++) {
			for (int x = 0; x < longueur; x++) {
				espaceDeJeu.append(recupererMarqueDeLaPosition(x, y));
			}
			espaceDeJeu.append(Constante.MARQUE_FIN_LIGNE);
		}
		return espaceDeJeu.toString();
	}

	private char recupererMarqueDeLaPosition(int x, int y) {
		char marque;
		if (this.aUnVaisseauQuiOccupeLaPosition(x, y)) {
			marque = Constante.MARQUE_VAISSEAU;
		} else {
			if (this.aUnMissileQuiOccupeLaPosition(x, y)) {

				marque = Constante.MARQUE_MISSILE;

			} else {
				if (this.aUnEnvahisseurQuiOccupeLaPosition(x, y)) {
					marque = Constante.MARQUE_ENVAHISSEUR;

				} else {

					marque = Constante.MARQUE_VIDE;

				}

			}
		}
		return marque;
	}

	private boolean aUnEnvahisseurQuiOccupeLaPosition(int x, int y) {
		return this.aUnEnvahisseur() && envahisseur.occupeLaPosition(x, y);
	}

	public boolean aUnEnvahisseur() {
		return envahisseur != null;
	}

	private boolean aUnMissileQuiOccupeLaPosition(int x, int y) {

		boolean missileEstPositionne = false;
		
		if (this.aUnMissile()) {
			for (Missile missile : missiles) {
				if (missile.occupeLaPosition(x, y)) {
					missileEstPositionne = true;
				}
			}
		}
				
		return missileEstPositionne;
	}

	public boolean aUnMissile() {
		return nombreDeMissiles != 0;
	}

	private boolean aUnVaisseauQuiOccupeLaPosition(int x, int y) {
		return this.aUnVaisseau() && vaisseau.occupeLaPosition(x, y);
	}

	public boolean aUnVaisseau() {
		return vaisseau != null;
	}

	public void positionnerUnNouveauVaisseau(Dimension dimension, Position position, int vitesse) {

		int x = position.abscisse();
		int y = position.ordonnee();

		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position du vaisseau est en dehors de l'espace jeu");

		int longueurVaisseau = dimension.longueur();
		int hauteurVaisseau = dimension.hauteur();

		if (!estDansEspaceJeu(x + longueurVaisseau - 1, y))
			throw new DebordementEspaceJeuException(
					"Le vaisseau déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurVaisseau + 1))
			throw new DebordementEspaceJeuException(
					"Le vaisseau déborde de l'espace jeu vers le bas à cause de sa hauteur");

		vaisseau = new Vaisseau(dimension, position, vitesse);
	}

	private boolean estDansEspaceJeu(int x, int y) {
		return ((x >= 0) && (x < longueur)) && ((y >= 0) && (y < hauteur));
	}

	public void deplacerVaisseauVersLaDroite() {
		if (vaisseau.abscisseLaPlusADroite() < (longueur - 1)) {
			vaisseau.deplacerHorizontalementVers(Direction.DROITE);
			if (!estDansEspaceJeu(vaisseau.abscisseLaPlusADroite(), vaisseau.ordonneeLaPlusHaute())) {
				vaisseau.positionner(longueur - vaisseau.longueur(), vaisseau.ordonneeLaPlusHaute());
			}
		}
	}

	public void deplacerVaisseauVersLaGauche() {
		if (0 < vaisseau.abscisseLaPlusAGauche())
			vaisseau.deplacerHorizontalementVers(Direction.GAUCHE);
		if (!estDansEspaceJeu(vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusHaute())) {
			vaisseau.positionner(0, vaisseau.ordonneeLaPlusHaute());
		}
	}

	public Vaisseau recupererVaisseau() {
		return this.vaisseau;
	}

	public void evoluer(Commande commandeUser) {

		if (commandeUser.gauche) {
			deplacerVaisseauVersLaGauche();
		}

		if (commandeUser.droite) {
			deplacerVaisseauVersLaDroite();
		}

		if (commandeUser.tir) {
			tirerUnMissile(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR),
					Constante.MISSILE_VITESSE);

		}

		if (this.aUnMissile()) {
			deplacerMissile();

		}

		if (this.aUnEnvahisseur()) {
			deplacerAutomatiquementEnvahisseur();
		}

	}


	private void deplacerAutomatiquementEnvahisseur() {
		if (envahisseur.sensDeplacement() == Direction.DROITE) {
			deplacerEnvahisseurVersLaDroite();
		} else { 
			deplacerEnvahisseurVersLaGauche();
		}

	}

	public boolean etreFini() {
		return partieFinie;
	}

	public void tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {

		if ((vaisseau.hauteur() + dimensionMissile.hauteur()) > this.hauteur)
			throw new MissileException(
					"Pas assez de hauteur libre entre le vaisseau et le haut de l'espace jeu pour tirer le missile");

		if (!this.aUnMissile() || (this.aUnMissile() && peutTirerUnMissile(dimensionMissile))) {
			this.missiles.add(this.vaisseau.tirerUnMissile(dimensionMissile, vitesseMissile));
			this.nombreDeMissiles++;
		}

	}

	public List<Missile> recupererTousLesMissiles() {
		return missiles;
	}

	private Missile recupererUnMissile(int numero) {
		return this.missiles.get(numero);
	}


	public void deplacerMissile() {
		for (int i = 0; i < nombreDeMissiles; i++) {
			
			Missile missile = recupererUnMissile(i);
			missile.deplacerVerticalementVers(Direction.HAUT_ECRAN);
			
			if (!estDansEspaceJeu(missile.abscisseLaPlusAGauche(), missile.ordonneeLaPlusHaute())) {
				detruireMissile(missile);
			} else if (collisionEntreMissileEtEnvahisseur(missile)) {
				detruireMissile(missile);
				this.envahisseur = null;
				terminerLaPartie();
			}
		}
	}
	
	private boolean collisionEntreMissileEtEnvahisseur(Missile missile) {
		return this.aUnEnvahisseur() && collision.detecterCollision(missile, envahisseur);
	}
	
	private void terminerLaPartie() {
		 partieFinie = true;
	}

	public void positionnerUnNouvelEnvahisseur(Dimension dimension, Position position, int vitesseEnvahisseur) {
		int x = position.abscisse();
		int y = position.ordonnee();

		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position de l'envahisseur est en dehors de l'espace jeu");

		int longueurEnvahisseur = dimension.longueur();
		int hauteurEnvahisseur = dimension.hauteur();

		if (!estDansEspaceJeu(x + longueurEnvahisseur - 1, y))
			throw new DebordementEspaceJeuException(
					"L'envahisseur déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurEnvahisseur + 1))
			throw new DebordementEspaceJeuException(
					"L'envahisseur déborde de l'espace jeu vers le bas à cause de sa hauteur");

		envahisseur = new Envahisseur(dimension, position, vitesseEnvahisseur);

	}

	public Envahisseur recupererEnvahisseur() {
		return this.envahisseur;
	}

	public void deplacerEnvahisseurVersLaGauche() {
		if (0 < envahisseur.abscisseLaPlusAGauche()) {
			envahisseur.deplacerHorizontalementVers(Direction.GAUCHE);

		}

		if (!estDansEspaceJeu(envahisseur.abscisseLaPlusAGauche(), envahisseur.ordonneeLaPlusHaute())) {
			envahisseur.positionner(0, envahisseur.ordonneeLaPlusHaute());
			envahisseur.changerSensDeplacement(Direction.DROITE);
		}

	}

	public void deplacerEnvahisseurVersLaDroite() {
		if (envahisseur.abscisseLaPlusADroite() < (longueur - 1)) {
			envahisseur.deplacerHorizontalementVers(Direction.DROITE);
			if (!estDansEspaceJeu(envahisseur.abscisseLaPlusADroite(), envahisseur.ordonneeLaPlusHaute())) {
				envahisseur.positionner(longueur - envahisseur.longueur(), envahisseur.ordonneeLaPlusHaute());
				envahisseur.changerSensDeplacement(Direction.GAUCHE);
			}
		}

	}
	private boolean peutTirerUnMissile(Dimension dimensionMissile) {
		return dimensionMissile.hauteur < (vaisseau.ordonneeLaPlusBasse() - missiles.get(nombreDeMissiles - 1).ordonneeLaPlusHaute() - 1);
	}

	private void detruireMissile(Missile missile) {
		missiles.remove(missile);
		this.nombreDeMissiles--;
	}
	

}