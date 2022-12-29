
// smer pohybu hraca alebo ducha
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

    public int getNasobicX() {
        return this.nasobicX;
    }

    public int getNasobicY() {
        return this.nasobicY;
    }
    
    public int getUhol() {
        return this.uhol;
    }



}
