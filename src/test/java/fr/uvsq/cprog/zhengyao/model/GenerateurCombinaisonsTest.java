package fr.uvsq.cprog.zhengyao.model;

import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import fr.uvsq.cprog.zhengyao.enumeration.CarteCouleur;
import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenerateurCombinaisonsTest {

    @Test
    void testGenererCartesUniques() {
        MainDeCartes main = new MainDeCartes();
        main.ajouterCarte(new Carte(CarteValeur.TROIS, CarteCouleur.COEUR));
        main.ajouterCarte(new Carte(CarteValeur.QUATRE, CarteCouleur.TREFLE));

        List<CombinaisonCartes> result = GenerateurCombinaisons.genererCartesUniques(main);

        assertEquals(2, result.size());
        assertEquals(TypeCombinaison.CARTE_UNIQUE, result.get(0).getType());
        assertEquals(1, result.get(0).getCartes().size());
    }

    @Test
    void testGenererTriplets() {
        MainDeCartes main = new MainDeCartes();
        main.ajouterCarte(new Carte(CarteValeur.QUATRE, CarteCouleur.COEUR));
        main.ajouterCarte(new Carte(CarteValeur.QUATRE, CarteCouleur.CARREAU));
        main.ajouterCarte(new Carte(CarteValeur.QUATRE, CarteCouleur.TREFLE));

        List<CombinaisonCartes> result = GenerateurCombinaisons.genererTriplets(main);

        assertEquals(1, result.size());
        assertEquals(TypeCombinaison.TRIPLET, result.get(0).getType());
        assertEquals(3, result.get(0).getCartes().size());
    }

    @Test
    void testGenererSuites() {
        MainDeCartes main = new MainDeCartes();
        main.ajouterCarte(new Carte(CarteValeur.TROIS, CarteCouleur.COEUR));
        main.ajouterCarte(new Carte(CarteValeur.QUATRE, CarteCouleur.TREFLE));
        main.ajouterCarte(new Carte(CarteValeur.CINQ, CarteCouleur.CARREAU));
        main.ajouterCarte(new Carte(CarteValeur.SIX, CarteCouleur.PIQUE));
        main.ajouterCarte(new Carte(CarteValeur.SEPT, CarteCouleur.COEUR));

        List<CombinaisonCartes> result = GenerateurCombinaisons.genererSuites(main);

        assertEquals(1, result.size());
        assertEquals(TypeCombinaison.SUITE, result.get(0).getType());
        assertEquals(5, result.get(0).getCartes().size());
    }

}