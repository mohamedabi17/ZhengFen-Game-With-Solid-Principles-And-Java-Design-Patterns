package fr.uvsq.cprog.zhengyao.model.strategy;

import static org.junit.jupiter.api.Assertions.*;

import fr.uvsq.cprog.zhengyao.enumeration.CarteCouleur;
import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import fr.uvsq.cprog.zhengyao.model.Carte;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class ValidationUtilsTest {

    @Test
    void testSontDeMemeValeur_avecCartesIdentiques() {
        List<Carte> cartes = Arrays.asList(
            new Carte(CarteValeur.TROIS, CarteCouleur.CARREAU),
            new Carte(CarteValeur.TROIS, CarteCouleur.COEUR),
            new Carte(CarteValeur.TROIS, CarteCouleur.TREFLE)
        );
        
        assertTrue(ValidationUtils.sontDeMemeValeur(cartes));
    }
    
    @Test
    void testSontDeMemeValeur_avecCartesDifferentes() {
        List<Carte> cartes = Arrays.asList(
            new Carte(CarteValeur.TROIS, CarteCouleur.CARREAU),
            new Carte(CarteValeur.QUATRE, CarteCouleur.COEUR),
            new Carte(CarteValeur.TROIS, CarteCouleur.TREFLE)
        );
        
        assertFalse(ValidationUtils.sontDeMemeValeur(cartes));
    }
    
    @Test
    void testSontDeMemeValeur_avecListeVide() {
        assertFalse(ValidationUtils.sontDeMemeValeur(new ArrayList<>()));
    }
    
    @Test
    void testSontDeMemeValeur_avecListeNull() {
        assertFalse(ValidationUtils.sontDeMemeValeur(null));
    }
    
    @Test
    void testSontEnSequence_avecSequenceValide() {
        List<Carte> cartes = Arrays.asList(
            new Carte(CarteValeur.TROIS, CarteCouleur.CARREAU),
            new Carte(CarteValeur.QUATRE, CarteCouleur.COEUR),
            new Carte(CarteValeur.CINQ, CarteCouleur.TREFLE),
            new Carte(CarteValeur.SIX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.SEPT, CarteCouleur.COEUR)
        );
        
        assertTrue(ValidationUtils.sontEnSequence(cartes));
    }
    
    @Test
    void testSontEnSequence_avecSequenceNonOrdonnee() {
        List<Carte> cartes = Arrays.asList(
            new Carte(CarteValeur.CINQ, CarteCouleur.TREFLE),
            new Carte(CarteValeur.TROIS, CarteCouleur.CARREAU),
            new Carte(CarteValeur.SEPT, CarteCouleur.COEUR),
            new Carte(CarteValeur.QUATRE, CarteCouleur.COEUR),
            new Carte(CarteValeur.SIX, CarteCouleur.PIQUE)
        );
        
        assertTrue(ValidationUtils.sontEnSequence(cartes)); // Devrait fonctionner car méthode trie les cartes
    }
    
    @Test
    void testSontEnSequence_avecSequenceInvalide() {
        List<Carte> cartes = Arrays.asList(
            new Carte(CarteValeur.TROIS, CarteCouleur.CARREAU),
            new Carte(CarteValeur.QUATRE, CarteCouleur.COEUR),
            new Carte(CarteValeur.CINQ, CarteCouleur.TREFLE),
            new Carte(CarteValeur.SEPT, CarteCouleur.PIQUE) // Manque le SIX
        );
        
        assertFalse(ValidationUtils.sontEnSequence(cartes));
    }
    
    @Test
    void testEstTripletPlusDeux_avecTripletPlusPaireValide() {
        List<Carte> cartes = Arrays.asList(
            new Carte(CarteValeur.TROIS, CarteCouleur.CARREAU),
            new Carte(CarteValeur.TROIS, CarteCouleur.COEUR),
            new Carte(CarteValeur.TROIS, CarteCouleur.TREFLE),
            new Carte(CarteValeur.DEUX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.DEUX, CarteCouleur.COEUR)
        );
        
        assertTrue(ValidationUtils.estTripletPlusDeux(cartes));
    }
    
    @Test
    void testEstTripletPlusDeux_avecOrdreInverse() {
        List<Carte> cartes = Arrays.asList(
            new Carte(CarteValeur.DEUX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.DEUX, CarteCouleur.COEUR),
            new Carte(CarteValeur.TROIS, CarteCouleur.CARREAU),
            new Carte(CarteValeur.TROIS, CarteCouleur.COEUR),
            new Carte(CarteValeur.TROIS, CarteCouleur.TREFLE)
        );
        
        assertTrue(ValidationUtils.estTripletPlusDeux(cartes));
    }
    
    @Test
    void testEstTripletPlusDeux_avecCombinaisionInvalide() {
        List<Carte> cartes = Arrays.asList(
            new Carte(CarteValeur.DEUX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.DEUX, CarteCouleur.COEUR),
            new Carte(CarteValeur.TROIS, CarteCouleur.CARREAU),
            new Carte(CarteValeur.QUATRE, CarteCouleur.COEUR),
            new Carte(CarteValeur.CINQ, CarteCouleur.TREFLE)
        );
        
        assertFalse(ValidationUtils.estTripletPlusDeux(cartes));
    }
    
    @Test
    void testEstSuiteDePaires_avecSuiteValide() {
        List<Carte> cartes = Arrays.asList(
            new Carte(CarteValeur.TROIS, CarteCouleur.CARREAU),
            new Carte(CarteValeur.TROIS, CarteCouleur.COEUR),
            new Carte(CarteValeur.QUATRE, CarteCouleur.TREFLE),
            new Carte(CarteValeur.QUATRE, CarteCouleur.PIQUE),
            new Carte(CarteValeur.CINQ, CarteCouleur.COEUR),
            new Carte(CarteValeur.CINQ, CarteCouleur.TREFLE)
        );
        
        assertTrue(ValidationUtils.estSuiteDePaires(cartes));
    }
    
    @Test
    void testEstSuiteDePaires_avecSuiteNonOrdonnee() {
        List<Carte> cartes = Arrays.asList(
            new Carte(CarteValeur.QUATRE, CarteCouleur.TREFLE),
            new Carte(CarteValeur.CINQ, CarteCouleur.COEUR),
            new Carte(CarteValeur.TROIS, CarteCouleur.CARREAU),
            new Carte(CarteValeur.QUATRE, CarteCouleur.PIQUE),
            new Carte(CarteValeur.CINQ, CarteCouleur.TREFLE),
            new Carte(CarteValeur.TROIS, CarteCouleur.COEUR)
        );
        
        assertTrue(ValidationUtils.estSuiteDePaires(cartes)); // Devrait fonctionner car méthode trie les cartes
    }
    
    @Test
    void testEstSuiteDePaires_avecSuiteInvalide() {
        List<Carte> cartes = Arrays.asList(
            new Carte(CarteValeur.TROIS, CarteCouleur.CARREAU),
            new Carte(CarteValeur.TROIS, CarteCouleur.COEUR),
            new Carte(CarteValeur.CINQ, CarteCouleur.TREFLE), // Manque le QUATRE
            new Carte(CarteValeur.CINQ, CarteCouleur.PIQUE),
            new Carte(CarteValeur.SIX, CarteCouleur.COEUR),
            new Carte(CarteValeur.SIX, CarteCouleur.TREFLE)
        );
        
        assertFalse(ValidationUtils.estSuiteDePaires(cartes));
    }
    
    @Test
    void testTrierCartes() {
        List<Carte> cartes = Arrays.asList(
            new Carte(CarteValeur.ROI, CarteCouleur.COEUR),
            new Carte(CarteValeur.DEUX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.AS, CarteCouleur.CARREAU)
        );
        
        List<Carte> cartesTries = ValidationUtils.trierCartes(cartes);
        
        assertEquals(CarteValeur.ROI, cartesTries.get(0).getValeur());
        assertEquals(CarteValeur.AS, cartesTries.get(1).getValeur());
        assertEquals(CarteValeur.DEUX, cartesTries.get(2).getValeur());
    }
}