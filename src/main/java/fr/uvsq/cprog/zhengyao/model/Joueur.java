package fr.uvsq.cprog.zhengyao.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.uvsq.cprog.zhengyao.enumeration.CouleurJoueur;
import java.util.List;
import java.util.Objects;

/**
 * Classe représentant un joueur dans le jeu Zheng Shangyou.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = JoueurHumain.class, name = "humain"),
  @JsonSubTypes.Type(value = JoueurVirtuel.class, name = "virtuel")
})
public abstract class Joueur implements Cloneable {
    private final String nom;
    private CouleurJoueur couleur;
    protected MainDeCartes main;
    private boolean alapriorite;
    private int points;

    protected Joueur(JoueurBuilder<?> builder) {
        this.nom = builder.nom;
        this.couleur = builder.couleur;
        this.main = builder.main;
        this.alapriorite = builder.alapriorite;
        this.points = builder.points;
    }

    protected Joueur(String nom, CouleurJoueur couleur) {
        this.nom = nom;
        this.couleur = couleur;
        this.main = new MainDeCartes();
        this.alapriorite = false;
        this.points = 0;
    }

    @JsonProperty("nom")
    public String getNom() {
        return nom;
    }

    @JsonProperty("couleur")
    public CouleurJoueur getCouleur() {
        return couleur;
    }

    @JsonProperty("main")
    public MainDeCartes getMain() {
        return main;
    }

    @JsonProperty("alapriorite")
    public boolean isAlapriorite() {
        return alapriorite;
    }

    public int getPoints() {
        return points;
    }

    public void ajouterPoints(int pointsGagnes) {
        this.points += pointsGagnes;
    }

    /**
     * recevoirCartes.
     */
    public void recevoirCartes(List<Carte> cartes) {
        if (cartes == null || cartes.isEmpty()) {
            throw new IllegalArgumentException("Les cartes ne peuvent pas être null ou vides");
        }
        cartes.forEach(main::ajouterCarte);
    }

    public boolean atermine() {
        return main.estVide();
    }

    public void setCouleur(CouleurJoueur couleur) {
        this.couleur = couleur;
    }

    public abstract CombinaisonCartes jouerCartes(CombinaisonCartes derniereCombinaisonCartes);

    @Override
    public String toString() {
        return "Joueur{" + "nom='" + nom + '\'' + ", couleur=" + couleur + ", main=" + main + '}';
    }

    @Override
    public Joueur clone() {
        try {
            Joueur cloned = (Joueur) super.clone();
            cloned.main = this.main.clone(); // Ensure the main attribute is cloned
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning not supported", e);
        }
    }

    /**
     * Pattern Builder pour créer des instances de Joueur.
     */
    public abstract static class JoueurBuilder<T extends JoueurBuilder<T>> {
        private final String nom;
        private CouleurJoueur couleur;
        private MainDeCartes main = new MainDeCartes();
        private boolean alapriorite = false;
        private int points = 0;

        protected JoueurBuilder(String nom) {
            this.nom = Objects.requireNonNull(nom, "Le nom ne peut pas être null");
        }

        public T couleur(CouleurJoueur couleur) {
            this.couleur = couleur;
            return self();
        }

        public T main(MainDeCartes main) {
            this.main = main;
            return self();
        }

        public T alapriorite(boolean alapriorite) {
            this.alapriorite = alapriorite;
            return self();
        }

        public T points(int points) {
            this.points = points;
            return self();
        }

        protected abstract T self();

        public abstract Joueur build();
    }
}