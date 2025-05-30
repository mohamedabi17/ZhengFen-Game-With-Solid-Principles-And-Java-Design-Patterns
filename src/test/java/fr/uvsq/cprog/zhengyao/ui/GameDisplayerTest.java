package fr.uvsq.cprog.zhengyao.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import fr.uvsq.cprog.zhengyao.model.Partie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameDisplayerTest {
    private GameDisplayer gameDisplayer;

    @BeforeEach
    void setUp() {
        Partie.resetInstance();
        Partie partie = Partie.getInstance(4, "Player1", 1);
        gameDisplayer = new GameDisplayer();
    }

    @Test
    void testAfficherMains() {
        assertDoesNotThrow(() -> gameDisplayer.afficherMains(), "La méthode ne doit pas lever d'exception.");
    }

    @Test
    void testAfficherScores() {
        assertDoesNotThrow(() -> gameDisplayer.afficherScores(), "La méthode ne doit pas lever d'exception.");
    }
}
