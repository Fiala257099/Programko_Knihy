public class Roman extends Kniha 
{
    public enum Zanr 
    {
        HISTORICKY,
        DOBRODRUZNY,
        DETEKTIVNI,
        FANTASY,
        SCI_FI
    }
    
    private Zanr zanr;

    public Roman(String nazev, String autor, int rok_vydani, boolean stav_dostupnosti, Zanr zanr) 
    {
        super(nazev, autor, rok_vydani, stav_dostupnosti);
        this.zanr = zanr;
    }

    public Zanr getZanr() 
    {
        return zanr;
    }
}
