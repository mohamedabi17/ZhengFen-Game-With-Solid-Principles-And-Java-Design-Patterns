package fr.uvsq.cprog.zhengyao.model;

import fr.uvsq.cprog.zhengyao.enumeration.CarteCouleur;
import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class MainDeCartesTest {

    @Test
    void testAjouterCarte() {
        MainDeCartes main = new MainDeCartes();
        Carte carte = new Carte.CarteBuilder().valeur(CarteValeur.DAME).couleur(CarteCouleur.PIQUE).build();
        main.ajouterCarte(carte);

        assertTrue(main.getCartes().contains(carte), "La carte doit être ajoutée à la main.");
    }

    @Test
    void testRetirerCarte() {
        MainDeCartes main = new MainDeCartes();
        Carte carte = new Carte.CarteBuilder().valeur(CarteValeur.DAME).couleur(CarteCouleur.PIQUE).build();
        main.ajouterCarte(carte);

        assertTrue(main.retirerCarte(carte), "La carte doit être retirée avec succès.");
        assertFalse(main.getCartes().contains(carte), "La carte ne doit plus être dans la main.");
    }

    @Test
    void testTrierCartes() {
        MainDeCartes main = new MainDeCartes();
        main.ajouterCarte(new Carte.CarteBuilder().valeur(CarteValeur.ROI).couleur(CarteCouleur.PIQUE).build());
        main.ajouterCarte(new Carte.CarteBuilder().valeur(CarteValeur.QUATRE).couleur(CarteCouleur.TREFLE).build());
        main.ajouterCarte(new Carte.CarteBuilder().valeur(CarteValeur.AS).couleur(CarteCouleur.COEUR).build());

        main.trier();

        List<Carte> cartes = main.getCartes();
        assertEquals(CarteValeur.QUATRE, cartes.get(0).getValeur(), "La carte la plus faible doit être au début.");
        assertEquals(CarteValeur.ROI, cartes.get(1).getValeur(), "Les cartes doivent être triées par ordre de valeur.");
        assertEquals(CarteValeur.AS, cartes.get(2).getValeur(), "La carte la plus forte doit être à la fin.");
    }

    @Test
    void testEstVide() {
        MainDeCartes main = new MainDeCartes();
        assertTrue(main.estVide(), "La main doit être vide.");

        main.ajouterCarte(new Carte.CarteBuilder().valeur(CarteValeur.CINQ).couleur(CarteCouleur.CARREAU).build());
        assertFalse(main.estVide(), "La main ne doit pas être vide après l'ajout d'une carte.");
    }
}
