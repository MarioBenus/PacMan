/**
 * Postava, ktora sa pohybuje na platne -- duch alebo hrac
 */
public class Postava {     
    private final int zaciatocnaPoziciaX;
    private final int zaciatocnaPoziciaY;

    private int rychlostPohybu;
    
    private Smer momentalnySmer;
    private Smer pozadovanySmer;
    private Obrazok obrazok;
    
    // atributy specificke pre ducha
    private boolean jeVBoxe;
    private boolean maOdistZBoxu;
    private final int poziciaBoxuX = 500;
    private final int poziciaBoxuY = -500;
    private final int dlzkaJedlostiDucha = 300; // 300 frameov = 5 sekund
    private int zostatokJedlostiDucha; // ako dlho sa ma duch este dat zjest

    private boolean jeNarazenyOStenu;

    private int pocetTikovAnimacia;

    private TypPostavy typPostavy;

    private Kolizia kolizia;

    /**
     * Konstruktor pre postavu
     * 
     * @param typPostavy enum typu postavy -- hrac alebo duch
     * @param stredX suradnica x bodu, kde sa ma na zaciatku nachadzat stred postavy
     * @param stredY suradnica y bodu, kde sa ma na zaciatku nachadzat stred postavy
     */
    public Postava(TypPostavy typPostavy, int stredX, int stredY) {
        this.zaciatocnaPoziciaX = stredX;
        this.zaciatocnaPoziciaY = stredY;

        this.maOdistZBoxu = false;
        this.jeNarazenyOStenu = false;
        this.typPostavy = typPostavy;
        this.kolizia = Kolizia.getKolizia();
        this.zostatokJedlostiDucha = 0;
        this.jeVBoxe = false;

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

        this.obrazok.zmenPolohu(this.zaciatocnaPoziciaX, this.zaciatocnaPoziciaY);
        this.obrazok.zobraz();

        this.momentalnySmer = Smer.HORE;
        this.pozadovanySmer = Smer.ZIADNY;

        this.rychlostPohybu = 4;

        this.pocetTikovAnimacia = 0;
    }

    /**
     * Update pohybu postavy a jej animacia
     */
    public void tick() {

        if (this.jeVBoxe && this.maOdistZBoxu) {
            this.vyjdenieZBoxu();
        } else if (!this.jeVBoxe) {
            this.koliziaStien();
            this.zmenaSmeru(this.pozadovanySmer);
            this.obrazok.posunVodorovne(this.rychlostPohybu * this.momentalnySmer.getNasobicX());
            this.obrazok.posunZvisle(this.rychlostPohybu * this.momentalnySmer.getNasobicY());
        } 

        this.animacia();
    }

    /**
     * Zabezpecuje, aby postava nebola v stene
     */
    public void koliziaStien() {
        int posun = this.kolizia.pozriKoliziaStena(this.obrazok.getLavyDolnyX(), this.obrazok.getLavyDolnyY(), this.obrazok.getVyska(), this.momentalnySmer);

        // urcuje ci postava je narazena o stenu a nema sa kam dalej pohnut
        if (posun != 0) {
            this.jeNarazenyOStenu = true;
        } else {
            this.jeNarazenyOStenu = false;
        }

        if (this.momentalnySmer == Smer.VPRAVO || this.momentalnySmer == Smer.VLAVO) {
            this.obrazok.posunVodorovne(posun);
        } else if (this.momentalnySmer == Smer.HORE || this.momentalnySmer == Smer.DOLE) {
            this.obrazok.posunZvisle(posun);
        }
    }

    /**
     * Nastavenie smeru, kde sa ma postava otocit pri dalsej zakrute
     * @param pozadovanySmer Smer, kde ma postava ist
     */
    public void zmenaSmeru(Smer pozadovanySmer) {
        if (pozadovanySmer != this.momentalnySmer) { // tieto 2 if-y su samostatne aby sa zbytocne nekontrolovala kolizia ked nemusi
            if (this.kolizia.pozriVolnySmer(this.obrazok.getLavyDolnyX(), this.obrazok.getLavyDolnyY(), this.obrazok.getVyska(), pozadovanySmer)) {

                // posun hraca do volneho smeru
                this.obrazok.posunVodorovne(this.rychlostPohybu * pozadovanySmer.getNasobicX());
                this.obrazok.posunZvisle(this.rychlostPohybu * pozadovanySmer.getNasobicY());     
                // hrac musi byt posunuty este trochu do stareho smeru, aby korekcia nastala vzhladom na spravnu stenu 
                this.obrazok.posunVodorovne(2 * this.rychlostPohybu * this.momentalnySmer.getNasobicX());
                this.obrazok.posunZvisle(2 * this.rychlostPohybu * this.momentalnySmer.getNasobicY());
                
                this.koliziaStien();
                this.jeNarazenyOStenu = false;
    
                this.momentalnySmer = pozadovanySmer;
            }
        }
    }

    /**
     * Vyjdenie z boxu -- pouzivane pre ducha
     */
    private void vyjdenieZBoxu() {
        // posun do stredu boxu
        if (this.obrazok.getLavyDolnyX() < 477) {
            this.obrazok.posunVodorovne((this.rychlostPohybu - 1) * Smer.VPRAVO.getNasobicX());
            this.momentalnySmer = Smer.VPRAVO;
            return;
        }
        if (this.obrazok.getLavyDolnyX() > 479) {
            this.obrazok.posunVodorovne((this.rychlostPohybu - 1) * Smer.VLAVO.getNasobicX());
            this.momentalnySmer = Smer.VLAVO;
            return;
        }

        // vyjdenie z boxu
        this.obrazok.posunZvisle((this.rychlostPohybu - 1) * Smer.HORE.getNasobicY());
        this.momentalnySmer = Smer.HORE;
        if (this.obrazok.getLavyDolnyY() >= -420) {
            this.koliziaStien();
            this.jeVBoxe = false;
        }
    }

    

    /**
     * Animacia postavy
     */
    private void animacia() {
        String cestkaKObrazkuDucha = "Obrazky\\";
        switch (this.typPostavy) {
            case HRAC:
                this.obrazok.zmenUhol(this.momentalnySmer.getUhol());
                switch (this.pocetTikovAnimacia) {
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
                        this.pocetTikovAnimacia = -1;
                        break;
                }
                this.pocetTikovAnimacia++;
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
        if (this.zostatokJedlostiDucha > 0) { // Ak je duch jedly, tak ma samostatny obrazok
            if (this.zostatokJedlostiDucha > 150) {
                cestkaKObrazkuDucha = "Obrazky\\eatableghost-blue-";
            } else if (this.zostatokJedlostiDucha > 120) {
                cestkaKObrazkuDucha = "Obrazky\\eatableghost-white-";
            } else if (this.zostatokJedlostiDucha > 90) {
                cestkaKObrazkuDucha = "Obrazky\\eatableghost-blue-";
            } else if (this.zostatokJedlostiDucha > 60) {
                cestkaKObrazkuDucha = "Obrazky\\eatableghost-white-";
            } else if (this.zostatokJedlostiDucha > 30) {
                cestkaKObrazkuDucha = "Obrazky\\eatableghost-blue-";
            } else {
                cestkaKObrazkuDucha = "Obrazky\\eatableghost-white-";
            }
            this.zostatokJedlostiDucha--;
            
        }
        switch (this.pocetTikovAnimacia) {
            case 0:
                cestkaKObrazkuDucha += "1.png";
                this.obrazok.zmenObrazok(cestkaKObrazkuDucha);
                break;
            case 10:
                cestkaKObrazkuDucha += "2.png";
                this.obrazok.zmenObrazok(cestkaKObrazkuDucha);
                break;
            case 19:
                this.pocetTikovAnimacia = -1;
                break;
        }   
        this.pocetTikovAnimacia++;
    }

    /**
     * @param smer Smer, ktorym sa ma postava hybat
     */
    public void setSmer(Smer smer) {
        this.momentalnySmer = smer;
    }

    /**
     * @return Smer, v ktorom sa momentalne postava hybe
     */
    public Smer getSmer() {
        return this.momentalnySmer;
    }

    /**
     * @return Suradnicu x laveho dolneho rohu postavy
     */
    public int getLavyDolnyX() {
        return this.obrazok.getLavyDolnyX();
    }
    
    /**
     * @return Suradnicu y laveho dolneho rohu postavy
     */
    public int getLavyDolnyY() {
        return this.obrazok.getLavyDolnyY();
    }

    /**
     * @return Rychlost, ktorou sa postava pohybuje
     */
    public int getRychlostPohybu() {
        return this.rychlostPohybu;
    }

    /**
     * @param vzdialenost Vzdialenost, o ktoru sa ma hrac posunut doprava
     */
    public void posunVodorovne(int vzdialenost) {
        this.obrazok.posunVodorovne(vzdialenost);
    }
    
    /**
     * @param vzdialenost Vzdialenost, o ktoru sa ma hrac posunut hore
     */
    public void posunZvisle(int vzdialenost) {
        this.obrazok.posunZvisle(vzdialenost);
    }

    /**
     * @return Velkost obrazku postavy
     */
    public int getVelkostObrazka() {
        return this.obrazok.getVyska();
    }

    /**
     * @param rychlost Rychlost, ktorou sa ma postava pohybovat
     */
    public void setRychlost(int rychlost) {
        this.rychlostPohybu = rychlost;
    }

    /**
     * Nastavuje suradnice x a y stredu postavy
     * @param x Suradnica x stredu postavy
     * @param y Suradnica y stredu postavy
     */
    public void setSuradnice(int x, int y) {
        this.obrazok.zmenPolohu(x, y);
    }

    /**
     * @param smer Smer, ktorym sa postava otocit, ked to bude mozne
     */
    public void setPozadovanySmer(Smer smer) {
        this.pozadovanySmer = smer;
    }

    /**
     * @return True, ak postava je narazena o stenu a nema sa kam dalej pohnut
     */
    public boolean jeNarazenyOStenu() {
        return this.jeNarazenyOStenu;
    }

    /**
     * @param smer Smer, ktory sa ma kontrolovat, ci je volny
     * @return True, ak sa v danom smere nenachadza ziadna stena
     */
    public boolean pozriVolnySmer(Smer smer) {
        return this.kolizia.pozriVolnySmer(this.obrazok.getLavyDolnyX(), this.obrazok.getLavyDolnyY(), this.obrazok.getVyska(), smer);
    }

    /**
     * @return Typ bodky, ktoru postava zjedla
     */
    public TypBodky zjedzBodku() {
        return this.kolizia.zjedzBodku(this.obrazok.getLavyDolnyX(), this.obrazok.getLavyDolnyY());
    }

    /**
     * Zjedenie postavy -- presunutie naspat to boxu a zrusenie jedlosti
     */
    public void zjedenie() {
        this.obrazok.zmenPolohu(this.poziciaBoxuX, this.poziciaBoxuY);
        this.jeVBoxe = true;
        this.zostatokJedlostiDucha = 0;
    }

    /**
     * @return True ak sa da zjest
     */
    public boolean daSaZjest() {
        return (this.zostatokJedlostiDucha > 0);
    }

    /**
     * @return True, ak postava narazila do ducha, ktory sa neda zjest
     */
    public boolean koliziaSDuchom() {
        return this.kolizia.koliziaSDuchom(this.obrazok.getLavyDolnyX(), this.obrazok.getLavyDolnyY(), this.obrazok.getVyska());
    }

    /**
     * Nastavi ci sa da duch dat zjest a otoci o 180 stupnov
     * @param moznost Moznost ci sa ma postava dat zjest alebo nie
     */
    public void setDaSaZjest(boolean moznost) {
        if (moznost) {
            this.zostatokJedlostiDucha = this.dlzkaJedlostiDucha;

            // otocenie ducha prec od hraca
            if (this.momentalnySmer == Smer.VPRAVO) {
                this.momentalnySmer = Smer.VLAVO;
            } else if (this.momentalnySmer == Smer.VLAVO) {
                this.momentalnySmer = Smer.VPRAVO;
            } else if (this.momentalnySmer == Smer.HORE) {
                this.momentalnySmer = Smer.DOLE;
            } else if (this.momentalnySmer == Smer.DOLE) {
                this.momentalnySmer = Smer.HORE;
            }
        }
    }

    /**
     * @return Typ Postavy
     */
    public TypPostavy getTypPostavy() {
        return this.typPostavy;
    }

    /**
     * Presunie postavu na jej zaciatocnu poziciu
     */
    public void presunNaZaciatocnuPoziciu() {
        this.obrazok.zmenPolohu(this.zaciatocnaPoziciaX, this.zaciatocnaPoziciaY);
        this.momentalnySmer = Smer.HORE;
        this.zostatokJedlostiDucha = -1;
        if (!this.jeVBoxe) {
            this.koliziaStien();
        }
    }

    /**
     * @param moznost Moznost, ci sa duch nachadza v boxe alebo nie
     */
    public void setJeVBoxe(boolean moznost) {
        this.jeVBoxe = moznost;
    }

    /**
     * Nastavuje ci ma duch zostat v boxe alebo odist
     * @param moznost True -- ma odist z boxu, False -- ma tam zostat
     */
    public void setMaOdistZBoxu(boolean moznost) {
        this.maOdistZBoxu = moznost;
    }
    
}
