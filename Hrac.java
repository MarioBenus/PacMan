import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class Hrac {
    private int rychlostPohybu;
    
    private Direction hracSmer;
    private Kruh hrac;
    
    // konstruktor
    public Hrac(int tickLength) {
        Platno.dajPlatno().addKeyListener(new ManazerKlaves());

        // nastavenie vlastnosti hraca

        /* 
         * rychlost pohybu je upravena podla dlzky ticku,
         * aby rychlost zostala rovnaka aj ked sa dlzka ticku zmeni
         */
        this.rychlostPohybu = tickLength / 4;

        this.hrac = new Kruh();
        this.hrac.zmenFarbu("yellow");
        this.hrac.zobraz();
        this.hracSmer = Direction.NONE;

    }

    // movement hraca podla posledneho stlaceneho smeru
    public void tick() {
        switch (this.hracSmer) {
            case UP:
                this.hrac.posunZvisle(-rychlostPohybu);
                break;
            case DOWN:
                this.hrac.posunZvisle(rychlostPohybu);
                break;
            case LEFT:
                this.hrac.posunVodorovne(-rychlostPohybu);
                break;
            case RIGHT:
                this.hrac.posunVodorovne(rychlostPohybu);
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

    
    // princip fungovania triedy ManazerKlaves je prevzaty z TvaryV3
    private class ManazerKlaves extends KeyAdapter {
        public void keyPressed(KeyEvent event) {

            // nastavuje smer pohybu hraca na smer poslednej stlacenej sipky
            if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                Hrac.this.hracSmer = Direction.DOWN;

            } else if (event.getKeyCode() == KeyEvent.VK_UP) {
                Hrac.this.hracSmer = Direction.UP;

            } else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                Hrac.this.hracSmer = Direction.LEFT;

            } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                Hrac.this.hracSmer = Direction.RIGHT;

            }
        }
    }


    
}
