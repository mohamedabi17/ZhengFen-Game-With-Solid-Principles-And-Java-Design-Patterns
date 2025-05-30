package fr.uvsq.cprog.zhengyao.ui;

import static org.mockito.Mockito.*;
import fr.uvsq.cprog.zhengyao.model.*;
// import fr.uvsq.cprog.zhengyao.services.UserInputHandlerService;
import fr.uvsq.cprog.zhengyao.enumeration.CarteCouleur;
import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import fr.uvsq.cprog.zhengyao.enumeration.CouleurJoueur;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;

class GameRoundManagerTest {
        // private GameRoundManager gameRoundManager;
        private Partie partieMock;
        private PaquetDeCartes paquetMock;
        private List<Joueur> joueursMock;

        @BeforeEach
        void setUp() {
                partieMock = mock(Partie.class);
                paquetMock = mock(PaquetDeCartes.class);

                // Mock players
                joueursMock = new ArrayList<>();
                Joueur joueur1 = mock(JoueurHumain.class);
                when(joueur1.getNom()).thenReturn("Player1");
                when(joueur1.getCouleur()).thenReturn(CouleurJoueur.ROUGE);
                when(joueur1.getMain()).thenReturn(new MainDeCartes(List.of(
                                new Carte.CarteBuilder().valeur(CarteValeur.TROIS).couleur(CarteCouleur.COEUR)
                                                .build())));
                joueursMock.add(joueur1);

                Joueur joueur2 = mock(JoueurVirtuel.class);
                when(joueur2.getNom()).thenReturn("Player2");
                when(joueur2.getCouleur()).thenReturn(CouleurJoueur.BLEU);
                when(joueur2.getMain()).thenReturn(new MainDeCartes(List.of(
                                new Carte.CarteBuilder().valeur(CarteValeur.QUATRE).couleur(CarteCouleur.TREFLE)
                                                .build())));
                joueursMock.add(joueur2);

                Joueur joueur3 = mock(JoueurVirtuel.class);
                when(joueur3.getNom()).thenReturn("Player3");
                when(joueur3.getCouleur()).thenReturn(CouleurJoueur.VERT);
                when(joueur3.getMain()).thenReturn(new MainDeCartes(List.of(
                                new Carte.CarteBuilder().valeur(CarteValeur.CINQ).couleur(CarteCouleur.PIQUE)
                                                .build())));
                joueursMock.add(joueur3);

                Joueur joueur4 = mock(JoueurVirtuel.class);
                when(joueur4.getNom()).thenReturn("Player4");
                when(joueur4.getCouleur()).thenReturn(CouleurJoueur.JAUNE);
                when(joueur4.getMain()).thenReturn(new MainDeCartes(List.of(
                                new Carte.CarteBuilder().valeur(CarteValeur.SIX).couleur(CarteCouleur.CARREAU)
                                                .build())));
                joueursMock.add(joueur4);

                when(partieMock.getNombreJoueurs()).thenReturn(4);
                when(partieMock.getJoueurs()).thenReturn(joueursMock);

                // Mock HistoriquePartie to avoid NullPointerException
                HistoriquePartie historiqueMock = mock(HistoriquePartie.class);
                when(partieMock.obtenirHistorique()).thenReturn(historiqueMock);

                // Mock PaquetDeCartes to avoid NullPointerException
                when(partieMock.getPaquet()).thenReturn(paquetMock);
                when(paquetMock.distribuer(4)).thenReturn(List.of(
                                new MainDeCartes(List.of(
                                                new Carte.CarteBuilder().valeur(CarteValeur.TROIS)
                                                                .couleur(CarteCouleur.COEUR).build())),
                                new MainDeCartes(List.of(
                                                new Carte.CarteBuilder().valeur(CarteValeur.QUATRE)
                                                                .couleur(CarteCouleur.TREFLE).build())),
                                new MainDeCartes(List.of(
                                                new Carte.CarteBuilder().valeur(CarteValeur.CINQ)
                                                                .couleur(CarteCouleur.PIQUE).build())),
                                new MainDeCartes(List.of(
                                                new Carte.CarteBuilder().valeur(CarteValeur.SIX)
                                                                .couleur(CarteCouleur.CARREAU).build()))));

                // UserInputHandlerService userInputHandler = new UserInputHandlerService(null);
                // gameRoundManager = new GameRoundManager(userInputHandler, false);
        }

}
