import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class Hrac {
    private double rychlostPohybu;
    
    private Direction hracSmer;
    private Obrazok hrac;

    
    
    // konstruktor
    public Hrac() {
        Platno.dajPlatno().addKeyListener(new ManazerKlaves());

        // nastavenie vlastnosti hraca

        /* 
         * rychlost pohybu je upravena podla dlzky ticku,
         * aby rychlost zostala rovnaka aj ked sa dlzka ticku zmeni
         */

        this.hrac = new Obrazok("Obrazky\\pacman-2.png");
        this.hrac.zobraz();

        this.hracSmer = Direction.NONE;

        this.rychlostPohybu = 0.25;

    }

    // movement hraca podla posledneho stlaceneho smeru
    public void tick(double deltaCas) {
        //System.out.println("posun o " + this.rychlostPohybu * deltaCas / 1000000);
        switch (this.hracSmer) {
            case UP:
                this.hrac.posunZvisle(this.rychlostPohybu * deltaCas / 1000000);
                break;
            case DOWN:
                this.hrac.posunZvisle(-this.rychlostPohybu * deltaCas / 1000000);
                break;
            case LEFT:
                this.hrac.posunVodorovne(-this.rychlostPohybu * deltaCas / 1000000);
                break;
            case RIGHT:
                this.hrac.posunVodorovne(this.rychlostPohybu * deltaCas / 1000000);
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
                Hrac.this.hrac.zmenUhol(180);

            } else if (event.getKeyCode() == KeyEvent.VK_UP) {
                Hrac.this.hracSmer = Direction.UP;
                Hrac.this.hrac.zmenUhol(0);

            } else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                Hrac.this.hracSmer = Direction.LEFT;
                Hrac.this.hrac.zmenUhol(270);

            } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                Hrac.this.hracSmer = Direction.RIGHT;
                Hrac.this.hrac.zmenUhol(90);

            } else if (event.getKeyCode() == KeyEvent.VK_SPACE) { // ODTIALTO NIZSIE  JE DEBUG
                Hrac.this.hracSmer = Direction.NONE;
            } else if (event.getKeyCode() == KeyEvent.VK_W) {
                Hrac.this.hrac.posunZvisle(1);
            } else if (event.getKeyCode() == KeyEvent.VK_S) {
                Hrac.this.hrac.posunZvisle(-1);
            } else if (event.getKeyCode() == KeyEvent.VK_A) {
                Hrac.this.hrac.posunVodorovne(-1);
            } else if (event.getKeyCode() == KeyEvent.VK_D) {
                Hrac.this.hrac.posunVodorovne(1);
            }
        }
    }


    
}
