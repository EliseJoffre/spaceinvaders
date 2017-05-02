 package fr.unilim.iut.spaceinvaders;

public class SpaceInvaders {

	    private static final char MARQUE_VIDE = '.';
		private static final char MARQUE_VAISSEAU = 'V';
		int largeur;
	    int hauteur;
	    Vaisseau vaisseau;
	   

	    public SpaceInvaders(int longueur, int hauteur) {
		   this.largeur = longueur;
		   this.hauteur = hauteur;
	   }
	    
	    @Override
		public String toString() {
			return recupererEspaceJeuDansChaineASCII();
		}

		public String recupererEspaceJeuDansChaineASCII() {
			StringBuilder espaceDeJeu = new StringBuilder();
			for (int y = 0; y < hauteur; y++) {
				for (int x = 0; x < largeur; x++) {
					espaceDeJeu.append(recupererMarqueDeLaPosition(x, y));
				}
				espaceDeJeu.append('\n');
			}
			return espaceDeJeu.toString();
		}
		
		 public void positionnerUnNouveauVaisseau(int x, int y) {
				
				if (  !estDansEspaceJeu(x, y) )
					throw new HorsEspaceJeuException("Vous êtes en dehors de l'espace jeu");
			
				vaisseau = new Vaisseau(x, y); 
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
			if (vaisseau.abscisse()< (largeur-1)) vaisseau.seDeplacerVersLaDroite();
		}

		public void deplacerVaisseauVersLaGauche() {
			if (vaisseau.abscisse()> 0) vaisseau.seDeplacerVersLaGauche();
			
		}

	    
		
		
	   
	    
	    

   }
