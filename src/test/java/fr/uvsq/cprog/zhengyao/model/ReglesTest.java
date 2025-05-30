package fr.uvsq.cprog.zhengyao.model;

import static org.junit.jupiter.api.Assertions.*;

import fr.uvsq.cprog.zhengyao.enumeration.CarteCouleur;
import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReglesTest {
    private Regles regles;
    private Carte carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8, carte9;

    @BeforeEach
    void setUp() {
        regles = new Regles();
        carte1 = new Carte.CarteBuilder().valeur(CarteValeur.TROIS).couleur(CarteCouleur.PIQUE).build();
        carte2 = new Carte.CarteBuilder().valeur(CarteValeur.TROIS).couleur(CarteCouleur.COEUR).build();
        carte3 = new Carte.CarteBuilder().valeur(CarteValeur.CINQ).couleur(CarteCouleur.TREFLE).build();
        carte4 = new Carte.CarteBuilder().valeur(CarteValeur.SIX).couleur(CarteCouleur.CARREAU).build();
        carte5 = new Carte.CarteBuilder().valeur(CarteValeur.QUATRE).couleur(CarteCouleur.PIQUE).build();
        carte6 = new Carte.CarteBuilder().valeur(CarteValeur.QUATRE).couleur(CarteCouleur.COEUR).build();
        carte7 = new Carte.CarteBuilder().valeur(CarteValeur.SEPT).couleur(CarteCouleur.COEUR).build();
        carte8 = new Carte.CarteBuilder().valeur(CarteValeur.HUIT).couleur(CarteCouleur.COEUR).build();
        carte9 = new Carte.CarteBuilder().valeur(CarteValeur.CINQ).couleur(CarteCouleur.COEUR).build();

    }

    @Test
    void testValiderCombinaisonCarteUnique() {
        List<Carte> cartes = List.of(carte1);
        TypeCombinaison type = regles.validerCombinaison(cartes);

        assertEquals(TypeCombinaison.CARTE_UNIQUE, type, "La combinaison devrait être une carte unique.");
    }

    @Test
    void testValiderCombinaisonPaire() {
        List<Carte> cartes = List.of(carte1, carte2);
        TypeCombinaison type = regles.validerCombinaison(cartes);

        assertEquals(TypeCombinaison.PAIRE, type, "La combinaison devrait être une paire.");
    }

    @Test
    void testValiderCombinaisonTriplet() {
        List<Carte> cartes = List.of(carte1, carte1, carte1);
        TypeCombinaison type = regles.validerCombinaison(cartes);

        assertEquals(TypeCombinaison.TRIPLET, type, "La combinaison devrait être un triplet.");
    }

    @Test
    void testValiderCombinaisonSuite() {
        List<Carte> cartes = List.of(carte3, carte4, carte5, carte7, carte8);
        TypeCombinaison type = regles.validerCombinaison(cartes);

        assertEquals(TypeCombinaison.SUITE, type, "La combinaison devrait être une suite.");
    }

    @Test
    void testValiderCombinaisonTripletPlusDeux() {
        List<Carte> cartes = List.of(carte1, carte1, carte1, carte5, carte6);
        TypeCombinaison type = regles.validerCombinaison(cartes);

        assertEquals(TypeCombinaison.TRIPLET_PLUS_DEUX, type, "La combinaison devrait être un triplet plus deux.");
    }

    @Test
    void testValiderCombinaisonSuiteDePaires() {
        List<Carte> cartes = List.of(carte5, carte6, carte1, carte2, carte3, carte9);
        TypeCombinaison type = regles.validerCombinaison(cartes);

        assertEquals(TypeCombinaison.SUITE_DE_PAIRES, type, "La combinaison devrait être une suite de paires.");
    }

    @Test
    void testValiderCombinaisonCarre() {
        List<Carte> cartes = List.of(carte1, carte1, carte1, carte1);
        TypeCombinaison type = regles.validerCombinaison(cartes);

        assertEquals(TypeCombinaison.CARRE, type, "La combinaison devrait être un carré.");
    }

    @Test
    void testValiderCombinaisonInvalide() {
        List<Carte> cartes = List.of(carte1, carte2, carte3);
        TypeCombinaison type = regles.validerCombinaison(cartes);

        assertEquals(TypeCombinaison.INVALIDE, type, "La combinaison devrait être invalide.");
    }

    @Test
    void testEstValideAvecCombinaisonPrecedente() {
        CombinaisonCartes combinaison1 = new CombinaisonCartes(TypeCombinaison.PAIRE, List.of(carte1, carte2)); // Paire
        List<Carte> combinaison2 = List.of(carte3, carte3); // Paire

        regles.validerCombinaison(combinaison1.getCartes()); // Première combinaison
        boolean valide = regles.estValide(combinaison2, combinaison1); // Vérifie si combinaison2 est valide par rapport
                                                                       // à combinaison1

        assertTrue(valide, "La combinaison devrait être valide si elle est du même type et plus forte.");
    }

    @Test
    void testEstValideAvecCombinaisonInvalide() {
        CombinaisonCartes combinaison1 = new CombinaisonCartes(TypeCombinaison.PAIRE, List.of(carte1, carte2)); // Paire
        List<Carte> combinaison2 = List.of(carte3, carte4, carte5); // Suite

        regles.validerCombinaison(combinaison1.getCartes()); // Première combinaison
        boolean valide = regles.estValide(combinaison2, combinaison1); // Vérifie si combinaison2 est valide par rapport
                                                                       // à combinaison1

        assertFalse(valide, "La combinaison devrait être invalide si elle n'est pas du même type.");
    }

    @Test
    void testEstValideAvecCombinaisonMoinsForte() {
        CombinaisonCartes combinaison1 = new CombinaisonCartes(TypeCombinaison.PAIRE, List.of(carte1, carte1)); // Paire
                                                                                                                // (plus
                                                                                                                // forte)
        List<Carte> combinaison2 = List.of(carte2, carte2); // Paire (moins forte)

        regles.validerCombinaison(combinaison1.getCartes()); // Première combinaison
        boolean valide = regles.estValide(combinaison2, combinaison1); // Vérifie si combinaison2 est plus faible que
                                                                       // combinaison1

        assertFalse(valide, "La combinaison devrait être invalide si elle est moins forte.");
    }
}
