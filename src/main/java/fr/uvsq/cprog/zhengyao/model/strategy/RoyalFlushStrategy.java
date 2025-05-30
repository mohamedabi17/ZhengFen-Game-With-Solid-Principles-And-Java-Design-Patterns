package fr.uvsq.cprog.zhengyao.model.strategy;

import fr.uvsq.cprog.zhengyao.enumeration.CarteCouleur;
import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import fr.uvsq.cprog.zhengyao.model.Carte;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Stratégie de validation pour une Suite Royale (Royal Flush).
 * Une Suite Royale est une suite de cartes de la même couleur qui se termine par un As.
 */
public class RoyalFlushStrategy implements CombinaisonValidationStrategy {
    
    @Override
    public boolean valider(List<Carte> cartes) {
        if (cartes.size() != 5) {
            return false;
        }

        // Vérifie que toutes les cartes sont de la même couleur
        CarteCouleur couleur = cartes.get(0).getCouleur();
        boolean memeCouleur = cartes.stream()
            .allMatch(carte -> carte.getCouleur() == couleur);

        if (!memeCouleur) {
            return false;
        }

        // Vérifie que les cartes forment une suite qui se termine par un As
        List<CarteValeur> valeurs = cartes.stream()
            .map(Carte::getValeur)
            .sorted()
            .collect(Collectors.toList());

        return valeurs.get(0) == CarteValeur.DIX 
            && valeurs.get(1) == CarteValeur.VALET 
            && valeurs.get(2) == CarteValeur.DAME 
            && valeurs.get(3) == CarteValeur.ROI 
            && valeurs.get(4) == CarteValeur.AS;
    }

    @Override
    public TypeCombinaison getTypeCombinaison() {
        return TypeCombinaison.SUITE; // Utilise SUITE car ROYAL_FLUSH n'existe pas dans l'énumération
    }
} 