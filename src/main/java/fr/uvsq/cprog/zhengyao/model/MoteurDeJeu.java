package fr.uvsq.cprog.zhengyao.model;

/**
 * Classe abstraite représentant un moteur de jeu.
 * Un moteur de jeu est responsable de déterminer les actions à effectuer
 * en fonction de l'état du jeu et des règles associées.
 */
public abstract class MoteurDeJeu {
    protected String nom;

    /**
     * Constructeur de la classe MoteurDeJeu.
     *
     * @param nom Le nom du moteur de jeu.
     */
    public MoteurDeJeu(String nom) {
        this.nom = nom;
    }

    /**
     * Permet de jouer un tour.
     *
     * @param main                     La main du joueur.
     * @param derniereCombinaisonJouee La dernière combinaison jouée.
     * @return La combinaison de cartes jouée.
     */
    public abstract CombinaisonCartes choisirCombinaison(MainDeCartes main, CombinaisonCartes derniereCombinaisonJouee);
}
