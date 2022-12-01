
/**
 * Write a description of class Kolizia here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Kolizia {
    private Stena[] steny;
    private Smer smerHraca;
    private Smer bufferedSmerHraca;

    private final int velkostHraca = 50;

    public Kolizia(Stena[] steny) {
        this.steny = steny;
    }

    public void setBufferedSmerHraca(Smer smer) {
        this.bufferedSmerHraca = smer;
    }

    public int checkKoliziaStena(int hracLavyDolnyX, int hracLavyDolnyY, Smer smer) {
        // chcem aby hitbox hraca bol 50x50, aj ked jeho skutocna sirka je 44x44
        hracLavyDolnyX -= 3;
        hracLavyDolnyY -= 3;

        for (Stena stena : this.steny) {
            int hracPravyHornyY = hracLavyDolnyY + this.velkostHraca;
            int hracPravyHornyX = hracLavyDolnyX + this.velkostHraca;

            int horizontalnaKorekcia = 0;
            int vertikalnaKorekcia = 0;
            
            // test ci je hrac vnutri steny
            if (hracPravyHornyY > stena.getLavyDolnyY() && hracLavyDolnyY < stena.getPravyHornyY() &&
                hracPravyHornyX > stena.getLavyDolnyX() && hracLavyDolnyX < stena.getPravyHornyX()) {
                    
                switch (smer) {
                    case VPRAVO:
                        return stena.getLavyDolnyX() - hracPravyHornyX;
                    case VLAVO:
                        return stena.getPravyHornyX() - hracLavyDolnyX;
                    case HORE:
                        return stena.getLavyDolnyY() - hracPravyHornyY;
                    case DOLE:
                        return stena.getPravyHornyY() - hracLavyDolnyY;
                }
            }
        }

        return 0;
    }


}
