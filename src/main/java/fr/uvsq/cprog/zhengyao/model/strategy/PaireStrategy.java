package fr.uvsq.cprog.zhengyao.model.strategy;

import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import fr.uvsq.cprog.zhengyao.model.Carte;
import java.util.List;

/**
 * Stratégie de validation pour une paire de cartes.
 */
public class PaireStrategy implements CombinaisonValidationStrategy {
    
    @Override
    public boolean valider(List<Carte> cartes) {
        return cartes != null && cartes.size() == 2 && ValidationUtils.sontDeMemeValeur(cartes);
    }
    
    @Override
    public TypeCombinaison getTypeCombinaison() {
        return TypeCombinaison.PAIRE;
    }
}