import java.util.Scanner;
import java.io.IOException;
import java.io.File;

// kontroluje koliziu medzi objektmi
public class Kolizia {
    private Stena[] steny;
    private Bodka[][] bodky;
    private Bodka[] velkeBodky;
    private int pocetZostavajucichBodiek;

    private final int velkostHraca = 50;
    private final int pocetMalychBodiek = 240;
    private final int pocetVelkychBodiek = 4;

    private int pocetTikov;

    private Obrazok pozadie;

    public Kolizia(Obrazok pozadie) throws IOException {
        this.pocetTikov = 0;
        this.pozadie = pozadie;
        this.pocetZostavajucichBodiek = this.pocetMalychBodiek + this.pocetVelkychBodiek;
        this.bodky = new Bodka[29][26];
        this.velkeBodky = new Bodka[4];
        this.nacitajBodky();
        this.nacitajLevel();
    }

    // vrati vzdialenost o ktoru sa hrac musi vratit aby bol mimo steny
    public int checkKoliziaStena(int hracLavyDolnyX, int hracLavyDolnyY, Smer smer) {
        // TODO: prerobit tento komentar?
        // chcem aby hitbox hraca bol 50x50, aj ked sirka obrazku je 44x44
        // bude to musiet byt trochu zmenene pri implementacii duchov pretoze duch ma velkost obrazku 46x46
        hracLavyDolnyX -= 3;
        hracLavyDolnyY -= 3;

        // warp tunnel
        if (hracLavyDolnyX + this.velkostHraca / 2 < this.pozadie.getLavyDolnyX()) {
            return this.pozadie.sirka();
        } else if (hracLavyDolnyX + this.velkostHraca / 2 > this.pozadie.getLavyDolnyX() + this.pozadie.sirka()) {
            return -this.pozadie.sirka();
        }

        for (Stena stena : this.steny) {

            // TODO: premenovat tento retardovany nazov
            int vysledok = this.checkKolizia(new int[] {hracLavyDolnyX, hracLavyDolnyY, hracLavyDolnyX + this.velkostHraca, hracLavyDolnyY + this.velkostHraca}, 
                                             new int[] {stena.getLavyDolnyX(), stena.getLavyDolnyY(), stena.getPravyHornyX(), stena.getPravyHornyY()}, 
                                             smer);
            
            if (vysledok != 0) {
                return vysledok;
            }

        }

        return 0;
    }

    // testuje ci pozadovanySmer je volny
    // vyuziva sa na zatacky
    public boolean checkVolnySmer(int hracLavyDolnyX, int hracLavyDolnyY, Smer pozadovanySmer) {
        // chcem aby hitbox bol mensi ako 50x50, pretoze hrac sa pohybuje po 4 pixeloch a nechcem
        // aby nahodou "preskocil" tu medzeru
        // asi budem musiet mierne zmenit pri implementacii duchov
        int testovanyHitboxLavyDolnyX = hracLavyDolnyX;
        int testovanyHitboxLavyDolnyY = hracLavyDolnyY;
        int testovanyHitboxPravyHornyX = hracLavyDolnyX + 44;
        int testovanyHitboxPravyHornyY = hracLavyDolnyY + 44;

        // posun hitboxu do smeru kam chce hrac ist
        // 10 je arbitrarna hodnota, mohla by byt o nieco viac aj menej, ale nezalezi na tom,
        // staci len ze bude posunuty do vnutra steny
        switch (pozadovanySmer) {
            case HORE:
                testovanyHitboxPravyHornyY += 10;
                break;
            case DOLE:
                testovanyHitboxLavyDolnyY -= 10;
                break;
            case VPRAVO:
                testovanyHitboxPravyHornyX += 10;
                break;
            case VLAVO:
                testovanyHitboxLavyDolnyX -= 10;
                break;
            default:
                return false;
        }

        // test ci posunuty hitbox sa nachadza v nejakej stene
        for (Stena stena : this.steny) {
            // TODO: premenovat tento retardovany nazov
            int vysledok = this.checkKolizia(new int[] {testovanyHitboxLavyDolnyX, testovanyHitboxLavyDolnyY, testovanyHitboxPravyHornyX, testovanyHitboxPravyHornyY}, 
                                             new int[] {stena.getLavyDolnyX(), stena.getLavyDolnyY(), stena.getPravyHornyX(), stena.getPravyHornyY()}, 
                                             Smer.HORE /* na smere v tomto pripade nezalezi */);

            if (vysledok != 0) {
                return false;
            }
        }

        return true;

        /*
        for (Stena stena : this.steny) {
            if (testovanyHitboxPravyHornyY > stena.getLavyDolnyY() && testovanyHitboxLavyDolnyY < stena.getPravyHornyY() &&
                testovanyHitboxPravyHornyX > stena.getLavyDolnyX() && testovanyHitboxLavyDolnyX < stena.getPravyHornyX()) {

                return false;
            }
        }
        return true;*/
    }

    // lavy dolny x, lavy dolny y, pravy horny x, pravy horny y
    private int checkKolizia(int[] prvyObjekt, int[] druhyObjekt, Smer smer) {
        int prvyObjektLavyDolnyX = prvyObjekt[0];
        int prvyObjektLavyDolnyY = prvyObjekt[1];
        int prvyObjektPravyHornyX = prvyObjekt[2];
        int prvyObjektPravyHornyY = prvyObjekt[3];
        
        int druhyObjektLavyDolnyX = druhyObjekt[0];
        int druhyObjektLavyDolnyY = druhyObjekt[1];
        int druhyObjektPravyHornyX = druhyObjekt[2];
        int druhyObjektPravyHornyY = druhyObjekt[3];

        if (prvyObjektPravyHornyY > druhyObjektLavyDolnyY && prvyObjektLavyDolnyY < druhyObjektPravyHornyY &&
            prvyObjektPravyHornyX > druhyObjektLavyDolnyX && prvyObjektLavyDolnyX < druhyObjektPravyHornyX) {
                    
            // return ako hlboko je prvy objekt v druhom objekte
            switch (smer) {
                case VPRAVO:
                    return druhyObjektLavyDolnyX - prvyObjektPravyHornyX;
                case VLAVO:
                    return druhyObjektPravyHornyX - prvyObjektLavyDolnyX;
                case HORE:
                    return druhyObjektLavyDolnyY - prvyObjektPravyHornyY;
                case DOLE:
                    return druhyObjektPravyHornyY - prvyObjektLavyDolnyY;
                default:
                    return 0;
            }
        }

        return 0;

    }

    public TypBodky zjedzBodku(int hracLavyDolnyX, int hracLavyDolnyY) {
        hracLavyDolnyX -= this.pozadie.getLavyDolnyX() + 14 - (int)(Bodka.VZDIALENOST_MEDZI_BODKAMI / 2);
        hracLavyDolnyY -= this.pozadie.getLavyDolnyY() + 14 - (int)(Bodka.VZDIALENOST_MEDZI_BODKAMI / 2);

        int suradnicaHracaX = (int)((double)hracLavyDolnyX / Bodka.VZDIALENOST_MEDZI_BODKAMI);
        int suradnicaHracaY = (int)((double)hracLavyDolnyY / Bodka.VZDIALENOST_MEDZI_BODKAMI);

        // nastava v pripade prechodu cez warp tunnel
        if (suradnicaHracaX < 0 || suradnicaHracaX > 25) {
            return TypBodky.ZIADNA;
        }

        if (this.bodky[suradnicaHracaY][suradnicaHracaX] != null) {
            if (!this.bodky[suradnicaHracaY][suradnicaHracaX].jeZjedena()) {
                this.bodky[suradnicaHracaY][suradnicaHracaX].zjedz();
                this.pocetZostavajucichBodiek--;
                return this.bodky[suradnicaHracaY][suradnicaHracaX].geTypBodky();
            }
        }
        return TypBodky.ZIADNA;

    }

    public int getPocetZostavajucichBodiek() {
        return this.pocetZostavajucichBodiek;
    }

    // TODO: zufalo potrebuje lepsi nazov
    public void reloadBodky() {
        for (Bodka[] bodky2 : this.bodky) {
            for (Bodka bodka : bodky2) {
                if (bodka != null) {
                    bodka.obnov();
                }
            }
        }
        this.pocetZostavajucichBodiek = this.pocetMalychBodiek;
    }

    public void nacitajLevel() throws IOException {
        int offsetX = this.pozadie.getLavyDolnyX();
        int offsetY = this.pozadie.getLavyDolnyY();

        // nacitavanie pozicii a velkosti stien zo suboru level.level
        // suradnice v tomto subore su relativne k lavemu dolnemu rohu pozadia
        Scanner scanner = new Scanner(new File("level.level"));
        scanner.nextLine(); // prvy riadok v level.level je len vysvetlenie syntaxe
        this.steny = new Stena[scanner.nextInt()];
        scanner.nextLine();
        for (int i = 0; i < this.steny.length; i++) {
            this.steny[i] = new Stena(offsetX + scanner.nextInt(), offsetY + scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
            scanner.nextLine();
        }
        scanner.close();   
    }

    public void nacitajBodky() throws IOException {
        
        Scanner scanner = new Scanner(new File("bodky.level"));

        // oznacuje x a y suradnice bodky v 0-tom riadku a 0-tom stlpci
        // 39 = sirka krajnej steny + polovica sirky priestoru pre hraca (14 + 25)
        double offsetX = this.pozadie.getLavyDolnyX() + 39 - Bodka.VELKOST / 2 - 1;
        double offsetY = this.pozadie.getLavyDolnyY() + 39 - Bodka.VELKOST / 2;

        scanner.nextLine(); // preskocenie radku kde sa vysvetluje syntax
        int i = 0;
        while (scanner.hasNextLine()) {
            double riadok = scanner.nextInt();
            double stlpec = scanner.nextInt();
            TypBodky typBodky;
            if (i < this.pocetMalychBodiek) {
                typBodky = TypBodky.MALA_BODKA;
            } else {
                typBodky = TypBodky.VELKA_BODKA;
            }
            this.bodky[(int)riadok][(int)stlpec] = new Bodka((int)(offsetX + stlpec * Bodka.VZDIALENOST_MEDZI_BODKAMI), 
                                                             (int)(offsetY + riadok * Bodka.VZDIALENOST_MEDZI_BODKAMI), typBodky);
            if (typBodky == TypBodky.VELKA_BODKA) {
                this.velkeBodky[i - this.pocetMalychBodiek] = this.bodky[(int)riadok][(int)stlpec];
            }
            i++;
        }
    }

    public void animaciaBodiek() {
        switch (this.pocetTikov) {
            case 10:
                for (Bodka bodka : this.velkeBodky) {
                    if (!bodka.jeZjedena()) {
                        bodka.skry();
                    }
                }
                break;
            case 20:
                for (Bodka bodka : this.velkeBodky) {
                    if (!bodka.jeZjedena()) {
                        bodka.zobraz();
                    }
                }
                this.pocetTikov = -1;
                break;
        }
        this.pocetTikov++;
    }
}
