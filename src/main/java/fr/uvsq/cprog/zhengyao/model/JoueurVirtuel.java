package fr.uvsq.cprog.zhengyao.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.uvsq.cprog.zhengyao.enumeration.CouleurJoueur;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un joueur virtuel dans le jeu Zheng Shangyou.
 * Le comportement du joueur est déterminé par le moteur de jeu fourni.
 */
public class JoueurVirtuel extends Joueur {
    @JsonIgnore
    private final MoteurDeJeu moteurDeJeu;
    @SuppressWarnings("unused")
    private final String type = "virtuel";

    /**
     * Constructeur du joueur virtuel.
     *
     * @param nom         Le nom du joueur.
     * @param couleur     La couleur du joueur.
     * @param moteurDeJeu Le moteur de jeu responsable du comportement du joueur.
     */
    @JsonCreator
    public JoueurVirtuel(
            @JsonProperty("nom") String nom,
            @JsonProperty("couleur") CouleurJoueur couleur,
            @JsonProperty("moteurDeJeu") MoteurDeJeu moteurDeJeu) {
        super(nom, couleur); // Call to the superclass constructor with nom and couleur
        this.moteurDeJeu = moteurDeJeu;
    }

    private JoueurVirtuel(JoueurVirtuelBuilder builder) {
        super(builder);
        this.moteurDeJeu = builder.moteurDeJeu;
    }

    @JsonProperty("type")
    public String getType() {
        return "virtuel";
    }

    // Getter pour moteurDeJeu (nécessaire pour Jackson)
    @JsonIgnore
    public MoteurDeJeu getMoteurDeJeu() {
        return moteurDeJeu;
    }

    /**
     * Permet au joueur virtuel de jouer une combinaison de cartes.
     *
     * @param derniereCombinaisonCartes La dernière combinaison jouée par l'autre
     *                                  joueur.
     * @return La liste de cartes jouée par le joueur virtuel, ou null s'il passe
     *         son tour.
     */
    @Override
    public CombinaisonCartes jouerCartes(CombinaisonCartes derniereCombinaisonCartes) {

        // Vérifier si la main ou ses cartes sont nulles
        if (main == null || main.getCartes() == null) {
            throw new IllegalStateException("La main ou les cartes ne peuvent pas être null");
        }

        // // Log the current state of the player's hand and the last combination
        // System.out.println("Main actuelle: " + main.getCartes());
        // System.out.println("Dernière combinaison jouée: " +
        // derniereCombinaisonCartes);

        // Utiliser le moteur pour déterminer la combinaison à jouer
        CombinaisonCartes combinaisonChoisie = moteurDeJeu.choisirCombinaison(getMain(), derniereCombinaisonCartes);

        // Log the chosen combination

        // Si aucune combinaison n'est choisie ou si la combinaison est vide, le joueur
        // passe son tour
        if (combinaisonChoisie == null || combinaisonChoisie.getCartes().isEmpty()) {
            return null; // Le joueur passe son tour
        }

        // Vérifier si la combinaison choisie est valide par rapport à la dernière
        // combinaison jouée
        Regles regles = new Regles();
        if (!regles.estValide(combinaisonChoisie.getCartes(), derniereCombinaisonCartes)) {
            System.out.println(getNom() + " essaie de jouer une combinaison invalide.");
            return null; // La combinaison n'est pas valide
        }

        // Si la combinaison est valide, retirer les cartes de la main
        List<Carte> cartesAjouer = new ArrayList<>(combinaisonChoisie.getCartes());
        boolean cartesRetirees = true;

        // Retirer les cartes de la main du joueur
        for (Carte carte : cartesAjouer) {
            if (!getMain().retirerCarte(carte)) {
                cartesRetirees = false;
                break;
            }
        }

        // Si les cartes ont été correctement retirées de la main, le joueur joue ses
        // cartes
        if (cartesRetirees) {
            return combinaisonChoisie;
        } else {
            System.out.println("Erreur : Certaines cartes de la combinaison ne peuvent pas être jouées.");
            return null; // Erreur dans le retrait des cartes
        }
    }

    /**
     * Builder class for creating instances of {@link JoueurVirtuel}.
     * This class allows for a step-by-step construction of a JoueurVirtuel object.
     */
    public static class JoueurVirtuelBuilder extends JoueurBuilder<JoueurVirtuelBuilder> {
        private MoteurDeJeu moteurDeJeu;

        public JoueurVirtuelBuilder(String nom) {
            super(nom);
        }

        public JoueurVirtuelBuilder moteurDeJeu(MoteurDeJeu moteurDeJeu) {
            this.moteurDeJeu = moteurDeJeu;
            return this;
        }

        @Override
        protected JoueurVirtuelBuilder self() {
            return this;
        }

        @Override
        public JoueurVirtuel build() {
            return new JoueurVirtuel(this);
        }
    }
}
