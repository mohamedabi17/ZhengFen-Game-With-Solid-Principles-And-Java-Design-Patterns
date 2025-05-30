package fr.uvsq.cprog.zhengyao.ui;

import fr.uvsq.cprog.zhengyao.model.HistoriquePartie;
import fr.uvsq.cprog.zhengyao.model.Joueur;
import fr.uvsq.cprog.zhengyao.model.Partie;
import fr.uvsq.cprog.zhengyao.services.UserInputHandlerService;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Classe pour rejouer une partie enregistrée.
 */
public class GameReplayer {
    private final UserInputHandlerService inputHandler;
    private final String configFilePath = "src/main/resources/config.properties";
    @SuppressWarnings("unused")
    private GameDisplayer displayer; // Remove initialization here

    public GameReplayer(UserInputHandlerService inputHandler) {
        this.inputHandler = inputHandler;
        this.displayer = new GameDisplayer();
    }

    /**
     * Rejoue une partie enregistrée.
     */
    public void rejouerPartie(GameDisplayer displayer) {
        String nomFichier = displayer.afficherHistorique(); // Use the instance method

        if (nomFichier.isEmpty()) {
            return;
        }

        Properties props = new Properties();

        try (FileInputStream in = new FileInputStream(configFilePath)) {
            props.load(in);
            String cheminDossierHistorique = props.getProperty("historiques.folder.path");

            if (cheminDossierHistorique == null) {
                throw new IOException("Le chemin du dossier HISTORIQUE n'est pas spécifié dans config.properties.");
            }

            Path cheminFichier = Paths.get(cheminDossierHistorique, nomFichier);

            if (Files.exists(cheminFichier)) {
                HistoriquePartie historique = HistoriquePartie.chargerDepuisFichier(cheminFichier.toString());

                System.out.println("Rejouer la partie " + nomFichier.replace(".json", "") + "...");
                System.out.println("Veuillez patienter...");

                // Créer une nouvelle partie avec les mêmes paramètres
                Partie partie = Partie.getInstance(
                        historique.getJoueurs().size(),
                        historique.getJoueurs().get(0).getNom(),
                        historique.getJoueurs().get(0).getCouleur().ordinal() + 1);

                for (Joueur joueur : partie.getJoueurs()) {
                    Joueur joueurHistorique = historique.getJoueurs().stream()
                            .filter(j -> j.getNom().equals(joueur.getNom()))
                            .findFirst()
                            .orElse(null);
                    joueur.recevoirCartes(joueurHistorique.getMain().getCartes());
                    joueur.setCouleur(joueurHistorique.getCouleur());
                    partie.obtenirHistorique().ajouterEtatJoueur(joueurHistorique);
                }

                partie.obtenirHistorique().ajouterEvenement("Rejouer la partie "
                        + nomFichier.replace(".json", "") + "...");

                // Initialize GameDisplayer with the newly created Partie
                this.displayer = new GameDisplayer();

                GameRoundManager gameReplayerNewGameRoundManager = new GameRoundManager(inputHandler, false);

                // Démarre la partie
                gameReplayerNewGameRoundManager.demarrer(partie, true);

            } else {
                System.out.println("Fichier d'historique introuvable.");
            }

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des propriétés : " + e.getMessage());
        }

    }

}
