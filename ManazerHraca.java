import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

// stara sa o zmenu stavu hraca -- zmena smeru podla stlacenej klavesy a udrziavania ho mimo stien
// a taktiez udrziavanie skore, zivotov a updateovania UI
public class ManazerHraca extends KeyAdapter {
    private Postava hrac;
    private Smer pozadovanySmer;
    private Smer tempSmer;
    private int najvyssieSkore;
    private int skore;
    private int zivoty;

    private UI ui;

    public ManazerHraca() {
        Platno.dajPlatno().addKeyListener(this);
        this.hrac = new Postava(TypPostavy.HRAC, 500, -700);
        this.hrac.setSmer(Smer.HORE);
        this.pozadovanySmer = Smer.ZIADNY;
        this.tempSmer = Smer.HORE;
        this.ui = new UI();
        this.zivoty = 3;
        this.skore = 0;

        try {
            Scanner scanner = new Scanner(new File("highscore.level"));
            this.najvyssieSkore = scanner.nextInt();
            scanner.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        

        this.ui.setHighScore(this.najvyssieSkore);
    }

    public void tick() {
        this.hrac.tick();

        this.ui.animacia();

        this.hrac.setPozadovanySmer(this.tempSmer);
        
        // zjedenie bodiek a logika ich zjedenia
        switch (this.hrac.zjedzBodku()) {
            case VELKA_BODKA:
                // TODO: pridanie logiky zjedenia velkej bodky
                this.skore += 50;
                this.ui.setCurrentScore(this.skore);
                break;
            case MALA_BODKA:
                this.skore += 10;
                this.ui.setCurrentScore(this.skore);
                break;
            default:
                break;
        }
        
    }

    public int[] getSuradniceHraca() {
        return new int[] {this.hrac.getX(), this.hrac.getY()};
    }

    public Postava getPostavaHrac() {
        return this.hrac;
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
