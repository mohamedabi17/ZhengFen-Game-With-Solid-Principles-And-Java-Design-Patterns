package fr.uvsq.cprog.zhengyao.model;

import static fr.uvsq.cprog.zhengyao.model.GenerateurCombinaisons.genererCombinaisonsValides;

import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import java.util.Collections;
import java.util.List;

/**
 * Moteur de jeu sophistiqué.
 * Cette classe choisit la meilleure combinaison possible basée sur les cartes
 * disponibles et la dernière combinaison jouée.
 */
public class MoteurSophistique extends MoteurDeJeu {
    /**
     * Constructeur de MoteurSophistique.
     *
     * @param name le nom du moteur de jeu.
     */

    public MoteurSophistique(String name) {
        super(name);

    }

    /**
     * Choisit la combinaison de cartes à jouer en fonction de la main et de la
     * dernière combinaison jouée.
     *
     * @param main                     La main de cartes actuelle.
     * @param derniereCombinaisonJouee La dernière combinaison jouée.
     * @return La combinaison de cartes choisie, ou null si aucune combinaison
     *         valide ne peut être jouée.
     */
    @Override
    public CombinaisonCartes choisirCombinaison(MainDeCartes main, CombinaisonCartes derniereCombinaisonJouee) {
        if (main == null || main.getCartes() == null) {
            throw new IllegalStateException("La main ou les cartes ne peuvent pas être null");
        }

        // logEtatInitial(main, derniereCombinaisonJouee);

        List<CombinaisonCartes> combinaisonsValides = genererCombinaisonsValides(main);
        // logCombinaisonsValides(combinaisonsValides);

        if (combinaisonsValides.isEmpty()) {
            return null; // No valid combinations available
        }

        List<CombinaisonCartes> combinaisonsValidesSuperieures = filtrerCombinaisonsSuperieures(combinaisonsValides,
                derniereCombinaisonJouee);
        // logCombinaisonsSuperieures(combinaisonsValidesSuperieures);

        return choisirMeilleureCombinaison(combinaisonsValidesSuperieures);
    }

    /**
     * Filtre les combinaisons valides qui peuvent battre la dernière combinaison
     * jouée.
     *
     * @param combinaisonsValides      Les combinaisons valides générées.
     * @param derniereCombinaisonJouee La dernière combinaison jouée.
     * @return Une liste de combinaisons valides qui peuvent battre la dernière
     *         combinaison.
     */
    private List<CombinaisonCartes> filtrerCombinaisonsSuperieures(List<CombinaisonCartes> combinaisonsValides,
            CombinaisonCartes derniereCombinaisonJouee) {
        if (derniereCombinaisonJouee == null || derniereCombinaisonJouee.getType() == TypeCombinaison.INVALIDE) {
            return combinaisonsValides;
        }
        return combinaisonsValides.stream()
                .filter(combinaison -> combinaison.gagneContre(derniereCombinaisonJouee))
                .toList();
    }

    /**
     * Choisit la meilleure combinaison parmi les combinaisons valides supérieures.
     *
     * @param combinaisonsValidesSuperieures Les combinaisons valides supérieures.
     * @return La meilleure combinaison, ou null si aucune combinaison n'est
     *         disponible.
     */
    private CombinaisonCartes choisirMeilleureCombinaison(List<CombinaisonCartes> combinaisonsValidesSuperieures) {
        if (combinaisonsValidesSuperieures.isEmpty()) {
            return null; // No valid superior combinations
        }

        // Sort by strength from weakest to strongest
        return Collections.min(combinaisonsValidesSuperieures);
    }

}
