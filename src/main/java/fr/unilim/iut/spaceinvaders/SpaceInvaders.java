 package fr.unilim.iut.spaceinvaders;

import fr.unilim.iut.spaceinvaders.utils.HorsEspaceJeuException;


public class SpaceInvaders {

	    private static final char MARQUE_FIN_LIGNE = '\n';
		private static final char MARQUE_VIDE = '.';
		private static final char MARQUE_VAISSEAU = 'V';
		int largeur;
	    int hauteur;
	    Vaisseau vaisseau;
	   

	 
	    public SpaceInvaders(int longueur, int hauteur) {
		   this.largeur = longueur;
		   this.hauteur = hauteur;
	   }
	    
		public String recupererEspaceJeuDansChaineASCII() {
			StringBuilder espaceDeJeu = new StringBuilder();
			for (int y = 0; y < hauteur; y++) {
				for (int x = 0; x < largeur; x++) {
					espaceDeJeu.append(recupererMarqueDeLaPosition(x, y));
				}
				espaceDeJeu.append(MARQUE_FIN_LIGNE);
			}
			return espaceDeJeu.toString();
		}
		

		private boolean estDansEspaceJeu(int x, int y) {
			return ((x >= 0) && (x < largeur)) && ((y >= 0) && (y < hauteur));
		}
		 
		private char recupererMarqueDeLaPosition(int x, int y) {
			char marque;
			  if (this.aUnVaisseauQuiOccupeLaPosition(x, y))
			      marque=MARQUE_VAISSEAU;
			  else
			      marque=MARQUE_VIDE;
			return marque;
		}

		private boolean aUnVaisseauQuiOccupeLaPosition(int x, int y) {
			return this.aUnVaisseau() && vaisseau.occupeLaPosition(x, y);
		}

		private boolean aUnVaisseau() {
			return vaisseau!=null;
		}

		public void deplacerVaisseauVersLaDroite() {
			if (vaisseau.abscisseLaPlusADroite() < (longueur - 1))
				vaisseau.seDeplacerVersLaDroite();
		}

		public void deplacerVaisseauVersLaGauche() {
			if (vaisseau.abscisseLaPlusAGauche()> 0) vaisseau.seDeplacerVersLaGauche();
			
		}

		public void positionnerUnNouveauVaisseau(int longueur, int hauteur, int x, int y) {
			if (!estDansEspaceJeu(x, y))
				throw new HorsEspaceJeuException("La position du vaisseau est en dehors de l'espace jeu");

			vaisseau = new Vaisseau(longueur, hauteur);
			vaisseau.positionner(x, y);
		}

	    
		
		
	   
	    
	    

   }