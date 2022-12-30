
// bodky, ktore hrac jedie a pridavaju mu skore
public class Bodka {
    private int lavyDolnyX;
    private int lavyDolnyY;

    public static final int VELKOST = 7;
    public static final double VZDIALENOST_MEDZI_BODKAMI = 25.5;

    private Obdlznik stvorec;
    
    public Bodka(int lavyDolnyX, int lavyDolnyY) {
        this.lavyDolnyX = lavyDolnyX;
        this.lavyDolnyY = lavyDolnyY;

        this.stvorec = new Obdlznik();
        this.stvorec.nastavSuradnice(this.lavyDolnyX, this.lavyDolnyY);
        this.stvorec.zmenStrany(VELKOST, VELKOST);
        this.stvorec.zmenFarbu("white");
        this.stvorec.zobraz();
    }

    public void skryBodku() {
        this.stvorec.skry();
    }

}
