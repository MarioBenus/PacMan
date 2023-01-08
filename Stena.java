/**
 * Hitboxy stien, cez ktore sa hrac a duch nemoze pohybovat
 */
public class Stena {
    private final int lavyDolnyX;
    private final int lavyDolnyY;
    private final int sirka;
    private final int vyska;

    /**
     * Vytvorenie noveho hitboxu steny
     * 
     * @param lavyDolnyX Suradnica x laveho dolneho rohu hitboxu steny
     * @param lavyDolnyY Suradnica y laveho dolneho rohu hitboxu steny
     * @param sirka Sirka hitboxu steny
     * @param vyska Vyska hitboxu steny
     */
    public Stena(int lavyDolnyX, int lavyDolnyY, int sirka, int vyska) {
        this.lavyDolnyX = lavyDolnyX;
        this.lavyDolnyY = lavyDolnyY;
        this.sirka = sirka;
        this.vyska = vyska;
    }

    /**
     * @return Suradnica x laveho dolneho rohu hitboxu steny
     */
    public int getLavyDolnyX() {
        return this.lavyDolnyX;
    }

    /**
     * @return Suradnica x laveho dolneho rohu hitboxu steny
     */
    public int getLavyDolnyY() {
        return this.lavyDolnyY;
    }

    /**
     * @return Suradnica x praveho horneho rohu hitboxu steny
     */
    public int getPravyHornyX() {
        return this.lavyDolnyX + sirka;
    }

    /**
     * @return Suradnica y praveho horneho rohu hitboxu steny
     */
    public int getPravyHornyY() {
        return this.lavyDolnyY + vyska;
    }

    /**
     * @return Sirka hitboxu steny
     */
    public int getSirka() {
        return this.sirka;
    }

    /**
     * @return Vyska hitboxu steny
     */
    public int getVyska() {
        return this.vyska;
    }
}
