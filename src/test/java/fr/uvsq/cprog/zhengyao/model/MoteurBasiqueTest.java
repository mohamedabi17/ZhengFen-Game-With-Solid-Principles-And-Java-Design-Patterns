package fr.uvsq.cprog.zhengyao.model;

import fr.uvsq.cprog.zhengyao.enumeration.CarteCouleur;
import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoteurBasiqueTest {

        private MoteurBasique moteurBasique;
        private MainDeCartes mainDeCartes;

        @BeforeEach
        void setUp() {
                moteurBasique = new MoteurBasique("Moteur Basique");
                mainDeCartes = new MainDeCartes();
                mainDeCartes
                                .ajouterCarte(new Carte.CarteBuilder().valeur(CarteValeur.TROIS)
                                                .couleur(CarteCouleur.COEUR).build());
                mainDeCartes
                                .ajouterCarte(new Carte.CarteBuilder().valeur(CarteValeur.QUATRE)
                                                .couleur(CarteCouleur.TREFLE).build());
                mainDeCartes
                                .ajouterCarte(new Carte.CarteBuilder().valeur(CarteValeur.CINQ)
                                                .couleur(CarteCouleur.CARREAU).build());

        }

        @Test
        void testChoisirCombinaison_ValidMoveAfterOpponent() {
                CombinaisonCartes derniereCombinaison = new CombinaisonCartes(
                                TypeCombinaison.CARTE_UNIQUE,
                                List.of(new Carte.CarteBuilder().valeur(CarteValeur.TROIS).couleur(CarteCouleur.PIQUE)
                                                .build()));

                CombinaisonCartes result = moteurBasique.choisirCombinaison(mainDeCartes, derniereCombinaison);

                assertNotNull(result);
                assertEquals(TypeCombinaison.CARTE_UNIQUE, result.getType());
                assertEquals(new Carte.CarteBuilder().valeur(CarteValeur.QUATRE).couleur(CarteCouleur.TREFLE).build(),
                                result.getCartes().get(0));
        }

        @Test
        void testChoisirCombinaison_NoValidMove() {
                CombinaisonCartes derniereCombinaison = new CombinaisonCartes(
                                TypeCombinaison.CARTE_UNIQUE,
                                List.of(new Carte.CarteBuilder().valeur(CarteValeur.SIX).couleur(CarteCouleur.COEUR)
                                                .build()));

                CombinaisonCartes result = moteurBasique.choisirCombinaison(mainDeCartes, derniereCombinaison);

                assertNull(result);
        }
}
