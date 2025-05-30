package fr.uvsq.cprog.zhengyao.model;

import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import fr.uvsq.cprog.zhengyao.model.strategy.ValidationStrategyFactory;
import fr.uvsq.cprog.zhengyao.services.DisplayService;
import fr.uvsq.cprog.zhengyao.ui.GameDisplayer;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe pour valider les combinaisons de cartes selon les règles du jeu Zheng Shangyou.
 */
@SuppressWarnings("unused")
public class Regles {
    private final ValidationStrategyFactory strategyFactory;

    /**
     * Constructeur par défaut qui initialise la factory de stratégies.
     */
    public Regles() {
        this.strategyFactory = new ValidationStrategyFactory();
    }

    /**
     * Valide si une liste de cartes forme une combinaison autorisée.
     *
     * @param cartes La liste de cartes à valider.
     *
     * @return Le type de la combinaison, ou INVALIDE si elle n'est pas valide.
     */
    public TypeCombinaison validerCombinaison(List<Carte> cartes) {
        return strategyFactory.validerCombinaison(cartes);
    }

    /**
     * Vérifie si une combinaison est valide par rapport aux règles et à la dernière combinaison jouée.
     *
     * @param combinaison               La combinaison que le joueur souhaite jouer.
     * @param derniereCombinaisonCartes La dernière combinaison jouée.
     *
     * @return true si la combinaison est valide, false sinon.
     */
    public boolean estValide(List<Carte> combinaison, CombinaisonCartes derniereCombinaisonCartes) {
        TypeCombinaison typeCombinaison = validerCombinaison(combinaison);
        if (typeCombinaison == TypeCombinaison.INVALIDE) {
            return false;
        }
        if (derniereCombinaisonCartes.getType() == TypeCombinaison.INVALIDE) {
            return true;
        }
        CombinaisonCartes nouvelleCombinaison = new CombinaisonCartes(typeCombinaison, combinaison);
        boolean gagne = nouvelleCombinaison.gagneContre(derniereCombinaisonCartes);
        return gagne;
    }
}
