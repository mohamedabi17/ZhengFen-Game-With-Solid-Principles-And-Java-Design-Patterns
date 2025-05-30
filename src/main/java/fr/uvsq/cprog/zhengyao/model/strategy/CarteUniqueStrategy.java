package fr.uvsq.cprog.zhengyao.model.strategy;

import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import fr.uvsq.cprog.zhengyao.model.Carte;
import java.util.List;

/**
 * Stratégie de validation pour une carte unique.
 */
public class CarteUniqueStrategy implements CombinaisonValidationStrategy {
    
    @Override
    public boolean valider(List<Carte> cartes) {
        return cartes != null && cartes.size() == 1;
    }
    
    @Override
    public TypeCombinaison getTypeCombinaison() {
        return TypeCombinaison.CARTE_UNIQUE;
    }
}