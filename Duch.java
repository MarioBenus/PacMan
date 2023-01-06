
/**
 * Write a description of class Duch here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Duch {
    private Obrazok obrazok;
    private Smer smer;
    
    public Duch() {
        // initialise instance variables
    }

    public void tick() {

    }

    public void nastavObrazok(String cestaKObrazku) {
        this.obrazok = new Obrazok(cestaKObrazku);
        this.obrazok.zobraz();
    }

    public void nastavPolohu(int strednyX, int strednyY) {
        this.obrazok.zmenPolohu(strednyX, strednyY);
    }

    public int[] getSuradnice() {
        return new int[] {this.obrazok.getLavyDolnyX(), this.obrazok.getLavyDolnyY()};
    }

    public int getVelkost() {
        return this.obrazok.vyska();
    }
}
