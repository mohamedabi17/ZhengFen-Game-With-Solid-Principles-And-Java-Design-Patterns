package fr.uvsq.cprog.zhengyao.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente l'historique d'une partie de Zheng Shangyou.
 * Cette classe permet de gérer l'enregistrement des événements, l'état des
 * joueurs et la date de la partie.
 * Elle peut aussi enregistrer l'historique dans un fichier JSON et charger un
 * historique depuis un fichier.
 */
public class HistoriquePartie {

    private List<String> evenements;
    private List<Joueur> joueurs;
    private String date;

    /**
     * Constructeur de l'historique d'une partie avec les événements, les joueurs et
     * la date de la partie.
     *
     * @param evenements La liste des événements de la partie.
     * @param joueurs    La liste des joueurs ayant participé à la partie.
     * @param date       La date à laquelle la partie a eu lieu.
     */
    @JsonCreator
    public HistoriquePartie(
            @JsonProperty("evenements") List<String> evenements,
            @JsonProperty("joueurs") List<Joueur> joueurs,
            @JsonProperty("date") String date) {
        this.evenements = evenements != null ? evenements : new ArrayList<>();
        this.joueurs = joueurs != null ? joueurs : new ArrayList<>();
        this.date = date != null
                ? date
                : LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
    }

    /**
     * Constructeur par défaut de l'historique d'une partie.
     * Initialisation avec des valeurs par défaut :
     * une date actuelle et des listes vides pour les événements et les joueurs.
     */
    public HistoriquePartie() {
        this.evenements = new ArrayList<>();
        this.joueurs = new ArrayList<>();
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
    }

    // Getter methods (necessary for Jackson serialization)

    /**
     * Retourne la liste des événements de la partie.
     *
     * @return La liste des événements.
     */
    public List<String> getEvenements() {
        return evenements;
    }

    /**
     * Retourne la liste des joueurs ayant participé à la partie.
     *
     * @return La liste des joueurs.
     */
    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    /**
     * Retourne la date de la partie sous forme de chaîne de caractères.
     *
     * @return La date de la partie.
     */
    public String getDate() {
        return date;
    }

    /**
     * Ajoute un événement à l'historique des événements de la partie.
     *
     * @param evenement L'événement à ajouter.
     */
    public void ajouterEvenement(String evenement) {
        evenements.add(evenement);
    }

    /**
     * Ajoute l'état d'un joueur à l'historique des joueurs.
     *
     * @param joueur Le joueur dont l'état est à ajouter.
     */
    public void ajouterEtatJoueur(Joueur joueur) {
        joueurs.add(joueur);
    }

    /**
     * Retourne la liste des événements enregistrés.
     *
     * @return La liste des événements.
     */
    public List<String> obtenirEvenements() {
        return evenements;
    }

    /**
     * Retourne la liste des états des joueurs.
     *
     * @return La liste des états des joueurs.
     */
    public List<Joueur> obtenirEtatsJoueurs() {
        return joueurs;
    }

    /**
     * Enregistre l'historique dans un fichier JSON et met à jour la liste des
     * fichiers d'historique.
     *
     * @param cheminDossier        Le dossier où enregistrer le fichier JSON.
     * @param cheminListHistorique Le fichier contenant la liste des fichiers
     *                             d'historique.
     * @throws IOException Si une erreur se produit lors de l'enregistrement dans le
     *                     fichier.
     */
    public void enregistrerDansFichier(String cheminDossier, String cheminListHistorique) throws IOException {
        String formattedDate = date.replace(":", "-");
        String jsonFileName = "historique_" + formattedDate + ".json";
        String fullFilePath = Paths.get(cheminDossier, jsonFileName).toString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(fullFilePath), this);

        // Mise à jour de la liste des fichiers d'historique
        File listFile = new File(cheminListHistorique);
        if (!listFile.exists()) {
            listFile.createNewFile();
        }
        Files.write(listFile.toPath(), (jsonFileName + System.lineSeparator()).getBytes(),
                java.nio.file.StandardOpenOption.APPEND);
    }

    /**
     * Charge un historique de partie à partir d'un fichier JSON.
     *
     * @param cheminFichier Le chemin du fichier JSON à charger.
     * @return L'objet HistoriquePartie chargé.
     * @throws IOException Si une erreur se produit lors du chargement du fichier.
     */
    public static HistoriquePartie chargerDepuisFichier(String cheminFichier) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(cheminFichier), HistoriquePartie.class);
    }
}
