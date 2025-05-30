package fr.uvsq.cprog.zhengyao;

import fr.uvsq.cprog.zhengyao.ui.PartieUi;

/**
 * Classe principale qui sert à demarer une partie de Zheng Shangyou.
 */
public class PartieApp {
    private PartieUi partieUi;
    private static final String WELCOME_MESSAGE = "Bonjour, bienvenue dans le jeu de Zheng Shangyou !";

    /**
     * Constructeur de la classe Partie.
     */
    public PartieApp() {
        this.partieUi = new PartieUi();
    }

    /**
     * Démarre la partie.
     */
    public void demarrer() {
        System.out.println(WELCOME_MESSAGE);
        // Crée une nouvelle partie
        partieUi.lancerApplication();

    }

    /**
     * Point d'entrée principal pour tester une partie.
     */
    public static void main(String[] args) {
        PartieApp app = new PartieApp();
        app.demarrer();
    }
}
