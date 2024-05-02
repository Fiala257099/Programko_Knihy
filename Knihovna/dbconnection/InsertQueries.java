import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertQueries {

    public InsertQueries() {}

    public void performInsertQuery(String insertQuery) {
        if (insertQuery == null) {
            throw new NullPointerException("Query must not be null!");
        } else if (insertQuery.isEmpty()) {
            throw new IllegalArgumentException("Query must not be empty!");
        }
        Connection conn = dbconnect.getConnection();
        try (PreparedStatement prStmt = conn.prepareStatement(insertQuery);) {
            int rowsInserted = prStmt.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
        } catch (SQLException e) {
            System.out.println("Error occurred while inserting data: " + e.getMessage());
        }
    }


    public void insertNewBook(String title, String author, int year, String genre, String type) {
      if (title == null || author == null || genre == null || type == null)
          throw new NullPointerException("Title, author, genre, and type must be set.");
  
      Connection conn = dbconnect.getConnection();
  
      String insertBook;
  
      if (type.equalsIgnoreCase("roman")) {
          insertBook = "INSERT INTO romany (nazev, autor, rok, zanr) VALUES (?, ?, ?, ?)";
      } else if (type.equalsIgnoreCase("ucebnice")) {
          insertBook = "INSERT INTO ucebnice (nazev, autor, rok, rocnik) VALUES (?, ?, ?, ?)";
      } else {
          throw new IllegalArgumentException("Invalid book type.");
      }
  
      try (PreparedStatement prStmt = conn.prepareStatement(insertBook)) {
          prStmt.setString(1, title);
          prStmt.setString(2, author);
          prStmt.setInt(3, year);
          prStmt.setString(4, genre);
  
          prStmt.executeUpdate();
  
          System.out.println("New book has been inserted into the database!");
      } catch (SQLException e) {
          System.out.println("Book has already been inserted or incorrect SQL INSERT command");
      }
  }
}
