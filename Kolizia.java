import java.util.Scanner;
import java.io.File;

/**
 * Stara sa o koliziu medzi objektami, sluzi hlavne pri kolizii hraca
 * s duchom a udrziavanie hraca a duchov mimo stien
 */
public class Kolizia {
    private Stena[] steny;
    private Bodka[][] bodky;
    private Bodka[] velkeBodky;
    private int pocetZostavajucichBodiek;
    private Postava[] duchovia;

    private final int velkostHitboxuPostavy = 50;
    private final int zmensenaVelkostHitboxuPostavy = 44;
    private final int pocetMalychBodiek = 240;
    private final int pocetVelkychBodiek = 4;

    private final Obrazok pozadie;

    private static Kolizia koliziaSingleton;

    private ManazerDuchov manazerDuchov;

    public static Kolizia getKolizia() {
        if (Kolizia.koliziaSingleton == null) {
            Kolizia.koliziaSingleton = new Kolizia();
        }
        return Kolizia.koliziaSingleton;
    }

    /**
     * Konstruktor pre koliziu
     */
    private Kolizia() {
        this.pozadie = new Obrazok("Obrazky\\level.png");
        this.pozadie.zmenPolohu(Platno.dajPlatno().getSirka() / 2, -Platno.dajPlatno().getVyska() / 2); // posun do stredu
        this.pozadie.zobraz();
        this.pocetZostavajucichBodiek = this.pocetMalychBodiek + this.pocetVelkychBodiek;
        this.bodky = new Bodka[29][26];
        this.velkeBodky = new Bodka[4];
        this.nacitajBodky();
        this.nacitajLevel();
    }

    /**
     * Vrati vzdialenost o ktoru sa hrac musi vratit aby bol mimo steny
     * 
     * @param postavaLavyDolnyX Suradnica x laveho dolneho rohu postavy
     * @param postavaLavyDolnyY Suradnica y laveho dolneho rohu postavy
     * @param velkostObrazka Velkost obrazka postavy v pixeloch
     * @param smer Smer postavy, pre ktory ma pocitat vzdialenost hraca v stene
     * @return Vzdialenost, o ktoru sa hrac musi vratit aby bol mimo steny
     */
    public int pozriKoliziaStena(int postavaLavyDolnyX, int postavaLavyDolnyY, int velkostObrazka, Smer smer) {
        // uprava suradnic aby hitbox postavy bol 50x50 a obrazok v strede
        postavaLavyDolnyX -= (this.velkostHitboxuPostavy - velkostObrazka) / 2;
        postavaLavyDolnyY -= (this.velkostHitboxuPostavy - velkostObrazka) / 2;

        // warp tunnel
        if (postavaLavyDolnyX + this.velkostHitboxuPostavy / 2 < this.pozadie.getLavyDolnyX()) {
            return this.pozadie.getSirka();
        } else if (postavaLavyDolnyX + this.velkostHitboxuPostavy / 2 > this.pozadie.getLavyDolnyX() + this.pozadie.getSirka()) {
            return -this.pozadie.getSirka();
        }

        // kontroluje koliziu pre kazdu stenu
        for (Stena stena : this.steny) {
            int vysledok = this.checkKolizia(new int[] {postavaLavyDolnyX, postavaLavyDolnyY, postavaLavyDolnyX + this.velkostHitboxuPostavy, postavaLavyDolnyY + this.velkostHitboxuPostavy}, 
                                             new int[] {stena.getLavyDolnyX(), stena.getLavyDolnyY(), stena.getPravyHornyX(), stena.getPravyHornyY()}, 
                                             smer);
            
            if (vysledok != 0) {
                return vysledok;
            }

        }

        return 0;
    }

    /**
     * Sluzi na kontrolu ci sa nachadza stena v danom smere od postavy
     * vyuziva sa pri zatacani
     * 
     * @param postavaLavyDolnyX Suradnica x laveho dolneho rohu postavy
     * @param postavaLavyDolnyY Suradnica y laveho dolneho rohu postavy
     * @param velkostObrazka Velkost obrazka postavy v pixeloch
     * @param pozadovanySmer Smer, ktory sa ma kontrolovat, ci je volny
     * @return True ak je smer volny, False ak sa tam nachadza stena
     */
    public boolean pozriVolnySmer(int postavaLavyDolnyX, int postavaLavyDolnyY, int velkostObrazka, Smer pozadovanySmer) {
        // chcem aby hitbox bol mensi ako 50x50, pretoze hrac sa pohybuje po 4 pixeloch a nechcem
        // aby nahodou "preskocil" tu medzeru
        int testovanyHitboxLavyDolnyX = postavaLavyDolnyX - (this.zmensenaVelkostHitboxuPostavy - velkostObrazka) / 2;
        int testovanyHitboxLavyDolnyY = postavaLavyDolnyY - (this.zmensenaVelkostHitboxuPostavy - velkostObrazka) / 2;
        int testovanyHitboxPravyHornyX = postavaLavyDolnyX + this.zmensenaVelkostHitboxuPostavy;
        int testovanyHitboxPravyHornyY = postavaLavyDolnyY + this.zmensenaVelkostHitboxuPostavy;

        // posun hitboxu do smeru kam chce hrac ist
        // 10 je arbitrarna hodnota, mohla by byt o nieco viac aj menej, ale moc na tom nezalezi,
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
    }

    /**
     * Kontroluje koliziu medzi dvomi objektami
     * 
     * @param prvyObjekt Array 4 integerov prveho objektu v poradi: lavy dolny x, lavy dolny y, pravy horny x, pravy horny y
     * @param druhyObjekt Array 4 integerov druheho objektu v poradi: lavy dolny x, lavy dolny y, pravy horny x, pravy horny y
     * @param smer Smer prveho objektu
     * @return Vzdialenost, o ktoru sa prvy objekt musi vratit aby bol mimo steny
     */
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

    /**
     * Ak sa nachadza bodka na suradniciach hraca, tak sa skryje
     * 
     * @param postavaLavyDolnyX Suradnica x laveho dolneho rohu postavy
     * @param postavaLavyDolnyY Suradnica y laveho dolneho rohu postavy
     * @return Typ bodky, ktoru hrac zjedol
     */
    public TypBodky zjedzBodku(int postavaLavyDolnyX, int postavaLavyDolnyY) {
        // uprava suradnic hraca na suradnice v mriezke bodiek
        postavaLavyDolnyX -= this.pozadie.getLavyDolnyX() + 14 - (int)(Bodka.VZDIALENOST_MEDZI_BODKAMI / 2);
        postavaLavyDolnyY -= this.pozadie.getLavyDolnyY() + 14 - (int)(Bodka.VZDIALENOST_MEDZI_BODKAMI / 2);
        int suradnicaHracaX = (int)((double)postavaLavyDolnyX / Bodka.VZDIALENOST_MEDZI_BODKAMI);
        int suradnicaHracaY = (int)((double)postavaLavyDolnyY / Bodka.VZDIALENOST_MEDZI_BODKAMI);

        // ak je hrac mimo pola bodiek tak ignoruj
        // nastava v pripade prechodu cez warp tunnel
        if (suradnicaHracaX < 0 || suradnicaHracaX > this.bodky[0].length - 1) {
            return TypBodky.ZIADNA;
        }

        if (this.bodky[suradnicaHracaY][suradnicaHracaX] != null) {
            if (!this.bodky[suradnicaHracaY][suradnicaHracaX].jeZjedena()) {
                this.bodky[suradnicaHracaY][suradnicaHracaX].zjedz();
                this.pocetZostavajucichBodiek--;
                if (this.bodky[suradnicaHracaY][suradnicaHracaX].getTypBodky() == TypBodky.VELKA_BODKA) {
                    this.manazerDuchov.vynulujNasobicSkoreJedeniaDucha();
                }
                return this.bodky[suradnicaHracaY][suradnicaHracaX].getTypBodky();
            }
        }

        // nastava ak sa na policku nenachadza bodka
        return TypBodky.ZIADNA;
    }

    /**
     * @return Pocet bodiek, ktore su este nezjedene
     */
    public int getPocetZostavajucichBodiek() {
        return this.pocetZostavajucichBodiek;
    }

    /**
     * Obnovi vsetky bodky a zobrazi ich na platne
     */
    public void obnovBodky() {
        for (Bodka[] bodky2 : this.bodky) {
            for (Bodka bodka : bodky2) {
                if (bodka != null) {
                    bodka.obnov();
                }
            }
        }
        this.pocetZostavajucichBodiek = this.pocetMalychBodiek + this.pocetVelkychBodiek;
    }

    /**
     * Nacita pozicie a velkosti stien zo suboru level.level
     */
    private void nacitajLevel() {
        int offsetX = this.pozadie.getLavyDolnyX();
        int offsetY = this.pozadie.getLavyDolnyY();

        // nacitavanie pozicii a velkosti stien zo suboru level.level
        // suradnice v tomto subore su relativne k lavemu dolnemu rohu pozadia
        try {
            Scanner scanner = new Scanner(new File("level.level"));
            scanner.nextLine(); // prvy riadok v level.level je len vysvetlenie syntaxe
            this.steny = new Stena[scanner.nextInt()];
            scanner.nextLine();
            for (int i = 0; i < this.steny.length; i++) {
                this.steny[i] = new Stena(offsetX + scanner.nextInt(), offsetY + scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                scanner.nextLine();
            }
            scanner.close(); 
        } catch (Exception e) {
            System.out.println(e);
        }
          
    }

    /*
     * Nacitava pozicie a typy bodiek zo suboru bodky.level a zobrazuje ich na platne
     */
    private void nacitajBodky() {
        // oznacuje x a y suradnice bodky v 0-tom riadku a 0-tom stlpci
        // 39 = sirka krajnej steny + polovica sirky priestoru pre hraca (14 + 25)
        double offsetX = this.pozadie.getLavyDolnyX() + 39 - Bodka.VELKOST / 2 - 1;
        double offsetY = this.pozadie.getLavyDolnyY() + 39 - Bodka.VELKOST / 2;
        
        try {
            Scanner scanner = new Scanner(new File("bodky.level"));

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
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }

    /**
     * Testuje, ci hrac je v kolizii s duchom
     * 
     * @param hracLavyDolnyX Suradnica x laveho dolneho rohu hraca
     * @param hracLavyDolnyY Suradnica y laveho dolneho rohu hraca
     * @param velkostObrazka Velkost obrazku hraca
     * @return True ak hrac je v kolizii s duchom a duch sa neda zjest, False ak nie je v kolizii s duchom, alebo hrac ducha zjedol
     */
    public boolean koliziaSDuchom(int hracLavyDolnyX, int hracLavyDolnyY, int velkostObrazka) {
        // uprava na mensi hitbox
        hracLavyDolnyX -= (this.zmensenaVelkostHitboxuPostavy - velkostObrazka) / 2;
        hracLavyDolnyY -= (this.zmensenaVelkostHitboxuPostavy - velkostObrazka) / 2;
        int[] suradniceHraca = new int[] {hracLavyDolnyX, hracLavyDolnyY, hracLavyDolnyX + this.zmensenaVelkostHitboxuPostavy, hracLavyDolnyY + this.zmensenaVelkostHitboxuPostavy};
        for (Postava duch : this.duchovia) {
            int duchLavyDolnyX = duch.getLavyDolnyX() - (this.zmensenaVelkostHitboxuPostavy - duch.getVelkostObrazka()) / 2;
            int duchLavyDolnyY = duch.getLavyDolnyY() - (this.zmensenaVelkostHitboxuPostavy - duch.getVelkostObrazka()) / 2;
            int[] suradniceDucha = new int[] {duchLavyDolnyX, duchLavyDolnyY, 
                                              duchLavyDolnyX + this.zmensenaVelkostHitboxuPostavy, duchLavyDolnyY + this.zmensenaVelkostHitboxuPostavy};
            if (this.checkKolizia(suradniceHraca, suradniceDucha, Smer.DOLE) != 0) {
                if (duch.daSaZjest()) {
                    // zjedenie ducha ak hrac je v kolizii s duchom a duch sa da zjest
                    this.manazerDuchov.zjedzDucha(duch);
                    return false;
                } else {
                    return true;
                }
                
            }
        }
        return false;
    }

    /**
     * @return Array s bodkami, ktore su velke
     */
    public Bodka[] getVelkeBodky() {
        return this.velkeBodky;
    }

    /**
     * Nastavi referencie na duchov
     * 
     * @param duchovia Vsetci duchovia, pre ktorych ma Kolizia kontrolovat koliziu
     */
    public void setDuchovia(Postava[] duchovia) {
        this.duchovia = duchovia;
    }

    /**
     * @param manazerDuchov Manazer duchov, s ktorym ma Kolizia komunikovat pri zjedeni ducha
     */
    public void setManazerDuchov(ManazerDuchov manazerDuchov) {
        this.manazerDuchov = manazerDuchov;
    }

}
