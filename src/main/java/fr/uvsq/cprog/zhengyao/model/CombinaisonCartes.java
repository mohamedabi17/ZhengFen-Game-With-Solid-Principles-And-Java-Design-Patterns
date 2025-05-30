package fr.uvsq.cprog.zhengyao.model;

import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import java.util.List;

/**
 * Représente une combinaison de cartes dans le jeu.
 * Une combinaison est définie par son type et la liste de cartes qui la composent.
 */
public class CombinaisonCartes implements Comparable<CombinaisonCartes> {
    private final TypeCombinaison type;
    private final List<Carte> cartes;

    /**
     * Constructeur de CombinaisonCartes.
     *
     * @param type   Le type de la combinaison.
     * @param cartes La liste de cartes composant la combinaison.
     */
    public CombinaisonCartes(TypeCombinaison type, List<Carte> cartes) {
        this.type = type;
        this.cartes = cartes;
    }

    /**
     * Retourne le type de la combinaison.
     *
     * @return Le type de la combinaison.
     */
    public TypeCombinaison getType() {
        return type;
    }

    /**
     * Retourne la liste des cartes de la combinaison.
     *
     * @return La liste des cartes.
     */
    public List<Carte> getCartes() {
        return cartes;
    }

    /**
     * Calcule la force de la combinaison.
     *
     * @return La force de la combinaison.
     */
    public int getForce() {
        return cartes == null ? 0 : cartes.stream().mapToInt(c -> c.getValeur().ordinal()).sum();
    }

    /**
     * Compare deux combinaisons de cartes pour déterminer laquelle est la plus forte.
     *
     * @param autreCombinaison L'autre combinaison à comparer.
     *
     * @return {@code true} si cette combinaison est plus forte, {@code false} sinon.
     */
    public boolean gagneContre(CombinaisonCartes autreCombinaison) {
        return compareTo(autreCombinaison) > 0;
    }

    @Override
    public int compareTo(CombinaisonCartes c) {
        if (c == null) {
            return 1;
        }
        if (type == c.getType()) {
            return Integer.compare(getForce(), c.getForce());
        } else {
            return Integer.compare(type.ordinal(), c.getType().ordinal());
        }
    }

    @Override
    public String toString() {
        return type.getCombinaison() + " " + cartes;
    }
}