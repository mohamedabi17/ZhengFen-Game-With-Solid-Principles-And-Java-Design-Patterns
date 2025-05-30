package fr.uvsq.cprog.zhengyao.model;

import com.fasterxml.jackson.annotation.JsonCreator;
// import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.uvsq.cprog.zhengyao.enumeration.CarteCouleur;
import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import java.util.Objects;

/**
 * Représente une carte d'un jeu de cartes standard pour Zheng Shangyou.
 */
public class Carte implements Comparable<Carte> {
    private final CarteValeur valeur;
    private final CarteCouleur couleur;

    private Carte(CarteBuilder builder) {
        this.valeur = builder.valeur;
        this.couleur = builder.couleur;
    }

    /**
     * Constructeur de la classe Carte.
     *
     * @param valeur  la valeur de la carte (TROIS à DEUX).
     * @param couleur la couleur de la carte (PIQUE, COEUR, CARREAU, TREFLE).
     */
    @JsonCreator
    public Carte(
            @JsonProperty("valeur") CarteValeur valeur,
            @JsonProperty("couleur") CarteCouleur couleur) {
        this.valeur = valeur;
        this.couleur = couleur;
    }

    /**
     * Retourne la valeur de la carte.
     *
     * @return la valeur de la carte.
     */
    @JsonProperty("valeur")
    public CarteValeur getValeur() {
        return valeur;
    }

    /**
     * Retourne la couleur de la carte.
     *
     * @return la couleur de la carte.
     */
    @JsonProperty("couleur")
    public CarteCouleur getCouleur() {
        return couleur;
    }

    /**
     * Retourne la force de la carte.
     *
     * @return la force de la carte basée sur sa valeur.
     */
    @JsonIgnore
    public int getForce() {
        return valeur.ordinal();
    }

    /**
     * Retourne une représentation textuelle de la carte.
     *
     * @return une chaîne représentant la carte.
     */
    @Override
    public String toString() {
        return valeur.getValeur() + "(" + couleur.getCouleur() + ")";
    }

    /**
     * Compare deux cartes pour déterminer leur ordre.
     *
     * @param autreCarte la carte à comparer.
     * @return un entier négatif, zéro ou un entier positif selon que cette carte
     *         est moins forte, égale ou plus forte que l'autre carte.
     */
    @Override
    public int compareTo(Carte autreCarte) {
        return Integer.compare(this.getForce(), autreCarte.getForce());
    }

    /**
     * Vérifie si cette carte est égale à une autre carte.
     * Deux cartes sont considérées comme égales si elles ont la même valeur
     * et la même couleur.
     *
     * @param obj L'objet à comparer avec cette carte.
     * @return {@code true} si l'objet est une carte ayant la même valeur
     *         et la même couleur, {@code false} sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Carte carte = (Carte) obj;
        return valeur == carte.valeur && couleur == carte.couleur;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valeur, couleur);
    }

    /**
     * Pattern Builder pour créer une carte.
     */
    public static class CarteBuilder {
        private CarteValeur valeur;
        private CarteCouleur couleur;

        /**
         * Définit la valeur de la carte.
         *
         * @param valeur La valeur de la carte à définir. Ne peut pas être null.
         * @return L'instance du builder pour permettre un chaînage fluide.
         * @throws IllegalArgumentException si la valeur est null.
         */
        public CarteBuilder valeur(CarteValeur valeur) {
            if (valeur == null) {
                throw new IllegalArgumentException("La valeur de la carte ne peut pas être null");
            }
            this.valeur = valeur;
            return this;
        }

        /**
         * Définit la couleur de la carte.
         *
         * @param couleur La couleur de la carte à définir. Ne peut pas être null.
         * @return L'instance du builder pour permettre un chaînage fluide.
         * @throws IllegalArgumentException si la couleur est null.
         */
        public CarteBuilder couleur(CarteCouleur couleur) {
            if (couleur == null) {
                throw new IllegalArgumentException("La couleur de la carte ne peut pas être null");
            }
            this.couleur = couleur;
            return this;
        }

        public Carte build() {
            return new Carte(this);
        }
    }
}
