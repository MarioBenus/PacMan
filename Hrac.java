
public class Hrac {
    private int rychlostPohybu;
    
    private Smer hracSmer;
    private Obrazok hrac;

    private int pocetTikov;

    private Kolizia kolizia;

    // konstruktor
    public Hrac(Kolizia kolizia) {
        // nastavenie vlastnosti hraca

        /* 
         * rychlost pohybu je upravena podla dlzky ticku,
         * aby rychlost zostala rovnaka aj ked sa dlzka ticku zmeni
         */

        this.hrac = new Obrazok("Obrazky\\pacman-2.png");
        this.hrac.zobraz();
        this.hrac.zmenPolohu(500, -500);

        this.hracSmer = Smer.ZIADNY;

        this.rychlostPohybu = 4;

        this.pocetTikov = 0;

        this.kolizia = kolizia;

    }

    // movement hraca podla posledneho stlaceneho smeru
    public void tick() {


        this.animacia();
        
        this.hrac.posunVodorovne(this.rychlostPohybu * this.hracSmer.getNasobicX());
        this.hrac.posunZvisle(this.rychlostPohybu * this.hracSmer.getNasobicY());
        this.hrac.zmenUhol(this.hracSmer.getUhol());

    }


    private void animacia() {
        switch (this.pocetTikov) {
            case 0:
                this.hrac.zmenObrazok("Obrazky\\pacman-1.png");
                break;
            case 5:
                this.hrac.zmenObrazok("Obrazky\\pacman-2.png");
                break;
            case 10:
                this.hrac.zmenObrazok("Obrazky\\pacman-3.png");
                break;
            case 15:
                this.hrac.zmenObrazok("Obrazky\\pacman-2.png");
                break;
            case 19:
                this.pocetTikov = -1;
                break;
              
        }
        this.pocetTikov++;
    }

    public void zmenSmer(Smer smer) {
        this.hracSmer = smer;
    }

    public int getX() {
        return this.hrac.getLavyDolnyX();
    }
    
    public int getY() {
        return this.hrac.getLavyDolnyY();
    }

    public void posunVodorovne(int vzdialenost) {
        this.hrac.posunVodorovne(vzdialenost);
    }
    
    public void posunZvisle(int vzdialenost) {
        this.hrac.posunZvisle(vzdialenost);
    }
    
    
    // princip fungovania triedy ManazerKlaves je prevzaty z TvaryV3
    /*private class ManazerKlaves extends KeyAdapter {

        public void keyPressed(KeyEvent event) {

            // nastavuje smer pohybu hraca na smer poslednej stlacenej sipky
            if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                Hrac.this.hracSmer = Smer.DOLE;
                Hrac.this.hrac.zmenUhol(180);

            } else if (event.getKeyCode() == KeyEvent.VK_UP) {
                Hrac.this.hracSmer = Smer.HORE;
                Hrac.this.hrac.zmenUhol(0);

            } else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                Hrac.this.hracSmer = Smer.VLAVO;
                Hrac.this.hrac.zmenUhol(270);

            } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                Hrac.this.hracSmer = Smer.VPRAVO;
                Hrac.this.hrac.zmenUhol(90);

            } else if (event.getKeyCode() == KeyEvent.VK_SPACE) { // ODTIALTO NIZSIE  JE DEBUG
                Hrac.this.hracSmer = Smer.ZIADNY;
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
    }*/


    
}
