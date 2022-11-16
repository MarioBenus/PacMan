/*import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;*/

/**
 * Write a description of class GameManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hra {
    private static final double TICK_DLZKA = 16666666;
    /*private long staryTick;
    private long staryStaryTick;*/

    private Hrac hrac;
    
    private Obrazok pozadie;
    
    public Hra() throws Exception {
        //Platno.dajPlatno().addTimerListener(new ManazerCasovaca());

        this.hrac = new Hrac();
        this.pozadie = new Obrazok("Obrazky\\level.png");
        this.pozadie.zmenPolohu(500, 500);
        this.pozadie.zobraz();

        /*this.staryTick = 0;
        this.staryStaryTick = 0;*/

        // double novyNovyTick = 0;
        // double dlzkaHernehoTicku2 = 0;
        double novyTick = 0;

        while (true) {
            double staryTick = System.nanoTime();

            // ZAKOMENTOVANE PROFILERY

            //double dlzkaHernehoTicku = System.nanoTime();

            Hra.this.hrac.tick(staryTick - novyTick);
            Platno.dajPlatno().redraw();

            //dlzkaHernehoTicku2 = System.nanoTime();
            //System.out.println("tato hovadina trva "  + (dlzkaHernehoTicku2 - dlzkaHernehoTicku));

            novyTick = System.nanoTime();
            //System.out.println("sleep for " + ((TICK_DLZKA - (novyTick - staryTick)) / 1000000));
            //System.out.println("delta tick: " + (novyTick - staryTick));
            if ((TICK_DLZKA - (novyTick - staryTick)) / 1000000 > 0) {
                Thread.sleep((long) ((TICK_DLZKA - (novyTick - staryTick)) / 1000000), (int) ((TICK_DLZKA - (novyTick - staryTick)) % 1000000));
            }
            //double staryStaryTick = System.nanoTime();
            //System.out.println(staryStaryTick - novyNovyTick + " pls");
            //novyNovyTick = System.nanoTime();
        }
    }
}
