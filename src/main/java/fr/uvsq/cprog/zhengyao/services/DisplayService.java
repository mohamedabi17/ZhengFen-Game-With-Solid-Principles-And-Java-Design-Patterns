package fr.uvsq.cprog.zhengyao.services;

import fr.uvsq.cprog.zhengyao.enumeration.CouleurJoueur;
import fr.uvsq.cprog.zhengyao.model.CombinaisonCartes;
import fr.uvsq.cprog.zhengyao.model.Joueur;
import java.util.List;

/**
 * Interface for display services in the game.
 * Following SOLID principles - Interface Segregation Principle
 */
public interface DisplayService {
    /**
     * Displays hands of all players.
     */
    void afficherMains();

    /**
     * Displays valid combinations for a player.
     *
     * @param combinaisonsValides List of valid combinations
     * @param joueur              The player
     */
    void afficherCombinaisonsValides(List<CombinaisonCartes> combinaisonsValides, Joueur joueur);

    /**
     * Displays the chosen combination.
     *
     * @param combinaisonChoisie The chosen combination
     * @param joueur             The player who chose the combination
     */
    void afficherCombinaisonChoisie(CombinaisonCartes combinaisonChoisie, Joueur joueur);

    /**
     * Spy mode - shows all virtual players' hands.
     */
    void afficherEspionnage();

    /**
     * Displays a player's current state.
     *
     * @param joueur                    The player
     * @param derniereCombinaisonCartes The last played combination
     */
    void afficherEtatJoueur(Joueur joueur, CombinaisonCartes derniereCombinaisonCartes);

    /**
     * Displays scores of all players.
     */
    void afficherScores();

    /**
     * Displays all game history records and returns the selected one.
     * 
     *
     * @return The selected history file name
     */
    String afficherHistorique();

    /**
     * Displays details of a specific game.
     */
    void afficherPartie();

    /**
     * Displays colored text based on player color.
     *
     * @param couleur The player's color
     * @param texte   The text to display
     */
    void afficherTexteColorise(CouleurJoueur couleur, String texte);
}
