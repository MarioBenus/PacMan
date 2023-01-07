
// postava, ktoru hrac ovlada
public class Postava {
    private int rychlostPohybu;
    
    private Smer momentalnySmer;
    private Smer pozadovanySmer;
    private Obrazok obrazok;

    // TODO: premenovat
    private boolean jeNarazenaOStenu;

    private int pocetTikov;

    private TypPostavy typPostavy;

    private Kolizia kolizia;


    public Postava(TypPostavy typPostavy, int stredX, int stredY) {
        this.jeNarazenaOStenu = false;
        this.typPostavy = typPostavy;
        this.kolizia = Kolizia.dajKoliziu();

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
        this.koliziaStien();
        this.zmenaSmeru(this.pozadovanySmer);

        this.animacia();

        
        this.obrazok.posunVodorovne(this.rychlostPohybu * this.momentalnySmer.getNasobicX());
        this.obrazok.posunZvisle(this.rychlostPohybu * this.momentalnySmer.getNasobicY());
        

    }

    // udrziavanie hraca mimo stien -- ked sa hrac dostane do steny, tak ho to posunie z nej von
    public void koliziaStien() {
        int korekcia = this.kolizia.checkKoliziaStena(this.obrazok.getLavyDolnyX(), this.obrazok.getLavyDolnyY(), this.obrazok.vyska(), this.momentalnySmer);
        if (korekcia != 0) {
            this.jeNarazenaOStenu = true;
        } else {
            this.jeNarazenaOStenu = false;
        }
        if (this.momentalnySmer == Smer.VPRAVO || this.momentalnySmer == Smer.VLAVO) {
            this.obrazok.posunVodorovne(korekcia);
        } else if (this.momentalnySmer == Smer.HORE || this.momentalnySmer == Smer.DOLE) {
            this.obrazok.posunZvisle(korekcia);
        }
    }

    public void zmenaSmeru(Smer pozadovanySmer) {
        if (pozadovanySmer != this.momentalnySmer) { // tieto 2 if-y su samostatne aby sa zbytocne nekontrolovala kolizia ked nemusi
            if (this.kolizia.checkVolnySmer(this.obrazok.getLavyDolnyX(), this.obrazok.getLavyDolnyY(), this.obrazok.vyska(), pozadovanySmer)) {

                // posun hraca do volneho smeru
                this.obrazok.posunVodorovne(this.rychlostPohybu * pozadovanySmer.getNasobicX());
                this.obrazok.posunZvisle(this.rychlostPohybu * pozadovanySmer.getNasobicY());     
                // hrac musi byt posunuty este trochu do stareho smeru, aby korekcia nastala vzhladom na spravnu stenu 
                this.obrazok.posunVodorovne(2 * this.rychlostPohybu * this.momentalnySmer.getNasobicX());
                this.obrazok.posunZvisle(2 * this.rychlostPohybu * this.momentalnySmer.getNasobicY());
                
                this.koliziaStien();
                this.jeNarazenaOStenu = false;
    
                this.momentalnySmer = pozadovanySmer;
            }
        }
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
            default:
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

    // TODO: tiez premenovat
    public boolean jeNarazenaOStenu() {
        return this.jeNarazenaOStenu;
    }

    public boolean checkVolnySmer(Smer smer) {
        return this.kolizia.checkVolnySmer(this.obrazok.getLavyDolnyX(), this.obrazok.getLavyDolnyY(), this.obrazok.vyska(), smer);
    }

    public TypBodky zjedzBodku() {
        return this.kolizia.zjedzBodku(this.obrazok.getLavyDolnyX(), this.obrazok.getLavyDolnyY());
    }
    
}
