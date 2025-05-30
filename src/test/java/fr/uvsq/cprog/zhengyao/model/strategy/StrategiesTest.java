package fr.uvsq.cprog.zhengyao.model.strategy;

import static org.junit.jupiter.api.Assertions.*;

import fr.uvsq.cprog.zhengyao.enumeration.CarteCouleur;
import fr.uvsq.cprog.zhengyao.enumeration.CarteValeur;
import fr.uvsq.cprog.zhengyao.enumeration.TypeCombinaison;
import fr.uvsq.cprog.zhengyao.model.Carte;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StrategiesTest {
    
    private CarteUniqueStrategy carteUniqueStrategy;
    private PaireStrategy paireStrategy;
    private TripletStrategy tripletStrategy;
    private SuiteStrategy suiteStrategy;
    private TripletPlusDeuxStrategy tripletPlusDeuxStrategy;
    private SuiteDePairesStrategy suiteDePairesStrategy;
    private CarreStrategy carreStrategy;
    
    @BeforeEach
    void setUp() {
        carteUniqueStrategy = new CarteUniqueStrategy();
        paireStrategy = new PaireStrategy();
        tripletStrategy = new TripletStrategy();
        suiteStrategy = new SuiteStrategy();
        tripletPlusDeuxStrategy = new TripletPlusDeuxStrategy();
        suiteDePairesStrategy = new SuiteDePairesStrategy();
        carreStrategy = new CarreStrategy();
    }
    
    @Test
    void testCarteUniqueStrategy() {
        List<Carte> carteUnique = Collections.singletonList(
            new Carte(CarteValeur.AS, CarteCouleur.PIQUE)
        );
        
        List<Carte> deuxCartes = Arrays.asList(
            new Carte(CarteValeur.DEUX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.TROIS, CarteCouleur.COEUR)
        );
        
        assertTrue(carteUniqueStrategy.valider(carteUnique));
        assertFalse(carteUniqueStrategy.valider(deuxCartes));
        assertEquals(TypeCombinaison.CARTE_UNIQUE, carteUniqueStrategy.getTypeCombinaison());
    }
    
    @Test
    void testPaireStrategy() {
        List<Carte> paireValide = Arrays.asList(
            new Carte(CarteValeur.DEUX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.DEUX, CarteCouleur.COEUR)
        );
        
        List<Carte> paireInvalide = Arrays.asList(
            new Carte(CarteValeur.DEUX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.TROIS, CarteCouleur.COEUR)
        );
        
        assertTrue(paireStrategy.valider(paireValide));
        assertFalse(paireStrategy.valider(paireInvalide));
        assertEquals(TypeCombinaison.PAIRE, paireStrategy.getTypeCombinaison());
    }
    
    @Test
    void testTripletStrategy() {
        List<Carte> tripletValide = Arrays.asList(
            new Carte(CarteValeur.DEUX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.DEUX, CarteCouleur.COEUR),
            new Carte(CarteValeur.DEUX, CarteCouleur.TREFLE)
        );
        
        List<Carte> tripletInvalide = Arrays.asList(
            new Carte(CarteValeur.DEUX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.DEUX, CarteCouleur.COEUR),
            new Carte(CarteValeur.TROIS, CarteCouleur.TREFLE)
        );
        
        assertTrue(tripletStrategy.valider(tripletValide));
        assertFalse(tripletStrategy.valider(tripletInvalide));
        assertEquals(TypeCombinaison.TRIPLET, tripletStrategy.getTypeCombinaison());
    }
    
    @Test
    void testSuiteStrategy() {
        List<Carte> suiteValide = Arrays.asList(
            new Carte(CarteValeur.DIX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.VALET, CarteCouleur.COEUR),
            new Carte(CarteValeur.DAME, CarteCouleur.TREFLE),
                new Carte(CarteValeur.ROI, CarteCouleur.PIQUE),
            new Carte(CarteValeur.AS, CarteCouleur.PIQUE),
            new Carte(CarteValeur.DEUX, CarteCouleur.COEUR)
        );
        
        List<Carte> suiteInvalide = Arrays.asList(
            new Carte(CarteValeur.DEUX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.TROIS, CarteCouleur.COEUR),
            new Carte(CarteValeur.CINQ, CarteCouleur.TREFLE),
            new Carte(CarteValeur.SIX, CarteCouleur.PIQUE)
        );
        
        assertTrue(suiteStrategy.valider(suiteValide));
        assertFalse(suiteStrategy.valider(suiteInvalide));
        assertEquals(TypeCombinaison.SUITE, suiteStrategy.getTypeCombinaison());
    }
    
    @Test
    void testTripletPlusDeuxStrategy() {
        List<Carte> tripletPlusDeuxValide = Arrays.asList(
            new Carte(CarteValeur.TROIS, CarteCouleur.PIQUE),
            new Carte(CarteValeur.TROIS, CarteCouleur.COEUR),
            new Carte(CarteValeur.TROIS, CarteCouleur.TREFLE),
            new Carte(CarteValeur.DEUX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.DEUX, CarteCouleur.COEUR)
        );
        
        List<Carte> tripletPlusDeuxInvalide = Arrays.asList(
            new Carte(CarteValeur.TROIS, CarteCouleur.PIQUE),
            new Carte(CarteValeur.TROIS, CarteCouleur.COEUR),
            new Carte(CarteValeur.TROIS, CarteCouleur.TREFLE),
            new Carte(CarteValeur.DEUX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.QUATRE, CarteCouleur.COEUR)
        );
        
        assertTrue(tripletPlusDeuxStrategy.valider(tripletPlusDeuxValide));
        assertFalse(tripletPlusDeuxStrategy.valider(tripletPlusDeuxInvalide));
        assertEquals(TypeCombinaison.TRIPLET_PLUS_DEUX, tripletPlusDeuxStrategy.getTypeCombinaison());
    }
    
    @Test
    void testSuiteDePairesStrategy() {
        List<Carte> suiteDePairesValide = Arrays.asList(
            new Carte(CarteValeur.TROIS, CarteCouleur.PIQUE),
            new Carte(CarteValeur.TROIS, CarteCouleur.COEUR),
            new Carte(CarteValeur.QUATRE, CarteCouleur.TREFLE),
            new Carte(CarteValeur.QUATRE, CarteCouleur.PIQUE),
            new Carte(CarteValeur.CINQ, CarteCouleur.PIQUE),
            new Carte(CarteValeur.CINQ, CarteCouleur.COEUR)
        );
        
        List<Carte> suiteDePairesInvalide = Arrays.asList(
            new Carte(CarteValeur.TROIS, CarteCouleur.PIQUE),
            new Carte(CarteValeur.TROIS, CarteCouleur.COEUR),
            new Carte(CarteValeur.CINQ, CarteCouleur.TREFLE),
            new Carte(CarteValeur.CINQ, CarteCouleur.PIQUE),
            new Carte(CarteValeur.SIX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.SIX, CarteCouleur.COEUR)
        );
        
        assertTrue(suiteDePairesStrategy.valider(suiteDePairesValide));
        assertFalse(suiteDePairesStrategy.valider(suiteDePairesInvalide));
        assertEquals(TypeCombinaison.SUITE_DE_PAIRES, suiteDePairesStrategy.getTypeCombinaison());
    }
    
    @Test
    void testCarreStrategy() {
        List<Carte> carreValide = Arrays.asList(
            new Carte(CarteValeur.DEUX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.DEUX, CarteCouleur.COEUR),
            new Carte(CarteValeur.DEUX, CarteCouleur.TREFLE),
            new Carte(CarteValeur.DEUX, CarteCouleur.CARREAU)
        );
        
        List<Carte> carreInvalide = Arrays.asList(
            new Carte(CarteValeur.DEUX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.DEUX, CarteCouleur.COEUR),
            new Carte(CarteValeur.DEUX, CarteCouleur.TREFLE),
            new Carte(CarteValeur.TROIS, CarteCouleur.CARREAU)
        );
        
        assertTrue(carreStrategy.valider(carreValide));
        assertFalse(carreStrategy.valider(carreInvalide));
        assertEquals(TypeCombinaison.CARRE, carreStrategy.getTypeCombinaison());
    }
}