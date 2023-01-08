/**
 * Text, ktory sa ma zobrazit na platne
 * Inspirovane tvarmi z TvaryV3
 */
public class Text {
    private String text;
    private int lavyDolnyX;
    private int lavyDolnyY;
    private boolean jeViditelny;
    private int velkost;
    private String farba;

    /**
     * Vytvorenie noveho textu
     * 
     * @param text Text, ktory ma byt zobrazeny
     * @param lavyDolnyX Suradnica x kde sa ma nachadzat lavy dolny roh textu
     * @param lavyDolnyY Suradnica y kde sa ma nachadzat lavy dolny roh textu
     */
    public Text(String text, int lavyDolnyX, int lavyDolnyY) {
        this.text = text;
        this.velkost = 35;
        this.farba = "white";
        this.lavyDolnyX = lavyDolnyX;
        this.lavyDolnyY = lavyDolnyY;
    }

    /**
     * Zobrazi text na platne
     */
    public void zobraz() {
        this.jeViditelny = true;
        this.nakresli();
    }

    /**
     * Skryje text z platna
     */
    public void skry() {
        this.jeViditelny = false;
        Platno canvas = Platno.dajPlatno();
        canvas.erase(this);
    }

    private void nakresli() {
        if (this.jeViditelny) {
            Platno.dajPlatno().draw(this, this.text, this.velkost, this.farba, this.lavyDolnyX, -this.lavyDolnyY);
        }
    }

    /**
     * @param text Text, ktory sa ma zobrazit
     */
    public void setText(String text) {
        this.text = text;
        this.nakresli();
    }

    /**
     * @param velkost Velkost, ktoru ma text mat
     */
    public void setVelkost(int velkost) {
        this.velkost = velkost;
        this.nakresli();
    }
}
