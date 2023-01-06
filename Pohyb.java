public class Pohyb {
    private Kolizia kolizia;

    public Pohyb(Kolizia kolizia) {
        this.kolizia = kolizia;
    }

    // udrziavanie hraca mimo stien -- ked sa hrac dostane do steny, tak ho to posunie z nej von
    public void koliziaStien(Postava postava) {
        int korekcia = this.kolizia.checkKoliziaStena(postava.getX(), postava.getY(), postava.getVelkostObrazka(), postava.getSmer());
        if (postava.getSmer() == Smer.VPRAVO || postava.getSmer() == Smer.VLAVO) {
            postava.posunVodorovne(korekcia);
        } else if (postava.getSmer() == Smer.HORE || postava.getSmer() == Smer.DOLE) {
            postava.posunZvisle(korekcia);
        }
    }

    public void zmenaSmeru(Smer pozadovanySmer, Postava postava) {
        if (pozadovanySmer != postava.getSmer()) { // tieto 2 if-y su samostatne aby sa zbytocne nekontrolovala kolizia ked nemusi
            if (this.kolizia.checkVolnySmer(postava.getX(), postava.getY(), pozadovanySmer)) {

                // posun hraca do volneho smeru
                postava.posunVodorovne(postava.getRychlostPohybu() * pozadovanySmer.getNasobicX());
                postava.posunZvisle(postava.getRychlostPohybu() * pozadovanySmer.getNasobicY());     
                // hrac musi byt posunuty este trochu do stareho smeru, aby korekcia nastala vzhladom na spravnu stenu 
                postava.posunVodorovne(2 * postava.getRychlostPohybu() * postava.getSmer().getNasobicX());
                postava.posunZvisle(2 * postava.getRychlostPohybu() * postava.getSmer().getNasobicY());
                
                this.koliziaStien(postava);
    
                postava.setSmer(pozadovanySmer);    
            }
        }
    }
    


}
