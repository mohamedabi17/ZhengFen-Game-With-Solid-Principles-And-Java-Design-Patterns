package fr.uvsq.cprog.zhengyao.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CouleurJoueurTest {

    @Test
    void testEnumValues() {
        assertEquals(4, CouleurJoueur.values().length);
        assertEquals(CouleurJoueur.ROUGE, CouleurJoueur.valueOf("ROUGE"));
        assertEquals(CouleurJoueur.BLEU, CouleurJoueur.valueOf("BLEU"));
        assertEquals(CouleurJoueur.VERT, CouleurJoueur.valueOf("VERT"));
        assertEquals(CouleurJoueur.JAUNE, CouleurJoueur.valueOf("JAUNE"));
    }
}
