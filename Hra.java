

/**
 * Write a description of class GameManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hra {
    private static final double TICK_DLZKA = 1000000000 / 60;

    private Hrac hrac;
    
    private Obrazok pozadie;

    
    public Hra() throws Exception {

        this.hrac = new Hrac();
        this.pozadie = new Obrazok("Obrazky\\level.png");
        this.pozadie.zmenPolohu(500, 500);
        this.pozadie.zobraz();


        double deltaCas = 16666666;
        double helpDeltaCas = System.nanoTime();

        double dalsiTick = System.nanoTime();


        while (true) {
            

            this.hrac.tick(deltaCas);
            Platno.dajPlatno().redraw();

            dalsiTick += TICK_DLZKA;

            while (System.nanoTime() < dalsiTick) {
                Thread.sleep(1);
            }
            
            deltaCas = System.nanoTime() - helpDeltaCas;
            helpDeltaCas = System.nanoTime();
            
            Platno.dajPlatno().repaint();

            



        }

         
    }

}
