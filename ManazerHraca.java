import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class ManazerHraca extends KeyAdapter {
    private Hrac hrac;
    private Kolizia kolizia;
    private Smer bufferedSmer;
    private Smer tempSmer;

    public ManazerHraca(Hrac hrac, Kolizia kolizia) {
        Platno.dajPlatno().addKeyListener(this);
        this.hrac = hrac;
        this.hrac.setSmer(Smer.HORE);
        this.kolizia = kolizia;
        this.bufferedSmer = Smer.ZIADNY;
        this.tempSmer = Smer.HORE;

    }

    public void tick() {
        this.korekcia();

        this.bufferedSmer = this.tempSmer;
        if (this.kolizia.checkVolnySmer(this.hrac.getX(), this.hrac.getY(), this.bufferedSmer) &&
            this.bufferedSmer != this.hrac.getSmer()) {

            // posun hraca do volneho smeru
            this.hrac.posunVodorovne(this.hrac.getRychlostPohybu() * this.bufferedSmer.getNasobicX());
            this.hrac.posunZvisle(this.hrac.getRychlostPohybu() * this.bufferedSmer.getNasobicY());     
            // hrac musi byt posunuty este trochu do stareho smeru, aby korekcia nastala vzhladom na spravnu stenu 
            this.hrac.posunVodorovne(this.hrac.getRychlostPohybu() * this.hrac.getSmer().getNasobicX());
            this.hrac.posunZvisle(this.hrac.getRychlostPohybu() * this.hrac.getSmer().getNasobicY());
            this.korekcia();

            this.hrac.setSmer(this.bufferedSmer);

        }
        
    }

    // udrziavanie hraca mimo stien -- ked sa hrac dostane do steny, tak ho to posunie z nej von
    public void korekcia() {
        int korekcia = this.kolizia.checkKoliziaStena(this.hrac.getX(), this.hrac.getY(), this.hrac.getSmer());
        if (this.hrac.getSmer() == Smer.VPRAVO || this.hrac.getSmer() == Smer.VLAVO) {
            this.hrac.posunVodorovne(korekcia);
        } else if (this.hrac.getSmer() == Smer.HORE || this.hrac.getSmer() == Smer.DOLE) {
            this.hrac.posunZvisle(korekcia);
        }
    }

    public void keyPressed(KeyEvent event) {

        // nastavuje bufferedSmer pohybu hraca na smer poslednej stlacenej sipky
        
        /* 
         * hodnota sa najskor uchovava do tempSmeru a potom sa v ticku
         * nastavuje na buffferedSmer, aby zmena bufferedSmeru nenastala pocas vykonavania
         * nejakej inej casti kodu
         */
        if (event.getKeyCode() == KeyEvent.VK_DOWN) {
            this.tempSmer = Smer.DOLE;

        } else if (event.getKeyCode() == KeyEvent.VK_UP) {
            this.tempSmer = Smer.HORE;

        } else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            this.tempSmer = Smer.VLAVO;

        } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.tempSmer = Smer.VPRAVO;

        } else if (event.getKeyCode() == KeyEvent.VK_SPACE) { // ODTIALTO NIZSIE JE DEBUG
            this.tempSmer = Smer.ZIADNY;
        }

    }
}
