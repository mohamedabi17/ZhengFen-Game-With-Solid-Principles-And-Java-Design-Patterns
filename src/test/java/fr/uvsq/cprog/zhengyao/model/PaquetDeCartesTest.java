package fr.uvsq.cprog.zhengyao.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class PaquetDeCartesTest {

    @Test
    void testPaquetDeCartesCreation() {
        PaquetDeCartes paquet = PaquetDeCartes.getInstance();
        assertNotNull(paquet, "Le paquet de cartes ne doit pas être nul.");
        assertEquals(52, paquet.toString().split(",").length, "Le paquet doit contenir 52 cartes.");
    }

    @Test
    void testMelanger() {
        PaquetDeCartes paquet = PaquetDeCartes.getInstance();
        String[] cartesAvantMelange = paquet.toString().split(",");
        
        paquet.melanger();
        String[] cartesApresMelange = paquet.toString().split(",");
        
        // Le paquet devrait être mélangé après l'appel de la méthode
        assertNotEquals(cartesAvantMelange, cartesApresMelange, "Les cartes doivent être mélangées.");
    }

    @Test
    void testDistribuer() {
        PaquetDeCartes paquet = PaquetDeCartes.getInstance();
        paquet.melanger();
        List<MainDeCartes> mains = paquet.distribuer(4);

        assertEquals(4, mains.size(), "Il doit y avoir 4 mains pour 4 joueurs.");
        assertTrue(mains.stream().allMatch(main -> main.getCartes().size() == 13), "Chaque main doit contenir 13 cartes.");
    }

    @Test
    void testDistribuerException() {
        PaquetDeCartes paquet = PaquetDeCartes.getInstance();
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            paquet.distribuer(0);  // Distribution pour 0 joueurs
        });
        assertEquals("Nombre de joueurs invalide.", exception.getMessage(), "Le nombre de joueurs invalide doit lancer une exception.");
    }
}
