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
                else if (kniha instanceof Učebnice) 
                {
                    psUcebnice.setString(1, kniha.getNazevKnihy());
                    psUcebnice.setString(2, kniha.getAutoraKnihy());
                    psUcebnice.setInt(3, kniha.getRokVydaniKnihy());
                    psUcebnice.setInt(4, ((Učebnice) kniha).getVhodny_Rocnik());
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

public Map<String, Kniha> nacteniKnihZDatabaze() {
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
            Učebnice ucebnice = new Učebnice(nazev, autor, rok, dostupnost, rocnik);
            knihy.put(nazev, ucebnice);
        }

        System.out.println("Všechny knihy byly úspěšně načteny z databáze.");
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return knihy;
}

public void odstranitKnihuZDatabaze(String nazevKnihy) {
    Map<String, Kniha> knihy = new HashMap<>();
    String sqlOdstranitRoman = "DELETE FROM roman WHERE nazev = ?";
    String sqlOdstranitUcebnice = "DELETE FROM ucebnice WHERE nazev = ?";

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db/test.db");
            PreparedStatement psOdstranitRoman = conn.prepareStatement(sqlOdstranitRoman);
            PreparedStatement psOdstranitUcebnice = conn.prepareStatement(sqlOdstranitUcebnice);)

    {

       
        boolean isRoman = knihy.get(nazevKnihy) instanceof Roman;

        
        if (isRoman) {
            psOdstranitRoman.setString(1, nazevKnihy);
            psOdstranitRoman.executeUpdate();
        } else {
            psOdstranitUcebnice.setString(1, nazevKnihy);
            psOdstranitUcebnice.executeUpdate();
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