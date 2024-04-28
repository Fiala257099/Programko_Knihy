public class Učebnice extends Kniha 
{
    private int vhodny_rocnik;

    public Učebnice(String nazev, String autor, int rok_vydani, boolean stav_dostupnosti, int vhodny_rocnik) 
    {
        super(nazev, autor, rok_vydani, stav_dostupnosti);
        this.stavDostupnosti = true;
    }

    public int getVhodny_Rocnik() 
    {
        return vhodny_rocnik; 
    }
}
