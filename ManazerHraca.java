import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

/**
 * stara sa o zmenu stavu hraca -- zmena smeru podla stlacenej klavesy
 * a taktiez udrziavanie skore, zivotov a updateovania UI
 */
public class ManazerHraca extends KeyAdapter {
    private Postava hrac;
    private Smer tempSmer;
    private int zivoty;
    private boolean koniecHry;
    private boolean smrt;
    private int pocetPridanychZivotov;

    private final int zaciatocneSuradniceHracaX = 500;
    private final int zaciatocneSuradniceHracaY = -700;


    private UI ui;

    /**
     * Konstruktor
     */
    public ManazerHraca() {
        this.pocetPridanychZivotov = 0;
        this.smrt = false;
        Platno.dajPlatno().addKeyListener(this);
        this.hrac = new Postava(TypPostavy.HRAC, this.zaciatocneSuradniceHracaX, this.zaciatocneSuradniceHracaY);
        this.hrac.setSmer(Smer.HORE);
        this.tempSmer = Smer.HORE;
        this.ui = UI.getUI();
        this.zivoty = 3;
        this.koniecHry = false;
        this.ui.setPocetZostavajucichZivotov(this.zivoty - 1);
    }

    /**
     * Kontrolovanie a manazovanie stavu hraca a UI
     */
    public void tick() {
        this.hrac.tick();

        this.ui.animacia();

        this.hrac.setPozadovanySmer(this.tempSmer);
        
        // zjedenie bodiek a logika ich zjedenia
        switch (this.hrac.zjedzBodku()) {
            case VELKA_BODKA:
                this.ui.pridajSkore(50);
                break;
            case MALA_BODKA:
                this.ui.pridajSkore(10);
                break;
            default:
                break;
        }

        if (this.hrac.koliziaSDuchom()) {
            this.zivoty--;
            if (this.zivoty == 0) {
                // Koniec hry
                this.ui.ulozSkore();
                this.ui.zobrazGameOver();
                this.koniecHry = true;
            } else {
                // Stratenie zivota
                this.smrt = true;
                this.ui.setPocetZostavajucichZivotov(this.zivoty - 1);
            }
        }

        // Pridava zivot ked hrac ziska 10 000 skore
        if (this.ui.getMomentalneScore() / 10000 > this.pocetPridanychZivotov) {
            this.zivoty++;
            this.ui.setPocetZostavajucichZivotov(this.zivoty - 1);
            this.pocetPridanychZivotov = this.ui.getMomentalneScore() / 10000;
        }
        
    }

    /**
     * @return Referencia na hraca
     */
    public Postava getPostavaHrac() {
        return this.hrac;
    }

    /**
     * @return True, ak hrac nema ziadne dalsie zivoty
     */
    public boolean jeKoniecHry() {
        return this.koniecHry;
    }

    /**
     * Obnovi hraca na zaciatocnu poziciu
     */
    public void reloadHrac() {
        this.smrt = false;
        this.hrac.presunNaZaciatocnuPoziciu();
    }

    /**
     * @return True, ak hrac zomrel
     */
    public boolean jeHracMrtvy() {
        return this.smrt;
    }

    /**
     * Kontroluje stlacene klavesy uzivatela
     * Princip tejto metody je prevzaty z triedy Manazer z TvaryV3
     */
    public void keyPressed(KeyEvent event) {

        // nastavuje pozadovanySmer pohybu hraca na smer poslednej stlacenej sipky
        
        /* 
         * hodnota sa najskor uchovava do tempSmeru a potom sa v ticku
         * nastavuje na buffferedSmer, aby zmena u pozadovanySmer nenastala pocas vykonavania
         * nejakej inej casti kodu
         */
        if (event.getKeyCode() == KeyEvent.VK_DOWN || event.getKeyCode() == KeyEvent.VK_S) {
            this.tempSmer = Smer.DOLE;

        } else if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_W) {
            this.tempSmer = Smer.HORE;

        } else if (event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyCode() == KeyEvent.VK_A) {
            this.tempSmer = Smer.VLAVO;

        } else if (event.getKeyCode() == KeyEvent.VK_RIGHT || event.getKeyCode() == KeyEvent.VK_D) {
            this.tempSmer = Smer.VPRAVO;

        } 

    }
}
