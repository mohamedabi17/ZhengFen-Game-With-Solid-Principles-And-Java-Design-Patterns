package fr.uvsq.cprog.zhengyao.ui;

import fr.uvsq.cprog.zhengyao.services.UserInputHandlerService;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import org.fusesource.jansi.Ansi;

/**
 * Classe pour gérer l'affichage de l'historique des parties.
 */
public class HistoryManager {
    private final UserInputHandlerService inputHandler;
    private final String configFilePath = "src/main/resources/config.properties"; // Define configFilePath

    public HistoryManager(UserInputHandlerService inputHandler) {
        this.inputHandler = inputHandler;
    }

    /**
     * Affiche l'historique des parties enregistrées.
     *
     * @return Le nom du fichier de l'historique sélectionné.
     */
    public String afficherHistorique() {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(configFilePath)) {
            props.load(in);
            String cheminFichierHistorique = props.getProperty("liste_parties_jouees.file.path");

            if (cheminFichierHistorique == null) {
                throw new IOException("Le chemin du fichier HISTORIQUE n'est pas spécifié dans config.properties.");
            }

            Path cheminFichier = Paths.get(cheminFichierHistorique);

            // Lire le fichier d'historique
            System.out.println("Historique des parties :");

            if (Files.exists(cheminFichier)) {
                List<String> lignes = Files.readAllLines(cheminFichier);
                for (String ligne : lignes) {
                    // Afficher chaque ligne du fichier sans l'extension .json
                    System.out.println("\t Partie "
                            + (lignes.indexOf(ligne) + 1) + ": "
                            + ligne.replace(".json", ""));
                }

                if (lignes.isEmpty()) {
                    System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("Aucune partie enregistrée.").reset());
                    return "";
                }

                System.out.println(Ansi.ansi().fgYellow().a("Veuillez choisir la partie souhaitée : ").reset());
                System.out.print("> ");
                int choix = inputHandler.lireEntier();
                inputHandler.lireLigne(); // Consume newline

                if (choix > 0 && choix <= lignes.size()) {
                    return lignes.get(choix - 1);
                } else {
                    System.out.println("Choix invalide. Veuillez choisir de 1 à " + lignes.size());
                }

            } else {
                System.out.println("Fichier d'historique introuvable.");
            }

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des propriétés : " + e.getMessage());
        }

        return "";
    }
}
