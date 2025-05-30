package fr.uvsq.cprog.zhengyao.enumeration;

/**
 * Enumération des valeurs possible des cartes.
 */
public enum CarteValeur {
    TROIS("3"), QUATRE("4"), CINQ("5"), SIX("6"), SEPT("7"), HUIT("8"),
    NEUF("9"), DIX("10"), VALET("J"), DAME("Q"), ROI("K"), AS("A"), DEUX("2");

    private String valeur;

    CarteValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getValeur() {
        return valeur;
    }
}
