import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/**
 * Write a description of class GameManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hra {
    private static final double TICK_DLZKA = 1000000000 / 60;

    private ManazerHraca manazerHraca;
    private Hrac hrac;
    private Kolizia kolizia;
    
    private Obrazok pozadie;

    private Stena[] steny;

    
    public Hra() throws Exception {
        this.pozadie = new Obrazok("Obrazky\\level.png");
        this.pozadie.zmenPolohu(Platno.dajPlatno().getSirka() / 2, -Platno.dajPlatno().getVyska() / 2); // posun do stredu
        this.pozadie.zobraz();
        this.nacitajLevel();
        this.kolizia = new Kolizia(this.steny);
        this.hrac = new Hrac();
        this.manazerHraca = new ManazerHraca(this.hrac, this.kolizia);

        double dalsiTick = System.nanoTime();

        /*Text text = new Text("Hello World", "Arial", 50, "white", 100, 100);
        text.zobraz();*/


        while (true) {
            

            this.hrac.tick();
            this.manazerHraca.tick();
            Platno.dajPlatno().redraw();

            dalsiTick += TICK_DLZKA;

            while (System.nanoTime() < dalsiTick) {
                Thread.sleep(1);
            }
            
            Platno.dajPlatno().repaint();

        }
         
    }

    public void nacitajLevel() throws IOException {
        int offsetX = this.pozadie.getLavyDolnyX();
        int offsetY = this.pozadie.getLavyDolnyY();


        Scanner scanner = new Scanner(new File("level.level"));
        scanner.nextLine();
        this.steny = new Stena[scanner.nextInt()];
        scanner.nextLine();
        for (int i = 0; i < this.steny.length; i++) {
            this.steny[i] = new Stena(offsetX + scanner.nextInt(), offsetY + scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
            scanner.nextLine();
        }
        scanner.close();
    }

}
