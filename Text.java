// text pre vykreslovanie na platne, inspirovane tvarmi z TvaryV3
public class Text {
    private String text;
    private int lavyHornyX;
    private int lavyHornyY;
    private boolean jeViditelny;
    private int velkost;
    private String farba;


    public Text(String text, int lavyHornyX, int lavyHornyY) {
        this.text = text;
        this.velkost = 35;
        this.farba = "white";
        this.lavyHornyX = lavyHornyX;
        this.lavyHornyY = lavyHornyY;
    }

    public void zobraz() {
        this.jeViditelny = true;
        this.nakresli();
    }

    public void skry() {
        this.jeViditelny = false;
        Platno canvas = Platno.dajPlatno();
        canvas.erase(this);
    }

    public void nakresli() {
        if (this.jeViditelny) {
            Platno.dajPlatno().draw(this, this.text, this.velkost, this.farba, this.lavyHornyX, -this.lavyHornyY);
        }
    }

    public void setText(String text) {
        this.text = text;
        this.zobraz();
    }
}
