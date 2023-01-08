import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;

/**
 * Trieda Obrazok, reprezentuje bitmapovy obrazok, ktory moze byt vykresleny na platno.
 * 
 * @author Miroslav Kvassay
 * @author Michal Varga
 * 
 * @version 1.1
 */
public class Obrazok {
    private boolean jeViditelny;
    
    private int lavyDolnyX; // ZMENA: zmenene z lavy horny na lavy dolny, pretoze sa mi s tym pracuje lepsie
    private int lavyDolnyY;
    private int uhol;
    
    private BufferedImage obrazok;

    /**
     * Parametricky konstruktor vytvori Obrazok na pozicii paX, paY s natocenim paUhol
     * 
     * @param suborSObrazkom cesta k suboru s obrazkom, ktory sa ma vykreslovat
     */
    public Obrazok(String suborSObrazkom) {      
        this.obrazok = this.nacitajObrazokZoSuboru(suborSObrazkom);                                   
 
        this.jeViditelny = false;
        this.lavyDolnyX = 100;
        this.lavyDolnyY = 100; 
        this.uhol = 0;         
    }

    /**
     * @return Suradnica x laveho dolneho rohu obrazka
     */
    public int getLavyDolnyX() { // ZMENA: getter pre lavy dolny x a y
        return this.lavyDolnyX;
    }

    /**
     * @return Suradnica y laveho dolneho rohu obrazka
     */
    public int getLavyDolnyY() {
        return this.lavyDolnyY;
    }

    
    /**
     * (Obrázok) Zobraz sa.
     */
    public void zobraz() {      
        this.jeViditelny = true;
        this.nakresli();
    }
    
    /**
     * (Obrázok) Zobraz sa.
     */
    public void skry() {       
        this.zmaz();
        this.jeViditelny = false;
    }                     
    
    /**
     * (Obrázok) Posuň sa vpravo o pevnú dĺžku.
     */
    public void posunVpravo() {
        this.posunVodorovne(20);
    }

    /**
     * (Obrázok) Posuň sa vľavo o pevnú dĺžku.
     */
    public void posunVlavo() {
        this.posunVodorovne(-20);
    }

    /**
     * (Obrázok) Posuň sa hore o pevnú dĺžku.
     */
    public void posunHore() {
        this.posunZvisle(-20);
    }

    /**
     * (Obrázok) Posuň sa dole o pevnú dĺžku.
     */
    public void posunDole() {
        this.posunZvisle(20);
    }

    /**
     * (Obrázok) Posuň sa vodorovne o dĺžku danú parametrom.
     */
    public void posunVodorovne(int vzdialenost) {
        //this.zmaz(); ZMENA: bolo to nadbytocne
        this.lavyDolnyX += vzdialenost;
        this.nakresli();
    }

    /**
     * (Obrázok) Posuň sa zvisle o dĺžku danú parametrom.
     */
    public void posunZvisle(int vzdialenost) {
        //this.zmaz(); ZMENA: bolo to nadbytocne
        this.lavyDolnyY += vzdialenost;
        this.nakresli();
    }

    /**
     * (Obrázok) Posuň sa pomaly vodorovne o dĺžku danú parametrom.
     */
    public void pomalyPosunVodorovne(int vzdialenost) {
        int delta;

        if (vzdialenost < 0) {
            delta = -1;
            vzdialenost = -vzdialenost;
        } else  {
            delta = 1;
        }

        for (int i = 0; i < vzdialenost; i++) {
            this.lavyDolnyX += delta;
            this.nakresli();
        }
    }

    /**
     * (Obrázok) Posuň sa pomaly vodorovne o dĺžku danú parametrom.
     */
    public void pomalyPosunZvisle(int vzdialenost) {
        int delta;

        if (vzdialenost < 0) {
            delta = -1;
            vzdialenost = -vzdialenost;
        } else {
            delta = 1;
        }

        for (int i = 0; i < vzdialenost; i++) {
            this.lavyDolnyY += delta;
            this.nakresli();
        }
    }
           
    /**
     * (Obrázok) Zmení obrázok.
     * Súbor s obrázkom musí existovať.
     * 
     * @param suborSObrazkom cesta k súboru s obrázkom, ktorý sa má načítať
     */
    public void zmenObrazok(String suborSObrazkom) {
        boolean nakresleny = this.jeViditelny;
        this.zmaz();        
        this.obrazok = this.nacitajObrazokZoSuboru(suborSObrazkom);
        if (nakresleny) {
            this.nakresli();
        }
    }    
    
    /**
     * (Obrázok) Zmeň polohu stredu obrázka na hodnoty dané parametrami. 
     */
    public void zmenPolohu(int stredX, int stredY) {
        boolean nakresleny = this.jeViditelny;
        //this.zmaz(); ZMENA: bolo to nadbytocne
        this.lavyDolnyX = stredX - this.getSirka() / 2;
        this.lavyDolnyY = stredY - this.getVyska() / 2;
        if (nakresleny) {
            this.nakresli();
        }
    }
    
    /**
     * (Obrázok) Zmeň uhol natočenia obrázku podľa parametra. Sever = 0.
     */
    public void zmenUhol(int uhol) {
        boolean nakresleny = this.jeViditelny;
        //this.zmaz(); ZMENA: bolo to nadbytocne
        this.uhol = uhol;
        if (nakresleny) {
            this.nakresli();
        }
    }  
    
    /*
     * Načíta obrázok zo súboru.
     */
    private BufferedImage nacitajObrazokZoSuboru(String subor) {
        BufferedImage nacitanyOBrazok = null;
        
        try {
            nacitanyOBrazok = ImageIO.read(new File(subor));
        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Subor " + subor + " sa nenasiel.");
        }        
        
        return nacitanyOBrazok;
    }     
    
    /*
     * (Obrázok) Vráti všírku obrázka.
     */
    public int getSirka() { // ZMENA: zmenene na public
        return this.obrazok.getWidth();
    }
    
    /*
     * (Obrázok) Vráti výšku obrázka.
     */
    public int getVyska() { // ZMENA: zmenene na public
        return this.obrazok.getHeight();
    }    
    
    /*
     * Draw the square with current specifications on screen.
     */
    private void nakresli() {
        if (this.jeViditelny) {
            Platno canvas = Platno.dajPlatno();
        
            AffineTransform at = new AffineTransform();
            at.translate(this.lavyDolnyX + this.getSirka() / 2, -(this.lavyDolnyY + this.getVyska() / 2)); // ZMENA: pridane minus aby +y išlo hore a -y dole
            at.rotate(this.uhol / 180.0 * Math.PI);
            at.translate(-this.getSirka() / 2, -this.getVyska() / 2);
            
        
            canvas.draw(this, this.obrazok, at);
            //canvas.wait(10); ZMENA: odstraneny nahodny 10ms wait???
        }
    }

    /*
     * Erase the square on screen.
     */
    private void zmaz() {
        if (this.jeViditelny) {
            Platno canvas = Platno.dajPlatno();
            canvas.erase(this);
        }
    }
    
}
