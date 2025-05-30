package fr.uvsq.cprog.zhengyao.model;

import fr.uvsq.cprog.zhengyao.enumeration.CarteCouleur;
import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PartieTest {

    @BeforeEach
    void setUp() {
        // Reset the singleton instance before each test to ensure clean state
        Partie.resetInstance();
    }

    @Test
    void testSingletonBehavior() {
        Partie partie1 = Partie.getInstance(2, "Player1", 1);
        Partie partie2 = Partie.getInstance();

        assertSame(partie1, partie2);
    }

    @Test
    void testResetInstance() {
        Partie partie1 = Partie.getInstance(2, "Player1", 1);
        Partie.resetInstance();

        Partie partie2 = Partie.getInstance(2, "Player2", 2);

        assertNotSame(partie1, partie2);
    }

    @Test
    void testDistribuerPoints() {
        Partie partie = Partie.getInstance(4, "Player1", 1);

        // Prepare mock rankings
        List<Joueur> classement = new ArrayList<>();
        classement.add(partie.getJoueurs().get(0)); // 1st place
        classement.add(partie.getJoueurs().get(1)); // 2nd place
        classement.add(partie.getJoueurs().get(2)); // 3rd place
        classement.add(partie.getJoueurs().get(3)); // 4th place

        partie.distribuerPoints(classement);

        assertEquals(150, classement.get(0).getPoints());
        assertEquals(100, classement.get(1).getPoints());
        assertEquals(50, classement.get(2).getPoints());
        assertEquals(30, classement.get(3).getPoints());
    }

    @Test
    void testVerifierGagnant_NoWinner() {
        Partie partie = Partie.getInstance(4, "Player1", 1);

        // Players have scores below the threshold
        partie.getJoueurs().forEach(joueur -> joueur.ajouterPoints(200));

        Joueur gagnant = partie.verifierGagnant();

        assertNull(gagnant);
    }

    @Test
    void testVerifierGagnant_WithWinner() {
        Partie partie = Partie.getInstance(4, "Player1", 1);

        // Set one player to have 500 points
        Joueur joueurGagnant = partie.getJoueurs().get(0);
        joueurGagnant.ajouterPoints(500);

        Joueur gagnant = partie.verifierGagnant();

        assertNotNull(gagnant);
        assertEquals(joueurGagnant, gagnant);
    }

    @Test
    void testConstructorInvalidNumberOfPlayers() {
        assertThrows(IllegalArgumentException.class, () -> Partie.getInstance(1, "Player1", 1));
    }

    @Test
    void testConstructorInvalidPlayerColor() {
        assertThrows(IllegalArgumentException.class, () -> Partie.getInstance(2, "Player1", 5));
    }

    @Test
    void testAfficherMains() {
        Partie partie = Partie.getInstance(2, "Player1", 1);

        // Add cards to players' hands
        partie.getJoueurs().get(0).getMain().ajouterCarte(
                new Carte.CarteBuilder().valeur(CarteValeur.DEUX).couleur(CarteCouleur.COEUR).build());
        partie.getJoueurs().get(1).getMain().ajouterCarte(
                new Carte.CarteBuilder().valeur(CarteValeur.TROIS).couleur(CarteCouleur.TREFLE).build());

        // Mock system output
        System.setOut(new java.io.PrintStream(new java.io.ByteArrayOutputStream()));

        partie.afficherMains();
    }
}
