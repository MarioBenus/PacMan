/**
 * Manazuje spravanie duchov
 */
public class ManazerDuchov {
    private final int skoreZaZjedenieDucha = 200;
    private int nasobicSkoreJedeniaDuchov;

    private Postava hrac;
    private Postava[] duchovia;
    private UI ui;

    private int odpocetDuchov;

    /**
     * Konstruktor
     * 
     * @param kolizia Referencia na koliziu
     * @param hrac Referencia na hraca (Pac-Mana)
     */
    public ManazerDuchov(Kolizia kolizia, Postava hrac) {
        this.ui = UI.getUI();
        this.hrac = hrac;
        this.duchovia = new Postava[4];
        this.odpocetDuchov = 60 * 24; // 24 sekund

        // vytvorenie duchov
        for (int i = 0; i < this.duchovia.length - 1; i++) {
            this.duchovia[i] = new Postava(TypPostavy.values()[i + 1], 450 + i * 50, -480);
            this.duchovia[i].setRychlost(3);
            this.duchovia[i].setJeVBoxe(true);
            this.duchovia[i].setMaOdistZBoxu(false);
        }       
        // Cerveny duch sa na zaciatku nachadza mimo boxu 
        this.duchovia[3] = new Postava(TypPostavy.CERVENY_DUCH, 500, -390);
        this.duchovia[3].setRychlost(3);
        this.duchovia[3].setJeVBoxe(false);
        this.duchovia[3].setMaOdistZBoxu(true);

    }

    /**
     * Update Spravania duchov
     */
    public void tick() {       
        this.rozhodniSmer();
 
        for (Postava duch : this.duchovia) {
            duch.tick();
        }

        // kazdych 8 sekund vypusti ducha z boxu
        if (this.odpocetDuchov >= 0) {
            switch (this.odpocetDuchov) {
                case 60 * 16:
                    this.duchovia[1].setMaOdistZBoxu(true);
                    break;
                case 60 * 8:
                    this.duchovia[0].setMaOdistZBoxu(true);
                    break;
                case 0:
                    this.duchovia[2].setMaOdistZBoxu(true);
                    break;
            }
            this.odpocetDuchov--;
        }


    }

    /**
     * Zjedenie ducha
     * @param duch Referencia na ducha, ktory ma byt zjedeny
     */
    public void zjedzDucha(Postava duch) {
        this.ui.pridajSkore(this.skoreZaZjedenieDucha * this.nasobicSkoreJedeniaDuchov);
        duch.zjedenie();
        this.nasobicSkoreJedeniaDuchov *= 2;
    }

    /**
     * Vynulovanie nasobica pre zjedenie viacerych duchov
     */
    public void vynulujNasobicSkoreJedeniaDucha() {
        this.nasobicSkoreJedeniaDuchov = 1;
        for (Postava duch : this.duchovia) {
            duch.setDaSaZjest(true);
        }
    }

    /**
     * Vrati duchov na ich zaciatocne pozicie
     */
    public void resetDuchov() {
        this.odpocetDuchov = 60 * 24;
        for (int i = 0; i < this.duchovia.length - 1; i++) {
            this.duchovia[i].setJeVBoxe(true);
            this.duchovia[i].setMaOdistZBoxu(false);
        }
        for (Postava duch : this.duchovia) {
            duch.presunNaZaciatocnuPoziciu();
        }
    }

    public Postava[] getDuchov() {
        return this.duchovia;
    }

    /**
     * Zmeni smer pre kazdeho ducha podla jeho pozicie k hracovi
     */
    private void rozhodniSmer() {
        for (Postava duch : this.duchovia) {
            int horizontalnaVzdialenostOdHraca = duch.getLavyDolnyX() - this.hrac.getLavyDolnyX();
            int vertikalnaVzdialenostOdHraca = duch.getLavyDolnyY() - this.hrac.getLavyDolnyY();

            // Ak sa duch da zjest, hybe sa prec od hraca
            if (duch.daSaZjest()) {
                horizontalnaVzdialenostOdHraca *= -1;
                vertikalnaVzdialenostOdHraca *= -1;
            }
            Smer smerDucha = duch.getSmer();
            boolean jeDuchNarazenyOStenu = duch.jeNarazenyOStenu();

            // Duch sa nemoze otocit o 180 stupnov
            if (Math.abs(vertikalnaVzdialenostOdHraca) > Math.abs(horizontalnaVzdialenostOdHraca)) {
                if ((smerDucha == Smer.HORE || smerDucha == Smer.DOLE) && jeDuchNarazenyOStenu) {
                    if ((horizontalnaVzdialenostOdHraca > 0 && duch.pozriVolnySmer(Smer.VLAVO)) || !duch.pozriVolnySmer(Smer.VPRAVO)) {
                        duch.setPozadovanySmer(Smer.VLAVO);  
                        continue;                     
                    } else {
                        duch.setPozadovanySmer(Smer.VPRAVO);
                        continue;
                    }
                }
                if (smerDucha == Smer.VPRAVO || smerDucha == Smer.VLAVO) {
                    if ((vertikalnaVzdialenostOdHraca > 0 && !jeDuchNarazenyOStenu) || (jeDuchNarazenyOStenu && duch.pozriVolnySmer(Smer.DOLE))) {
                        duch.setPozadovanySmer(Smer.DOLE);
                        continue;
                    } else {
                        duch.setPozadovanySmer(Smer.HORE);
                        continue;
                    }
                } 
            } else {
                if ((smerDucha == Smer.VPRAVO || smerDucha == Smer.VLAVO) && jeDuchNarazenyOStenu) {
                    if ((vertikalnaVzdialenostOdHraca > 0 && duch.pozriVolnySmer(Smer.DOLE)) || !duch.pozriVolnySmer(Smer.HORE)) {
                        duch.setPozadovanySmer(Smer.DOLE);  
                        continue;                     
                    } else {
                        duch.setPozadovanySmer(Smer.HORE);
                        continue;
                    }
                }
                if (smerDucha == Smer.HORE || smerDucha == Smer.DOLE) {
                    if ((horizontalnaVzdialenostOdHraca > 0 && !jeDuchNarazenyOStenu) || (jeDuchNarazenyOStenu && duch.pozriVolnySmer(Smer.VLAVO))) {
                        duch.setPozadovanySmer(Smer.VLAVO);
                        continue;
                    } else {
                        duch.setPozadovanySmer(Smer.VPRAVO);
                        continue;
                    }
                } 
            }
        }
    }
}
