package fr.unilim.iut.spaceinvaders.model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import fr.unilim.iut.spaceinvaders.moteurjeu.DessinJeu;

public class DessinSpaceInvaders implements DessinJeu {

	private SpaceInvaders jeu;

	public DessinSpaceInvaders(SpaceInvaders spaceInvaders) {
		this.jeu = spaceInvaders;
	}

	public void dessiner(BufferedImage im) {
		String jeu = this.jeu.recupererEspaceJeuDansChaineASCII();
		this.dessinerUnFond(jeu, im);
		if (this.jeu.aUnEnvahisseur()) {
			List<Envahisseur> envahisseurs = this.jeu.recupererTousLesEnvahisseurs();
			for (Envahisseur envahisseur : envahisseurs) {
				this.dessinerUnEnvahisseur(envahisseur, im);
			}
		}

		if (this.jeu.aUnVaisseau()) {
			Vaisseau vaisseau = this.jeu.recupererVaisseau();
			this.dessinerUnVaisseau(vaisseau, im);
		}

		if (this.jeu.aUnMissile()) {
			List<Missile> missiles = this.jeu.recupererTousLesMissiles();
			for (Missile missile : missiles) {
				this.dessinerUnMissile(missile, im);
			}
		}
	}

	private void dessinerUnVaisseau(Vaisseau vaisseau, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("./img/vaisseau.png");
		crayon.drawImage(image, vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusBasse(), vaisseau.longueur(),
				vaisseau.hauteur(), null);

	}

	private void dessinerUnMissile(Missile missile, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("./img/missile.png");
		crayon.drawImage(image, missile.abscisseLaPlusAGauche(), missile.ordonneeLaPlusBasse(), missile.longueur(),
				missile.hauteur(), null);

	}

	private void dessinerUnEnvahisseur(Envahisseur envahisseur, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("./img/envahisseur.png");
		crayon.drawImage(image, envahisseur.abscisseLaPlusAGauche(), envahisseur.ordonneeLaPlusBasse(),
				envahisseur.longueur(), envahisseur.hauteur(), null);

	}

	private void dessinerUnFond(String jeu2, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("./img/fond.png");
		crayon.drawImage(image, 0, 0, 750, 650, null);

	}

}