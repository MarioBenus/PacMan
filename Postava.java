
// postava, ktoru hrac ovlada
public class Postava {
    private int rychlostPohybu;
    
    private Smer momentalnySmer;
    private Smer pozadovanySmer;
    private Obrazok obrazok;

    private int pocetTikov;

    private TypPostavy typPostavy;
    private Pohyb pohyb;

    private Kolizia kolizia;


    public Postava(TypPostavy typPostavy, int stredX, int stredY) {
        this.typPostavy = typPostavy;
        this.kolizia = Kolizia.dajKoliziu();
        this.pohyb = new Pohyb(this.kolizia);

        switch (this.typPostavy) {
            case HRAC:
                this.obrazok = new Obrazok("Obrazky\\player-1.png");
                break;
            case CERVENY_DUCH:
                this.obrazok = new Obrazok("Obrazky\\redghost-up-1.png");
                break;
            case MODRY_DUCH:
                this.obrazok = new Obrazok("Obrazky\\blueghost-up-1.png");
                break;
            case ORANZOVY_DUCH:
                this.obrazok = new Obrazok("Obrazky\\orangeghost-up-1.png");
                break;
            case RUZOVY_DUCH:
                this.obrazok = new Obrazok("Obrazky\\pinkghost-up-1.png");
                break;
        }

        this.obrazok.zmenPolohu(stredX, stredY);
        this.obrazok.zobraz();

        this.momentalnySmer = Smer.HORE;
        this.pozadovanySmer = Smer.ZIADNY;

        this.rychlostPohybu = 4;

        this.pocetTikov = 0;
    }

    // movement hraca podla posledneho stlaceneho smeru
    public void tick() {
        this.pohyb.koliziaStien(this);
        this.pohyb.zmenaSmeru(this.pozadovanySmer, this);

        this.animacia();

        
        this.obrazok.posunVodorovne(this.rychlostPohybu * this.momentalnySmer.getNasobicX());
        this.obrazok.posunZvisle(this.rychlostPohybu * this.momentalnySmer.getNasobicY());
        

    }


    private void animacia() {
        String cestkaKObrazkuDucha = "Obrazky\\";
        switch (this.typPostavy) {
            case HRAC:
                this.obrazok.zmenUhol(this.momentalnySmer.getUhol());
                switch (this.pocetTikov) {
                    case 0:
                        this.obrazok.zmenObrazok("Obrazky\\player-1.png");
                        break;
                    case 5:
                        this.obrazok.zmenObrazok("Obrazky\\player-2.png");
                        break;
                    case 10:
                        this.obrazok.zmenObrazok("Obrazky\\player-3.png");
                        break;
                    case 15:
                        this.obrazok.zmenObrazok("Obrazky\\player-2.png");
                        break;
                    case 19:
                        this.pocetTikov = -1;
                        break;
                }
                this.pocetTikov++;
                return;

        
            case CERVENY_DUCH:
                cestkaKObrazkuDucha += "redghost-";
                break;
            case MODRY_DUCH:
                cestkaKObrazkuDucha += "blueghost-";
                break;
            case ORANZOVY_DUCH:
                cestkaKObrazkuDucha += "orangeghost-";
                break;
            case RUZOVY_DUCH:
                cestkaKObrazkuDucha += "pinkghost-";
                break;
        }
        switch (this.momentalnySmer) {
            case HORE:
                cestkaKObrazkuDucha += "up-";
                break;
            case VPRAVO:
                cestkaKObrazkuDucha += "right-";
                break;
            case VLAVO:
                cestkaKObrazkuDucha += "left-";
                break;
            case DOLE:
                cestkaKObrazkuDucha += "down-";
                break;        
        }
        switch (this.pocetTikov) {
            case 0:
                cestkaKObrazkuDucha += "1.png";
                this.obrazok.zmenObrazok(cestkaKObrazkuDucha);
                break;
            case 10:
                cestkaKObrazkuDucha += "2.png";
                this.obrazok.zmenObrazok(cestkaKObrazkuDucha);
                break;
            case 19:
                this.pocetTikov = -1;
                break;
        }

        
        this.pocetTikov++;
    }

    public void setSmer(Smer smer) {
        this.momentalnySmer = smer;
    }

    public Smer getSmer() {
        return this.momentalnySmer;
    }

    public int getX() {
        return this.obrazok.getLavyDolnyX();
    }
    
    public int getY() {
        return this.obrazok.getLavyDolnyY();
    }

    public int getRychlostPohybu() {
        return this.rychlostPohybu;
    }

    public void posunVodorovne(int vzdialenost) {
        this.obrazok.posunVodorovne(vzdialenost);
    }
    
    public void posunZvisle(int vzdialenost) {
        this.obrazok.posunZvisle(vzdialenost);
    }

    public int getVelkostObrazka() {
        return this.obrazok.vyska();
    }

    public Obrazok getObrazok() {
        return this.obrazok;
    }

    public void setRychlost(int rychlost) {
        this.rychlostPohybu = rychlost;
    }

    public void setSuradnice(int x, int y) {
        this.obrazok.zmenPolohu(x, y);
    }

    public void setPozadovanySmer(Smer smer) {
        this.pozadovanySmer = smer;
    }
    
}
