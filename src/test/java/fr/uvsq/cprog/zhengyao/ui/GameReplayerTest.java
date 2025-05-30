package fr.uvsq.cprog.zhengyao.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.uvsq.cprog.zhengyao.services.UserInputHandlerService;

class GameReplayerTest {
    private GameReplayer gameReplayer;

    @BeforeEach
    void setUp() {
        // Simulate user input for selecting a file
        String input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        UserInputHandlerService userInputHandler = new UserInputHandlerService(null);
        gameReplayer = new GameReplayer(userInputHandler);
    }

    @Test
    void testRejouerPartie() {
        assertDoesNotThrow(() -> gameReplayer.rejouerPartie(new GameDisplayer()),
                "La méthode ne doit pas lever d'exception.");
    }
}
