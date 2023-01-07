

public class ManazerDuchov {
    private Kolizia kolizia;
    private Postava hrac;
    private Postava[] duchovia;

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
        
        this.rozhodniSmer(new Postava[] {this.duchovia[3]});
        

        this.duchovia[3].tick();

    }

    public void rozhodniSmer(Postava[] duchovia) {
        for (Postava duch : duchovia) {
            int horizontalnaVzdialenostOdHraca = duch.getX() - this.hrac.getX();
            int vertikalnaVzdialenostOdHraca = duch.getY() - this.hrac.getY();

            if (Math.abs(vertikalnaVzdialenostOdHraca) > Math.abs(horizontalnaVzdialenostOdHraca)) {
                if ((duch.getSmer() == Smer.HORE || duch.getSmer() == Smer.DOLE) && duch.jeNarazenaOStenu()) {
                    if ((horizontalnaVzdialenostOdHraca > 0 && duch.checkVolnySmer(Smer.VLAVO)) || !duch.checkVolnySmer(Smer.VPRAVO)) {
                        duch.setPozadovanySmer(Smer.VLAVO);  
                        return;                     
                    } else {
                        duch.setPozadovanySmer(Smer.VPRAVO);
                        return;
                    }
                }
                if (duch.getSmer() == Smer.VPRAVO || duch.getSmer() == Smer.VLAVO) {
                    if ((vertikalnaVzdialenostOdHraca > 0 && !duch.jeNarazenaOStenu()) || (duch.jeNarazenaOStenu() && duch.checkVolnySmer(Smer.DOLE))) {
                        duch.setPozadovanySmer(Smer.DOLE);
                        return;
                    } else {
                        duch.setPozadovanySmer(Smer.HORE);
                        return;
                    }
                } 
            } else {
                if ((duch.getSmer() == Smer.VPRAVO || duch.getSmer() == Smer.VLAVO) && duch.jeNarazenaOStenu()) {
                    if ((vertikalnaVzdialenostOdHraca > 0 && duch.checkVolnySmer(Smer.DOLE)) || !duch.checkVolnySmer(Smer.HORE)) {
                        duch.setPozadovanySmer(Smer.DOLE);  
                        return;                     
                    } else {
                        duch.setPozadovanySmer(Smer.HORE);
                        return;
                    }
                }
                if (duch.getSmer() == Smer.HORE || duch.getSmer() == Smer.DOLE) {
                    if ((horizontalnaVzdialenostOdHraca > 0 && !duch.jeNarazenaOStenu()) || (duch.jeNarazenaOStenu() && duch.checkVolnySmer(Smer.VLAVO))) {
                        duch.setPozadovanySmer(Smer.VLAVO);
                        return;
                    } else {
                        duch.setPozadovanySmer(Smer.VPRAVO);
                        return;
                    }
                } 
            }
        }
    }
}
