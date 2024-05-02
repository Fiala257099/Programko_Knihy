import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Databaze 
{
    private Map<String,Kniha>  prvkyDatabaze;


    Databaze()
    {
        prvkyDatabaze = new HashMap<String, Kniha>();
    }

    Databaze(Map<String, Kniha> knihyZDatabaze) 
    {
        this.prvkyDatabaze = knihyZDatabaze;
    }


    public void nacteniKnihZDatabaze()
    {
        Map<String, Kniha> databazoveKnihy = dbconnect.nacteniKnihZDatabaze();
        prvkyDatabaze.putAll(databazoveKnihy);
    }

    public void ulozeniKnihDoDatabaze()
    {
            dbconnect.ulozeniKnihDoDatabaze(prvkyDatabaze);
    }

    public void closeConnection() 
    {
        dbconnect.closeConnection(prvkyDatabaze);
    }

    public void setKniha(String nazev, String autor, int rok_vydani, boolean stav_dostupnosti)
    {
        Kniha kniha = new Kniha(nazev, autor, rok_vydani, false);
        this.prvkyDatabaze.put(nazev, kniha);
    }


    public void pridejRoman(String nazev, String autor, int rok_vydani, boolean stav_dostupnosti, Roman.Zanr zanr)
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


    public void zmenaNazvu(String staryNazev, String novyNazev) 
    {
        for (Map.Entry<String, Kniha> entry : prvkyDatabaze.entrySet()) 
        {
            Kniha kniha = entry.getValue();
            if (kniha.getNazevKnihy().equals(staryNazev)) 
            {
                kniha.setNazevKnihy(novyNazev);
                System.out.println("Název knihy aktualizován: " + kniha);
                return;
            }
        }
        System.out.println("Kniha s názvem " + staryNazev + " nebyla nalezená.");
    }
    
    public void zmenaAutora(String nazev_knihy, String novyAutor) 
    {
        Kniha kniha = prvkyDatabaze.get(nazev_knihy);
        if (kniha != null) 
        {
            kniha.setAutoraKnihy(novyAutor);
            System.out.println("Autor knihy aktualizován: " + kniha);
        } 
        else 
        {
            System.out.println("Kniha s názvem " + nazev_knihy + " nebyla nalezená.");
        }
    }
    
    public void zmenaRokuVydani(String nazev_knihy, int novyRokVydani)
    {
        Kniha kniha = prvkyDatabaze.get(nazev_knihy);
        if (kniha != null) 
        {
            kniha.setRokVydaniKnihy(novyRokVydani);
            System.out.println("Rok vydání knihy aktualizován: " + kniha);
        } 
        else 
        {
            System.out.println("Kniha s názvem " + nazev_knihy + " nebyla nalezená.");
        }
    }
    
    public void zmenaStavuDostupnosti(String nazev_knihy, boolean novyStav)
    {
        Kniha kniha = prvkyDatabaze.get(nazev_knihy);
        if (kniha != null)
        {
            kniha.setStavDostupnostiKnihy(novyStav);
            System.out.println("Stav dostupnosti knihy aktualizován: " + kniha);
        } 
        else
        {
            System.out.println("Kniha s názvem " + nazev_knihy + " nebyla nalezená.");
        }
    }

    public void odstranitKnihu(String nazev_knihy) 
    {
        if (prvkyDatabaze.containsKey(nazev_knihy)) 
        {
            prvkyDatabaze.remove(nazev_knihy);
            System.out.println("Kniha '" + nazev_knihy + "' byla úspěšně odstraněna.");
        } 
        else 
        {
            System.out.println("Kniha s názvem '" + nazev_knihy + "' nebyla nalezena.");
        }
    }    

    public void setStavDostupnosti(String nazev_knihy, boolean stav) 
    {
        Kniha kniha = prvkyDatabaze.get(nazev_knihy);
        if (kniha != null) 
        {
            kniha.setStavDostupnostiKnihy(stav);
            System.out.println("Stav dostupnosti knihy '" + nazev_knihy + "' byl aktualizován na: " + (stav ? "Dostupna" : "vypůjčena"));
        } 
        else 
        {
            System.out.println("Kniha s názvem '" + nazev_knihy + "' nebyla nalezena.");
        }
    }

    public void vypisVsechnyKnihy() 
    {
        List<Kniha> serazeneKnihy = new ArrayList<>(prvkyDatabaze.values());
        serazeneKnihy.sort((kniha1, kniha2) -> kniha1.getNazevKnihy().compareToIgnoreCase(kniha2.getNazevKnihy()));
    
        for (Kniha kniha : serazeneKnihy)
        {
            System.out.println("Název: " + kniha.getNazevKnihy());
            System.out.println("Autor: " + kniha.getAutoraKnihy());
            if (kniha instanceof Roman) 
            {
                System.out.println("Žánr: " + ((Roman) kniha).getZanr());
            } 
            else if (kniha instanceof Učebnice) 
            {
                System.out.println("Ročník: " + ((Učebnice) kniha).getVhodny_Rocnik());
            }
            System.out.println("Rok vydání: " + kniha.getRokVydaniKnihy());
            System.out.println("Stav dostupnosti: " + (kniha.StavDostupnosti() ? "dostupná" : "vypůjčená"));
            System.out.println();
        }
    }

    public void vyhledatKnihu(String nazev) 
    {
        boolean nalezeno = false;
    
        for (Kniha kniha : prvkyDatabaze.values()) 
        {
            if (kniha.getNazevKnihy().equalsIgnoreCase(nazev)) 
            {
                System.out.println("Název: " + kniha.getNazevKnihy());
                System.out.println("Autor: " + kniha.getAutoraKnihy());
                if (kniha instanceof Roman)
                {
                    System.out.println("Žánr: " + ((Roman) kniha).getZanr());
                } 
                else if (kniha instanceof Učebnice) 
                {
                    System.out.println("Ročník: " + ((Učebnice) kniha).getVhodny_Rocnik());
                }
                System.out.println("Rok vydání: " + kniha.getRokVydaniKnihy());
                System.out.println("Stav dostupnosti: " + (kniha.StavDostupnosti() ? "dostupná" : "vypůjčená"));
                System.out.println();
                nalezeno = true;
                break;
            }
        }
    
        if (!nalezeno) 
        {
            System.out.println("Kniha s názvem '" + nazev + "' nebyla nalezena.");
        }
    }
    

     public void vypisKnihAutora(String autor) 
     {
        List<Kniha> knihyAutora = new ArrayList<>();

        for (Kniha kniha : prvkyDatabaze.values()) 
        {
            if (kniha.getAutoraKnihy().equals(autor)) 
            {
                knihyAutora.add(kniha);
            }
        }

        Collections.sort(knihyAutora, (kniha1, kniha2) -> kniha1.getNazevKnihy().compareToIgnoreCase(kniha2.getNazevKnihy()));

        for (Kniha kniha : knihyAutora) 
        {
            System.out.println("Název: " + kniha.getNazevKnihy());
            System.out.println("Autor: " + kniha.getAutoraKnihy());
            if (kniha instanceof Roman) 
            {
                System.out.println("Žánr: " + ((Roman) kniha).getZanr());
            } 
            else if (kniha instanceof Učebnice) 
            {
                System.out.println("Ročník: " + ((Učebnice) kniha).getVhodny_Rocnik());
            }
            System.out.println("Rok vydání: " + kniha.getRokVydaniKnihy());
            System.out.println("Stav dostupnosti: " + (kniha.StavDostupnosti() ? "dostupná" : "vypůjčená"));
            System.out.println();
        }
    }



    public void vypisKnihyDleZanru(Roman.Zanr zanr)
     {
        List<Kniha> knihyZanru = new ArrayList<>();

        for (Kniha kniha : prvkyDatabaze.values()) 
        {
            if (kniha instanceof Roman && ((Roman) kniha).getZanr().equals(zanr))
            {
                knihyZanru.add(kniha);
            }
        }

        Collections.sort(knihyZanru, (kniha1, kniha2) -> kniha1.getNazevKnihy().compareToIgnoreCase(kniha2.getNazevKnihy()));

        for (Kniha kniha : knihyZanru) 
        {
            System.out.println("Název: " + kniha.getNazevKnihy());
            System.out.println("Autor: " + kniha.getAutoraKnihy());
            System.out.println("Žánr: " + ((Roman) kniha).getZanr());
            System.out.println("Rok vydání: " + kniha.getRokVydaniKnihy());
            System.out.println("Stav dostupnosti: " + (kniha.StavDostupnosti() ? "dostupná" : "vypůjčená"));
            System.out.println();
        }
    }



    public void vypisVypujnychKnih() {
        boolean zadneVypujcene = true;
        for (Kniha kniha : prvkyDatabaze.values()) {
            if (!kniha.StavDostupnosti()) {
                zadneVypujcene = false;
                if (kniha instanceof Roman) {
                    System.out.println("Název vypůjčeného románu: " + kniha.getNazevKnihy());
                } else if (kniha instanceof Učebnice) {
                    System.out.println("Název vypůjčené učebnice: " + kniha.getNazevKnihy());
                }
            }
        }
        if (zadneVypujcene) {
            System.out.println("Žádná kniha není momentálně vypůjčená.");
        }
    }




    public boolean ulozeniKnihyDoSouboru(String nazevKnihy) {
        // Pevně definovaná složka, kam budou ukládány soubory
        String slozka = "knizky_v_souboru/";
    
        try {
            // Vytvoření složky, pokud neexistuje
            File folder = new File(slozka);
            if (!folder.exists()) {
                folder.mkdirs();
            }
    
            // Získání knihy z databáze
            Kniha kniha = prvkyDatabaze.get(nazevKnihy);
            if (kniha == null) {
                System.out.println("Kniha s názvem " + nazevKnihy + " nebyla nalezena v databázi.");
                return false;
            }
    
            // Sestavení názvu souboru
            String nazevSouboru = slozka + nazevKnihy + ".txt";
    
            // Otevření souboru pro zápis
            FileWriter fw = new FileWriter(nazevSouboru);
            BufferedWriter out = new BufferedWriter(fw);
    
            // Zápis informací o knize do souboru
            out.write("Název knihy: " + kniha.getNazevKnihy());
            out.newLine();
            out.write("Autor: " + kniha.getAutoraKnihy());
            out.newLine();
            out.write("Rok vydání: " + kniha.getRokVydaniKnihy());
            out.newLine();
            out.write("Stav dostupnosti: " + (kniha.StavDostupnosti() ? "dostupná" : "vypůjčená"));
            out.newLine();
            if (kniha instanceof Roman) {
                out.write("Žánr: " + ((Roman) kniha).getZanr());
                out.newLine();
            } else if (kniha instanceof Učebnice) {
                out.write("Ročník: " + ((Učebnice) kniha).getVhodny_Rocnik());
                out.newLine();
            }
    
            // Uzavření souboru
            out.close();
    
            System.out.println("Kniha byla úspěšně uložena do souboru: " + nazevSouboru);
            return true;
    
        } catch (IOException e) {
            System.out.println("Chyba při ukládání databáze: " + e.getMessage());
            return false;
        }
    }

    public boolean nacteniKnihyZeSouboru(String nazevKnihy) {
        String slozka = "knizky_v_souboru/";

        try {
            File folder = new File(slozka);
            if (!folder.exists()) {
                System.out.println("Složka s databází neexistuje.");
                return false;
            }
            File[] files = folder.listFiles();
            if (files == null) {
                System.out.println("Složka s databází neobsahuje žádné soubory.");
                return false;
            }
            for (File file : files) {
                if (file.isFile()) {
                    String nazevSouboru = file.getName().replace(".txt", ""); // Získá název knihy z názvu souboru
                    if (nazevSouboru.equals(nazevKnihy)) {
                        FileReader fr = new FileReader(file);
                        BufferedReader in = new BufferedReader(fr);
                        String line;
                        String autor = null;
                        int rokVydani = 0;
                        boolean stavDostupnosti = false;
                        int rocnik = 0;
                        Roman.Zanr zanr = null;
                        while ((line = in.readLine()) != null) {
                            String[] parts = line.split(":");
                            if (parts.length == 2) {
                                String key = parts[0].trim();
                                String value = parts[1].trim();
                                switch (key) {
                                    case "Název knihy":
                                        // Už máme název knihy, není potřeba ho znovu nastavovat
                                        break;
                                    case "Autor":
                                        autor = value;
                                        break;
                                    case "Rok vydání":
                                        rokVydani = Integer.parseInt(value);
                                        break;
                                    case "Stav dostupnosti":
                                        stavDostupnosti = value.equals("dostupná");
                                        break;
                                    case "Ročník":
                                        rocnik = Integer.parseInt(value);
                                        break;
                                    case "Žánr":
                                        zanr = Roman.Zanr.valueOf(value);
                                        break;
                                    default:
                                        System.out.println("Neznámý klíčový údaj: " + key);
                                }
                            }
                        }
                        in.close();
                        fr.close();
                        // Výpis načtených informací o knize do konzole
                        System.out.println("Název knihy: " + nazevKnihy);
                        System.out.println("Autor: " + autor);
                        System.out.println("Rok vydání: " + rokVydani);
                        System.out.println("Stav dostupnosti: " + (stavDostupnosti ? "dostupná" : "vypůjčená"));
                        if (zanr != null) {
                            System.out.println("Žánr: " + zanr);
                        } else if (rocnik != 0) {
                            System.out.println("Ročník: " + rocnik);
                        }
                        System.out.println("--------------------------------------");
                        return true; // Ukončení metody po vypsání informací o zadané knize
                    }
                }
            }
            System.out.println("Kniha s názvem " + nazevKnihy + " nebyla nalezena v databázi.");
            return false; // Pokud není kniha nalezena, vrátí se false
        } catch (IOException e) {
            System.out.println("Chyba při načítání databáze: " + e.getMessage());
            return false;
        }
    }
      



    
}