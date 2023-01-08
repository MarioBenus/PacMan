/**
 * Smer pre pohyb
 */
public enum Smer {
    HORE(0, 1, 0),
    DOLE(0, -1, 180),
    VPRAVO(1, 0, 90),
    VLAVO(-1, 0, 270),
    ZIADNY(0, 0, 0);

    // nasobic urcuje smer pohybu, pri pohybe hraca a duchov sa vzdialenost posunutia
    // nasobi tymito nasobicmi
    private final int nasobicX;
    private final int nasobicY;

    // uhol na ktory sa musi nastavit hrac pri tomto smere
    private final int uhol;

    Smer(int nasobicX, int nasobicY, int uhol)  {
        this.uhol = uhol;
        this.nasobicX = nasobicX;
        this.nasobicY = nasobicY;
    }

    /**
     * Nasobic pre posunVodorovne pri pohybe
     * @return -1 = dolava, 0 = nikde, 1 = doprava
     */
    public int getNasobicX() {
        return this.nasobicX;
    }

    /**
     * Nasobic pre posunZvisle pri pohybe
     * @return -1 = dole, 0 = nikde, 1 = hore
     */
    public int getNasobicY() {
        return this.nasobicY;
    }
    
    /**
     * @return Pocet stupov v smere hodinovych ruciciek, pre otocenie v dany smer
     */
    public int getUhol() {
        return this.uhol;
    }



}
