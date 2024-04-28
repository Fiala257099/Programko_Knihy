public class Kniha 
{
    String nazev;
    String autor;
    int rok_vydani;
    boolean stav_dostupnosti;

    
    public Kniha(String nazev, String autor, int rok_vydani , boolean stav_dostupnosti) 
    {
        this.nazev = nazev;
        this.autor = autor;
        this.rok_vydani = rok_vydani;
        this.stav_dostupnosti = stav_dostupnosti;
    }


    public String getNazevKnihy()
    {
        return nazev;
    }


    public void setNazevKnihy(String nazev) 
    {
        this.nazev = nazev;
    }


    public String getAutoraKnihy() 
    {
        return autor;
    }
    
    public void setAutoraKnihy(String autor) 
    {
        this.autor = autor;
    }
    
    
    
    public int getRokVydaniKnihy()
    {
        return rok_vydani;
    }


    public void setRokVydaniKnihy(int rok_vydani) 
    {
        this.rok_vydani = rok_vydani;
    }
    
    public boolean StavDostupnosti() 
    {
        return stav_dostupnosti;
    }

  
    public void setStavDostupnostiKnihy(boolean stav_dostupnosti) 
    {
        this.stav_dostupnosti = stav_dostupnosti;
    }


    // Příklad použití třídy Kniha
    public static void main(String args) {
        Kniha kniha1 = new Kniha("Java pro začátečníky", 2020, "John Smith", true);

        System.out.println("Informace o knize:");
        System.out.println("Název: " + kniha1.getNazev());
        System.out.println("Rok vydání: " + kniha1.getRokVydani());
        System.out.println("Autor: " + kniha1.getAutor());
        System.out.println("Stav dostupnosti: " + (kniha1.StavDostupnosti() ? "Dostupná" : "Nedostupná"));
    }
}
