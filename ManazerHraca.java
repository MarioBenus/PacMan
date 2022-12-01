import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class ManazerHraca extends KeyAdapter {
    private Smer smer;
    private Hrac hrac;
    private Kolizia kolizia;
    private Smer bufferedSmer;
    private Smer tempSmer;

    public ManazerHraca(Hrac hrac, Kolizia kolizia) {
        Platno.dajPlatno().addKeyListener(this);
        this.hrac = hrac;
        this.smer = Smer.VPRAVO;
        this.hrac.zmenSmer(this.smer);
        this.kolizia = kolizia;
        this.bufferedSmer = Smer.ZIADNY;
        this.tempSmer = Smer.ZIADNY;

    }

    public void tick() {
        int korekcia = this.kolizia.checkKoliziaStena(this.hrac.getX(), this.hrac.getY(), this.smer);
        if (this.smer == Smer.VPRAVO || this.smer == Smer.VLAVO) {
            this.hrac.posunVodorovne(korekcia);
        } else if (this.smer == Smer.HORE || this.smer == Smer.DOLE) {
            this.hrac.posunZvisle(korekcia);
        }

        this.smer = this.tempSmer;
        this.hrac.zmenSmer(this.smer);
        
    }

    public void keyPressed(KeyEvent event) {

        // nastavuje smer pohybu hraca na smer poslednej stlacenej sipky
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
