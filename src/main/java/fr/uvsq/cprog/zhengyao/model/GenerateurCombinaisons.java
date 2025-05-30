package fr.uvsq.cprog.zhengyao.model;

import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Générateur de combinaisons de cartes possibles à partir d'une main.
 */
public class GenerateurCombinaisons {

    /**
     * Génère les combinaisons de cartes possibles à partir d'une main.
     *
     * @param main La main de cartes
     * @return La liste des combinaisons de cartes possibles
     */
    public static List<CombinaisonCartes> genererCombinaisonsValides(MainDeCartes main) {
        List<CombinaisonCartes> combinaisonsValides = new ArrayList<>();
        Map<CarteValeur, List<Carte>> groupeParValeur = main.getCartes().stream()
                .collect(Collectors.groupingBy(Carte::getValeur));

        // Ajoute les combinaisons
        combinaisonsValides.addAll(genererCartesUniques(main));
        combinaisonsValides.addAll(genererPaires(groupeParValeur));
        combinaisonsValides.addAll(genererTriplets(main));
        combinaisonsValides.addAll(genererSuites(main));
        combinaisonsValides.addAll(genererTripletPlusDeux(groupeParValeur));
        combinaisonsValides.addAll(genererCarres(groupeParValeur));

        // Sort combinations by type and strength
        combinaisonsValides.sort(Comparator.reverseOrder());

        return combinaisonsValides;
    }

    /**
     * Génère les combinaisons de cartes possibles à partir d'une main.
     *
     * @param main La main de cartes
     * @return La liste des combinaisons de cartes possibles
     */
    public static List<CombinaisonCartes> genererCartesUniques(MainDeCartes main) {
        return main.getCartes().stream()
                .map(carte -> new CombinaisonCartes(TypeCombinaison.CARTE_UNIQUE, List.of(carte)))
                .collect(Collectors.toList());
    }

    /**
     * Génère les combinaisons de paires possibles à partir d'une main.
     *
     * @param groupeParValeur Les cartes regroupées par valeur
     * @return La liste des combinaisons de paires possibles
     */
    public static List<CombinaisonCartes> genererPaires(Map<CarteValeur, List<Carte>> groupeParValeur) {
        return groupeParValeur.values().stream()
                .filter(cartes -> cartes.size() == 2)
                .map(cartes -> new CombinaisonCartes(TypeCombinaison.PAIRE, cartes))
                .collect(Collectors.toList());
    }

    /**
     * Génère les combinaisons de triplets possibles à partir d'une main.
     *
     * @param main La main de cartes
     * @return La liste des combinaisons de triplets possibles
     */
    public static List<CombinaisonCartes> genererTriplets(MainDeCartes main) {
        return main.getCartes().stream()
                .collect(Collectors.groupingBy(Carte::getValeur))
                .values().stream()
                .filter(cartes -> cartes.size() == 3)
                .map(cartes -> new CombinaisonCartes(TypeCombinaison.TRIPLET, cartes))
                .collect(Collectors.toList());
    }

    /**
     * Génère les combinaisons de suites possibles à partir d'une main.
     *
     * @param main La main de cartes
     * @return La liste des combinaisons de suites possibles
     */
    public static List<CombinaisonCartes> genererSuites(MainDeCartes main) {
        List<Carte> cartesTriees = new ArrayList<>(main.getCartes());
        cartesTriees.sort(Comparator.comparing(Carte::getForce));

        List<CombinaisonCartes> toutesLesSuites = new ArrayList<>();
        Map<Integer, Carte> valeurToCarte = new LinkedHashMap<>();

        // Supprimer les doublons basés sur la valeur de la carte
        for (Carte carte : cartesTriees) {
            valeurToCarte.putIfAbsent(carte.getForce(), carte);
        }

        List<Carte> cartesUniques = new ArrayList<>(valeurToCarte.values());

        // Générer toutes les suites possibles d'au moins 5 cartes
        for (int start = 0; start < cartesUniques.size(); start++) {
            List<Carte> suiteActuelle = new ArrayList<>();
            for (int end = start; end < cartesUniques.size(); end++) {
                if (suiteActuelle.isEmpty() 
                        || cartesUniques.get(end).getForce() == suiteActuelle.get(suiteActuelle.size() - 1).getForce()
                                + 1) {
                    suiteActuelle.add(cartesUniques.get(end));
                } else {
                    break; // Arrêtez si la séquence est interrompue
                }

                if (suiteActuelle.size() >= 5) {
                    CombinaisonCartes nouvelleSuite = new CombinaisonCartes(TypeCombinaison.SUITE,
                            new ArrayList<>(suiteActuelle));
                    if (!toutesLesSuites.contains(nouvelleSuite)) {
                        toutesLesSuites.add(nouvelleSuite);
                    }
                }
            }
        }

        return toutesLesSuites;
    }

    private static List<CombinaisonCartes> genererTripletPlusDeux(Map<CarteValeur, List<Carte>> groupeParValeur) {
        List<CombinaisonCartes> tripletPlusDeux = new ArrayList<>();
        List<List<Carte>> triplets = groupeParValeur.values().stream()
                .filter(cartes -> cartes.size() == 3)
                .map(cartes -> cartes.subList(0, 3))
                .toList();

        for (List<Carte> triplet : triplets) {
            for (Map.Entry<CarteValeur, List<Carte>> entry : groupeParValeur.entrySet()) {
                if (!triplet.contains(entry.getValue().get(0)) && entry.getValue().size() >= 2) {
                    List<Carte> tripletPlusDeuxCartes = new ArrayList<>(triplet);
                    tripletPlusDeuxCartes.addAll(entry.getValue().subList(0, 2));
                    tripletPlusDeux.add(
                            new CombinaisonCartes(TypeCombinaison.TRIPLET_PLUS_DEUX, tripletPlusDeuxCartes));
                }
            }
        }

        return tripletPlusDeux;
    }

    private static List<CombinaisonCartes> genererCarres(Map<CarteValeur, List<Carte>> groupeParValeur) {
        return groupeParValeur.values().stream()
                .filter(cartes -> cartes.size() == 4)
                .map(cartes -> new CombinaisonCartes(TypeCombinaison.CARRE, cartes))
                .collect(Collectors.toList());
    }
}