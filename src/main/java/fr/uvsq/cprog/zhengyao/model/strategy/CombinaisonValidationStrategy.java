package fr.uvsq.cprog.zhengyao.model.strategy;

import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import fr.uvsq.cprog.zhengyao.model.Carte;
import java.util.List;

/**
 * Interface définissant une stratégie de validation pour un type de combinaison.
 * Permet d'appliquer le principe OCP (Open/Closed Principle) en rendant
 * l'ajout de nouvelles règles de validation facile sans modifier le code existant.
 */
public interface CombinaisonValidationStrategy {
    
    /**
     * Vérifie si une liste de cartes correspond à un type de combinaison spécifique.
     *
     * @param cartes La liste de cartes à vérifier
     * @return true si les cartes forment la combinaison définie par cette stratégie
     */
    boolean valider(List<Carte> cartes);
    
    /**
     * Retourne le type de combinaison que cette stratégie valide.
     *
     * @return Le type de combinaison
     */
    TypeCombinaison getTypeCombinaison();
}