package fr.unilim.iut.spaceinvaders;
import fr.unilim.iut.spaceinvaders.model.*;
import fr.unilim.iut.spaceinvaders.utils.*;
import static org.junit.Assert.*;
import org.junit.*;

    public class SpaceInvadersTest {
	
    	private SpaceInvaders spaceinvaders;

	    @Before
	    public void initialisation() {
		    spaceinvaders = new SpaceInvaders(15, 10);
	    }
	    
	   @Test
	   public void test_AuDebut_JeuSpaceInvaderEstVide() {
		    assertEquals("" + 
		    "...............\n" + 
		    "...............\n" +
		    "...............\n" + 
		    "...............\n" + 
		    "...............\n" + 
		    "...............\n" + 
		    "...............\n" + 
		    "...............\n" + 
		    "...............\n" + 
		    "...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	        }
	   
	   @Test
		public void test_unNouveauVaisseauEstCorrectementPositionneDansEspaceJeu() {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1),new Position(7,9), 1);
			assertEquals("" + 
			"...............\n" + 
			"...............\n" +
			"...............\n" + 
			"...............\n" + 
			"...............\n" + 
			"...............\n" + 
			"...............\n" + 
			"...............\n" + 
			"...............\n" + 
			".......V.......\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
		}
	   
	   
	    @Test(expected = HorsEspaceJeuException.class)
		public void test_unNouveauVaisseauEstPositionneHorsEspaceJeuTropEnBas_UneExceptionEstLevee() throws Exception {
	    	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1),new Position(14,10), 1);
		}
	    
	  
	    @Test
		public void test_UnNouveauVaisseauPositionneHorsEspaceJeu_DoitLeverUneException() {
			
			try {
				spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1),new Position(15,9), 1);
				fail("Position trop à droite : devrait déclencher une exception HorsEspaceJeuException");
			} catch (final HorsEspaceJeuException e) {
			}
			
			
			try {
				spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1),new Position(-1,9), 1);
				fail("Position trop à gauche : devrait déclencher une exception HorsEspaceJeuException");
			} catch (final HorsEspaceJeuException e) {
			}
			
			
			try {
				spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1),new Position(14,10), 1);
				fail("Position trop en bas : devrait déclencher une exception HorsEspaceJeuException");
			} catch (final HorsEspaceJeuException e) {
			}
			
			
			try {
				spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1),new Position(14,-1), 1);
				fail("Position trop à haut : devrait déclencher une exception HorsEspaceJeuException");
			} catch (final HorsEspaceJeuException e) {
			}
				
		}
    
	    @Test
	 	public void test_unNouveauVaisseauAvecDimensionEstCorrectementPositionneDansEspaceJeu() {
	 		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(7,9), 1);
	 		assertEquals("" + 
	 		"...............\n" + 
	 		"...............\n" +
	 		"...............\n" + 
	 		"...............\n" + 
	 		"...............\n" + 
	 		"...............\n" + 
	 		"...............\n" + 
	 		"...............\n" + 
	 		".......VVV.....\n" + 
	 		".......VVV.....\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	 	}
    
    @Test
	public void test_UnNouveauVaisseauPositionneDansEspaceJeuMaisAvecDimensionTropGrande_DoitLeverUneExceptionDeDebordement() {
		
		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(9,2),new Position(7,9), 1);
			fail("Dépassement du vaisseau à droite en raison de sa longueur trop importante : devrait déclencher une exception DebordementEspaceJeuException");
		} catch (final DebordementEspaceJeuException e) {
		}
		
		
		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,4),new Position(7,1), 1);
			fail("Dépassement du vaisseau vers le haut en raison de sa hauteur trop importante : devrait déclencher une exception DebordementEspaceJeuException");
		} catch (final DebordementEspaceJeuException e) {
		}
			
	}
    
    
    @Test
	public void test_VaisseauImmobile_DeplacerVaisseauVersLaDroite() {
		
    	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(12,9), 3);
		spaceinvaders.deplacerVaisseauADroite();
		assertEquals("" + 
		"...............\n" + 
		"...............\n" +
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"............VVV\n" + 
		"............VVV\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
    
    
    @Test
    public void test_VaisseauAvance_DeplacerVaisseauVersLaGauche() {

       spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(7,9), 3);
       spaceinvaders.deplacerVaisseauAGauche();

       assertEquals("" + 
       "...............\n" + 
       "...............\n" +
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "....VVV........\n" + 
       "....VVV........\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
   }
    
    @Test
    public void test_VaisseauImmobile_DeplacerVaisseauVersLaGauche() {

	   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(0,9), 3);
       spaceinvaders.deplacerVaisseauAGauche();

       assertEquals("" + 
       "...............\n" + 
       "...............\n" +
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "VVV............\n" + 
       "VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
     }
    public void test_VaisseauAvance_DeplacerVaisseauVersLaDroite() {

        spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(7,9),3);
        spaceinvaders.deplacerVaisseauADroite();
        assertEquals("" + 
        "...............\n" + 
        "...............\n" +
        "...............\n" + 
        "...............\n" + 
        "...............\n" + 
        "...............\n" + 
        "...............\n" + 
        "...............\n" + 
        "..........VVV..\n" + 
        "..........VVV..\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
    
    @Test
    public void test_VaisseauAvancePartiellement_DeplacerVaisseauVersLaDroite() {

       spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(10,9),3);
       spaceinvaders.deplacerVaisseauADroite();
       assertEquals("" + 
       "...............\n" + 
       "...............\n" +
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "............VVV\n" + 
       "............VVV\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
	
    @Test
    public void test_VaisseauAvancePartiellement_DeplacerVaisseauVersLaGauche() {

       spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(1,9), 3);
       spaceinvaders.deplacerVaisseauAGauche();

       assertEquals("" + 
       "...............\n" + 
       "...............\n" +
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "VVV............\n" + 
       "VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
     }
    
    @Test
    public void test_MissileBienTireDepuisVaisseau_VaisseauLongueurImpaireMissileLongueurImpaire() {

	   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
	   spaceinvaders.tirerUnMissile(new Dimension(3,2),2);

      assertEquals("" + 
      "...............\n" + 
      "...............\n" +
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      "...............\n" + 
      ".......MMM.....\n" + 
      ".......MMM.....\n" + 
      ".....VVVVVVV...\n" + 
      ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
   }
    
    @Test(expected = MissileException.class)
	public void test_PasAssezDePlacePourTirerUnMissile_UneExceptionEstLevee() throws Exception { 
	   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
	   spaceinvaders.tirerUnMissile(new Dimension(7,9),1);
	}
    
    @Test
    public void test_MissileAvanceAutomatiquement_ApresTirDepuisLeVaisseau() {

	   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
	   spaceinvaders.tirerUnMissile(new Dimension(3,2),2);

	   spaceinvaders.deplacerLesMissiles();
	   
       assertEquals("" + 
       "...............\n" + 
       "...............\n" +
       "...............\n" + 
       "...............\n" + 
       ".......MMM.....\n" + 
       ".......MMM.....\n" + 
       "...............\n" + 
       "...............\n" + 
       ".....VVVVVVV...\n" + 
       ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
   }
    
    @Test
    public void test_MissileDisparait_QuandIlCommenceASortirDeEspaceJeu() {

 	   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
 	   spaceinvaders.tirerUnMissile(new Dimension(3,2),1);
 	   for (int i = 1; i <=7 ; i++) {
 		   spaceinvaders.deplacerLesMissiles();
 	   }
 	   
 	   spaceinvaders.deplacerLesMissiles();
 	  
        assertEquals("" +
        "...............\n" + 
        "...............\n" +
        "...............\n" + 
        "...............\n" +
        "...............\n" +
        "...............\n" + 
        "...............\n" +
        "...............\n" + 
        ".....VVVVVVV...\n" + 
        ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
    
   
    @Test
    public void test_LeVaisseauTirePlusieursMissiles() {
    	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(7, 9), 3);
    	spaceinvaders.tirerUnMissile(new Dimension(1, 2), 1);
    	
    	for (int i = 0; i < 4; i++) {
    		spaceinvaders.deplacerLesMissiles();
    	}
    	
    	spaceinvaders.tirerUnMissile(new Dimension(1, 2), 1);
    	
    	
    	assertEquals("" + 
    			"...............\n" + 
    			"...............\n" +
    			"........M......\n" + 
    			"........M......\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"........M......\n" + 
    			"........M......\n" + 
    			".......VVV.....\n" + 
    			".......VVV.....\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
    
    @Test
    public void test_LeVaisseauPeutTirerPlusieursMissilesEtNeSeChevauchentPas() {
    	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(7, 9), 3);
    	spaceinvaders.tirerUnMissile(new Dimension(1, 2), 1);
    	spaceinvaders.deplacerLesMissiles();
    	spaceinvaders.deplacerLesMissiles();
    	spaceinvaders.tirerUnMissile(new Dimension(1, 2), 1);
    	
    	
    	assertEquals("" + 
    			"...............\n" + 
    			"...............\n" +
    			"...............\n" + 
    			"...............\n" + 
    			"........M......\n" + 
    			"........M......\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			".......VVV.....\n" + 
    			".......VVV.....\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
    
    @Test
    public void test_UneLigneDEnvahisseurEstPositionnée() {
    	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(7, 9), 3);
    	spaceinvaders.positionnerUneLigneDEnvahisseurs(3, new Dimension(1, 1), 0, 1);
    	
    	
    	assertEquals("" + 
    			"...E...E...E...\n" + 
    			"...............\n" +
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			".......VVV.....\n" + 
    			".......VVV.....\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
    
    @Test
    public void test_UneLigneDEnvahisseurSeDeplace() {
    	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(7, 9), 3);
    	spaceinvaders.positionnerUneLigneDEnvahisseurs(3, new Dimension(1, 1), 0, 1);
    	spaceinvaders. deplacerLaLigneDEnvahisseur();
    	
    	assertEquals("" + 
    			"....E...E...E..\n" + 
    			"...............\n" +
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			".......VVV.....\n" + 
    			".......VVV.....\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
    
    @Test
    public void test_UneLigneDEnvahisseurChangeDeSensArriverAuBout() {
    	spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(7, 9), 3);
    	spaceinvaders.positionnerUneLigneDEnvahisseurs(3, new Dimension(1, 1), 0, 1);
    	for(int i=0; i<5;i++){
    		spaceinvaders. deplacerLaLigneDEnvahisseur();
        	
    	}
    
    	
    	assertEquals("" + 
    			".....E...E...E.\n" + 
    			"...............\n" +
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			"...............\n" + 
    			".......VVV.....\n" + 
    			".......VVV.....\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
    }
   
    
   

    }