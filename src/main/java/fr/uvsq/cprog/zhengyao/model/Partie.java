package fr.uvsq.cprog.zhengyao.model;

import fr.uvsq.cprog.zhengyao.enumeration.CarteCouleur;
import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import fr.uvsq.cprog.zhengyao.enumeration.CouleurJoueur;
import fr.uvsq.cprog.zhengyao.ui.GameRoundManager;
import java.util.ArrayList;
import java.util.List;
import org.fusesource.jansi.Ansi;

/**
 * Classe représentant une partie de Zheng Shangyou.
 * Utilise le pattern singleton pour garantir qu'une seule instance est créée.
 */
@SuppressWarnings("unused")
public class Partie {
    private static Partie instance = null;
    private final List<Joueur> joueurs; // Liste des joueurs participant à la partie
    private final int nombreJoueurs; // Nombre total de joueurs
    private final HistoriquePartie historique;
    private final PaquetDeCartes paquet; // Ajout de l'attribut PaquetDeCartes
    private GameRoundManager gameRoundManager; // Add a field for GameRoundManager

    /**
     * Constructeur privé pour la classe Partie.
     * Initialise les joueurs de la partie.
     *
     * @param nombreJoueurs Le nombre total de joueurs.
     * @param nomJoueur     Le nom du joueur humain principal.
     * @param couleur       La couleur choisie par le joueur humain.
     */
    private Partie(int nombreJoueurs, String nomJoueur, int couleur) {
        if (nombreJoueurs < 2) {
            throw new IllegalArgumentException("La partie doit avoir au moins 2 joueurs.");
        }

        this.joueurs = new ArrayList<>();
        this.nombreJoueurs = nombreJoueurs;
        this.historique = new HistoriquePartie();
        this.paquet = PaquetDeCartes.getInstance(); // Use Singleton instance

        // Ajoute le joueur humain
        if (couleur < 1 || couleur > CouleurJoueur.values().length) {
            throw new IllegalArgumentException("Couleur de joueur invalide.");
        }

        Joueur joueurPrincipal = new JoueurHumain(nomJoueur, CouleurJoueur.values()[couleur - 1]);
        joueurs.add(joueurPrincipal);

        // Ajoute les joueurs virtuels
        for (int i = 1; i < nombreJoueurs; i++) {
            // On s'assure que les couleurs des joueurs sont uniques et valides
            CouleurJoueur couleurVirtuel = CouleurJoueur.values()[(couleur - 1 + i) % CouleurJoueur.values().length];
            MoteurDeJeu moteur = i % 2 == 0 ? new MoteurBasique(nomJoueur) : new MoteurSophistique(nomJoueur);
            Joueur joueur = new JoueurVirtuel("JV_" + i, couleurVirtuel, moteur);
            joueurs.add(joueur);
        }

        historique.ajouterEvenement("Partie initialisée avec " + nombreJoueurs + " joueurs.");
        historique.ajouterEvenement("Le joueur principal est " + nomJoueur);
    }

    /**
     * Méthode pour obtenir ou créer une instance de Partie.
     *
     * @param nombreJoueurs Le nombre total de joueurs.
     * @param nomJoueur     Le nom du joueur humain principal.
     * @param couleur       La couleur choisie par le joueur humain.
     * @return L'instance unique de Partie.
     */
    public static synchronized Partie getInstance(int nombreJoueurs, String nomJoueur, int couleur) {
        if (instance == null) {
            instance = new Partie(nombreJoueurs, nomJoueur, couleur);
        }
        return instance;
    }

    /**
     * Retourne l'instance unique de la partie.
     * Utilisé lorsque la partie a déjà été initialisée.
     *
     * @return L'instance unique de Partie.
     * @throws IllegalStateException Si la partie n'a pas encore été initialisée.
     */
    public static Partie getInstance() {
        if (instance == null) {
            throw new IllegalStateException("La partie n'a pas été initialisée.");
        }
        return instance;
    }

    /**
     * Réinitialise l'instance unique de la partie.
     * Utile dans certains tests ou redémarrages.
     */
    public static void resetInstance() {
        instance = null;
    }

    /**
     * Affiche les mains initiales de tous les joueurs.
     */
    public void afficherMains() {
        System.out.println("\nMains initiales des joueurs :");
        for (Joueur joueur : joueurs) {
            System.out.println(joueur.getNom() + " : " + joueur.getMain().getCartes());
        }
    }

    /**
     * Retourne le nombre total de joueurs dans la partie.
     *
     * @return Le nombre de joueurs.
     */
    public int getNombreJoueurs() {
        return nombreJoueurs;
    }

    /**
     * Retourne la liste des joueurs participant à la partie.
     *
     * @return La liste des joueurs.
     */
    public List<Joueur> getJoueurs() {
        return new ArrayList<>(joueurs); // Retourne une copie pour éviter les modifications directes
    }

    /**
     * Retourne un joueur par son nom.
     *
     * @param nom Le nom du joueur à rechercher.
     * @return Le joueur correspondant, ou null s'il n'est pas trouvé.
     */
    public Joueur getJoueurByName(String nom) {
        return joueurs.stream()
                .filter(joueur -> joueur.getNom().equals(nom))
                .findFirst()
                .orElse(null);
    }

    /**
     * Distribue les points aux joueurs en fonction de leur classement.
     *
     * @param classement La liste des joueurs classés du premier au dernier.
     */
    public void distribuerPoints(List<Joueur> classement) {
        int[] pointsDistribues = { 150, 100, 50, 30 };
        for (int i = 0; i < classement.size(); i++) {
            classement.get(i).ajouterPoints(pointsDistribues[i]);
        }
    }

    /**
     * Vérifie si un joueur a atteint le seuil de points pour gagner.
     *
     * @return Le joueur gagnant, ou null si aucun joueur n'a atteint le seuil.
     */
    public Joueur verifierGagnant() {
        for (Joueur joueur : joueurs) {
            if (joueur.getPoints() >= 500) {
                return joueur;
            }
        }
        return null;
    }

    /**
     * retourne l'historique de la partie.
     */
    public HistoriquePartie obtenirHistorique() {
        return historique;
    }

    /**
     * Retourne le paquet de cartes utilisé dans la partie.
     *
     * @return Le paquet de cartes.
     */
    public PaquetDeCartes getPaquet() {
        return paquet;
    }

    /**
     * Définit une instance de GameRoundManager pour cette partie.
     *
     * @param gameRoundManager L'instance de GameRoundManager à associer.
     */
    public void setGameRoundManager(GameRoundManager gameRoundManager) {
        this.gameRoundManager = gameRoundManager;
    }

    /**
     * Retourne l'instance de GameRoundManager associée à cette partie.
     *
     * @return L'instance de GameRoundManager, ou null si elle n'a pas été définie.
     */
    public GameRoundManager getGameRoundManager() {
        return gameRoundManager;
    }

    /**
     * Affiche la main des joueurs virtuels dans la partie.
     */
    public void espionner() {
        System.out.println("\n--- Espionnage des cartes des autres joueurs ---");
        for (Joueur joueur : joueurs) {
            if (joueur instanceof JoueurVirtuel) {
                JoueurVirtuel jv = (JoueurVirtuel) joueur;
                afficherTexteColorise(jv.getCouleur(), joueur.toString());
            }
        }
    }

    private void afficherTexteColorise(CouleurJoueur couleur, String texte) {
        if (couleur == CouleurJoueur.ROUGE) {
            System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a(texte).reset());
        } else if (couleur == CouleurJoueur.BLEU) {
            System.out.println(Ansi.ansi().fg(Ansi.Color.BLUE).a(texte).reset());
        } else if (couleur == CouleurJoueur.VERT) {
            System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a(texte).reset());
        } else if (couleur == CouleurJoueur.JAUNE) {
            System.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW).a(texte).reset());
        } else {
            System.out.println(texte); // Par défaut, pas de couleur
        }
    }
}
