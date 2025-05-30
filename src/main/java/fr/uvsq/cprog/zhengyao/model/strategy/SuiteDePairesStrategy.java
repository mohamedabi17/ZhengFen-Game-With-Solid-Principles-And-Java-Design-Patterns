package fr.uvsq.cprog.zhengyao.model.strategy;

import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import fr.uvsq.cprog.zhengyao.model.Carte;
import java.util.List;

/**
 * Stratégie de validation pour une suite de paires.
 */
public class SuiteDePairesStrategy implements CombinaisonValidationStrategy {
    
    @Override
    public boolean valider(List<Carte> cartes) {
        return cartes != null && cartes.size() == 6 && ValidationUtils.estSuiteDePaires(cartes);
    }
    
    @Override
    public TypeCombinaison getTypeCombinaison() {
        return TypeCombinaison.SUITE_DE_PAIRES;
    }
}