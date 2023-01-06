
// "najvyssia" trieda, ktora vsetko vytvara a spravuje
public class Hra {
    private static final double TICK_DLZKA = 1000000000 / 60; // 60 FPS

    private ManazerHraca manazerHraca;
    private ManazerDuchov manazerDuchov;
    
    private Obrazok pozadie;
    private Kolizia kolizia;
    
    public Hra() throws InterruptedException {
        this.kolizia = Kolizia.dajKoliziu();
        this.manazerHraca = new ManazerHraca();
        this.manazerDuchov = new ManazerDuchov(this.kolizia, this.manazerHraca.getPostavaHrac());

        double dalsiTick = System.nanoTime();

        // game loop
        while (true) {
            double testDeltaTime = System.nanoTime();

            this.manazerHraca.tick();
            this.manazerDuchov.tick();
            this.kolizia.animaciaBodiek();
            if (this.kolizia.getPocetZostavajucichBodiek() == 0) {
                // TODO: dalsi level
                this.kolizia.reloadBodky();
            }
            
            
            Platno.dajPlatno().redraw();
            dalsiTick += TICK_DLZKA;

            while (System.nanoTime() < dalsiTick) {
                //Thread.sleep(0, 500000);
            }
            
            // repaint je za sleepom aby sa vykonával v čo najpravidelnejšom intervale
            Platno.dajPlatno().repaint();

            //System.out.println((System.nanoTime() - testDeltaTime) / 1000000);

        }
         
    }


    
}
