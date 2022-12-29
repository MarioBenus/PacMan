
// hitboxy stien
public class Stena {
    private final int lavyDolnyX;
    private final int lavyDolnyY;
    private final int sirka;
    private final int vyska;

    public Stena(int lavyDolnyX, int lavyDolnyY, int sirka, int vyska) {
        this.lavyDolnyX = lavyDolnyX;
        this.lavyDolnyY = lavyDolnyY;
        this.sirka = sirka;
        this.vyska = vyska;
    }

    public int getLavyDolnyX() {
        return this.lavyDolnyX;
    }
    public int getLavyDolnyY() {
        return this.lavyDolnyY;
    }
    public int getPravyHornyX() {
        return this.lavyDolnyX + sirka;
    }
    public int getPravyHornyY() {
        return this.lavyDolnyY + vyska;
    }
    public int getSirka() {
        return this.sirka;
    }
    public int getVyska() {
        return this.vyska;
    }
}
