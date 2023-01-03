
// bodky, ktore hrac jedie a pridavaju mu skore
public class Bodka {
    private int lavyDolnyX;
    private int lavyDolnyY;
    private boolean jeZjedena;

    private TypBodky typBodky;

    public static final int VELKOST = 7;
    public static final double VZDIALENOST_MEDZI_BODKAMI = 25.5;

    private Obdlznik stvorec;
    private Obrazok obrazok;
    
    public Bodka(int lavyDolnyX, int lavyDolnyY, TypBodky typBodky) {
        this.lavyDolnyX = lavyDolnyX;
        this.lavyDolnyY = lavyDolnyY;
        this.typBodky = typBodky;
        this.jeZjedena = false;

        if (this.typBodky == TypBodky.MALA_BODKA) {
            this.stvorec = new Obdlznik();
            this.stvorec.nastavSuradnice(this.lavyDolnyX, this.lavyDolnyY);
            this.stvorec.zmenStrany(VELKOST, VELKOST);
            this.stvorec.zmenFarbu("white");
            this.stvorec.zobraz();
        } else {
            this.obrazok = new Obrazok("Obrazky\\big-dot.png");
            
            // vycentrovanie
            this.lavyDolnyX += 3;
            this.lavyDolnyY += 4;

            this.obrazok.zmenPolohu(this.lavyDolnyX, this.lavyDolnyY);
            this.obrazok.zobraz();
        }
    }

    public void zjedz() {
        if (this.typBodky == TypBodky.MALA_BODKA) {
            this.stvorec.skry();
        } else {
            this.obrazok.skry();
        }
        this.jeZjedena = true;
    }

    
    public boolean jeZjedena() {
        return this.jeZjedena;    
    }

    public void obnov() {
        if (this.typBodky == TypBodky.MALA_BODKA) {
            this.stvorec.zobraz();
        } else {
            this.obrazok.zobraz();
        }
        this.jeZjedena = false;
    }
    
    public void skry() {
        if (this.typBodky == TypBodky.MALA_BODKA) {
            this.stvorec.skry();
        } else {
            this.obrazok.skry();
        }
    }

    public void zobraz() {
        if (this.typBodky == TypBodky.MALA_BODKA) {
            this.stvorec.zobraz();
        } else {
            this.obrazok.zobraz();
        }
    }
}
