
/**
 * "Najvyssia" trieda ktora spravuje a vytvara vsetko
 */
public class Hra {
    private static final double TICK_DLZKA = 1000000000 / 60; // 60 FPS

    private ManazerHraca manazerHraca;
    private ManazerDuchov manazerDuchov;
    
    private Kolizia kolizia;

    private int animaciaPocetTickov;
    private Bodka[] velkeBodky;
    
    /**
     * Konstruktor
     */
    public Hra() {
        this.kolizia = Kolizia.getKolizia();
        this.manazerHraca = new ManazerHraca();
        this.manazerDuchov = new ManazerDuchov(this.kolizia, this.manazerHraca.getPostavaHrac());    
        this.animaciaPocetTickov = 0;
        this.velkeBodky = this.kolizia.getVelkeBodky();
        this.kolizia.setManazerDuchov(this.manazerDuchov);
        this.kolizia.setDuchovia(this.manazerDuchov.getDuchov());

        // Zobrazenie vsetkeho a pockanie 2 sekund
        this.manazerHraca.reloadHrac();
        this.manazerDuchov.resetDuchov();
        Platno.dajPlatno().redraw();
        Platno.dajPlatno().repaint();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Zacne hru
     */
    public void start() {
        double dalsiTick = System.nanoTime();

        // game loop
        while (true) {

            this.manazerHraca.tick();
            this.manazerDuchov.tick();
            this.animaciaBodiek();
           
            Platno.dajPlatno().redraw();
            dalsiTick += TICK_DLZKA;

            long momentalnyCas;
            do {
                momentalnyCas = System.nanoTime();
            } while (momentalnyCas < dalsiTick);

            
            // repaint je za sleepom aby sa vykonával v čo najpravidelnejšom intervale
            Platno.dajPlatno().repaint();

            // zomretie hraca
            if (this.manazerHraca.jeHracMrtvy()) {
                try {
                    Thread.sleep(2000);
                    this.manazerHraca.reloadHrac();
                    this.manazerDuchov.resetDuchov();
                    Platno.dajPlatno().redraw();
                    Platno.dajPlatno().repaint();
                    Thread.sleep(2000);
                    dalsiTick = System.nanoTime() + TICK_DLZKA;
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            // minutie vsetkych zivotov hraca
            if (this.manazerHraca.jeKoniecHry()) {
                try {
                    Thread.sleep(10000);
                    System.exit(0);
                    return;
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            // obnovenie levelu
            if (this.kolizia.getPocetZostavajucichBodiek() == 0) {
                try {
                    this.manazerHraca.reloadHrac();
                    this.manazerDuchov.resetDuchov();
                    this.kolizia.obnovBodky();
                    Thread.sleep(2000);
                    dalsiTick = System.nanoTime() + TICK_DLZKA;
                } catch (Exception e) {
                    System.out.println(e);
                }
                
            }

        }
    }

    /**
     * Blikanie velkych bodiek
     */
    private void animaciaBodiek() {
        switch (this.animaciaPocetTickov) {
            case 10:
                for (Bodka bodka : this.velkeBodky) {
                    if (!bodka.jeZjedena()) {
                        bodka.skry();
                    }
                }
                break;
            case 20:
                for (Bodka bodka : this.velkeBodky) {
                    if (!bodka.jeZjedena()) {
                        bodka.zobraz();
                    }
                }
                this.animaciaPocetTickov = -1;
                break;
        }
        this.animaciaPocetTickov++;
    }


    
}
