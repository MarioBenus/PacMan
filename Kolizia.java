
// kontroluje koliziu medzi objektmi
public class Kolizia {
    private Stena[] steny;
    private Bodka[][] bodky;

    private final int velkostHraca = 50;

    private Obrazok pozadie;

    public Kolizia(Stena[] steny, Obrazok pozadie, Bodka[][] bodky) {
        this.bodky = bodky;
        this.steny = steny;
        this.pozadie = pozadie;
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

            
            /*// test ci je hrac vnutri steny
            if (hracPravyHornyY > stena.getLavyDolnyY() && hracLavyDolnyY < stena.getPravyHornyY() &&
                hracPravyHornyX > stena.getLavyDolnyX() && hracLavyDolnyX < stena.getPravyHornyX()) {
                    
                // return o kolko sa ma hrac posunut naspat
                switch (smer) {
                    case VPRAVO:
                        return stena.getLavyDolnyX() - hracPravyHornyX;
                    case VLAVO:
                        return stena.getPravyHornyX() - hracLavyDolnyX;
                    case HORE:
                        return stena.getLavyDolnyY() - hracPravyHornyY;
                    case DOLE:
                        return stena.getPravyHornyY() - hracLavyDolnyY;
                    default:
                        return 0;
                }
            }*/
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
        // TODO: duplicitne s checkKoliziaStena
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
                    
            // return ako moc je prvy objekt v druhom objekte
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


}
