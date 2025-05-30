package fr.uvsq.cprog.zhengyao.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.uvsq.cprog.zhengyao.services.UserInputHandlerService;

class HistoryManagerTest {
    private HistoryManager historyManager;
    private UserInputHandlerService inputHandlerMock;

    @BeforeEach
    void setUp() {
        // Mock UserInputHandler
        inputHandlerMock = mock(UserInputHandlerService.class);

        // Mock lireEntier behavior to simulate user input
        when(inputHandlerMock.lireEntier()).thenReturn(1);

        historyManager = new HistoryManager(inputHandlerMock);
    }

    @Test
    void testAfficherHistorique() {
        // Ensure no exceptions are thrown when calling afficherHistorique
        assertDoesNotThrow(() -> historyManager.afficherHistorique());
    }
}
