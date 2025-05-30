package fr.uvsq.cprog.zhengyao.model.strategy;

import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import fr.uvsq.cprog.zhengyao.model.Carte;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitaire contenant des méthodes communes pour les stratégies de validation.
 */
public class ValidationUtils {

    /**
     * Vérifie si toutes les cartes ont la même valeur.
     *
     * @param cartes La liste de cartes.
     * @return true si toutes les cartes ont la même valeur, false sinon.
     */
    public static boolean sontDeMemeValeur(List<Carte> cartes) {
        if (cartes == null || cartes.isEmpty()) {
            return false;
        }
        CarteValeur valeur = cartes.get(0).getValeur();
        return cartes.stream().allMatch(c -> c.getValeur() == valeur);
    }

    /**
     * Vérifie si les cartes forment une séquence.
     *
     * @param cartes La liste de cartes.
     * @return true si les cartes sont en séquence, false sinon.
     */
    public static boolean sontEnSequence(List<Carte> cartes) {
        if (cartes == null || cartes.size() < 2) {
            return false; // Une suite nécessite au moins deux cartes
        }
    
        // Crée une liste triée locale
        List<Carte> cartesTriees = new ArrayList<>(cartes);
        cartesTriees.sort((c1, c2) -> c1.compareTo(c2));
    
        for (int i = 0; i < cartesTriees.size() - 1; i++) {
            if (cartesTriees.get(i).getValeur().ordinal() + 1 != cartesTriees.get(i + 1).getValeur().ordinal()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Vérifie si une liste de cinq cartes est un triplet plus une paire.
     *
     * @param cartes La liste de cinq cartes.
     * @return true si c'est un triplet plus une paire, false sinon.
     */
    public static boolean estTripletPlusDeux(List<Carte> cartes) {
        if (cartes == null || cartes.size() != 5) {
            return false;
        }
        
        // Créer une liste triée
        List<Carte> cartesTriees = new ArrayList<>(cartes);
        cartesTriees.sort((c1, c2) -> c1.compareTo(c2));
        
        // Exemples valides : 33322 ou 22333
        return (cartesTriees.get(0).getValeur() == cartesTriees.get(2).getValeur() 
                && 
                cartesTriees.get(3).getValeur() == cartesTriees.get(4).getValeur()) 
                ||
               (cartesTriees.get(0).getValeur() == cartesTriees.get(1).getValeur() 
               && 
               cartesTriees.get(2).getValeur() == cartesTriees.get(4).getValeur());
    }

    /**
     * Vérifie si les cartes forment une suite de paires.
     *
     * @param cartes La liste de cartes.
     * @return true si les cartes forment une suite de paires, false sinon.
     */
    public static boolean estSuiteDePaires(List<Carte> cartes) {
        if (cartes == null || cartes.size() < 6 || cartes.size() % 2 != 0) {
            return false; // Une suite de paires doit avoir un nombre pair de cartes, minimum 6
        }
    
        // Crée une liste triée locale
        List<Carte> cartesTriees = new ArrayList<>(cartes);
        cartesTriees.sort((c1, c2) -> c1.compareTo(c2));
    
        for (int i = 0; i < cartesTriees.size(); i += 2) {
            // Vérifie que chaque paire est composée de deux cartes identiques
            if (cartesTriees.get(i).getValeur() != cartesTriees.get(i + 1).getValeur()) {
                return false;
            }
    
            // Vérifie que les paires se suivent (par rapport à la paire précédente)
            if (i >= 2 && cartesTriees.get(i).getValeur().ordinal() 
                != cartesTriees.get(i - 2).getValeur().ordinal() + 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Trie les cartes par valeur.
     *
     * @param cartes La liste de cartes à trier.
     * @return Une nouvelle liste de cartes triées.
     */
    public static List<Carte> trierCartes(List<Carte> cartes) {
        if (cartes == null) {
            return new ArrayList<>();
        }
        List<Carte> cartesTriees = new ArrayList<>(cartes);
        cartesTriees.sort((c1, c2) -> c1.compareTo(c2));
        return cartesTriees;
    }
}