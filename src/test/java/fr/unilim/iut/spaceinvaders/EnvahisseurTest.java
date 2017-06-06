package fr.unilim.iut.spaceinvaders;

import fr.unilim.iut.spaceinvaders.model.Dimension;

import fr.unilim.iut.spaceinvaders.model.Position;
import fr.unilim.iut.spaceinvaders.model.SpaceInvaders;
import fr.unilim.iut.spaceinvaders.utils.*;
import static org.junit.Assert.*;

import org.junit.*;

public class EnvahisseurTest {

	private SpaceInvaders spaceinvaders;

	@Before
	public void initialisation() {
		spaceinvaders = new SpaceInvaders(15, 10);
	}

	@Test
	public void test_unNouveauEnvahisseurAvecDimensionEstCorrectementPositionneDansEspaceJeu() {
		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(7, 9), 1);
 		assertEquals("" + 
 		"...............\n" + 
 		"...............\n" +
 		"...............\n" + 
 		"...............\n" + 
 		"...............\n" + 
 		"...............\n" + 
 		"...............\n" + 
 		"...............\n" + 
 		".......EEE.....\n" + 
 		".......EEE.....\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void test_unNouveauEnvahisseurAvecDimensionEtUnNouveauVaisseauSontCorrectementPositionneDansEspaceJeu() {
		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(7, 1), 1);
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(7, 9), 1);
		assertEquals("" + 
		".......EEE.....\n" +
		".......EEE.....\n" +
		"...............\n" + 
		"...............\n" +
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		".......VVV.....\n" + 
		".......VVV.....\n"  , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test(expected = HorsEspaceJeuException.class)
	public void test_unNouvelEnvahisseurEstPositionneHorsEspaceJeuTropEnBas_UneExceptionEstLevee() throws Exception {
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1, 1), new Position(14, 10), 1);
	}

	@Test
	public void test_unNouvelEnvahisseurPositionneHorsEspaceJeu_DoitLeverUneException() {

		try {
			spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(1, 1), new Position(15, 9), 1);
			fail("Position trop à droite : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}

		try {
			spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(1, 1), new Position(-1, 9), 1);
			fail("Position trop à gauche : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}

		try {
			spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(1, 1), new Position(14, 10), 1);
			fail("Position trop en bas : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}

		try {
			spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(1, 1), new Position(14, -1), 1);
			fail("Position trop à haut : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}
	}

	@Test
	public void test_UnNouvelEnvahisseurPositionneDansEspaceJeuMaisAvecDimensionTropGrande_DoitLeverUneExceptionDeDebordement() {

		try {
			spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(9, 2), new Position(7, 9), 1);
			fail("Dépassement du vaisseau à droite en raison de sa longueur trop importante : devrait déclencher une exception DebordementEspaceJeuException");
		} catch (final DebordementEspaceJeuException e) {
		}

		try {
			spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 4), new Position(7, 1), 1);
			fail("Dépassement du vaisseau vers le haut en raison de sa hauteur trop importante : devrait déclencher une exception DebordementEspaceJeuException");
		} catch (final DebordementEspaceJeuException e) {
		}

	}


	
	@Test
	public void test_deuxNouveauxEnvahisseursAvecDimensionEtUnNouveauVaisseauSontCorrectementPositionneDansEspaceJeu() {
		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(7, 1), 1);
		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3, 2), new Position(1, 1), 1);
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(7, 9), 1);
		assertEquals("" + 
		".EEE...EEE.....\n" +
		".EEE...EEE.....\n" +
		"...............\n" + 
		"...............\n" +
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		"...............\n" + 
		".......VVV.....\n" + 
		".......VVV.....\n"  , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
 

}

    
