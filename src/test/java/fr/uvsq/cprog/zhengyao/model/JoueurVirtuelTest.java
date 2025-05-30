package fr.uvsq.cprog.zhengyao.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import fr.uvsq.cprog.zhengyao.enumeration.CarteCouleur;
import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import fr.uvsq.cprog.zhengyao.enumeration.CouleurJoueur;
import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JoueurVirtuelTest {

        private JoueurVirtuel joueurVirtuel;
        private MoteurDeJeu moteurMock;

        @BeforeEach
        void setUp() {
                moteurMock = mock(MoteurDeJeu.class);
                joueurVirtuel = new JoueurVirtuel("Bot", CouleurJoueur.BLEU, moteurMock);

                joueurVirtuel.getMain().ajouterCarte(
                                new Carte.CarteBuilder().valeur(CarteValeur.TROIS).couleur(CarteCouleur.COEUR).build());
                joueurVirtuel.getMain().ajouterCarte(
                                new Carte.CarteBuilder().valeur(CarteValeur.QUATRE).couleur(CarteCouleur.TREFLE)
                                                .build());
                joueurVirtuel.getMain().ajouterCarte(
                                new Carte.CarteBuilder().valeur(CarteValeur.CINQ).couleur(CarteCouleur.CARREAU)
                                                .build());
        }

        @Test
        void testJouerCartes_ValidCombination() {
                CombinaisonCartes derniereCombinaison = new CombinaisonCartes(TypeCombinaison.INVALIDE, List.of());
                CombinaisonCartes combinaisonChoisie = new CombinaisonCartes(
                                TypeCombinaison.CARTE_UNIQUE,
                                List.of(new Carte.CarteBuilder().valeur(CarteValeur.TROIS).couleur(CarteCouleur.COEUR)
                                                .build()));

                when(moteurMock.choisirCombinaison(any(MainDeCartes.class), eq(derniereCombinaison)))
                                .thenReturn(combinaisonChoisie);

                CombinaisonCartes result = joueurVirtuel.jouerCartes(derniereCombinaison);

                assertNotNull(result);
                assertEquals(TypeCombinaison.CARTE_UNIQUE, result.getType());
                assertFalse(joueurVirtuel.getMain().getCartes().contains(
                                new Carte.CarteBuilder().valeur(CarteValeur.TROIS).couleur(CarteCouleur.COEUR)
                                                .build()));
        }

        @Test
        void testJouerCartes_PassTurn() {
                CombinaisonCartes derniereCombinaison = new CombinaisonCartes(TypeCombinaison.INVALIDE, List.of());

                when(moteurMock.choisirCombinaison(any(MainDeCartes.class), eq(derniereCombinaison))).thenReturn(null);

                CombinaisonCartes result = joueurVirtuel.jouerCartes(derniereCombinaison);

                assertNull(result);
                assertEquals(3, joueurVirtuel.getMain().getCartes().size());
        }
}
