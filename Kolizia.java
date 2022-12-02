
/**
 * Write a description of class Kolizia here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Kolizia {
    private Stena[] steny;

    private final int velkostHraca = 50;

    public Kolizia(Stena[] steny) {
        this.steny = steny;
    }

    public int checkKoliziaStena(int hracLavyDolnyX, int hracLavyDolnyY, Smer smer) {
        // chcem aby hitbox hraca bol 50x50, aj ked jeho skutocna sirka je 44x44
        hracLavyDolnyX -= 3;
        hracLavyDolnyY -= 3;

        for (Stena stena : this.steny) {
            int hracPravyHornyY = hracLavyDolnyY + this.velkostHraca;
            int hracPravyHornyX = hracLavyDolnyX + this.velkostHraca;
            
            // test ci je hrac vnutri steny
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
            }
        }

        return 0;
    }

    public boolean checkVolnySmer(int hracLavyDolnyX, int hracLavyDolnyY, Smer bufferedSmer) {
        // chcem aby hitbox bol mensi ako 50x50, pretoze hrac sa pohybuje po 4 pixeloch a nechcem
        // aby nahodou "preskocil" tu medzeru
        int testovanyHitboxLavyDolnyX = hracLavyDolnyX;
        int testovanyHitboxLavyDolnyY = hracLavyDolnyY;
        int testovanyHitboxPravyHornyX = hracLavyDolnyX + 44;
        int testovanyHitboxPravyHornyY = hracLavyDolnyY + 44;

        // posun hitboxu do smeru kam chce hrac ist
        // 10 je arbitrarna hodnota, mohla by byt o nieco viac aj menej, ale nezalezi na tom
        switch (bufferedSmer) {
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
            if (testovanyHitboxPravyHornyY > stena.getLavyDolnyY() && testovanyHitboxLavyDolnyY < stena.getPravyHornyY() &&
                testovanyHitboxPravyHornyX > stena.getLavyDolnyX() && testovanyHitboxLavyDolnyX < stena.getPravyHornyX()) {

                return false;
            }
        }
        return true;
    }


}
