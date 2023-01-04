
public class UI {
    private Text highScoreText;
    private Text highScoreCislo;
    private Text currentScoreCislo;
    private Text oneUP;
    private Obrazok[] zostavajuceZivoty;
    private int tikAnimacie;

    public UI() {
        this.highScoreText = new Text("HIGH SCORE", 380, -40);
        this.highScoreText.zobraz();
        this.highScoreCislo = new Text("0000", 500, -80);
        this.highScoreCislo.zobraz();
        this.currentScoreCislo = new Text("0", 180, -80);
        this.currentScoreCislo.zobraz();
        this.oneUP = new Text("1UP", 180, -40);
        this.oneUP.zobraz();
        this.zostavajuceZivoty = new Obrazok[2];
        this.tikAnimacie = 0;

        for (int i = 0; i < zostavajuceZivoty.length; i++) {
            this.zostavajuceZivoty[i] = new Obrazok("Obrazky\\player-2.png");
            this.zostavajuceZivoty[i].zmenUhol(270);
            this.zostavajuceZivoty[i].zmenPolohu(180 + i * 60, -930);
            this.zostavajuceZivoty[i].zobraz();
        }
    }

    public void setPocetZostavajucichZivotov(int pocet) {
        if (pocet <= 1) {
            this.zostavajuceZivoty[1].skry();
        } else if (pocet == 0) {
            this.zostavajuceZivoty[0].skry();
        }
    }

    public void setHighScore(int skore) {
        this.highScoreCislo.setText(Integer.toString(skore));
    }

    public void setCurrentScore(int skore) {
        this.currentScoreCislo.setText(Integer.toString(skore));
    }

    public void animacia() {
        switch (this.tikAnimacie) {
            case 10:
                this.oneUP.skry();
                break;
            case 20:
                this.oneUP.zobraz();
                this.tikAnimacie = -1;
                break;
        }
        this.tikAnimacie++;
    }
}
