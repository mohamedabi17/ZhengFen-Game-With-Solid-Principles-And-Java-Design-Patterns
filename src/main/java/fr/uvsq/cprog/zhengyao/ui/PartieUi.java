package fr.uvsq.cprog.zhengyao.ui;

import fr.uvsq.cprog.zhengyao.command.InitialiserLeJeuCommande;
import fr.uvsq.cprog.zhengyao.model.Partie;
import fr.uvsq.cprog.zhengyao.services.UserInputHandlerService;
import java.util.Scanner;

import org.fusesource.jansi.Ansi;

/**
 * Classe représentant l'interface utilisateur pour gérer une partie du jeu Zheng Shangyou.
 */
public class PartieUi {
    private final GameInitializer gameInitializer;
    private final GameReplayer gameReplayer;
    private GameRoundManager gameRoundManager;
    private final HistoryManager historyManager;
    private final UserInputHandlerService inputHandler;
    private final Scanner scanner;
    private final GameDisplayer displayer;

    /**
     * Constructeur de PartieUi avec injection de dépendances.
     *
     * @param inputHandler Le gestionnaire d'entrées utilisateur.
     */
    public PartieUi(UserInputHandlerService inputHandler) {
        this.inputHandler = inputHandler;
        this.gameInitializer = new GameInitializer(inputHandler);
        this.gameReplayer = new GameReplayer(inputHandler);
        this.historyManager = new HistoryManager(inputHandler);
        this.scanner = new Scanner(System.in);
        this.gameRoundManager = null; // Initialize later when Partie is available
        this.displayer = new GameDisplayer();
    }

    /**
     * Constructeur par défaut de PartieUi.
     * Initialise les dépendances avec un scanner par défaut.
     */
    public PartieUi() {
        this(new UserInputHandlerService(new Scanner(System.in)));
    }

    /**
     * Lance l'application et affiche le menu de jeu.
     */
    public void lancerApplication() {
        while (true) {
            displayer.afficherMenuPrincipal();
            int choix = inputHandler.scanMenuPrincipal(scanner);
            switch (choix) {
              case 1 -> {
                  displayer.afficherPartie();
              }
              case 2 -> {
                  gameReplayer.rejouerPartie(displayer);
              }
              case 3 -> {
                  int nombreJoueurs = inputHandler.demanderNombreJoueurs();
                  boolean espion = inputHandler.demanderModeEspionnage();
                  String nomJoueur = inputHandler.demanderNomJoueur();
                  int couleurJoueur = inputHandler.demanderCouleurJoueur();

                  Partie partie = new InitialiserLeJeuCommande(nomJoueur, couleurJoueur, nombreJoueurs).execute();

                  this.gameRoundManager = new GameRoundManager(inputHandler, espion);
                  partie.setGameRoundManager(this.gameRoundManager);
                  gameRoundManager.demarrer(partie, false);
              }
              case 4 -> {
                  //System.out.println("Merci d'avoir joué à Zheng Shangyou !");
                  System.out.println(Ansi.ansi().fgGreen().a("Merci d'avoir joué à Zheng Shangyou !").reset());
                  return;
              }
              case 5 -> {
                displayer.afficherRegles();
              }
              default -> System.out.println(Ansi.ansi().fgRed().a("Choix invalide. Veuillez choisir une option valide.").reset());

            }
        }
    }
}
