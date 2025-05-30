package fr.uvsq.cprog.zhengyao.ui;

import fr.uvsq.cprog.zhengyao.enumeration.CouleurJoueur;
import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import fr.uvsq.cprog.zhengyao.model.Carte;
import fr.uvsq.cprog.zhengyao.model.CombinaisonCartes;
import fr.uvsq.cprog.zhengyao.model.HistoriquePartie;
import fr.uvsq.cprog.zhengyao.model.Joueur;
import fr.uvsq.cprog.zhengyao.model.JoueurHumain;
import fr.uvsq.cprog.zhengyao.model.JoueurVirtuel;
import fr.uvsq.cprog.zhengyao.model.Partie;
import fr.uvsq.cprog.zhengyao.services.DisplayService;
import fr.uvsq.cprog.zhengyao.services.MessageDisplayService;
import fr.uvsq.cprog.zhengyao.utils.CardUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.fusesource.jansi.Ansi;

/**
 * Implementation of DisplayService for game information.
 */
@SuppressWarnings("unused")
public class GameDisplayer implements DisplayService, MessageDisplayService {
    /**
     * Affiche les mains des joueurs dans les deux formats.
     */
    public void afficherMains() {
        System.out.println("\nMains des joueurs :");
        for (Joueur joueur : Partie.getInstance().getJoueurs()) {
            List<String> cartesEnTexte = joueur.getMain().getCartes().stream()
                    .map(Carte::toString)
                    .collect(Collectors.toList());
            @SuppressWarnings("unused")
            String formatTexte = CardUtils.formatPlayerCards(joueur.getNom(), cartesEnTexte);
            // String formatUnicode = joueur.getNom() + ": " +
            // CardUtils.toUnicodeSymbols(joueur.getMain().getCartes());
            // afficherTexteColorise(joueur.getCouleur(), formatTexte + " | " +
            // formatUnicode);
            // afficherTexteColorise(joueur.getCouleur(), formatTexte);
        }
    }

    /**
     * Affiche les combinaisons valides d'un joueur, triées par type et force.
     *
     * @param combinaisonsValides La liste des combinaisons valides.
     * @param joueur              Le joueur pour lequel afficher les combinaisons.
     */
    public void afficherCombinaisonsValides(List<CombinaisonCartes> combinaisonsValides, Joueur joueur) {
        // Filter out single cards and sort combinations by type and strength
        List<CombinaisonCartes> filteredCombinaisons = combinaisonsValides.stream()
                .filter(combinaison -> combinaison.getType() != TypeCombinaison.CARTE_UNIQUE)
                .sorted(Comparator.reverseOrder())
                .toList();

        if (joueur instanceof JoueurHumain) {
            afficherTexteColorise(joueur.getCouleur(), "\n--- Combinaisons valides générées (triées) ---");

            for (int i = 0; i < filteredCombinaisons.size(); i++) {
                CombinaisonCartes combinaison = filteredCombinaisons.get(i);

                // Format each card with its Unicode symbol
                String cartesFormatees = combinaison.getCartes().stream()
                        .map(carte -> carte + " " + CardUtils.getUnicodeSymbol(carte))
                        .collect(Collectors.joining(", "));

                afficherTexteColorise(joueur.getCouleur(),
                        (i + 1) + ". " + combinaison.getType().getCombinaison() + " [" + cartesFormatees + "]");
            }
        }
    }

    /**
     * Affiche un résumé de l'initialisation de la partie.
     *
     * @param joueurGagnant Le joueur qui a gagné la partie.
     */
    public void afficherFinDeJeux(Joueur joueurGagnant) {
        afficherTexteColorise(joueurGagnant.getCouleur(),
                "\n\n================== PARTIE TERMINÉE ==================\n");
        afficherTexteColorise(joueurGagnant.getCouleur(), "Félicitations à " + joueurGagnant.getNom()
                + " qui a gagné la partie avec " + joueurGagnant.getPoints() + " points !\n");
        afficherTexteColorise(joueurGagnant.getCouleur(),
                "================== FIN DU JEU ==================");

    }

    /**
     * Affiche un résumé de l'initialisation de la partie.
     *
     * @param partie La partie initialisée.
     */
    @SuppressWarnings("unused")
    private void afficherResumeInitialisation(Partie partie) {
        System.out.println("Initialisation de la partie terminée.");
        System.out.println("Nombre de joueurs: " + partie.getNombreJoueurs());
        for (int i = 0; i < partie.getNombreJoueurs(); i++) {
            System.out.println("Joueur " + partie.getJoueurs().get(i).getNom()
                    + " a " + partie.getJoueurs().get(i).getMain().getCartes().size() + " cartes");
        }
    }

    /**
     * Affiche le menu principal.
     */
    public void afficherMenuPrincipal() {
        System.out.println(Ansi.ansi().fgBrightBlue().a("\n╔════════════════════════════════════╗").reset());
        System.out.println(Ansi.ansi().fgBrightBlue().a("║        MENU PRINCIPAL DU JEU       ║").reset());
        System.out.println(Ansi.ansi().fgBrightBlue().a("╚════════════════════════════════════╝").reset());

        System.out.println(Ansi.ansi().fgBrightGreen().a("1. Consulter l'historique des parties").reset());
        System.out.println(Ansi.ansi().fgBrightGreen().a("2. Rejouer une partie").reset());
        System.out.println(Ansi.ansi().fgBrightGreen().a("3. Commencer une nouvelle partie").reset());
        System.out.println(Ansi.ansi().fgBrightGreen().a("4. Quitter le jeu").reset());
        System.out.println(Ansi.ansi().fgBrightGreen().a("5. Consulter les règles du jeu").reset());

        System.out.println();
        System.out.println(
                Ansi.ansi().fgBrightYellow().a("Veuillez entrer le numéro correspondant à votre choix.").reset());
        System.out.print("> ");
    }

    /**
     * Affiche la combinaison choisie par un joueur.
     *
     * @param combinaisonChoisie La combinaison de cartes choisie.
     * @param joueur             Le joueur qui a choisi la combinaison.
     */
    public void afficherCombinaisonChoisie(CombinaisonCartes combinaisonChoisie, Joueur joueur) {
        // Format each card with its Unicode symbol
        String cartesFormatees = combinaisonChoisie.getCartes().stream()
                .map(carte -> carte + " " + CardUtils.getUnicodeSymbol(carte))
                .collect(Collectors.joining(", "));

        afficherTexteColorise(joueur.getCouleur(),
                "\n" + joueur.getNom() + " : " + combinaisonChoisie.getType().getCombinaison() + " [" + cartesFormatees
                        + "]");

    }

    /**
     * Affiche les mains des joueurs virtuels.
     */
    public void afficherEspionnage() {
        System.out.println("\n--- Mains des joueurs virtuels ---");
        for (Joueur joueur : Partie.getInstance().getJoueurs()) {
            if (joueur instanceof JoueurVirtuel) {
                // Format each card with its Unicode symbol
                String cartesFormatees = joueur.getMain().getCartes().stream()
                        .map(carte -> carte + " " + CardUtils.getUnicodeSymbol(carte))
                        .collect(Collectors.joining(", "));

                afficherTexteColorise(joueur.getCouleur(), joueur.getNom() + " : [" + cartesFormatees + "]");
            }
        }
    }

    /**
     * Affiche l'historique d'une partie.
     */
    public void afficherPartie() {
        String nomFichier = afficherHistorique();

        if (nomFichier.isEmpty()) {
            return;
        }

        Properties props = new Properties();

        try (FileInputStream in = new FileInputStream("src/main/resources/config.properties")) { // Fixed configFilePath
            props.load(in);
            String cheminDossierHistorique = props.getProperty("historiques.folder.path");

            if (cheminDossierHistorique == null || cheminDossierHistorique.isBlank()) {
                throw new IOException("Le chemin du dossier HISTORIQUE n'est pas spécifié dans config.properties.");
            }

            Path cheminFichier = Paths.get(cheminDossierHistorique, nomFichier);

            if (Files.exists(cheminFichier)) {
                HistoriquePartie historique = HistoriquePartie.chargerDepuisFichier(cheminFichier.toString());

                System.out.println("======================= Historique de la partie " + nomFichier.replace(
                        ".json", "") + " =======================");

                for (String evenement : historique.obtenirEvenements()) {
                    System.out.println(evenement);
                }
            } else {
                System.out.println("Fichier d'historique introuvable : " + cheminFichier);
            }

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des propriétés ou de l'historique : "
                    + e.getMessage());
        }
    }

    /**
     * Affiche l'historique des parties enregistrées et retourne le nom du fichier
     * sélectionné.
     *
     * @return Le nom du fichier sélectionné ou une chaîne vide si aucun fichier
     *         n'est sélectionné.
     */
    public String afficherHistorique() { // Changed from private to public
        Properties props = new Properties();

        try (FileInputStream in = new FileInputStream("src/main/resources/config.properties")) { // Fixed configFilePath
            props.load(in);
            String cheminFichierHistorique = props.getProperty("liste_parties_jouees.file.path");

            if (cheminFichierHistorique == null || cheminFichierHistorique.isBlank()) {
                throw new IOException("Le chemin du fichier HISTORIQUE n'est pas spécifié dans config.properties.");
            }

            Path cheminFichier = Paths.get(cheminFichierHistorique);

            if (Files.exists(cheminFichier)) {
                List<String> lignes = Files.readAllLines(cheminFichier);
                if (lignes.isEmpty()) {
                    System.out.println("Aucune partie enregistrée.");
                    return "";
                }

                System.out.println("Historique des parties :");
                for (int i = 0; i < lignes.size(); i++) {
                    System.out.println((i + 1) + ". " + lignes.get(i).replace(".json", ""));
                }

                System.out.print("Veuillez choisir une partie (1-" + lignes.size() + ") : ");
                java.util.Scanner scanner = new java.util.Scanner(System.in);
                int choix = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer

                scanner.close();

                if (choix > 0 && choix <= lignes.size()) {
                    return lignes.get(choix - 1);
                } else {
                    System.out.println("Choix invalide.");
                }
            } else {
                System.out.println("Fichier d'historique introuvable : " + cheminFichier);
            }

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des propriétés ou de l'historique : "
                    + e.getMessage());
        }

        return "";
    }

    /**
     * Affiche les scores des joueurs.
     */
    public void afficherScores() {
        System.out.println("\n--- Scores ---");
        for (Joueur joueur : Partie.getInstance().getJoueurs().stream()
                .sorted((j1, j2) -> Integer.compare(j2.getPoints(), j1.getPoints()))
                .toList()) {
            afficherTexteColorise(joueur.getCouleur(), joueur.getNom() + ": " + joueur.getPoints() + " points");
            Partie.getInstance().obtenirHistorique().ajouterEvenement("Le score de " + joueur.getNom()
                    + " devient " + joueur.getPoints() + " points.");
        }
    }

    /**
     * Affiche les etat des joueurs.
     */
    public void afficherEtatJoueur(Joueur joueur, CombinaisonCartes derniereCombinaisonCartes) {
        afficherTexteColorise(joueur.getCouleur(), "\n--- Tour de " + joueur.getNom() + " ---");
    }

    /**
     * Affiche les etat des joueurs humains.
     */
    public void afficherEtatJoueurHumain(Joueur joueur, CombinaisonCartes derniereCombinaisonCartes) {
        // Display the sorted hand with indices
        System.out.println("Votre main :");
        List<Carte> cartes = joueur.getMain().getCartes();
        List<Carte> cartesTriees = cartes.stream()
                .sorted(Carte::compareTo)
                .collect(Collectors.toList());
        // System.out.println(cartesTriees);
        for (int i = 0; i < cartes.size(); i++) {
            afficherTexteColorise(joueur.getCouleur(),
                    i + ": {" + cartesTriees.get(i) + CardUtils.getUnicodeSymbol(cartesTriees.get(i)) + "}");
            if (i < cartesTriees.size() - 1) {
                System.out.print(" | "); // Separator between cards
            }
        }
        System.out.println(); // Move to the next line after printing the hand

        // Display the last combination played
        if (derniereCombinaisonCartes.getCartes() == null || derniereCombinaisonCartes.getCartes().isEmpty()) {
            afficherTexteColorise(joueur.getCouleur(),
                    "Aucune combinaison actuellement sur le tapis.");
        }
    }

    /**
     * Affiche un message générique.
     *
     * @param message Le message à afficher.
     */
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    /**
     * Affiche un message sans saut de ligne.
     *
     * @param message Le message à afficher.
     */
    public void afficherMessageInline(String message) {
        System.out.print(message);
    }

    /**
     * Affiche un message d'erreur.
     *
     * @param message Le message d'erreur à afficher.
     */
    public void afficherErreur(String message) {
        System.err.println(message);
    }

    /**
     * Affiche un message pour demander une entrée utilisateur.
     *
     * @param message Le message à afficher.
     */
    public void afficherDemande(String message) {
        System.out.print(message + " > ");
    }

    /**
     * Affiche les cartes jouées par un joueur dans les deux formats (texte et
     * Unicode).
     *
     * @param joueur            Le joueur qui a joué les cartes.
     * @param combinaisonAjouer La combinaison de cartes jouée.
     */
    void afficherCartesJouees(Joueur joueur, CombinaisonCartes combinaisonAjouer) {
        String formatUnicode = joueur.getNom() + ": "
                + CardUtils.toUnicodeSymbols(combinaisonAjouer.getCartes());
        String formatTexte = joueur.getNom() + ": "
                + combinaisonAjouer.getCartes();

        // afficherTexteColorise(joueur.getCouleur(), formatTexte + " | " +
        // formatUnicode);
        afficherTexteColorise(joueur.getCouleur(), formatUnicode);
    }

    /**
     * Static utility method for displaying colored text.
     *
     * @param couleur The player's color.
     * @param texte   The text to display.
     */
    public void afficherTexteColorise(CouleurJoueur couleur, String texte) {
        switch (couleur) {
          case ROUGE:
              System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a(texte).reset());
              break;
          case BLEU:
              System.out.println(Ansi.ansi().fg(Ansi.Color.BLUE).a(texte).reset());
              break;
          case VERT:
              System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a(texte).reset());
              break;
          case JAUNE:
              System.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW).a(texte).reset());
              break;
          default:
              System.out.println(texte); // Par défaut, pas de couleur
        }
    }

    /**
     * Affiche les informations de début de manche.
     */
    public void afficherDebutManche(int mancheCourante, String premierJoueurNom) {
        System.out.println("\n\n================== NOUVEAU PLI COMMENCE ==================\n");
        System.out.println("Début de la manche " + mancheCourante + ".");
        System.out.println("\n" + premierJoueurNom + " commence la manche.");
    }

    /**
     * Affiche le message quand un joueur passe son tour.
     */
    public void afficherPasseTour(Joueur joueur) {
        afficherTexteColorise(joueur.getCouleur(), joueur.getNom() + " passe son tour.");
    }

    /**
     * Affiche le message de fin de pli.
     */
    public void afficherFinPli() {
        System.out.println("\nTous les joueurs ont passé. Le pli se termine.");
        System.out.println("\n=======================================================================\n");
    }

    /**
     *  afficherRegles.
     */
    public void afficherRegles() {
        System.out.println(Ansi.ansi().fgBrightBlue().a("\n╔════════════════════════════════════╗").reset());
        System.out.println(Ansi.ansi().fgBrightBlue().a("║        RÈGLES DU JEU ZHENGYAO       ║").reset());
        System.out.println(Ansi.ansi().fgBrightBlue().a("╚════════════════════════════════════╝").reset());

        System.out.println(Ansi.ansi().fgYellow().a(
                "\nBut du jeu :\n" 
                + "Chaque joueur essaie de se débarrasser de toutes ses cartes.\n" 
                        + "Le premier joueur sans carte gagne la partie.\n\n" 
                        + "Déroulement :\n" 
                        + "- Les joueurs jouent chacun leur tour dans le sens des aiguilles d'une montre.\n" 
                        + "- Un joueur peut jouer une carte ou une combinaison supérieure à celle sur la table.\n" 
                        + "- Si un joueur ne peut pas jouer, il passe son tour.\n\n" 
                        + "Types de combinaisons autorisées :\n" 
                        + "- Carte simple\n" 
                        + "- Paire\n" 
                        + "- Brelan\n" 
                        + "- Suite de 5 cartes ou plus\n" 
                        + "- Bombes (brelan de deux + paire, ou quatre cartes identiques)\n")
                .reset());

        System.out.println(Ansi.ansi().fgCyan().a("\nAppuyez sur Entrée pour revenir au menu...").reset());
        try {
            System.in.read(); // pause
        } catch (Exception e) {
            // ignore
        }
    }

}
