package fr.uvsq.cprog.zhengyao.utils;

import fr.uvsq.cprog.zhengyao.model.Carte;
import java.util.List;

/**
 * Utility class for card-related operations.
 */
public class CardUtils {

    /**
     * Formate les cartes d'un joueur en une chaîne de caractères.
     *
     * @param playerId L'identifiant du joueur (ex: "JV1").
     * @param cards    La liste des cartes sous forme de chaînes (ex: "K(SHD)", "8(DC)").
     *
     * @return Une chaîne formatée représentant les actions du joueur.
     */
    public static String formatPlayerCards(String playerId, List<String> cards) {
        return playerId + ": " + String.join(", ", cards);
    }

    /**
     * Convertit une liste de cartes en une chaîne de symboles Unicode.
     *
     * @param cards La liste des cartes.
     *
     * @return Une chaîne contenant les symboles Unicode des cartes.
     */
    public static String toUnicodeSymbols(List<Carte> cards) {
        StringBuilder sb = new StringBuilder();
        for (Carte card : cards) {
            sb.append(getUnicodeSymbol(card)).append(", ");
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : ""; // Retirer la dernière virgule
    }

    /**
     * Retourne le symbole Unicode correspondant à une carte.
     *
     * @param card La carte.
     *
     * @return Le symbole Unicode de la carte.
     */
    public static String getUnicodeSymbol(Carte card) {
        String unicodeSymbol = switch (card.getValeur()) {
          case AS -> switch (card.getCouleur()) {
            case PIQUE -> "🂡";
            case COEUR -> "🂱";
            case CARREAU -> "🃁";
            case TREFLE -> "🃑";
          };
          case ROI -> switch (card.getCouleur()) {
            case PIQUE -> "🂮";
            case COEUR -> "🂾";
            case CARREAU -> "🃎";
            case TREFLE -> "🃞";
          };
          case DAME -> switch (card.getCouleur()) {
            case PIQUE -> "🂭";
            case COEUR -> "🂽";
            case CARREAU -> "🃍";
            case TREFLE -> "🃝";
          };
          case VALET -> switch (card.getCouleur()) {
            case PIQUE -> "🂫";
            case COEUR -> "🂻";
            case CARREAU -> "🃋";
            case TREFLE -> "🃛";
          };
          case DIX -> switch (card.getCouleur()) {
            case PIQUE -> "🂪";
            case COEUR -> "🂺";
            case CARREAU -> "🃊";
            case TREFLE -> "🃚";
          };
          case NEUF -> switch (card.getCouleur()) {
            case PIQUE -> "🂩";
            case COEUR -> "🂹";
            case CARREAU -> "🃉";
            case TREFLE -> "🃙";
          };
          case HUIT -> switch (card.getCouleur()) {
            case PIQUE -> "🂨";
            case COEUR -> "🂸";
            case CARREAU -> "🃈";
            case TREFLE -> "🃘";
          };
          case SEPT -> switch (card.getCouleur()) {
            case PIQUE -> "🂧";
            case COEUR -> "🂷";
            case CARREAU -> "🃇";
            case TREFLE -> "🃗";
          };
          case SIX -> switch (card.getCouleur()) {
            case PIQUE -> "🂦";
            case COEUR -> "🂶";
            case CARREAU -> "🃆";
            case TREFLE -> "🃖";
          };
          case CINQ -> switch (card.getCouleur()) {
            case PIQUE -> "🂥";
            case COEUR -> "🂵";
            case CARREAU -> "🃅";
            case TREFLE -> "🃕";
          };
          case QUATRE -> switch (card.getCouleur()) {
            case PIQUE -> "🂤";
            case COEUR -> "🂴";
            case CARREAU -> "🃄";
            case TREFLE -> "🃔";
          };
          case TROIS -> switch (card.getCouleur()) {
            case PIQUE -> "🂣";
            case COEUR -> "🂳";
            case CARREAU -> "🃃";
            case TREFLE -> "🃓";
          };
          case DEUX -> switch (card.getCouleur()) {
            case PIQUE -> "🂢";
            case COEUR -> "🂲";
            case CARREAU -> "🃂";
            case TREFLE -> "🃒";
          };
        };
        return unicodeSymbol;
    }
}
