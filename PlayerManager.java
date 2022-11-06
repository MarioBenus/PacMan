import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class PlayerManager {
    private int rychlostPohybu;
    
    private Direction playerDirection;
    private Kruh player;
    
    // konstruktor
    public PlayerManager(int tickLength) {
        Platno.dajPlatno().addKeyListener(new ManazerKlaves());

        // nastavenie vlastnosti hraca

        /* 
         * rychlost pohybu je upravena podla dlzky ticku,
         * aby rychlost zostala rovnaka aj ked sa dlzka ticku zmeni
         */
        this.rychlostPohybu = tickLength / 4;
        this.player = new Kruh();
        this.player.zmenFarbu("yellow");
        this.player.zobraz();
        this.playerDirection = Direction.NONE;

    }

    // movement hraca podla posledneho stlaceneho smeru
    public void tick() {
        switch (this.playerDirection) {
            case UP:
                this.player.posunZvisle(-rychlostPohybu);
                break;
            case DOWN:
                this.player.posunZvisle(rychlostPohybu);
                break;
            case LEFT:
                this.player.posunVodorovne(-rychlostPohybu);
                break;
            case RIGHT:
                this.player.posunVodorovne(rychlostPohybu);
                break;
            default:
                break;
        }
    }

    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        NONE
    }

    
    private class ManazerKlaves extends KeyAdapter {
        public void keyPressed(KeyEvent event) {

            // nastavuje smer pohybu hraca na smer poslednej stlacenej sipky
            if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                PlayerManager.this.playerDirection = Direction.DOWN;

            } else if (event.getKeyCode() == KeyEvent.VK_UP) {
                PlayerManager.this.playerDirection = Direction.UP;

            } else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                PlayerManager.this.playerDirection = Direction.LEFT;

            } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                PlayerManager.this.playerDirection = Direction.RIGHT;

            }
        }
    }


    
}
