import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Write a description of class GameManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameManager {
    private static final int TICK_LENGTH = 16;
    private long oldTick;

    private PlayerManager playerManager;
    
    
    public GameManager() {
        Platno.dajPlatno().addTimerListener(new ManazerCasovaca());

        this.playerManager = new PlayerManager(TICK_LENGTH);
    }

    private class ManazerCasovaca implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            long newTick = System.nanoTime() / 1000000;
            if (newTick - GameManager.this.oldTick >= TICK_LENGTH || newTick < TICK_LENGTH) {
                GameManager.this.oldTick = newTick;
                GameManager.this.playerManager.tick();
            }
        }
    }
}
