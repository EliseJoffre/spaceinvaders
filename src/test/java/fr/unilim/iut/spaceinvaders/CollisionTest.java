package fr.unilim.iut.spaceinvaders;

import static org.junit.Assert.*;
import org.junit.*;

import fr.unilim.iut.spaceinvaders.model.Collision;
import fr.unilim.iut.spaceinvaders.model.Dimension;
import fr.unilim.iut.spaceinvaders.model.Envahisseur;
import fr.unilim.iut.spaceinvaders.model.Missile;
import fr.unilim.iut.spaceinvaders.model.Position;
import fr.unilim.iut.spaceinvaders.model.SpaceInvaders;
import fr.unilim.iut.spaceinvaders.model.Sprite;

public class CollisionTest {

	private SpaceInvaders spaceinvaders;
	private Collision collision;

	@Before
	public void initialisation() {
		spaceinvaders = new SpaceInvaders(15, 10);
		collision = new Collision();
	}

	// Tests lorsqu'il y a une collision entre le missile et l'envahisseur
	@Test
	public void test_detecterUneCollisionEntreUnEnvahisseurEtUnMissileAuCentreEnvahisseur() {

		Envahisseur envahisseur = new Envahisseur(new Dimension(3, 2), new Position(7, 1), 1);
		Missile missile = new Missile(new Dimension(1, 1), new Position(8, 1), 1);
		assertTrue(collision.detecterCollision(missile, envahisseur));
		
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(7,9), 1);
		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(7, 1), 1);
		spaceinvaders.tirerUnMissile(new Dimension(1, 1), 1);
		
		for(int i=0;i<6;i++){
			spaceinvaders.deplacerMissile();
		}
 		assertEquals("" + 
 		".......EEE.....\n" + 
 		".......EME.....\n" +
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
	public void test_detecterUneCollisionEntreUnEnvahisseurEtUnMissileLePlusAGaucheDeEnvahisseur() {

		Envahisseur envahisseur = new Envahisseur(new Dimension(3, 2), new Position(9, 1), 1);
		Missile missile = new Missile(new Dimension(1, 1), new Position(9, 1), 1);
		assertTrue(collision.detecterCollision(missile, envahisseur));
		
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(7,9), 1);
		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(8, 1), 1);
		spaceinvaders.tirerUnMissile(new Dimension(1, 1), 1);
		
		for(int i=0;i<6;i++){
			spaceinvaders.deplacerMissile();
		}
 		assertEquals("" + 
 		"........EEE....\n" + 
 		"........MEE....\n" +
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
	public void test_detecterUneCollisionEntreUnEnvahisseurEtUnMissileLePlusADroiteDeEnvahisseur() {

		Envahisseur envahisseur = new Envahisseur(new Dimension(3, 2), new Position(7, 1), 1);
		Missile missile = new Missile(new Dimension(1, 1), new Position(9, 1), 1);
		assertTrue(collision.detecterCollision(missile, envahisseur));
		
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(7,9), 1);
		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(6, 1), 1);
		spaceinvaders.tirerUnMissile(new Dimension(1, 1), 1);
		
		for(int i=0;i<6;i++){
			spaceinvaders.deplacerMissile();
		}
 		assertEquals("" + 
 		"......EEE......\n" + 
 		"......EEM......\n" +
 		"...............\n" + 
 		"...............\n" + 
 		"...............\n" + 
 		"...............\n" + 
 		"...............\n" + 
 		"...............\n" + 
 		".......VVV.....\n" + 
 		".......VVV.....\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());

	}
	
	// Tests lorsqu'il y n'y a pas de collision entre le missile et l'envahisseur
	
	
	@Test
	public void test_leMissileEstTropBas() {

		Envahisseur envahisseur = new Envahisseur(new Dimension(3, 2), new Position(7, 1), 1);
		Missile missile = new Missile(new Dimension(1, 1), new Position(8, 3), 1);
		assertFalse(collision.detecterCollision(missile, envahisseur));

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(7,9), 1);
		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(7, 1), 1);
		spaceinvaders.tirerUnMissile(new Dimension(1, 1), 1);
		
		for(int i=0;i<4;i++){
			spaceinvaders.deplacerMissile();
		}
 		assertEquals("" + 
 		".......EEE.....\n" + 
 		".......EEE.....\n" +
 		"...............\n" + 
 		"........M......\n" + 
 		"...............\n" + 
 		"...............\n" + 
 		"...............\n" + 
 		"...............\n" + 
 		".......VVV.....\n" + 
 		".......VVV.....\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	
	@Test
	public void test_leMissileEstTropADroite() {

		Envahisseur envahisseur = new Envahisseur(new Dimension(3, 2), new Position(7, 1), 1);
		Missile missile = new Missile(new Dimension(1, 1), new Position(8, 3), 1);
		assertFalse(collision.detecterCollision(missile, envahisseur));

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(10,9), 1);
		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(7, 1), 1);
		spaceinvaders.tirerUnMissile(new Dimension(1, 1), 1);
		
		for(int i=0;i<6;i++){
			spaceinvaders.deplacerMissile();
		}
 		assertEquals("" + 
 		".......EEE.....\n" + 
 		".......EEE.M...\n" +
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
	
	public void test_leMissileEstTropAGauche() {

		Envahisseur envahisseur = new Envahisseur(new Dimension(3, 2), new Position(7, 1), 1);
		Missile missile = new Missile(new Dimension(1, 1), new Position(8, 3), 1);
		assertFalse(collision.detecterCollision(missile, envahisseur));

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(3,9), 1);
		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(7, 1), 1);
		spaceinvaders.tirerUnMissile(new Dimension(1, 1), 1);
		
		for(int i=0;i<6;i++){
			spaceinvaders.deplacerMissile();
		}
 		assertEquals("" + 
 		".......EEE.....\n" + 
 		"....M..EEE.....\n" +
 		"...............\n" + 
 		"...............\n" + 
 		"...............\n" + 
 		"...............\n" + 
 		"...............\n" + 
 		"...............\n" + 
 		"...VVV.........\n" + 
 		"...VVV.........\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
}