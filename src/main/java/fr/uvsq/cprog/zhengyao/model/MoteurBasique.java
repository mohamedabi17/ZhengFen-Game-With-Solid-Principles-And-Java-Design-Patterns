package fr.uvsq.cprog.zhengyao.model;

import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import java.util.List;

/**
 * Moteur de jeu basique pour le jeu de cartes.
 * Cette implémentation utilise une logique simple pour choisir une combinaison
 * à jouer.
 */
public class MoteurBasique extends MoteurDeJeu {

    /**
     * Constructeur de MoteurBasique.
     *
     * @param name le nom du moteur de jeu.
     */
    public MoteurBasique(String name) {
        super(name);
    }

    /**
     * Choisit une combinaison de cartes à jouer en fonction de la main et de la
     * dernière combinaison jouée.
     *
     * @param main                     la main du joueur.
     * @param derniereCombinaisonJouee la dernière combinaison jouée par un autre
     *                                 joueur.
     * @return une nouvelle combinaison de cartes ou null si aucune n'est valide.
     */
    @Override
    public CombinaisonCartes choisirCombinaison(MainDeCartes main, CombinaisonCartes derniereCombinaisonJouee) {
        if (!peutJouer(derniereCombinaisonJouee)) {
            return null;
        }

        main.trier();
        return trouverCarteValide(main, derniereCombinaisonJouee);
    }

    /**
     * Vérifie si le moteur peut jouer en fonction de la dernière combinaison jouée.
     *
     * @param derniereCombinaisonJouee la dernière combinaison jouée.
     * @return true si le moteur peut jouer, false sinon.
     */
    private boolean peutJouer(CombinaisonCartes derniereCombinaisonJouee) {
        return derniereCombinaisonJouee == null
                || derniereCombinaisonJouee.getType() == TypeCombinaison.INVALIDE
                || derniereCombinaisonJouee.getType() == TypeCombinaison.CARTE_UNIQUE;
    }

    /**
     * Trouve une carte valide à jouer en fonction de la main et de la dernière
     * combinaison jouée.
     *
     * @param main                     la main du joueur.
     * @param derniereCombinaisonJouee la dernière combinaison jouée.
     * @return une combinaison de cartes valide ou null si aucune n'est trouvée.
     */
    private CombinaisonCartes trouverCarteValide(MainDeCartes main, CombinaisonCartes derniereCombinaisonJouee) {
        for (Carte carte : main.getCartes()) {
            if (derniereCombinaisonJouee == null || carte.getForce() > derniereCombinaisonJouee.getForce()) {
                return new CombinaisonCartes(TypeCombinaison.CARTE_UNIQUE, List.of(carte));
            }
        }
        return null;
    }
}
