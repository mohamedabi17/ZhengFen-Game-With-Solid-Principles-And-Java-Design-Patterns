package fr.uvsq.cprog.zhengyao.model;

import fr.uvsq.cprog.zhengyao.enumeration.CarteCouleur;
import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Représente un paquet de cartes pour Zheng Shangyou.
 */
public class PaquetDeCartes {
    private static PaquetDeCartes instance;
    private final List<Carte> cartes;

    /**
     * Retourne l'instance unique de PaquetDeCartes.
     *
     * @return l'instance unique de PaquetDeCartes.
     */
    public static PaquetDeCartes getInstance() {
        if (instance == null) {
            synchronized (PaquetDeCartes.class) {
                if (instance == null) {
                    instance = new PaquetDeCartes();
                }
            }
        }
        return instance;
    }

    /**
     * Constructeur privé pour empêcher l'instanciation directe.
     */
    private PaquetDeCartes() {
        this.cartes = new ArrayList<>();
        initialiserPaquet();
    }

    /**
     * Initialise un paquet standard de 52 cartes.
     */
    private void initialiserPaquet() {
        for (CarteValeur valeur : CarteValeur.values()) {
            for (CarteCouleur couleur : CarteCouleur.values()) {
                cartes.add(new Carte.CarteBuilder()
                        .valeur(valeur)
                        .couleur(couleur)
                        .build());
            }
        }
    }

    /**
     * Mélange le paquet de cartes.
     */
    public void melanger() {
        Collections.shuffle(cartes);
    }

    /**
     * Réinitialise le paquet de cartes.
     * Remet toutes les cartes dans le paquet et les mélange.
     */
    public void reinitialiser() {
        cartes.clear();
        initialiserPaquet();
        melanger();
    }

    /**
     * Distribue les cartes équitablement entre les joueurs.
     *
     * @param nombreJoueurs le nombre de joueurs.
     * @return une liste de mains de cartes pour les joueurs.
     * @throws IllegalArgumentException si le nombre de joueurs est invalide.
     */
    public List<MainDeCartes> distribuer(int nombreJoueurs) {
        validerNombreJoueurs(nombreJoueurs);

        List<MainDeCartes> mains = initialiserMains(nombreJoueurs);
        repartirCartesEntreMains(mains, nombreJoueurs);

        return mains;
    }

    /**
     * Valide le nombre de joueurs pour la distribution.
     *
     * @param nombreJoueurs le nombre de joueurs.
     * @throws IllegalArgumentException si le nombre de joueurs est invalide.
     */
    private void validerNombreJoueurs(int nombreJoueurs) {
        if (nombreJoueurs <= 0 || nombreJoueurs > cartes.size()) {
            throw new IllegalArgumentException("Nombre de joueurs invalide.");
        }
    }

    /**
     * Initialise les mains des joueurs.
     *
     * @param nombreJoueurs le nombre de joueurs.
     * @return une liste de mains vides pour les joueurs.
     */
    private List<MainDeCartes> initialiserMains(int nombreJoueurs) {
        List<MainDeCartes> mains = new ArrayList<>();
        for (int i = 0; i < nombreJoueurs; i++) {
            mains.add(new MainDeCartes());
        }
        return mains;
    }

    /**
     * Répartit les cartes entre les mains des joueurs.
     *
     * @param mains         les mains des joueurs.
     * @param nombreJoueurs le nombre de joueurs.
     */
    private void repartirCartesEntreMains(List<MainDeCartes> mains, int nombreJoueurs) {
        for (int i = 0; i < cartes.size(); i++) {
            mains.get(i % nombreJoueurs).ajouterCarte(cartes.get(i));
        }
    }

    /**
     * Retourne une représentation textuelle du paquet.
     *
     * @return une chaîne représentant le paquet.
     */
    @Override
    public String toString() {
        return cartes.toString();
    }
}
