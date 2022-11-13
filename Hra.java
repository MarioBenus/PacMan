import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Write a description of class GameManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hra {
    private static final int TICK_DLZKA = 16;
    private long staryTick;

    private Hrac hrac;
    
    
    public Hra() {
        Platno.dajPlatno().addTimerListener(new ManazerCasovaca());

        this.hrac = new Hrac(TICK_DLZKA);
    }

    // princip fungovania triedy ManazerCasovaca je prevzaty z TvaryV3
    private class ManazerCasovaca implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            long novyTick = System.nanoTime() / 1000000; // prevod z nanosekund do milisekund
            if (novyTick - Hra.this.staryTick >= TICK_DLZKA || novyTick < TICK_DLZKA) {
                Hra.this.staryTick = novyTick;
                Hra.this.hrac.tick();
            }
        }
    }
}
