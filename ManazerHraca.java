import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

// stara sa o zmenu stavu hraca -- zmena smeru podla stlacenej klavesy a udrziavania ho mimo stien
// a taktiez udrziavanie skore, zivotov a updateovania UI
public class ManazerHraca extends KeyAdapter {
    private Hrac hrac;
    private Kolizia kolizia;
    private Smer pozadovanySmer;
    private Smer tempSmer;
    private final int najvyssieSkore;
    private int skore;
    private int zivoty;

    private UI ui;

    public ManazerHraca(Hrac hrac, Kolizia kolizia) throws IOException {
        Platno.dajPlatno().addKeyListener(this);
        this.hrac = hrac;
        this.hrac.setSmer(Smer.HORE);
        this.kolizia = kolizia;
        this.pozadovanySmer = Smer.ZIADNY;
        this.tempSmer = Smer.HORE;
        this.ui = new UI();
        this.zivoty = 3;
        this.skore = 0;

        Scanner scanner = new Scanner(new File("highscore.level"));
        this.najvyssieSkore = scanner.nextInt();
        scanner.close();

        this.ui.setHighScore(this.najvyssieSkore);
    }

    public void tick() {
        this.ui.animacia();


        this.koliziaStien();

        // zmena smeru hraca
        this.pozadovanySmer = this.tempSmer;
        if (this.pozadovanySmer != this.hrac.getSmer()) { // tieto 2 if-y su samostatne aby sa zbytocne nekontrolovala kolizia ked nemusi
            if (this.kolizia.checkVolnySmer(this.hrac.getX(), this.hrac.getY(), this.pozadovanySmer)) {

                // posun hraca do volneho smeru
                this.hrac.posunVodorovne(this.hrac.getRychlostPohybu() * this.pozadovanySmer.getNasobicX());
                this.hrac.posunZvisle(this.hrac.getRychlostPohybu() * this.pozadovanySmer.getNasobicY());     
                // hrac musi byt posunuty este trochu do stareho smeru, aby korekcia nastala vzhladom na spravnu stenu 
                this.hrac.posunVodorovne(this.hrac.getRychlostPohybu() * this.hrac.getSmer().getNasobicX());
                this.hrac.posunZvisle(this.hrac.getRychlostPohybu() * this.hrac.getSmer().getNasobicY());
                this.koliziaStien();
    
                this.hrac.setSmer(this.pozadovanySmer);    
            }
        }
        
        // zjedenie bodiek a logika ich zjedenia
        switch (this.kolizia.zjedzBodku(this.hrac.getX(), this.hrac.getY())) {
            case VELKA_BODKA:
                // TODO: pridanie logiky zjedenia velkej bodky
                this.skore += 50;
                this.ui.setCurrentScore(skore);
                break;
            case MALA_BODKA:
                this.skore += 10;
                this.ui.setCurrentScore(skore);
                break;
        }
        
    }

    // udrziavanie hraca mimo stien -- ked sa hrac dostane do steny, tak ho to posunie z nej von
    public void koliziaStien() {
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
