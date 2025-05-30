package fr.uvsq.cprog.zhengyao.ui;

import fr.uvsq.cprog.zhengyao.enumeration.CarteCouleur;
import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import fr.uvsq.cprog.zhengyao.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe pour gérer les joueurs lors d'une manche.
 */
public class RoundPlayer {
    private final Partie partie;
    private final GameDisplayer displayer;
    private int indexJoueurActuel;
    private PaquetDeCartes paquet;

    /**
     * Constructeur de la classe RoundPlayer.
     *
     * @param displayer L'afficheur de jeu.
     */
    public RoundPlayer(GameDisplayer displayer) {
        this.partie = Partie.getInstance();
        this.displayer = displayer;
        this.paquet = PaquetDeCartes.getInstance();
    }

    /**
     * Plays a round of the game.
     *
     * @param mancheCourante The current round number.
     * @param estRejouer     Indicates if the round is being replayed.
     * @param espion         Indicates if the spy mode is enabled.
     * @return A list of players in the order of their ranking for the round.
     */
    public List<Joueur> jouerManche(int mancheCourante, boolean estRejouer, boolean espion) {
        System.out.println("Début de la manche " + mancheCourante + ".");
        partie.obtenirHistorique().ajouterEvenement("Début de la manche " + mancheCourante + ".");

        if (!estRejouer || mancheCourante > 1) {
            distribuerCartes(mancheCourante);
        }

//        displayer.afficherMains();
//        // Ajouter les mains des joueurs à l'historique
//        partie.obtenirHistorique().ajouterEvenement("Mains des joueurs :");
//        for (Joueur joueur : partie.getJoueurs()) {
//            partie.obtenirHistorique().ajouterEvenement(joueur.toString());
//        }

        Joueur premierJoueur = trouverPremierJoueur();
        if (premierJoueur == null) {
            throw new IllegalStateException("Aucun joueur n'a été trouvé pour commencer la manche.");
        }
        indexJoueurActuel = partie.getJoueurs().indexOf(premierJoueur);
        if (indexJoueurActuel == -1) {
            throw new IllegalStateException(
                    "Le joueur trouvé n'est pas dans la liste des joueurs. "
                            + "Vérifiez que 'trouverPremierJoueur' retourne un joueur valide.");
        }

        displayer.afficherDebutManche(mancheCourante, premierJoueur.getNom());
        partie.obtenirHistorique().ajouterEvenement(premierJoueur.getNom() + " commence la manche.");

        // Logique de jeu pour la manche
        return jouerTours(espion);
    }

    /**
     * Exécute la logique de jeu pour les tours d'une manche.
     *
     * @return Le classement des joueurs à la fin de la manche
     */
    private List<Joueur> jouerTours(boolean espion) {
        CombinaisonCartes derniereCombinaisonCartes = new CombinaisonCartes(TypeCombinaison.INVALIDE, null);
        int passesConsecutives = 0;
        Joueur dernierJoueurAyantPose = null;
        List<Joueur> joueursSansCartes = new ArrayList<>();

        while (joueursSansCartes.size() < partie.getNombreJoueurs() - 1) {
            Joueur joueurActuel = partie.getJoueurs().get(indexJoueurActuel);

            if (joueursSansCartes.contains(joueurActuel)) {
                indexJoueurActuel = (indexJoueurActuel + 1) % partie.getNombreJoueurs();
                continue;
            }

            if (joueurActuel instanceof JoueurHumain) {
                System.out.println("\nC'est votre tour " + joueurActuel.getNom() + ".");
                displayer.afficherEtatJoueurHumain(joueurActuel, derniereCombinaisonCartes);
            } else {
                System.out.println("\nC'est le tour de " + joueurActuel.getNom() + ".");
            }

            if (joueurActuel instanceof JoueurHumain && espion) {
                displayer.afficherEspionnage();
            }

            CombinaisonCartes combinaisonAjouer = joueurActuel.jouerCartes(derniereCombinaisonCartes);

            if (combinaisonAjouer != null
                    && new Regles().estValide(combinaisonAjouer.getCartes(), derniereCombinaisonCartes)) {
                derniereCombinaisonCartes = combinaisonAjouer;
                displayer.afficherCombinaisonChoisie(combinaisonAjouer, joueurActuel);
                partie.obtenirHistorique().ajouterEvenement(joueurActuel.getNom()
                        + " a joué : " + combinaisonAjouer);

                if (joueurActuel.atermine()) {
                    joueursSansCartes.add(joueurActuel);
                }
                passesConsecutives = 0;
                dernierJoueurAyantPose = joueurActuel;

                displayer.afficherCartesJouees(joueurActuel, combinaisonAjouer);
            } else {
                displayer.afficherPasseTour(joueurActuel);
                partie.obtenirHistorique().ajouterEvenement(joueurActuel.getNom() + " passe son tour.");
                passesConsecutives++;

                if (passesConsecutives == partie.getNombreJoueurs() - joueursSansCartes.size()) {
                    displayer.afficherFinPli();
                    partie.obtenirHistorique().ajouterEvenement("Le pli se termine.");
                    derniereCombinaisonCartes = new CombinaisonCartes(TypeCombinaison.INVALIDE, null);
                    passesConsecutives = 0;
                    indexJoueurActuel = partie.getJoueurs().indexOf(dernierJoueurAyantPose);
                    continue;
                }
            }

            indexJoueurActuel = (indexJoueurActuel + 1) % partie.getNombreJoueurs();
        }

        return calculerClassement(joueursSansCartes);
    }

    /**
     * Distribue les cartes aux joueurs.
     *
     * @param mancheCourante Numéro de la manche en cours
     */
    private void distribuerCartes(int mancheCourante) {
        paquet.melanger();
        List<MainDeCartes> nouvellesMains = paquet.distribuer(partie.getNombreJoueurs());

        // Réinitialiser les mains des joueurs et leur distribuer les nouvelles cartes
        for (int i = 0; i < partie.getNombreJoueurs(); i++) {
            Joueur joueur = partie.getJoueurs().get(i);
            joueur.getMain().vider();
            joueur.recevoirCartes(nouvellesMains.get(i).getCartes());

            if (mancheCourante == 1) {
                Joueur cloneJoueur = joueur.clone();
                partie.obtenirHistorique().ajouterEtatJoueur(cloneJoueur);
            }
        }
        System.out.println("Les cartes ont été mélangées et distribuées aux joueurs.");
        partie.obtenirHistorique().ajouterEvenement("Les cartes ont été mélangées et distribuées aux joueurs.");
    }

    /**
     * Trouve le joueur qui possède le 3 de trèfle.
     *
     * @return Le joueur qui a le 3 de trèfle
     */
    private Joueur trouverPremierJoueur() {
        for (Joueur joueur : partie.getJoueurs()) {
            for (Carte carte : joueur.getMain().getCartes()) {
                if (carte.getValeur() == CarteValeur.TROIS && carte.getCouleur() == CarteCouleur.TREFLE) {
                    return joueur;
                }
            }
        }
        throw new IllegalStateException("Aucun joueur ne possède le 3 de trèfle, cela ne devrait pas arriver !");
    }

    /**
     * Calcule le classement des joueurs en fonction de ceux qui ont terminé en premier.
     *
     * @param joueursSansCartes Liste des joueurs ayant terminé la manche
     * @return Le classement des joueurs du premier au dernier
     */
    private List<Joueur> calculerClassement(List<Joueur> joueursSansCartes) {
        List<Joueur> classement = new ArrayList<>(joueursSansCartes);
        for (Joueur joueur : partie.getJoueurs()) {
            if (!joueursSansCartes.contains(joueur)) {
                classement.add(joueur);
            }
        }
        return classement;
    }
}
