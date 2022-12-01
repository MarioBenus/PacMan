
/**
 * Enumeration class Smer - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum Smer {
    HORE(0, 1, 0),
    DOLE(0, -1, 180),
    VPRAVO(1, 0, 90),
    VLAVO(-1, 0, 270),
    ZIADNY(0, 0, 0);

    private final int nasobicX;
    private final int nasobicY;
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
