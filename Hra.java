import java.util.Scanner;
import java.io.File;
import java.io.IOException;


// "najvyssia" trieda, ktora vsetko vytvara a spravuje
public class Hra {
    private static final double TICK_DLZKA = 1000000000 / 60;

    private ManazerHraca manazerHraca;
    private Hrac hrac;
    private Kolizia kolizia;
    
    private Obrazok pozadie;

    private Stena[] steny;
    // 
    private Bodka[][] bodky;

    private final double velkostMedziBodkami = 25.5;

    
    public Hra() throws IOException, InterruptedException {
        this.bodky = new Bodka[29][26]; 
        this.pozadie = new Obrazok("Obrazky\\level.png");
        this.pozadie.zmenPolohu(Platno.dajPlatno().getSirka() / 2, -Platno.dajPlatno().getVyska() / 2); // posun do stredu
        this.pozadie.zobraz();
        this.nacitajLevel();
        this.kolizia = new Kolizia(this.steny, this.pozadie, this.bodky);
        this.hrac = new Hrac();
        this.manazerHraca = new ManazerHraca(this.hrac, this.kolizia);

        double dalsiTick = System.nanoTime();

        /*Text text = new Text("Hello World", "Arial", 50, "white", 100, -100);
        text.zobraz();*/

        // game loop
        while (true) {
            

            this.hrac.tick();
            this.manazerHraca.tick(); // ManazerHraca sa musi vykonavat az za Hracom
            Platno.dajPlatno().redraw();

            dalsiTick += TICK_DLZKA;

            while (System.nanoTime() < dalsiTick) {
                Thread.sleep(1);
            }
            
            // repaint je za sleepom aby sa vykonával v čo najpravidelnejšom intervale
            Platno.dajPlatno().repaint();

        }
         
    }

    public void nacitajLevel() throws IOException {
        int offsetX = this.pozadie.getLavyDolnyX();
        int offsetY = this.pozadie.getLavyDolnyY();

        // nacitavanie pozicii a velkosti stien zo suboru level.level
        // suradnice v tomto subore su relativne k lavemu dolnemu rohu pozadia
        Scanner scanner = new Scanner(new File("level.level"));
        scanner.nextLine(); // prvy riadok v level.level je len vysvetlenie syntaxe
        this.steny = new Stena[scanner.nextInt()];
        scanner.nextLine();
        for (int i = 0; i < this.steny.length; i++) {
            this.steny[i] = new Stena(offsetX + scanner.nextInt(), offsetY + scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
            scanner.nextLine();
        }
        scanner.close();

        this.nacitajBodky();

        
    }

    public void nacitajBodky() throws IOException {
        
        Scanner scanner = new Scanner(new File("bodky.level"));

        // oznacuje x a y suradnice bodky v 0-tom riadku a 0-tom stlpci
        // 39 = sirka krajnej steny + polovica sirky priestoru pre hraca (14 + 25)
        double offsetX = this.pozadie.getLavyDolnyX() + 39 - Bodka.VELKOST / 2 - 1;
        double offsetY = this.pozadie.getLavyDolnyY() + 39 - Bodka.VELKOST / 2;

        scanner.nextLine(); // preskocenie radku kde sa vysvetluje syntax
        while (scanner.hasNextLine()) {
            double riadok = scanner.nextInt();
            double stlpec = scanner.nextInt();
            this.bodky[(int)riadok][(int)stlpec] = new Bodka((int)(offsetX + stlpec * this.velkostMedziBodkami), (int)(offsetY + riadok * this.velkostMedziBodkami));
        }
    }
}
