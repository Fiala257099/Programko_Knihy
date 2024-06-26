import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class DBConnection {
    private Connection connection;

    public boolean getDBConnection() {
        if (connection == null)
        {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:db/test.db"); 
                
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                return  false;
            }
        }
        return true;
    }

    public void createTables() {
        String roman = "CREATE TABLE IF NOT EXISTS roman (ID int, nazev varchar(50), autor varchar(50), rok int, zanr varchar, dostupnost varchar);";
        String ucebnice = "CREATE TABLE IF NOT EXISTS ucebnice (ID int, nazev varchar(50), autor varchar(50), rok int, rocnik varchar, dostupnost varchar);";
        
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(roman);
            stmt.execute(ucebnice);
            System.out.println("Tabulky byly vytvořeny, pokud neexistují.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

public void ulozeniKnihDoDatabaze(Map<String, Kniha> knihy) 
    {
        String sqlRoman = "INSERT INTO roman (nazev, autor, rok, zanr, dostupnost) VALUES (?, ?, ?, ?, ?)";
        String sqlUcebnice = "INSERT INTO ucebnice (nazev, autor, rok, rocnik, dostupnost) VALUES (?, ?, ?, ?, ?)";
        
        try {
             PreparedStatement psRoman = connection.prepareStatement(sqlRoman);
             PreparedStatement psUcebnice = connection.prepareStatement(sqlUcebnice);
        
            {

            for (Kniha kniha : knihy.values()) 
            {
                if (kniha instanceof Roman) 
                {
                    psRoman.setString(1, kniha.getNazevKnihy());
                    psRoman.setString(2, kniha.getAutoraKnihy());
                    psRoman.setInt(3, kniha.getRokVydaniKnihy());
                    psRoman.setString(4, ((Roman) kniha).getZanr().toString());
                    psRoman.setBoolean(5, kniha.StavDostupnosti());
                    psRoman.addBatch();
                } 
                else if (kniha instanceof Ucebnice) 
                {
                    psUcebnice.setString(1, kniha.getNazevKnihy());
                    psUcebnice.setString(2, kniha.getAutoraKnihy());
                    psUcebnice.setInt(3, kniha.getRokVydaniKnihy());
                    psUcebnice.setInt(4, ((Ucebnice) kniha).getVhodny_Rocnik());
                    psUcebnice.setBoolean(5, kniha.StavDostupnosti());
                    psUcebnice.addBatch();
                }
            }
        }
            psRoman.executeBatch();
            psUcebnice.executeBatch();

            System.out.println("Všechny knihy byly úspěšně uloženy do databáze.");
    }
    
         
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        
    
    }

public Map<String, Kniha> nacteniKnihZDatabaze() 
{
    Map<String, Kniha> knihy = new HashMap<>();
    String sqlRoman = "SELECT nazev, autor, rok, zanr, dostupnost FROM roman";
    String sqlUcebnice = "SELECT nazev, autor, rok, rocnik, dostupnost FROM ucebnice";

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db/test.db");
        PreparedStatement psRoman = conn.prepareStatement(sqlRoman);
        PreparedStatement psUcebnice = conn.prepareStatement(sqlUcebnice);
        ResultSet rsRoman = psRoman.executeQuery();
        ResultSet rsUcebnice = psUcebnice.executeQuery()) {

        while (rsRoman.next()) {
            String nazev = rsRoman.getString("nazev");
            String autor = rsRoman.getString("autor");
            int rok = rsRoman.getInt("rok");
            Roman.Zanr zanr = Roman.Zanr.valueOf(rsRoman.getString("zanr"));
            boolean dostupnost = rsRoman.getBoolean("dostupnost");
            Roman roman = new Roman(nazev, autor, rok, dostupnost, zanr);
            knihy.put(nazev, roman);
        }

        while (rsUcebnice.next()) {
            String nazev = rsUcebnice.getString("nazev");
            String autor = rsUcebnice.getString("autor");
            int rok = rsUcebnice.getInt("rok");
            int rocnik = rsUcebnice.getInt("rocnik");
            boolean dostupnost = rsUcebnice.getBoolean("dostupnost");
            Ucebnice ucebnice = new Ucebnice(nazev, autor, rok, dostupnost, rocnik);
            knihy.put(nazev, ucebnice);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return knihy;
    
}

public void odstranitKnihuZDatabaze(String nazev_knihy) {

    Map<String, Kniha> knihy = nacteniKnihZDatabaze();
    String sqlOdstranitRoman = "DELETE FROM roman WHERE nazev = ?";
    String sqlOdstranitUcebnice = "DELETE FROM ucebnice WHERE nazev = ?";

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db/test.db");
            PreparedStatement psOdstranitRoman = conn.prepareStatement(sqlOdstranitRoman);
            PreparedStatement psOdstranitUcebnice = conn.prepareStatement(sqlOdstranitUcebnice);)

    {
        
        if (knihy.containsKey(nazev_knihy)) {
            
            boolean isRoman = knihy.get(nazev_knihy) instanceof Roman;
        
            if (isRoman) {
                psOdstranitRoman.setString(1, nazev_knihy);
                psOdstranitRoman.executeUpdate();
            } else {
                psOdstranitUcebnice.setString(1, nazev_knihy);
                psOdstranitUcebnice.executeUpdate();
            }
        } else {
            System.out.println("Kniha s názvem '" + nazev_knihy + "' nebyla nalezena v databázi.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

}
public void aktualizujNazevKnihy(String staryNazev, String novyNazev) {
    String sqlRoman = "UPDATE roman SET nazev = ? WHERE nazev = ?";
    String sqlUcebnice = "UPDATE ucebnice SET nazev = ? WHERE nazev = ?";
    
    try {
        PreparedStatement psRoman = connection.prepareStatement(sqlRoman);
        PreparedStatement psUcebnice = connection.prepareStatement(sqlUcebnice);
        
        // Aktualizuj záznamy v databázi
        psRoman.setString(1, novyNazev);
        psRoman.setString(2, staryNazev);
        psRoman.executeUpdate();
        
        psUcebnice.setString(1, novyNazev);
        psUcebnice.setString(2, staryNazev);
        psUcebnice.executeUpdate();
        
        System.out.println("Název knihy byl aktualizován v databázi.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void aktualizujAutoraKnihy(String nazev_knihy, String novyAutor) {
    String sqlRoman = "UPDATE roman SET autor = ? WHERE nazev = ?";
    String sqlUcebnice = "UPDATE ucebnice SET autor = ? WHERE nazev = ?";
    
    try {
        PreparedStatement psRoman = connection.prepareStatement(sqlRoman);
        PreparedStatement psUcebnice = connection.prepareStatement(sqlUcebnice);
        
    
        psRoman.setString(1, novyAutor);
        psRoman.setString(2, nazev_knihy);
        psRoman.executeUpdate();
        
        psUcebnice.setString(1, novyAutor);
        psUcebnice.setString(2, nazev_knihy);
        psUcebnice.executeUpdate();
        
        System.out.println("Autor knihy byl aktualizován v databázi.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void aktualizujRokVydaniKnihy(String nazev_knihy, int novyRokVydani) {
    String sqlRoman = "UPDATE roman SET rok = ? WHERE nazev = ?";
    String sqlUcebnice = "UPDATE ucebnice SET rok = ? WHERE nazev = ?";
    
    try {
        PreparedStatement psRoman = connection.prepareStatement(sqlRoman);
        PreparedStatement psUcebnice = connection.prepareStatement(sqlUcebnice);
        
        
        psRoman.setInt(1, novyRokVydani);
        psRoman.setString(2, nazev_knihy);
        psRoman.executeUpdate();
        
        psUcebnice.setInt(1, novyRokVydani);
        psUcebnice.setString(2, nazev_knihy);
        psUcebnice.executeUpdate();
        
        System.out.println("Rok vydání knihy byl aktualizován v databázi.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void aktualizujStavDostupnostiKnihy(String nazev_knihy, boolean novyStav) {
    String sqlUcebnice = "UPDATE ucebnice SET dostupnost = ? WHERE nazev = ?";
    String sqlRoman = "UPDATE roman SET dostupnost = ? WHERE nazev = ?";
    
    try {
        PreparedStatement psUcebnice = connection.prepareStatement(sqlUcebnice);
        PreparedStatement psRoman = connection.prepareStatement(sqlRoman);
        
        psRoman.setBoolean(1, novyStav);
        psRoman.setString(2, nazev_knihy);
        psRoman.executeUpdate();
        
        psUcebnice.setBoolean(1, novyStav);
        psUcebnice.setString(2, nazev_knihy);
        psUcebnice.executeUpdate();
        
    
        for (Kniha kniha : nacteniKnihZDatabaze().values()) {
            if (kniha.getNazevKnihy().equals(nazev_knihy)) {
                kniha.setStavDostupnostiKnihy(novyStav);
                break; 
            }
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}








public void closeConnection() { 
    try {
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}