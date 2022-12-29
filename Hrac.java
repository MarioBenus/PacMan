
// postava, ktoru hrac ovlada
public class Hrac {
    private final int rychlostPohybu;
    
    private Smer hracSmer;
    private Obrazok hrac;

    private int pocetTikov;


    public Hrac() {
        this.hrac = new Obrazok("Obrazky\\player-2.png");
        this.hrac.zobraz();
        this.hrac.zmenPolohu(500, -700);

        this.hracSmer = Smer.ZIADNY;

        this.rychlostPohybu = 4;

        this.pocetTikov = 0;
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
                this.hrac.zmenObrazok("Obrazky\\player-1.png");
                break;
            case 5:
                this.hrac.zmenObrazok("Obrazky\\player-2.png");
                break;
            case 10:
                this.hrac.zmenObrazok("Obrazky\\player-3.png");
                break;
            case 15:
                this.hrac.zmenObrazok("Obrazky\\player-2.png");
                break;
            case 19:
                this.pocetTikov = -1;
                break;
              
        }
        this.pocetTikov++;
    }

    public void setSmer(Smer smer) {
        this.hracSmer = smer;
    }

    public Smer getSmer() {
        return this.hracSmer;
    }

    public int getX() {
        return this.hrac.getLavyDolnyX();
    }
    
    public int getY() {
        return this.hrac.getLavyDolnyY();
    }

    public int getRychlostPohybu() {
        return this.rychlostPohybu;
    }

    public void posunVodorovne(int vzdialenost) {
        this.hrac.posunVodorovne(vzdialenost);
    }
    
    public void posunZvisle(int vzdialenost) {
        this.hrac.posunZvisle(vzdialenost);
    }
    
}
