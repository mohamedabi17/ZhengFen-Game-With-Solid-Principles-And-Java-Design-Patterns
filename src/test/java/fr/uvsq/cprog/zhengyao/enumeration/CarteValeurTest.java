package fr.uvsq.cprog.zhengyao.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarteValeurTest {

    @Test
    void testGetValeur() {
        assertEquals("3", CarteValeur.TROIS.getValeur());
        assertEquals("4", CarteValeur.QUATRE.getValeur());
        assertEquals("J", CarteValeur.VALET.getValeur());
        assertEquals("2", CarteValeur.DEUX.getValeur());
    }

    @Test
    void testEnumValues() {
        assertEquals(13, CarteValeur.values().length);
        assertEquals(CarteValeur.AS, CarteValeur.valueOf("AS"));
    }
}
