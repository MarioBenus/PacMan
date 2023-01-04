import java.io.IOException;


// "najvyssia" trieda, ktora vsetko vytvara a spravuje
public class Hra {
    private static final double TICK_DLZKA = 1000000000 / 60;

    private ManazerHraca manazerHraca;
    private Hrac hrac;
    private Kolizia kolizia;
    
    private Obrazok pozadie;

    
    public Hra() throws IOException, InterruptedException {
        this.pozadie = new Obrazok("Obrazky\\level.png");
        this.pozadie.zmenPolohu(Platno.dajPlatno().getSirka() / 2, -Platno.dajPlatno().getVyska() / 2); // posun do stredu
        this.pozadie.zobraz();
        this.kolizia = new Kolizia(this.pozadie);
        this.hrac = new Hrac();
        this.manazerHraca = new ManazerHraca(this.hrac, this.kolizia);

        double dalsiTick = System.nanoTime();

        // game loop
        while (true) {
            

            this.hrac.tick();
            this.manazerHraca.tick(); // ManazerHraca sa musi vykonavat az za Hracom
            this.kolizia.animaciaBodiek();
            if (this.kolizia.getPocetZostavajucichBodiek() == 0) {
                // TODO: dalsi level
                this.kolizia.reloadBodky();
            }
            
            Platno.dajPlatno().redraw();
            dalsiTick += TICK_DLZKA;

            while (System.nanoTime() < dalsiTick) {
                Thread.sleep(1);
            }
            
            // repaint je za sleepom aby sa vykonával v čo najpravidelnejšom intervale
            Platno.dajPlatno().repaint();

        }
         
    }


    
}
