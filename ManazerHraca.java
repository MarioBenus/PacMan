import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

// stara sa o zmenu stavu hraca -- zmena smeru podla stlacenej klavesy a udrziavania ho mimo stien
public class ManazerHraca extends KeyAdapter {
    private Hrac hrac;
    private Kolizia kolizia;
    private Smer pozadovanySmer;
    private Smer tempSmer;

    public ManazerHraca(Hrac hrac, Kolizia kolizia) {
        Platno.dajPlatno().addKeyListener(this);
        this.hrac = hrac;
        this.hrac.setSmer(Smer.HORE);
        this.kolizia = kolizia;
        this.pozadovanySmer = Smer.ZIADNY;
        this.tempSmer = Smer.HORE;

    }

    public void tick() {
        this.korekcia();

        this.pozadovanySmer = this.tempSmer;
        if (this.pozadovanySmer != this.hrac.getSmer()) { // tieto 2 if-y su samostatne aby sa zbytocne nekontrolovala kolizia ked nemusi
            if (this.kolizia.checkVolnySmer(this.hrac.getX(), this.hrac.getY(), this.pozadovanySmer)) {

                // posun hraca do volneho smeru
                this.hrac.posunVodorovne(this.hrac.getRychlostPohybu() * this.pozadovanySmer.getNasobicX());
                this.hrac.posunZvisle(this.hrac.getRychlostPohybu() * this.pozadovanySmer.getNasobicY());     
                // hrac musi byt posunuty este trochu do stareho smeru, aby korekcia nastala vzhladom na spravnu stenu 
                this.hrac.posunVodorovne(this.hrac.getRychlostPohybu() * this.hrac.getSmer().getNasobicX());
                this.hrac.posunZvisle(this.hrac.getRychlostPohybu() * this.hrac.getSmer().getNasobicY());
                this.korekcia();
    
                this.hrac.setSmer(this.pozadovanySmer);    
            }
        }
        

        this.kolizia.zjedzBodku(this.hrac.getX(), this.hrac.getY());
        
    }

    // udrziavanie hraca mimo stien -- ked sa hrac dostane do steny, tak ho to posunie z nej von
    // TODO: omg tento nazov
    public void korekcia() {
        int korekcia = this.kolizia.checkKoliziaStena(this.hrac.getX(), this.hrac.getY(), this.hrac.getSmer());
        if (this.hrac.getSmer() == Smer.VPRAVO || this.hrac.getSmer() == Smer.VLAVO) {
            this.hrac.posunVodorovne(korekcia);
        } else if (this.hrac.getSmer() == Smer.HORE || this.hrac.getSmer() == Smer.DOLE) {
            this.hrac.posunZvisle(korekcia);
        }
    }

    // princip tejto metody je prevzaty z triedy Manazer z TvaryV3
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
