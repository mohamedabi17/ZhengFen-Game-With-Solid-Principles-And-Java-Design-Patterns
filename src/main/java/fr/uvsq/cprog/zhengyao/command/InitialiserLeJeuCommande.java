package fr.uvsq.cprog.zhengyao.command;

import fr.uvsq.cprog.zhengyao.model.Partie;

/**
 * Commande pour initialiser une partie de jeu.
 */
public class InitialiserLeJeuCommande {

    /** Le nom du joueur. */
    private String nomJoueur;

    /** La couleur du joueur (1 à 4). */
    private Integer couleurJoueur;

    /** Le nombre total de joueurs (2 à 4). */
    private Integer nombreJoueurs;

    /**
     * Constructeur de la commande d'initialisation du jeu.
     *
     * @param nomJoueur     Le nom du joueur principal.
     * @param couleurJoueur La couleur du joueur principal (1 à 4).
     * @param nombreJoueurs Le nombre total de joueurs (entre 2 et 4).
     */
    public InitialiserLeJeuCommande(
            String nomJoueur, Integer couleurJoueur, Integer nombreJoueurs) {
        this.nomJoueur = nomJoueur;
        this.couleurJoueur = couleurJoueur;
        this.nombreJoueurs = nombreJoueurs;
    }

    /**
     * Exécute la commande pour initialiser une partie de jeu.
     *
     * @return Une instance de {@link Partie} initialisée avec les paramètres
     *         fournis.
     * @throws IllegalArgumentException si le nombre de joueurs n'est pas compris
     *                                  entre 2 et 4,
     *                                  ou si la couleur du joueur n'est pas
     *                                  comprise entre 1 et 4.
     */
    public Partie execute() {
        if (nombreJoueurs < 2 || nombreJoueurs > 4) {
            throw new IllegalArgumentException(
                    "Le nombre de joueurs doit être compris entre 2 et 4.");
        }

        if (couleurJoueur < 1 || couleurJoueur > 4) {
            throw new IllegalArgumentException(
                    "La couleur du joueur doit être comprise entre 1 et 4.");
        }

        return Partie.getInstance(nombreJoueurs, nomJoueur, couleurJoueur);
    }
}
