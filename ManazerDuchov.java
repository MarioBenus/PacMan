

public class ManazerDuchov {
    private Kolizia kolizia;
    private Postava hrac;
    private Postava[] duchovia;
    private Pohyb pohyb;

    public ManazerDuchov(Kolizia kolizia, Postava hrac) {
        this.kolizia = Kolizia.dajKoliziu();
        this.hrac = hrac;
        this.duchovia = new Postava[4];

        for (int i = 0; i < this.duchovia.length; i++) {
            this.duchovia[i] = new Postava(TypPostavy.values()[i + 1], 450 + i * 50, -500);
            this.duchovia[i].setRychlost(3);
        }        
        this.duchovia[3].setSuradnice(500, -450);

    }

    public void tick() {
        int horizontalnaVzdialenostOdHraca = this.duchovia[3].getX() - this.hrac.getX();
        int vertikalnaVzdialenostOdHraca = this.duchovia[3].getY() - this.hrac.getY();

        if (Math.abs(horizontalnaVzdialenostOdHraca) > Math.abs(vertikalnaVzdialenostOdHraca)) {
            if (horizontalnaVzdialenostOdHraca < 0) {
                this.duchovia[3].setPozadovanySmer(Smer.VPRAVO);
            } else {
                this.duchovia[3].setPozadovanySmer(Smer.VLAVO);
            }
        } else {
            if (vertikalnaVzdialenostOdHraca < 0) {
                this.duchovia[3].setPozadovanySmer(Smer.HORE);
            } else {
                this.duchovia[3].setPozadovanySmer(Smer.DOLE);
            }
        }

        this.duchovia[3].tick();

    }
}
