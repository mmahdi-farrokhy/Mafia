package mmf.mafia.scenario;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ClassicMafiaShould {
    @Test
    public void get_number_of_players_and_set_number_of_citizens_and_mafias() {
        ClassicMafia classicMafia = new ClassicMafia(5);
        assertEquals(2, classicMafia.getMafiasCount());
        assertEquals(3, classicMafia.getCitizensCount());

        classicMafia = new ClassicMafia(6);
        assertEquals(2, classicMafia.getMafiasCount());
        assertEquals(4, classicMafia.getCitizensCount());

        classicMafia = new ClassicMafia(7);
        assertEquals(2, classicMafia.getMafiasCount());
        assertEquals(5, classicMafia.getCitizensCount());

        classicMafia = new ClassicMafia(8);
        assertEquals(3, classicMafia.getMafiasCount());
        assertEquals(5, classicMafia.getCitizensCount());

        classicMafia = new ClassicMafia(9);
        assertEquals(3, classicMafia.getMafiasCount());
        assertEquals(6, classicMafia.getCitizensCount());

        classicMafia = new ClassicMafia(10);
        assertEquals(3, classicMafia.getMafiasCount());
        assertEquals(7, classicMafia.getCitizensCount());

        classicMafia = new ClassicMafia(11);
        assertEquals(3, classicMafia.getMafiasCount());
        assertEquals(8, classicMafia.getCitizensCount());

        classicMafia = new ClassicMafia(12);
        assertEquals(4, classicMafia.getMafiasCount());
        assertEquals(8, classicMafia.getCitizensCount());
    }
}