package fr.uvsq.cprog.zhengyao.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InitialiserLeJeuCommandeTest {

    @Test
    public void testExecute() {
        String nomJoueur = "Joueur1";
        Integer couleurJoueur = 4;
        Integer nombreJoueurs = 8;
        InitialiserLeJeuCommande initialiserLeJeuCommande = new InitialiserLeJeuCommande(nomJoueur, couleurJoueur, nombreJoueurs);

        assertThrows(IllegalArgumentException.class, initialiserLeJeuCommande::execute);

        nombreJoueurs = 1;
        InitialiserLeJeuCommande initialiserLeJeuCommande2 = new InitialiserLeJeuCommande(nomJoueur, couleurJoueur, nombreJoueurs);
        assertThrows(IllegalArgumentException.class, initialiserLeJeuCommande2::execute);

        couleurJoueur = 5;
        InitialiserLeJeuCommande initialiserLeJeuCommande3 = new InitialiserLeJeuCommande(nomJoueur, couleurJoueur, nombreJoueurs);
        assertThrows(IllegalArgumentException.class, initialiserLeJeuCommande3::execute);
    }
}
