package fr.uvsq.cprog.zhengyao.ui;

import fr.uvsq.cprog.zhengyao.model.*;
import fr.uvsq.cprog.zhengyao.services.UserInputHandlerService;
import java.io.IOException;
import java.util.List;

/**
 * Classe pour gérer les rondes de jeu.
 */
public class GameRoundManager {
    private final UserInputHandlerService inputHandler;
    private final GameDisplayer displayer;
    private final RoundPlayer roundPlayer;
    private final boolean espion;

    /**
     * Constructeur de la classe GameRoundManager.
     *
     * @param inputHandler Le gestionnaire d'entrée utilisateur.
     */
    public GameRoundManager(UserInputHandlerService inputHandler, boolean espion) {
        this.inputHandler = inputHandler;
        this.displayer = new GameDisplayer();
        this.roundPlayer = new RoundPlayer(displayer);
        this.espion = espion;
    }

    /**
     * Démarre une partie.
     *
     * @param partie     La partie à démarrer.
     * @param estRejouer Indique si la partie est une reprise.
     */
    public void demarrer(Partie partie, boolean estRejouer) {
        partie.obtenirHistorique().ajouterEvenement("La partie a commencé.");
        Joueur joueurGagnant = null;

        int mancheCourante = 0;
        while (joueurGagnant == null) {
            mancheCourante++;

            // Delegate playing a round to RoundPlayer
            List<Joueur> classement = roundPlayer.jouerManche(mancheCourante, estRejouer, espion);

            // Distribuer les points
            partie.distribuerPoints(classement);
            displayer.afficherScores();

            // Vérifie si un joueur a gagné
            joueurGagnant = partie.verifierGagnant();

            if (joueurGagnant == null) {
                System.out.println("La partie continue avec une nouvelle manche.");
                System.out
                        .println("Appuyez sur 'ENTRER' pour continuer ou tapez 'quitter' pour sauvegarder et quitter.");
                String input = inputHandler.lireLigne().trim();

                if (input.equalsIgnoreCase("quitter")) {
                    try {
                        partie.obtenirHistorique().enregistrerDansFichier("src/main/resources/historiques_des_parties",
                                "src/main/resources/parties_jouees.txt");
                        System.out.println("La partie a été sauvegardée dans l'historique. Merci d'avoir joué !");
                    } catch (IOException e) {
                        System.err.println("Erreur lors de l'enregistrement de l'historique : " + e.getMessage());
                    }
                    return; // Quit the game
                }
            }
        }
        displayer.afficherFinDeJeux(joueurGagnant);
        partie.obtenirHistorique().ajouterEvenement(joueurGagnant.getNom()
                + " a gagné la partie avec " + joueurGagnant.getPoints() + " points.");

        try {
            partie.obtenirHistorique().enregistrerDansFichier("src/main/resources/historiques_des_parties",
                    "src/main/resources/parties_jouees.txt");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'enregistrement de l'historique : " + e.getMessage());
        }
    }
}
