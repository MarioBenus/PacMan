// text pre vykreslovanie na platne, inspirovane tvarmi z TvaryV3
public class Text {
    private String text;
    private int lavyHornyX;
    private int lavyHornyY;
    private boolean jeViditelny;
    private String font;
    private int velkost;
    private String farba;


    public Text(String text, String font, int velkost, String farba, int lavyHornyX, int lavyHornyY) {
        this.text = text;
        this.font = font;
        this.velkost = velkost;
        this.farba = farba;
        this.lavyHornyX = lavyHornyX;
        this.lavyHornyY = lavyHornyY;
    }

    public void zobraz() {
        this.jeViditelny = true;
        this.nakresli();
    }

    public void nakresli() {
        if (this.jeViditelny) {
            Platno.dajPlatno().draw(this, this.text, this.font, this.velkost, this.farba, this.lavyHornyX, -this.lavyHornyY);
        }
    }
}
