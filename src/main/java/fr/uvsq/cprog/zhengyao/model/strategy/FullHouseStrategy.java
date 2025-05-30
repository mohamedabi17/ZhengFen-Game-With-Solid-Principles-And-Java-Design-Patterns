package fr.uvsq.cprog.zhengyao.model.strategy;

import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import fr.uvsq.cprog.zhengyao.model.Carte;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Stratégie de validation pour un Full House (un triplet et une paire).
 */
public class FullHouseStrategy implements CombinaisonValidationStrategy {
    
    @Override
    public boolean valider(List<Carte> cartes) {
        if (cartes == null || cartes.size() != 5) {
            return false;
        }

        // Compte les occurrences de chaque valeur de carte
        Map<Integer, Long> valeurCounts = cartes.stream()
            .map(carte -> carte.getValeur().ordinal())
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Vérifie s'il y a un triplet et une paire
        return valeurCounts.containsValue(3L) && valeurCounts.containsValue(2L);
    }
    
    @Override
    public TypeCombinaison getTypeCombinaison() {
        return TypeCombinaison.TRIPLET_PLUS_DEUX; // Utilise TRIPLET_PLUS_DEUX pour représenter Full House
    }
} 