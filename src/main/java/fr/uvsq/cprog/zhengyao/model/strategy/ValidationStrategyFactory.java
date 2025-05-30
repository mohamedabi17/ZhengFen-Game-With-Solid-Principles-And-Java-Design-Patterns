package fr.uvsq.cprog.zhengyao.model.strategy;

import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import fr.uvsq.cprog.zhengyao.model.Carte;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory pour les stratégies de validation de combinaisons.
 * Cette classe gère la création et l'accès aux différentes stratégies de validation.
 */
public class ValidationStrategyFactory {
    private final List<CombinaisonValidationStrategy> strategies;
    
    /**
     * Constructeur qui initialise toutes les stratégies disponibles.
     */
    public ValidationStrategyFactory() {
        strategies = new ArrayList<>();
        // Ajouter toutes les stratégies disponibles
        strategies.add(new CarteUniqueStrategy());
        strategies.add(new PaireStrategy());
        strategies.add(new TripletStrategy());
        strategies.add(new SuiteStrategy());
        strategies.add(new TripletPlusDeuxStrategy());
        strategies.add(new SuiteDePairesStrategy());
        strategies.add(new CarreStrategy());
    }
    
    /**
     * Valide une liste de cartes avec toutes les stratégies disponibles.
     *
     * @param cartes La liste de cartes à valider.
     * @return Le type de combinaison valide, ou INVALIDE si aucune stratégie ne correspond.
     */
    public TypeCombinaison validerCombinaison(List<Carte> cartes) {
        if (cartes == null || cartes.isEmpty()) {
            return TypeCombinaison.INVALIDE;
        }
        
        List<Carte> cartesTriees = ValidationUtils.trierCartes(cartes);
        
        for (CombinaisonValidationStrategy strategy : strategies) {
            if (strategy.valider(cartesTriees)) {
                return strategy.getTypeCombinaison();
            }
        }
        
        return TypeCombinaison.INVALIDE;
    }
}