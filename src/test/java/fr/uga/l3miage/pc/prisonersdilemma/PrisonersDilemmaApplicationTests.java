package fr.uga.l3miage.pc.prisonersdilemma;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

class PrisonersDilemmaApplicationTests {

    private Client client1;
    private Joueur joueur1;
    private Joueur joueur2;

    @BeforeEach
    void setUp() throws IOException {
        // Mock des objets Joueur
        joueur1 = mock(Joueur.class);
        joueur2 = mock(Joueur.class);
        client1 = new Client(joueur1);
    }


    @Test
    void testRecevoirCoup() throws IOException {
        // when
        when(joueur1.decision()).thenReturn("t");

        //then
        assertEquals("t", client1.recevoirCoup());
    }



}
