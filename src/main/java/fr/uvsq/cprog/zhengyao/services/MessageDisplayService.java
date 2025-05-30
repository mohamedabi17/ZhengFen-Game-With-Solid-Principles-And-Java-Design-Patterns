package fr.uvsq.cprog.zhengyao.services;

/**
 * Interface for message display services in the game.
 */
public interface MessageDisplayService {
    /**
     * Affiche un message générique.
     *
     * @param message Le message à afficher.
     */
    void afficherMessage(String message);

    /**
     * Affiche un message sans saut de ligne.
     *
     * @param message Le message à afficher.
     */
    void afficherMessageInline(String message);

    /**
     * Affiche un message d'erreur.
     *
     * @param message Le message d'erreur à afficher.
     */
    void afficherErreur(String message);

    /**
     * Affiche un message pour demander une entrée utilisateur.
     *
     * @param message Le message à afficher.
     */
    void afficherDemande(String message);
} 