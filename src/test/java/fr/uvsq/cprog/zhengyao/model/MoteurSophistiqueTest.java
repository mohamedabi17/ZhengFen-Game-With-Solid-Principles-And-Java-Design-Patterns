package fr.uvsq.cprog.zhengyao.model;

import fr.uvsq.cprog.zhengyao.enumeration.CarteCouleur;
import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoteurSophistiqueTest {

        private MoteurSophistique moteurSophistique;
        private MainDeCartes mainDeCartes;

        @BeforeEach
        void setUp() {
                moteurSophistique = new MoteurSophistique("Moteur Sophistique");
                mainDeCartes = new MainDeCartes();

                // Add cards to the hand
                mainDeCartes.ajouterCarte(new Carte.CarteBuilder()
                                .valeur(CarteValeur.CINQ)
                                .couleur(CarteCouleur.COEUR)
                                .build());
                mainDeCartes.ajouterCarte(new Carte.CarteBuilder()
                                .valeur(CarteValeur.SIX)
                                .couleur(CarteCouleur.TREFLE)
                                .build());
                mainDeCartes.ajouterCarte(new Carte.CarteBuilder()
                                .valeur(CarteValeur.SEPT)
                                .couleur(CarteCouleur.CARREAU)
                                .build());
        }

        @Test
        void testChoisirCombinaison_FirstMove() {
                CombinaisonCartes derniereCombinaison = new CombinaisonCartes(TypeCombinaison.INVALIDE, List.of());

                CombinaisonCartes result = moteurSophistique.choisirCombinaison(mainDeCartes, derniereCombinaison);

                assertNotNull(result, "La combinaison choisie ne doit pas être nulle.");
                assertEquals(TypeCombinaison.CARTE_UNIQUE, result.getType(),
                                "Le type de la combinaison doit être CARTE_UNIQUE.");
                assertEquals(CarteValeur.CINQ, result.getCartes().get(0).getValeur(),
                                "La carte jouée doit être le CINQ.");
        }

        @Test
        void testChoisirCombinaison_NoValidMove() {
                CombinaisonCartes derniereCombinaison = new CombinaisonCartes(
                                TypeCombinaison.CARTE_UNIQUE,
                                List.of(new Carte.CarteBuilder()
                                                .valeur(CarteValeur.HUIT)
                                                .couleur(CarteCouleur.PIQUE)
                                                .build()));

                CombinaisonCartes result = moteurSophistique.choisirCombinaison(mainDeCartes, derniereCombinaison);

                // Vérifier que la combinaison choisie est strictement invalide
                assertTrue(result == null || (result.getCartes() != null && result.getCartes().isEmpty()),
                                "Aucune combinaison valide ne doit être choisie.");
        }

        @Test
        void testChoisirCombinaison_ValidMove() {
                CombinaisonCartes derniereCombinaison = new CombinaisonCartes(
                                TypeCombinaison.CARTE_UNIQUE,
                                List.of(new Carte.CarteBuilder()
                                                .valeur(CarteValeur.QUATRE)
                                                .couleur(CarteCouleur.PIQUE)
                                                .build()));

                CombinaisonCartes result = moteurSophistique.choisirCombinaison(mainDeCartes, derniereCombinaison);

                assertNotNull(result, "La combinaison choisie ne doit pas être nulle.");
                assertEquals(TypeCombinaison.CARTE_UNIQUE, result.getType(),
                                "Le type de la combinaison doit être CARTE_UNIQUE.");
                assertEquals(CarteValeur.CINQ, result.getCartes().get(0).getValeur(),
                                "La carte jouée doit être le CINQ.");
        }
}
