package fr.uvsq.cprog.zhengyao.enumeration;

/**
 * Types de combinaisons possibles dans le jeu.
 */
public enum TypeCombinaison {
    CARTE_UNIQUE("Cartes uniques"),
    PAIRE("Paires"),
    TRIPLET("Triplets"),
    SUITE("Suites"),
    TRIPLET_PLUS_DEUX("Triplets + 2 cartes"),
    SUITE_DE_PAIRES("Suite de 3 fois deux cartes"),
    CARRE("Carrés (Bombes)"),
    INVALIDE("Combinaison invalide");

    private String combinaison;

    TypeCombinaison(String combinaison) {
        this.combinaison = combinaison;
    }

    public String getCombinaison() {
        return combinaison;
    }
}
