package fr.uvsq.cprog.zhengyao.model;

import static org.junit.jupiter.api.Assertions.*;

import fr.uvsq.cprog.zhengyao.enumeration.CarteCouleur;
import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import org.junit.jupiter.api.Test;

class CarteTest {

    @Test
    void testCarteCreation() {
        Carte carte = new Carte.CarteBuilder()
                .valeur(CarteValeur.CINQ)
                .couleur(CarteCouleur.TREFLE)
                .build();
        assertNotNull(carte, "La carte ne doit pas être nulle.");
        assertEquals(CarteValeur.CINQ, carte.getValeur(), "La valeur de la carte doit être CINQ.");
        assertEquals(CarteCouleur.TREFLE, carte.getCouleur(), "La couleur de la carte doit être TREFLE.");
    }

    @Test
    void testCarteToString() {
        Carte carte = new Carte.CarteBuilder().valeur(CarteValeur.ROI).couleur(CarteCouleur.COEUR).build();
        String expectedString = "K(HC Coeur)"; // Updated expected format
        assertEquals(expectedString, carte.toString(), "La méthode toString doit retourner la chaîne correcte.");
    }

    @Test
    void testComparerCartes() {
        Carte carte1 = new Carte.CarteBuilder()
                .valeur(CarteValeur.CINQ)
                .couleur(CarteCouleur.PIQUE)
                .build();
        Carte carte2 = new Carte.CarteBuilder()
                .valeur(CarteValeur.SIX)
                .couleur(CarteCouleur.COEUR)
                .build();
        assertTrue(carte1.compareTo(carte2) < 0, "La carte1 doit être inférieure à carte2.");
        assertTrue(carte2.compareTo(carte1) > 0, "La carte2 doit être supérieure à carte1.");
    }

    @Test
    void testComparerCartesEgales() {
        Carte carte1 = new Carte.CarteBuilder()
                .valeur(CarteValeur.CINQ)
                .couleur(CarteCouleur.PIQUE)
                .build();
        Carte carte2 = new Carte.CarteBuilder()
                .valeur(CarteValeur.CINQ)
                .couleur(CarteCouleur.COEUR)
                .build();

        assertEquals(0, carte1.compareTo(carte2), "Les cartes doivent être égales.");
    }

    @Test
    void testCarteBuilder_NullValeur() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Carte.CarteBuilder().valeur(null).couleur(CarteCouleur.COEUR).build();
        });
    }

    @Test
    void testCarteBuilder_NullCouleur() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Carte.CarteBuilder().valeur(CarteValeur.AS).couleur(null).build();
        });
    }
}
