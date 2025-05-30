package fr.uvsq.cprog.zhengyao.services;

// import fr.uvsq.cprog.zhengyao.ui.GameDisplayer;
import java.util.Scanner;

import org.fusesource.jansi.Ansi;

/**
 * Classe pour gérer les entrées utilisateur.
 */
public class UserInputHandlerService {
    private final Scanner scanner;
    // private final MessageDisplayService displayService = new GameDisplayer();

    public UserInputHandlerService(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Demande à l'utilisateur le nombre de joueurs.
     *
     * @return Le nombre de joueurs.
     */
    public int demanderNombreJoueurs() {
        System.out.println(Ansi.ansi().fgYellow().a("Veuillez entrer le nombre de joueurs (2 à 4)").reset());
        int nombreJoueurs = scanner.nextInt();
        scanner.nextLine();
        return nombreJoueurs;
    }

    /**
     * Demande à l'utilisateur s'il souhaite activer le mode espionnage.
     *
     * @return {@code true} si le mode espionnage est activé, {@code false} sinon.
     */
    public boolean demanderModeEspionnage() {
        System.out.println(Ansi.ansi().fgYellow().a("Voulez-vous activer le mode espionnage ? o/n").reset());
        String entree = scanner.nextLine().trim();
        return entree.equalsIgnoreCase("o");
    }

    /**
     * Demande à l'utilisateur le nom du joueur principal.
     *
     * @return Le nom du joueur principal.
     */
    public String demanderNomJoueur() {
        System.out.println(Ansi.ansi().fgYellow().a("Veuillez entrer le nom du joueur principal").reset());
        return scanner.nextLine();
    }

    /**
     * Demande à l'utilisateur de choisir une couleur pour le joueur principal.
     *
     * @return Le numéro correspondant à la couleur choisie.
     */
    public int demanderCouleurJoueur() {
       /*  displayService.afficherMessage("Veuillez choisir la couleur de votre joueur : ");
        displayService.afficherMessage("1. Rouge");
        displayService.afficherMessage("2. Bleu");
        displayService.afficherMessage("3. Vert");
        displayService.afficherMessage("4. Jaune");
        displayService.afficherDemande("");*/
        System.out.println(Ansi.ansi().fgYellow().a("Veuillez choisir la couleur de votre joueur : ").reset());
        System.out.println(Ansi.ansi().fgYellow().a("1. Rouge").reset());
        System.out.println(Ansi.ansi().fgYellow().a("2. Bleu").reset());
        System.out.println(Ansi.ansi().fgYellow().a("3. Vert").reset());
        System.out.println(Ansi.ansi().fgYellow().a("4. Jaune").reset());
        System.out.println(Ansi.ansi().fgYellow().a("").reset());

        int couleurJoueur = scanner.nextInt();
        scanner.nextLine();
        return couleurJoueur;
    }

    /**
     * Affiche le menu principal et retourne le choix de l'utilisateur.
     *
     * @return Le choix de l'utilisateur.
     */
    public int scanMenuPrincipal(Scanner scanner) {
        if (!scanner.hasNextInt()) {
            scanner.nextLine(); // Clear invalid input
            return -1;
        }
        int choix = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer
        return choix;
    }

    public int lireEntier() {
        return scanner.nextInt();
    }

    public String lireLigne() {
        return scanner.nextLine();
    }
}
