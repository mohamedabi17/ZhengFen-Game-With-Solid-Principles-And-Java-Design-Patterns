package fr.uvsq.cprog.zhengyao.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarteCouleurTest {

    @Test
    void testGetCouleur() {
        assertEquals("SHD Pique", CarteCouleur.PIQUE.getCouleur());
        assertEquals("HC Coeur", CarteCouleur.COEUR.getCouleur());
        assertEquals("DC Carreau", CarteCouleur.CARREAU.getCouleur());
        assertEquals("CC Trefle", CarteCouleur.TREFLE.getCouleur());
    }

    @Test
    void testEnumValues() {
        assertEquals(4, CarteCouleur.values().length);
        assertEquals(CarteCouleur.PIQUE, CarteCouleur.valueOf("PIQUE"));
    }
}
