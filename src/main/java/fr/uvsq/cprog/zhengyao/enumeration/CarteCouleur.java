package fr.uvsq.cprog.zhengyao.enumeration;

/**
 * Enumération des couleurs possible des cartes.
 */
public enum CarteCouleur {
    PIQUE("SHD Pique"), COEUR("HC Coeur"), CARREAU("DC Carreau"), TREFLE("CC Trefle");

    private String couleur;

    CarteCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getCouleur() {
        return couleur;
    }
}
