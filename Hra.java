/*import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;*/

/**
 * Write a description of class GameManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hra {
    private static final double TICK_DLZKA = 1000000000 / 60;
    /*private long zaciatocnyTick;
    private long staryzaciatocnyTick;*/

    private Hrac hrac;
    
    private Obrazok pozadie;
    
    public Hra() throws Exception {
        //Platno.dajPlatno().addTimerListener(new ManazerCasovaca());

        this.hrac = new Hrac();
        this.pozadie = new Obrazok("Obrazky\\level.png");
        this.pozadie.zmenPolohu(500, 500);
        this.pozadie.zobraz();

        /*this.zaciatocnyTick = 0;
        this.staryzaciatocnyTick = 0;*/

        // double novyNovyTick = 0;
        // double dlzkaHernehoTicku2 = 0;
        double sleepTick = 0;
        double zaciatocnyTick = 0;

        while (true) {
            double deltaCas = System.nanoTime() - zaciatocnyTick;
            System.out.println("delta cas: " + deltaCas);
            zaciatocnyTick = System.nanoTime();



            Hra.this.hrac.tick(deltaCas);
            Platno.dajPlatno().redraw();

            sleepTick = System.nanoTime();
            System.out.println("game loop: " + (sleepTick - zaciatocnyTick));

            System.out.print("spat: " + (long)((TICK_DLZKA - (sleepTick - zaciatocnyTick)) / 1000000));
            System.out.println(" " + (int)((TICK_DLZKA - (sleepTick - zaciatocnyTick)) % 1000000));
            double actualSpanie = System.nanoTime();
            if ((TICK_DLZKA - (sleepTick - zaciatocnyTick)) / 1000000 > 0) {
                Thread.sleep((long)((TICK_DLZKA - (sleepTick - zaciatocnyTick)) / 1000000), (int)((TICK_DLZKA - (sleepTick - zaciatocnyTick)) % 1000000));
            }
            double koniecActualSpanie = System.nanoTime();
            System.out.println("Spanie trvalo: " + (koniecActualSpanie - actualSpanie));

        }
    }
}
