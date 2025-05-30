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

class ValidationStrategyFactoryTest {
    
    private ValidationStrategyFactory factory;
    
    @BeforeEach
    void setUp() {
        factory = new ValidationStrategyFactory();
    }
    
    @Test
    void testValiderCombinaison_avecCarteUnique() {
        List<Carte> cartes = Collections.singletonList(
            new Carte(CarteValeur.AS, CarteCouleur.PIQUE)
        );
        
        assertEquals(TypeCombinaison.CARTE_UNIQUE, factory.validerCombinaison(cartes));
    }
    
    @Test
    void testValiderCombinaison_avecPaire() {
        List<Carte> cartes = Arrays.asList(
            new Carte(CarteValeur.DEUX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.DEUX, CarteCouleur.COEUR)
        );
        
        assertEquals(TypeCombinaison.PAIRE, factory.validerCombinaison(cartes));
    }
    
    @Test
    void testValiderCombinaison_avecTriplet() {
        List<Carte> cartes = Arrays.asList(
            new Carte(CarteValeur.DEUX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.DEUX, CarteCouleur.COEUR),
            new Carte(CarteValeur.DEUX, CarteCouleur.TREFLE)
        );
        
        assertEquals(TypeCombinaison.TRIPLET, factory.validerCombinaison(cartes));
    }
    
    @Test
    void testValiderCombinaison_avecCarre() {
        List<Carte> cartes = Arrays.asList(
            new Carte(CarteValeur.DEUX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.DEUX, CarteCouleur.COEUR),
            new Carte(CarteValeur.DEUX, CarteCouleur.TREFLE),
            new Carte(CarteValeur.DEUX, CarteCouleur.CARREAU)
        );
        
        assertEquals(TypeCombinaison.CARRE, factory.validerCombinaison(cartes));
    }
    
    @Test
    void testValiderCombinaison_avecSuite() {
        List<Carte> cartes = Arrays.asList(
                new Carte(CarteValeur.DIX, CarteCouleur.PIQUE),
                new Carte(CarteValeur.VALET, CarteCouleur.COEUR),
                new Carte(CarteValeur.DAME, CarteCouleur.TREFLE),
                new Carte(CarteValeur.ROI, CarteCouleur.PIQUE),
                new Carte(CarteValeur.AS, CarteCouleur.PIQUE)
        );
        
        assertEquals(TypeCombinaison.SUITE, factory.validerCombinaison(cartes));
    }
    
    @Test
    void testValiderCombinaison_avecTripletPlusDeux() {
        List<Carte> cartes = Arrays.asList(
            new Carte(CarteValeur.TROIS, CarteCouleur.PIQUE),
            new Carte(CarteValeur.TROIS, CarteCouleur.COEUR),
            new Carte(CarteValeur.TROIS, CarteCouleur.TREFLE),
            new Carte(CarteValeur.DEUX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.DEUX, CarteCouleur.COEUR)
        );
        
        assertEquals(TypeCombinaison.TRIPLET_PLUS_DEUX, factory.validerCombinaison(cartes));
    }
    
    @Test
    void testValiderCombinaison_avecSuiteDePaires() {
        List<Carte> cartes = Arrays.asList(
            new Carte(CarteValeur.TROIS, CarteCouleur.PIQUE),
            new Carte(CarteValeur.TROIS, CarteCouleur.COEUR),
            new Carte(CarteValeur.QUATRE, CarteCouleur.TREFLE),
            new Carte(CarteValeur.QUATRE, CarteCouleur.PIQUE),
            new Carte(CarteValeur.CINQ, CarteCouleur.PIQUE),
            new Carte(CarteValeur.CINQ, CarteCouleur.COEUR)
        );
        
        assertEquals(TypeCombinaison.SUITE_DE_PAIRES, factory.validerCombinaison(cartes));
    }
    
    @Test
    void testValiderCombinaison_avecCombinaisionInvalide() {
        List<Carte> cartes = Arrays.asList(
            new Carte(CarteValeur.DEUX, CarteCouleur.PIQUE),
            new Carte(CarteValeur.TROIS, CarteCouleur.COEUR),
            new Carte(CarteValeur.DEUX, CarteCouleur.TREFLE),
            new Carte(CarteValeur.CINQ, CarteCouleur.PIQUE)
        );
        
        assertEquals(TypeCombinaison.INVALIDE, factory.validerCombinaison(cartes));
    }
    
    @Test
    void testValiderCombinaison_avecListeVide() {
        assertEquals(TypeCombinaison.INVALIDE, factory.validerCombinaison(Collections.emptyList()));
    }
    
    @Test
    void testValiderCombinaison_avecListeNull() {
        assertEquals(TypeCombinaison.INVALIDE, factory.validerCombinaison(null));
    }
}