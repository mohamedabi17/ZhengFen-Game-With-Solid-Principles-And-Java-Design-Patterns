package fr.uvsq.cprog.zhengyao.ui;

import fr.uvsq.cprog.zhengyao.command.InitialiserLeJeuCommande;
import fr.uvsq.cprog.zhengyao.model.Partie;
import fr.uvsq.cprog.zhengyao.services.UserInputHandlerService;

/**
 * Classe pour initialiser une nouvelle partie.
 */
public class GameInitializer {
    private final UserInputHandlerService inputHandler;

    public GameInitializer(UserInputHandlerService inputHandler) {
        this.inputHandler = inputHandler;
    }

    /**
     * Initialise une nouvelle partie.
     *
     * @return La partie initialisée.
     */
    public Partie initialiser() {
        int nombreJoueurs = inputHandler.demanderNombreJoueurs();
        @SuppressWarnings("unused")
        boolean espion = inputHandler.demanderModeEspionnage();
        String nomJoueur = inputHandler.demanderNomJoueur();
        int couleurJoueur = inputHandler.demanderCouleurJoueur();

        Partie partie = new InitialiserLeJeuCommande(nomJoueur, couleurJoueur, nombreJoueurs).execute();

        // afficherResumeInitialisation(partie);

        return partie;
    }


}
