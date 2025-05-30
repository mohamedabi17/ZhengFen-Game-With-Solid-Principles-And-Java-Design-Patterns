package fr.uvsq.cprog.zhengyao.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe représentant la main d'un joueur.
 */
public class MainDeCartes {
    private final List<Carte> cartes;

    /**
     * Constructeur par défaut.
     * Initialise une main vide.
     */
    public MainDeCartes() {
        this.cartes = new ArrayList<>();
    }

    /**
     * Constructeur de la classe MainDeCartes.
     */
    public MainDeCartes(List<Carte> cartes) {
        this.cartes = new ArrayList<>(cartes);
    }

    /**
     * Ajoute une carte à la main.
     *
     * @param carte La carte à ajouter.
     */
    public void ajouterCarte(Carte carte) {
        if (carte != null) {
            cartes.add(carte);
        }
    }

    /**
     * Retire une carte de la main.
     *
     * @param carte La carte à retirer.
     * @return true si la carte a été retirée, false sinon.
     */
    public boolean retirerCarte(Carte carte) {
        return cartes.remove(carte);
    }

    /**
     * Trie les cartes de la main par ordre croissant de valeur.
     */
    public void trier() {
        Collections.sort(cartes);
    }

    /**
     * Vide la main de toutes ses cartes.
     */
    public void vider() {
        cartes.clear();
    }

    /**
     * Vérifie si la main est vide.
     *
     * @return true si la main est vide, false sinon.
     */
    public boolean estVide() {
        return cartes.isEmpty();
    }

    /**
     * Vérifie si la main contient toutes les cartes spécifiées.
     *
     * @param listeCartes La liste des cartes à vérifier.
     * @return {@code true} si la main contient toutes les cartes, {@code false}
     *         sinon.
     */
    public boolean contientToutes(List<Carte> listeCartes) {
        return listeCartes != null && cartes.containsAll(listeCartes);
    }

    /**
     * Retourne les cartes de la main.
     *
     * @return Une liste immuable des cartes de la main.
     */
    public List<Carte> getCartes() {
        return Collections.unmodifiableList(cartes);
    }

    @Override
    public String toString() {
        return cartes.toString();
    }

    @Override
    public MainDeCartes clone() {
        return new MainDeCartes(cartes);
    }
}
