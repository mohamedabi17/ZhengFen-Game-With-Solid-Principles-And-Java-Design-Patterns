package fr.uvsq.cprog.zhengyao.ui;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import fr.uvsq.cprog.zhengyao.model.Partie;
import fr.uvsq.cprog.zhengyao.services.UserInputHandlerService;

class GameInitializerTest {
    private GameInitializer gameInitializer;

    @BeforeEach
    void setUp() {
        // Simulate user input for initializing a game
        String input = """
                4
                n
                Player1
                1
                """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        gameInitializer = new GameInitializer(new UserInputHandlerService(new Scanner(System.in)));
    }

    @Test
    void testInitialiser() {
        Partie partie = gameInitializer.initialiser();
        assertNotNull(partie, "La partie ne doit pas être nulle.");
        assertEquals("Player1", partie.getJoueurs().get(0).getNom(), "Le nom du joueur principal doit être 'Player1'.");
    }
}
