import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class dbconnect {

    private static Connection dbConnection = null;

    public static Connection getConnection() {
        if (dbConnection == null) {
            synchronized (dbconnect.class) {
                if (dbConnection == null) {
                    try {
                        Class.forName("org.sqlite.JDBC");
                        dbConnection = DriverManager.getConnection("jdbc:sqlite:db/test.db");
                        createTables(); // Vytvoření tabulek, pokud neexistují
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace(); // log it
                    }
                }
            }
        }
        return dbConnection;
    }
    
    private static void createTables() {
        String createRomanTable = "CREATE TABLE IF NOT EXISTS romany (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nazev TEXT NOT NULL," +
            "autor TEXT NOT NULL," +
            "rok INTEGER NOT NULL," +
            "zanr TEXT NOT NULL," +
            "dostupnost INTEGER NOT NULL)";
        String createUcebniceTable = "CREATE TABLE IF NOT EXISTS ucebnice (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nazev TEXT NOT NULL," +
            "autor TEXT NOT NULL," +
            "rok INTEGER NOT NULL," +
            "rocnik INTEGER NOT NULL," +
            "dostupnost INTEGER NOT NULL)";
    
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            // Vytvoření tabulek
            stmt.execute(createRomanTable);
            stmt.execute(createUcebniceTable);
            System.out.println("Tabulky byly vytvořeny (pokud neexistují).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    // Metoda pro uložení všech knih do databáze
    public static void ulozeniKnihDoDatabaze(Map<String, Kniha> knihy) {
        String sqlRoman = "INSERT INTO romany (nazev, autor, rok, zanr, dostupnost) VALUES (?, ?, ?, ?, ?)";
        String sqlUcebnice = "INSERT INTO ucebnice (nazev, autor, rok, rocnik, dostupnost) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement psRoman = conn.prepareStatement(sqlRoman);
             PreparedStatement psUcebnice = conn.prepareStatement(sqlUcebnice)) {

            for (Kniha kniha : knihy.values()) {
                if (kniha instanceof Roman) {
                    psRoman.setString(1, kniha.getNazevKnihy());
                    psRoman.setString(2, kniha.getAutoraKnihy());
                    psRoman.setInt(3, kniha.getRokVydaniKnihy());
                    psRoman.setString(4, ((Roman) kniha).getZanr().toString());
                    psRoman.setBoolean(5, kniha.StavDostupnosti());
                    psRoman.addBatch();
                } else if (kniha instanceof Učebnice) {
                    psUcebnice.setString(1, kniha.getNazevKnihy());
                    psUcebnice.setString(2, kniha.getAutoraKnihy());
                    psUcebnice.setInt(3, kniha.getRokVydaniKnihy());
                    psUcebnice.setInt(4, ((Učebnice) kniha).getVhodny_Rocnik());
                    psUcebnice.setBoolean(5, kniha.StavDostupnosti());
                    psUcebnice.addBatch();
                }
            }

            // Provedení dávkového vkládání
            psRoman.executeBatch();
            psUcebnice.executeBatch();

            System.out.println("Všechny knihy byly úspěšně uloženy do databáze.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metoda pro načtení všech knih z databáze
    public static Map<String, Kniha> nacteniKnihZDatabaze() {
        Map<String, Kniha> knihy = new HashMap<>();
        String sqlRoman = "SELECT nazev, autor, rok, zanr, dostupnost FROM romany";
        String sqlUcebnice = "SELECT nazev, autor, rok, rocnik, dostupnost FROM ucebnice";

        try (Connection conn = getConnection();
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

    public static void closeConnection(Map<String, Kniha> knihy) {
            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    System.out.println("Spojení s databází bylo úspěšně uzavřeno.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }