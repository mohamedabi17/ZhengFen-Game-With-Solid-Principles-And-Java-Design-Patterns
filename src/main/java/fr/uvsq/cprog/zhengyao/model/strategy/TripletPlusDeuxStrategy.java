package fr.uvsq.cprog.zhengyao.model.strategy;

import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import fr.uvsq.cprog.zhengyao.model.Carte;
import java.util.List;

/**
 * Stratégie de validation pour un triplet plus deux cartes.
 */
public class TripletPlusDeuxStrategy implements CombinaisonValidationStrategy {
    
    @Override
    public boolean valider(List<Carte> cartes) {
        return cartes != null && cartes.size() == 5 && ValidationUtils.estTripletPlusDeux(cartes);
    }
    
    @Override
    public TypeCombinaison getTypeCombinaison() {
        return TypeCombinaison.TRIPLET_PLUS_DEUX;
    }
}