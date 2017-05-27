package fr.unilim.iut.spaceinvaders;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.unilim.iut.spaceinvaders.model.Dimension;
import fr.unilim.iut.spaceinvaders.model.Position;
import fr.unilim.iut.spaceinvaders.model.SpaceInvaders;

public class FinPartieTest {

	SpaceInvaders spaceinvaders;
	
	@Before
    public void initialisation() {
	    spaceinvaders = new SpaceInvaders(15, 10);
    }
     
	 @Test
	    public void test_CollisionEntreMissileEtEnvahisseur_TermineLaPartie() {
	    	
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(7, 9), 3);
	    	spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(2, 2), new Position(7, 1), 1);
	    	spaceinvaders.tirerUnMissile(new Dimension(1, 2), 1);
	    	
	    	for (int i = 0; i < 6; i++) {
	    		spaceinvaders.deplacerMissile();
	    	}
	    	
	    	Assert.assertTrue(spaceinvaders.etreFini());
	    	
	    }
	 
}
