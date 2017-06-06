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
	Vaisseau vaisseau;
	Collision collision;
	List<Missile> missiles;
	List<Envahisseur> envahisseurs;
	boolean partieTerminee;
	int nbMissiles;
	int nbEnvahisseurs;
	Direction sensDeDeplacementDeLaLigne;

	public SpaceInvaders(int longueur, int hauteur) {
		this.longueur = longueur;
		this.hauteur = hauteur;
		this.collision = new Collision();
		this.partieTerminee = false;
		this.missiles = new ArrayList<>();
		this.nbMissiles = 0;

		this.envahisseurs = new ArrayList<>();
		this.nbEnvahisseurs = 0;
	}

	@Override
	public String toString() {
		return recupererEspaceJeuDansChaineASCII();
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
		if (this.aUnVaisseauQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_VAISSEAU;
		else if (this.aUnMissileQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_MISSILE;
		else if (this.aUnEnvahisseurQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_ENVAHISSEUR;
		else
			marque = Constante.MARQUE_VIDE;
		return marque;
	}

	private boolean aUnEnvahisseurQuiOccupeLaPosition(int x, int y) {
		boolean occupeLaPosition = false;

		if (this.aUnEnvahisseur()) {
			for (Envahisseur envahisseur : envahisseurs) {
				if (envahisseur.occupeLaPosition(x, y)) {
					occupeLaPosition = true;
				}
			}
		}

		return occupeLaPosition;
	}

	public boolean aUnEnvahisseur() {
		return nbEnvahisseurs != 0;
	}

	private boolean aUnMissileQuiOccupeLaPosition(int x, int y) {
		boolean occupeLaPosition = false;

		if (this.aUnMissile()) {
			for (Missile missile : missiles) {
				if (missile.occupeLaPosition(x, y)) {
					occupeLaPosition = true;
				}
			}
		}

		return occupeLaPosition;
	}

	public boolean aUnMissile() {
		return nbMissiles != 0;
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
		return (((x >= 0) && (x < longueur)) && ((y >= 0) && (y < hauteur)));
	}

	public void deplacerVaisseauADroite() {
		if (vaisseau.abscisseLaPlusADroite() < (longueur - 1)) {
			vaisseau.deplacerHorizontalementVers(Direction.DROITE);
			;
			if (!estDansEspaceJeu(vaisseau.abscisseLaPlusADroite(), vaisseau.ordonneeLaPlusHaute())) {
				vaisseau.positionner(longueur - vaisseau.longueur(), vaisseau.ordonneeLaPlusHaute());
			}
		}
	}

	public void deplacerVaisseauAGauche() {
		if (0 < vaisseau.abscisseLaPlusAGauche())
			vaisseau.deplacerHorizontalementVers(Direction.GAUCHE);
		if (!estDansEspaceJeu(vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusHaute())) {
			vaisseau.positionner(0, vaisseau.ordonneeLaPlusHaute());
		}
	}

	public Vaisseau recupererVaisseau() {
		return this.vaisseau;
	}

	public void initialiserJeu() {
		Position positionVaisseau = new Position(this.longueur / 2, this.hauteur - 1);
		Dimension dimensionVaisseau = new Dimension(Constante.VAISSEAU_LONGUEUR, Constante.VAISSEAU_HAUTEUR);
		positionnerUnNouveauVaisseau(dimensionVaisseau, positionVaisseau, Constante.VAISSEAU_VITESSE);

		Dimension dimensionEnvahisseur = new Dimension(Constante.ENVAHISSEUR_LONGUEUR, Constante.ENVAHISSEUR_HAUTEUR);
		positionnerUneLigneDEnvahisseurs(Constante.NOMBRE_ENVAHISSEURS, dimensionEnvahisseur, Constante.ORDONNEE_LIGNE,
				Constante.ENVAHISSEUR_VITESSE);

	}

	public boolean etreFini() {
		return partieTerminee;

	}

	@Override
	public void evoluer(Commande commande) {
		if (this.aUnMissile())
			deplacerLesMissiles();
		if (this.aUnEnvahisseur())
			deplacerLaLigneDEnvahisseur();
		if (commande.gauche)
			deplacerVaisseauAGauche();
		if (commande.droite)
			deplacerVaisseauADroite();
		if (commande.tir)
			tirerUnMissile(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR),
					Constante.MISSILE_VITESSE);
	}

	public void tirerUnMissile(Dimension dimension, int vitesse) {
		if (dimension.hauteur() + vaisseau.hauteur() > this.hauteur)
			throw new MissileException("Pas assez de hauteur libre entre le vaisseau et le haut de l'espace de jeu.");

		if (!this.aUnMissile() || (this.aUnMissile() && assezDePlacePourUnMissile(dimension))) {
			this.missiles.add(this.vaisseau.tirerUnMissile(dimension, vitesse));
			this.nbMissiles++;
		}
	}

	private boolean assezDePlacePourUnMissile(Dimension dimension) {
		return dimension.hauteur < (vaisseau.ordonneeLaPlusBasse()
				- missiles.get(nbMissiles - 1).ordonneeLaPlusHaute() - 1);
	}

	public void deplacerMissile(Missile missile) {
		missile.deplacerVerticalementVers(Direction.HAUT_ECRAN);
	}

	public void deplacerLesMissiles() {

		int compteurMissiles = 0;

		while (compteurMissiles < nbMissiles) {
			Missile missile = missiles.get(compteurMissiles);
			deplacerMissile(missile);

			if (!estDansEspaceJeu(missile.abscisseLaPlusAGauche(), missile.ordonneeLaPlusHaute())) {
				detruireUnMissile(missile);
			} else if (this.aUnEnvahisseur()) {
				collisionALieu(missile);
			}
			compteurMissiles++;

		}
	}

	private void collisionALieu(Missile missile) {
		int compteurEnvahisseurs = 0;
		while (compteurEnvahisseurs < nbEnvahisseurs) {
			Envahisseur envahisseur = envahisseurs.get(compteurEnvahisseurs);
			if (collision.detecterCollision(missile, envahisseur)) {
				detruireUnMissile(missile);
				detruireUnEnvahisseur(envahisseur);
			}
			if (nbEnvahisseurs == 0) {
				terminerLaPartie();
			}
			compteurEnvahisseurs++;
		}
	}

	public Envahisseur recupererUnEnvahisseur(int numero) {
		return envahisseurs.get(numero);
	}

	public Missile recupererUnMissile(int numero) {
		return missiles.get(numero);
	}

	private void detruireUnMissile(Missile missile) {
		missiles.remove(missile);
		this.nbMissiles--;
	}

	private void detruireUnEnvahisseur(Envahisseur envahisseur) {
		envahisseurs.remove(envahisseur);
		nbEnvahisseurs--;
	}

	private void terminerLaPartie() {

		partieTerminee = true;
	}

	public void positionnerUnNouvelEnvahisseur(Dimension dimension, Position position, int vitesse) {
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

		this.envahisseurs.add(new Envahisseur(dimension, position, vitesse));
		this.nbEnvahisseurs++;
	}

	public List<Missile> recupererTousLesMissiles() {
		return missiles;
	}

	public List<Envahisseur> recupererTousLesEnvahisseurs() {

		return this.envahisseurs;
	}

	public void positionnerUneLigneDEnvahisseurs(int nombreDEnvahisseurs, Dimension dimensionDUnEnvahisseur,
			int ordonneeLigne, int vitesse) {
		int tailleEspace = calculerEspaceEnvahisseurs(nombreDEnvahisseurs, dimensionDUnEnvahisseur);

		int abscissePlacement = 0;
		for (int i = 0; i < nombreDEnvahisseurs; i++) {
			abscissePlacement += tailleEspace;
			positionnerUnNouvelEnvahisseur(dimensionDUnEnvahisseur, new Position(abscissePlacement, ordonneeLigne),
					vitesse);
			abscissePlacement += dimensionDUnEnvahisseur.longueur();
		}
		changerSensDeplacementLigne(Direction.DROITE);
	}

	private int calculerEspaceEnvahisseurs(int nombreDEnvahisseurs, Dimension dimension) {
		int espaceTotalDisponible = this.longueur - (nombreDEnvahisseurs * dimension.longueur());
		int nombreEspaces = nombreDEnvahisseurs + 1;
		return espaceTotalDisponible / nombreEspaces;
	}

	public void deplacerLaLigneDEnvahisseur() {
		if (sensDeDeplacementDeLaLigne == Direction.DROITE) {
			deplacerLaLigneDEnvahisseursADroite();
		} else {
			deplacerLaLigneDEnvahisseursAGauche();
		}
	}

	public void deplacerLaLigneDEnvahisseursAGauche() {
		for (Envahisseur envahisseur : envahisseurs)
			deplacerEnvahisseurAGauche(envahisseur);

		if (!estDansEspaceJeu(envahisseurAGauche().abscisseLaPlusAGauche(),
				envahisseurAGauche().ordonneeLaPlusHaute())) {
			int espaceHorsJeu = Math.abs(envahisseurAGauche().abscisseLaPlusAGauche());
			for (Envahisseur envahisseur : envahisseurs) {
				envahisseur.positionner(envahisseur.abscisseLaPlusAGauche() + espaceHorsJeu,
						envahisseur.ordonneeLaPlusHaute());
				changerSensDeplacementLigne(Direction.DROITE);
			}
		}
	}

	private Envahisseur envahisseurAGauche() {
		return recupererUnEnvahisseur(0);
	}

	public void deplacerEnvahisseurAGauche(Envahisseur envahisseur) {
		if (envahisseur.abscisseLaPlusAGauche() >= 0)
			envahisseur.deplacerHorizontalementVers(Direction.GAUCHE);
	}

	public void deplacerLaLigneDEnvahisseursADroite() {
		for (Envahisseur envahisseur : envahisseurs)
			deplacerUnEnvahisseurADroite(envahisseur);

		if (!estDansEspaceJeu(envahisseurADroite().abscisseLaPlusADroite(),
				envahisseurADroite().ordonneeLaPlusHaute())) {
			int espaceHorsJeu = envahisseurADroite().abscisseLaPlusADroite() - longueur + 1;
			for (Envahisseur envahisseur : envahisseurs) {
				envahisseur.positionner(envahisseur.abscisseLaPlusAGauche() - espaceHorsJeu,
						envahisseur.ordonneeLaPlusHaute());
				changerSensDeplacementLigne(Direction.GAUCHE);
			}
		}
	}

	private Envahisseur envahisseurADroite() {
		return recupererUnEnvahisseur(nbEnvahisseurs - 1);
	}

	private void changerSensDeplacementLigne(Direction direction) {
		sensDeDeplacementDeLaLigne = direction;
	}

	public void deplacerUnEnvahisseurADroite(Envahisseur envahisseur) {
		if (envahisseur.abscisseLaPlusADroite() <= (this.longueur - 1))
			envahisseur.deplacerHorizontalementVers(Direction.DROITE);
	}

}