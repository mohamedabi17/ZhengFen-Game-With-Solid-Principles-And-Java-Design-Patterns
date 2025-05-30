package fr.uvsq.cprog.zhengyao.model;

import static org.junit.jupiter.api.Assertions.*;

import fr.uvsq.cprog.zhengyao.enumeration.CarteCouleur;
import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import java.util.List;
import org.junit.jupiter.api.Test;

class CombinaisonCartesTest {

    @Test
    void testGetType() {
        List<Carte> cartes = List.of(
                new Carte.CarteBuilder()
                        .valeur(CarteValeur.CINQ)
                        .couleur(CarteCouleur.COEUR)
                        .build());
        CombinaisonCartes combinaison = new CombinaisonCartes(TypeCombinaison.CARTE_UNIQUE, cartes);

        assertEquals(TypeCombinaison.CARTE_UNIQUE, combinaison.getType());
    }

    @Test
    void testGetCartes() {
        List<Carte> cartes = List.of(
                new Carte.CarteBuilder()
                        .valeur(CarteValeur.TROIS)
                        .couleur(CarteCouleur.COEUR)
                        .build(),
                new Carte.CarteBuilder()
                        .valeur(CarteValeur.QUATRE)
                        .couleur(CarteCouleur.TREFLE)
                        .build());
        CombinaisonCartes combinaison = new CombinaisonCartes(TypeCombinaison.PAIRE, cartes);

        assertEquals(cartes, combinaison.getCartes());
    }

    @Test
    void testGetForce() {
        List<Carte> cartes = List.of(
                new Carte.CarteBuilder()
                        .valeur(CarteValeur.TROIS)
                        .couleur(CarteCouleur.COEUR)
                        .build(),
                new Carte.CarteBuilder()
                        .valeur(CarteValeur.CINQ)
                        .couleur(CarteCouleur.CARREAU)
                        .build());
        CombinaisonCartes combinaison = new CombinaisonCartes(TypeCombinaison.PAIRE, cartes);

        int expectedForce = CarteValeur.TROIS.ordinal() + CarteValeur.CINQ.ordinal();
        assertEquals(expectedForce, combinaison.getForce());
    }

    @Test
    void testGagneContre() {
        List<Carte> cartes1 = List.of(
                new Carte.CarteBuilder().valeur(CarteValeur.TROIS).couleur(CarteCouleur.COEUR).build());
        List<Carte> cartes2 = List.of(
                new Carte.CarteBuilder().valeur(CarteValeur.QUATRE).couleur(CarteCouleur.CARREAU)
                        .build());
        CombinaisonCartes combinaison1 = new CombinaisonCartes(TypeCombinaison.CARTE_UNIQUE, cartes1);
        CombinaisonCartes combinaison2 = new CombinaisonCartes(TypeCombinaison.CARTE_UNIQUE, cartes2);

        assertTrue(combinaison2.gagneContre(combinaison1));
        assertFalse(combinaison1.gagneContre(combinaison2));
    }

    @Test
    void testToString() {
        List<Carte> cartes = List.of(
                new Carte.CarteBuilder().valeur(CarteValeur.TROIS).couleur(CarteCouleur.COEUR).build());
        CombinaisonCartes combinaison = new CombinaisonCartes(TypeCombinaison.CARTE_UNIQUE, cartes);

        assertEquals("Cartes uniques [3(HC Coeur)]", combinaison.toString()); // Updated expected format
    }
}
