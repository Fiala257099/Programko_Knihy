import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Databaze {
    Databaze()
    {
        prvkyDatabaze = new HashMap<String, Kniha>();
    }

    public void setKniha(String nazev, String autor, int rok_vydani, boolean stav_dostupnosti)
    {
        Kniha kniha = new Kniha(nazev, autor, rok_vydani, false);
        this.prvkyDatabaze.put(nazev, kniha);
    }


    public void pridejRoman(String nazev, String autor, int rok_vydani, boolean stav_dostupnosti, String zanr)
    {
        Roman roman = new Roman(nazev, autor, rok_vydani, stav_dostupnosti, zanr);
        this.prvkyDatabaze.put(nazev, roman);
    }

    public void pridejUcebnici(String nazev, String autor, int rok_vydani, boolean stav_dostupnosti, int vhodny_rocnik)
    {
        Učebnice učebnice = new Učebnice(nazev, autor, rok_vydani, stav_dostupnosti, vhodny_rocnik);
        this.prvkyDatabaze.put(nazev, učebnice);
    }



    public Kniha getKniha(int typ, String jmeno_autora)
    {
        return prvkyDatabaze.get(jmeno_autora);
    }


    public void zmenaNazvu(String staryNazev, String novyNazev) {
        for (Map.Entry<String, Kniha> entry : prvkyDatabaze.entrySet()) {
            Kniha kniha = entry.getValue();
            if (kniha.getNazevKnihy().equals(staryNazev)) {
                kniha.setNazevKnihy(novyNazev);
                System.out.println("Název knihy aktualizován: " + kniha);
                return;
            }
        }
        System.out.println("Kniha s názvem " + staryNazev + " nenalezena.");
    }
    
    public void zmenaAutora(String nazev_knihy, String novyAutor) {
        Kniha kniha = prvkyDatabaze.get(nazev_knihy);
        if (kniha != null) {
            kniha.setAutoraKnihy(novyAutor);
            System.out.println("Autor knihy aktualizován: " + kniha);
        } else {
            System.out.println("Kniha s názvem " + nazev_knihy + " nenalezena.");
        }
    }
    
    public void zmenaRokuVydani(String nazev_knihy, int novyRokVydani) {
        Kniha kniha = prvkyDatabaze.get(nazev_knihy);
        if (kniha != null) {
            kniha.setRokVydaniKnihy(novyRokVydani);
            System.out.println("Rok vydání knihy aktualizován: " + kniha);
        } else {
            System.out.println("Kniha s názvem " + nazev_knihy + " nenalezena.");
        }
    }
    
    public void zmenaStavuDostupnosti(String nazev_knihy, boolean novyStav) {
        Kniha kniha = prvkyDatabaze.get(nazev_knihy);
        if (kniha != null) {
            kniha.setStavDostupnostiKnihy(novyStav);
            System.out.println("Stav dostupnosti knihy aktualizován: " + kniha);
        } else {
            System.out.println("Kniha s názvem " + nazev_knihy + " nenalezena.");
        }
    }

    public void odstranitKnihu(String nazev_knihy) {
        if (prvkyDatabaze.containsKey(nazev_knihy)) {
            prvkyDatabaze.remove(nazev_knihy);
            System.out.println("Kniha '" + nazev_knihy + "' byla úspěšně odstraněna.");
        } else {
            System.out.println("Kniha s názvem '" + nazev_knihy + "' nebyla nalezena.");
        }
    }    

    public void setStavDostupnosti(String nazev_knihy, boolean stav) {
        Kniha kniha = prvkyDatabaze.get(nazev_knihy);
        if (kniha != null) {
            kniha.setStavDostupnostiKnihy(stav);
            System.out.println("Stav dostupnosti knihy '" + nazev_knihy + "' byl aktualizován na: " + (stav ? "Dostupna" : "vypůjčena"));
        } else {
            System.out.println("Kniha s názvem '" + nazev_knihy + "' nebyla nalezena.");
        }
    }

    public void vypisVsechnyKnihy() {
        List<Kniha> serazeneKnihy = new ArrayList<>(prvkyDatabaze.values());
        serazeneKnihy.sort((kniha1, kniha2) -> kniha1.getNazevKnihy().compareToIgnoreCase(kniha2.getNazevKnihy()));
    
        for (Kniha kniha : serazeneKnihy) {
            System.out.println("Název: " + kniha.getNazevKnihy());
            System.out.println("Autor(i): " + kniha.getAutoraKnihy());
            if (kniha instanceof Roman) {
                System.out.println("Žánr: " + ((Roman) kniha).getZanr());
            } else if (kniha instanceof Učebnice) {
                System.out.println("Ročník: " + ((Učebnice) kniha).getVhodny_Rocnik());
            }
            System.out.println("Rok vydání: " + kniha.getRokVydaniKnihy());
            System.out.println("Stav dostupnosti: " + (kniha.StavDostupnosti() ? "dostupná" : "vypůjčená"));
            System.out.println();
        }
    }
    
    
    
    	private Map<String,Kniha>  prvkyDatabaze;
}
