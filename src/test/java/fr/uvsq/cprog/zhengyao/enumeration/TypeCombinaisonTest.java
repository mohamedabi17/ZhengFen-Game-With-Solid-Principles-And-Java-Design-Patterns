package fr.uvsq.cprog.zhengyao.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TypeCombinaisonTest {

    @Test
    void testGetCombinaison() {
        assertEquals("Cartes uniques", TypeCombinaison.CARTE_UNIQUE.getCombinaison());
        assertEquals("Paires", TypeCombinaison.PAIRE.getCombinaison());
        assertEquals("Combinaison invalide", TypeCombinaison.INVALIDE.getCombinaison());
    }

    @Test
    void testEnumValues() {
        assertEquals(8, TypeCombinaison.values().length);
        assertEquals(TypeCombinaison.TRIPLET, TypeCombinaison.valueOf("TRIPLET"));
    }
}
