package fr.uvsq.cprog.zhengyao.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.uvsq.cprog.zhengyao.enumeration.CouleurJoueur;
import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import fr.uvsq.cprog.zhengyao.services.DisplayService;
import fr.uvsq.cprog.zhengyao.ui.GameDisplayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe représentant un joueur humain dans le jeu Zheng Shangyou.
 */
public class JoueurHumain extends Joueur {
    @SuppressWarnings("unused")
    private final String type = "humain";

    /**
     * Constructeur pour un joueur humain.
     *
     * @param nom     Le nom du joueur.
     * @param couleur La couleur associée au joueur.
     */
    @JsonCreator
    public JoueurHumain(
            @JsonProperty("nom") String nom,
            @JsonProperty("couleur") CouleurJoueur couleur) {
        super(nom, couleur); // Call to the superclass constructor with nom and couleur
    }

    private JoueurHumain(JoueurHumainBuilder builder) {
        super(builder);
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * Builder class for creating instances of {@link JoueurHumain}.
     * This class follows the builder pattern to facilitate the construction of
     * {@link JoueurHumain} objects.
     */
    public static class JoueurHumainBuilder extends JoueurBuilder<JoueurHumainBuilder> {
        /**
         * Constructor for the JoueurHumainBuilder.
         *
         * @param nom The name of the player.
         */
        public JoueurHumainBuilder(String nom) {
            super(nom);
        }

        @Override
        protected JoueurHumainBuilder self() {
            return this;
        }

        @Override
        public JoueurHumain build() {
            return new JoueurHumain(this);
        }
    }

    /**
     * Implémentation de la méthode abstraite jouerCartes.
     * Permet au joueur humain de choisir une combinaison à jouer ou de passer.
     *
     * @param derniereCombinaisonCartes La dernière combinaison jouée sur le tapis.
     *
     * @return La combinaison jouée (liste de {@code Carte}) ou {@code null} si le joueur passe.
     */
    @Override
    public CombinaisonCartes jouerCartes(CombinaisonCartes derniereCombinaisonCartes) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        DisplayService gameDisplayer = new GameDisplayer();
        Regles regles = new Regles(); // Instance des règles
        List<CombinaisonCartes> combinaisonsValides;

        while (true) {
            try {
                getMain().trier();

                // Generate and display valid combinations
                combinaisonsValides = GenerateurCombinaisons.genererCombinaisonsValides(getMain());

                // Create a DisplayService to show combinations - use the interface
                gameDisplayer.afficherCombinaisonsValides(combinaisonsValides, this);

                gameDisplayer.afficherTexteColorise(this.getCouleur(),
                        "Entrez 'combo <index>' pour jouer une combinaison, 'carte <indices>' pour jouer des cartes, ou 'passe' :");

                if (!scanner.hasNextLine()) {
                    gameDisplayer.afficherTexteColorise(this.getCouleur(),
                            "Erreur : Aucune entrée détectée. Le joueur passe automatiquement.");
                    return null; // Sortir de la boucle si aucune entrée n'est détectée
                }
                String entree = scanner.nextLine().trim();

                if (entree.equalsIgnoreCase("espionner")) {
                    Partie.getInstance().espionner();
                    continue;
                }

                if (entree.isEmpty()) {
                    gameDisplayer.afficherTexteColorise(this.getCouleur(),
                            "Erreur : Entrée vide. Le joueur passe automatiquement.");
                    return null; // Sortir de la boucle si l'entrée est vide
                }

                if (entree.equalsIgnoreCase("passe")) {
                    return null; // Le joueur passe son tour
                }

                try {
                    if (entree.startsWith("combo")) {
                        // Play a combination by index
                        int index = Integer.parseInt(entree.split(" ")[1]) - 1;

                        // Filter out only valid combinations (exclude single cards)
                        List<CombinaisonCartes> combinaisonsFiltrees = combinaisonsValides.stream()
                                .filter(c -> c.getType() != TypeCombinaison.CARTE_UNIQUE)
                                .toList();

                        if (index >= 0 && index < combinaisonsFiltrees.size()) {
                            CombinaisonCartes combinaisonChoisie = combinaisonsFiltrees.get(index);

                            TypeCombinaison typeCombinaison = regles.validerCombinaison(combinaisonChoisie.getCartes());
                            if (typeCombinaison != TypeCombinaison.INVALIDE 
                                    && regles.estValide(combinaisonChoisie.getCartes(), derniereCombinaisonCartes)) {

                                // Retirer les cartes de la main
                                combinaisonChoisie.getCartes().forEach(carte -> {
                                    boolean removed = getMain().retirerCarte(carte);
                                    if (!removed) {
                                        gameDisplayer.afficherTexteColorise(this.getCouleur(),
                                                "Erreur : La carte " + carte + " n'a pas pu être retirée.");
                                    }
                                });
                                gameDisplayer.afficherTexteColorise(this.getCouleur(),
                                        "\nVous avez joué : " + combinaisonChoisie);
                                return combinaisonChoisie;
                            } else {
                                gameDisplayer.afficherTexteColorise(this.getCouleur(),
                                        "Erreur : La combinaison est invalide selon les règles. "
                                        + "Moins forte que la dernière combinaison jouée.");
                                gameDisplayer.afficherTexteColorise(this.getCouleur(),
                                        "\nla dernière combinaison jouée.: " + derniereCombinaisonCartes);
                            }
                        } else {
                            System.out.println("Erreur : Index de combinaison invalide.");
                        }
                    } else if (entree.startsWith("carte")) {
                        // Play cards by specifying indices
                        List<Carte> combinaisonChoisie = new ArrayList<>();
                        try {
                            // Sort the cards using the compareTo method
                            List<Carte> cartesTriees = new ArrayList<>(getMain().getCartes());
                            cartesTriees.sort(Carte::compareTo);

                            String[] indices = entree.split(" ")[1].split(",");
                            for (String index : indices) {
                                int i = Integer.parseInt(index.trim());
                                combinaisonChoisie.add(cartesTriees.get(i));
                            }

                            if (regles.estValide(combinaisonChoisie, derniereCombinaisonCartes)) {
                                // Retirer les cartes de la main
                                combinaisonChoisie.forEach(carte -> {
                                    boolean removed = getMain().retirerCarte(carte);
                                    if (!removed) {
                                        gameDisplayer.afficherTexteColorise(this.getCouleur(),
                                                "Erreur : La carte " + carte + " n'a pas pu être retirée.");
                                    }
                                });

                                gameDisplayer.afficherTexteColorise(this.getCouleur(),
                                        "\nVous avez joué : " + combinaisonChoisie);
                                return new CombinaisonCartes(
                                        regles.validerCombinaison(combinaisonChoisie),
                                        combinaisonChoisie);
                            } else {

                                gameDisplayer.afficherTexteColorise(this.getCouleur(),
                                        "Erreur : La carte est Moins forte que la dernière combinaison jouée.");
                                gameDisplayer.afficherTexteColorise(this.getCouleur(),
                                        "\nla dernière combinaison jouée.: " + derniereCombinaisonCartes);

                            }
                        } catch (IndexOutOfBoundsException e) {

                            gameDisplayer.afficherTexteColorise(this.getCouleur(),
                                    "Erreur : Un ou plusieurs indices sont invalides.");
                        }
                    } else {

                        gameDisplayer.afficherTexteColorise(this.getCouleur(),
                                "Erreur : Commande invalide. Utilisez 'combo <index>' ou 'carte <indices>'.");

                    }
                } catch (IndexOutOfBoundsException e) {
                    gameDisplayer.afficherTexteColorise(this.getCouleur(),
                            "Erreur : Un ou plusieurs indices sont invalides.");
                } catch (NumberFormatException e) {

                    gameDisplayer.afficherTexteColorise(this.getCouleur(),
                            "Erreur : Entrée invalide. Veuillez entrer uniquement des nombres.");
                } catch (Exception e) {

                    gameDisplayer.afficherTexteColorise(this.getCouleur(),
                            "Une erreur inattendue s'est produite. Veuillez réessayer.");
                }
            } catch (Exception e) {
                System.out.println("Une erreur inattendue s'est produite. Veuillez réessayer.");
            }
        }
    }

}