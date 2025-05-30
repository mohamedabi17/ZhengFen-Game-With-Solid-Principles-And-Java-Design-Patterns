package fr.uvsq.cprog.zhengyao.model;

import fr.uvsq.cprog.zhengyao.enumeration.CarteCouleur;
import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import fr.uvsq.cprog.zhengyao.enumeration.CouleurJoueur;
import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JoueurHumainTest {

        private JoueurHumain joueurHumain;
        private Regles reglesMock;

        @BeforeEach
        void setUp() {
                joueurHumain = new JoueurHumain("Alice", CouleurJoueur.ROUGE);
                reglesMock = mock(Regles.class);

                // Set up a mock hand with cards
                MainDeCartes mainDeCartes = joueurHumain.getMain();
                mainDeCartes.ajouterCarte(new Carte(CarteValeur.TROIS, CarteCouleur.COEUR));
                mainDeCartes.ajouterCarte(new Carte(CarteValeur.QUATRE, CarteCouleur.TREFLE));
                mainDeCartes.ajouterCarte(new Carte(CarteValeur.CINQ, CarteCouleur.CARREAU));
        }

        @Test
        void testJouerCartes_PassTurn() {
                CombinaisonCartes derniereCombinaison = new CombinaisonCartes(TypeCombinaison.INVALIDE, null);

                // Simulate input for "pass"
                String input = "passe\n";
                System.setIn(new ByteArrayInputStream(input.getBytes()));

                CombinaisonCartes result = joueurHumain.jouerCartes(derniereCombinaison);

                assertNull(result);
                assertEquals(3, joueurHumain.getMain().getCartes().size());
        }

        @Test
        void testJouerCartes_InvalidCombination() {
                CombinaisonCartes derniereCombinaison = new CombinaisonCartes(TypeCombinaison.CARTE_UNIQUE,
                                List.of(new Carte(CarteValeur.CINQ, CarteCouleur.PIQUE)));

                // Simulate invalid input
                String input = "combo 1\n";
                System.setIn(new ByteArrayInputStream(input.getBytes()));

                List<Carte> combinaisonChoisie = List.of(new Carte(CarteValeur.TROIS, CarteCouleur.COEUR));
                when(reglesMock.estValide(combinaisonChoisie, derniereCombinaison)).thenReturn(false);

                CombinaisonCartes result = joueurHumain.jouerCartes(derniereCombinaison);

                assertNull(result);
                assertEquals(3, joueurHumain.getMain().getCartes().size());
        }

        @Test
        void testJouerCartes_InvalidIndices() {
                CombinaisonCartes derniereCombinaison = new CombinaisonCartes(TypeCombinaison.INVALIDE, null);

                // Simulate input with invalid indices
                String input = "carte 5,6\n";
                System.setIn(new ByteArrayInputStream(input.getBytes()));

                CombinaisonCartes result = joueurHumain.jouerCartes(derniereCombinaison);

                assertNull(result);
                assertEquals(3, joueurHumain.getMain().getCartes().size());
        }

        @Test
        void testJouerCartes_EmptyInput() {
                CombinaisonCartes derniereCombinaison = new CombinaisonCartes(TypeCombinaison.INVALIDE, null);

                String input = "\n";
                System.setIn(new ByteArrayInputStream(input.getBytes()));

                CombinaisonCartes result = joueurHumain.jouerCartes(derniereCombinaison);

                assertNull(result, "Une entrée vide ne doit pas provoquer une action.");
                assertEquals(3, joueurHumain.getMain().getCartes().size());
        }

        @Test
        void testJouerCartes_InvalidCommandFormat() {
                CombinaisonCartes derniereCombinaison = new CombinaisonCartes(TypeCombinaison.INVALIDE, null);

                String input = "xyz 1,2\n";
                System.setIn(new ByteArrayInputStream(input.getBytes()));

                CombinaisonCartes result = joueurHumain.jouerCartes(derniereCombinaison);

                assertNull(result, "Une commande inconnue ne doit pas provoquer une action.");
                assertEquals(3, joueurHumain.getMain().getCartes().size());
        }

        @Test
        void testJouerCartes_CombinaisonNeBatsPasDerniere() {
                CombinaisonCartes derniereCombinaison = new CombinaisonCartes(
                                TypeCombinaison.CARTE_UNIQUE,
                                List.of(new Carte(CarteValeur.SIX, CarteCouleur.COEUR)));

                String input = "carte 0\n"; // Carte TROIS
                System.setIn(new ByteArrayInputStream(input.getBytes()));

                List<Carte> combinaisonChoisie = List.of(new Carte(CarteValeur.TROIS, CarteCouleur.COEUR));
                when(reglesMock.estValide(combinaisonChoisie, derniereCombinaison)).thenReturn(false);

                CombinaisonCartes result = joueurHumain.jouerCartes(derniereCombinaison);

                assertNull(result, "La combinaison ne doit pas être acceptée si elle est plus faible.");
        }

}