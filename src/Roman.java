public class Roman extends Kniha 
{
    private String zanr;

    public Roman(String nazev, String autor, int rok_vydani, boolean stav_dostupnosti, String zanr) 
    {
        super(nazev, autor, rok_vydani, stav_dostupnosti);
        this.zanr = zanr;
    }

    public String getZanr() 
    {
        return zanr;
    }
}
