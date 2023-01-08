import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

/**
 * Zobrazuje HUD pre hraca -- pocet zostavajucich zivotov, momentalne skore a najvyssie skore
 */
public class UI {
    private Text najvyssieSkoreText;
    private Text zobrazeneNajvyssieSkore;
    private int najvyssieSkore;
    private Text zobrazeneMomentalneScore;
    private int momentalneScore;
    private Text oneUP;
    private Obrazok[] zostavajuceZivoty;
    private Text gameOverText;

    private int tikAnimacie;


    private static UI singletonUI;

    /**
     * @return Singleton UI
     */
    public static UI getUI() {
        if (UI.singletonUI == null) {
            UI.singletonUI = new UI();
        }
        return UI.singletonUI;
    }

    /**
     * Konstruktor
     */
    private UI() {
        this.momentalneScore = 0;

        this.najvyssieSkoreText = new Text("HIGH SCORE", 380, -40);
        this.najvyssieSkoreText.zobraz();

        this.zobrazeneMomentalneScore = new Text("0", 180, -80);
        this.zobrazeneMomentalneScore.zobraz();

        // Text "1UP" len na okrasu, v povodnych arkadovych automatoch sluzil
        // na udavanie cisla hraca, ktory ma prave hrat
        this.oneUP = new Text("1UP", 180, -40);
        this.oneUP.zobraz();

        this.zostavajuceZivoty = new Obrazok[5];
        this.tikAnimacie = 0;

        this.gameOverText = new Text("GAME OVER", 200, -490);
        this.gameOverText.setVelkost(80);

        // citanie najvyssieho score zo suboru highscore.level
        try {
            Scanner scanner = new Scanner(new File("highscore.level"));
            this.najvyssieSkore = scanner.nextInt();
            scanner.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        this.zobrazeneNajvyssieSkore = new Text(Integer.toString(this.najvyssieSkore), 500, -80);
        this.zobrazeneNajvyssieSkore.zobraz();

        for (int i = 0; i < this.zostavajuceZivoty.length; i++) {
            this.zostavajuceZivoty[i] = new Obrazok("Obrazky\\player-2.png");
            this.zostavajuceZivoty[i].zmenUhol(270);
            this.zostavajuceZivoty[i].zmenPolohu(180 + i * 60, -930);
            this.zostavajuceZivoty[i].zobraz();
        }
    }

    /**
     * Nastavuje pocet zostavajucich zivotov, ktore sa maju zobrazit
     * @param pocet Pocet zivotov
     */
    public void setPocetZostavajucichZivotov(int pocet) {
        for (int i = 0; i < this.zostavajuceZivoty.length; i++) {
            if (i < pocet) {
                this.zostavajuceZivoty[i].zobraz();
            } else {
                this.zostavajuceZivoty[i].skry();
            }

        }
    }

    /**
     * Zvysuje momentalne skore
     * @param skore Velkost o ktoru sa ma skore zvysit
     */
    public void pridajSkore(int skore) {
        this.momentalneScore += skore;
        this.zobrazeneMomentalneScore.setText(Integer.toString(this.momentalneScore));
    }

    /**
     * @return Momentalne skore hraca
     */
    public int getMomentalneScore() {
        return this.momentalneScore;
    }

    /**
     * Animacia pre blikanie textu "1UP"
     */
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

    /**
     * Ak je momentalne skore vacsie ako najvyssie skore, tak ho ulozi do suboru highscore.level
     */
    public void ulozSkore() {
        if (this.momentalneScore > this.najvyssieSkore) {
            try {
                FileWriter fileWriter = new FileWriter(new File("highscore.level"));
                fileWriter.write(Integer.toString(this.momentalneScore));
                fileWriter.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            

        }
    }

    /**
     * Zobrazi text "GAME OVER"
     */
    public void zobrazGameOver() {
        this.gameOverText.zobraz();
    }

}
