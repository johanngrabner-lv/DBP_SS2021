package campus02.dbp;

import java.sql.*;

public class GameLanguagesAndAgeDAO {

    private String connectionString="jdbc:sqlite:C://sqlite/db/donnerstag.db";
    private int gameLanguagesAndAgeId;
    int gameId;
    String nameDeutsch;
    String nameEnglish;
    String nameSpanish;

    public int getGameLanguagesAndAgeId() {
        return gameLanguagesAndAgeId;
    }

    public void setGameLanguagesAndAgeId(int gameLanguagesAndAgeId) {
        this.gameLanguagesAndAgeId = gameLanguagesAndAgeId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getNameDeutsch() {
        return nameDeutsch;
    }

    public void setNameDeutsch(String nameDeutsch) {
        this.nameDeutsch = nameDeutsch;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getNameSpanish() {
        return nameSpanish;
    }

    public void setNameSpanish(String nameSpanish) {
        this.nameSpanish = nameSpanish;
    }

    public double getMinAge() {
        return minAge;
    }

    public void setMinAge(double minAge) {
        this.minAge = minAge;
    }

    double minAge;

    public  void createTable() {

        // SQL statement for creating a new table
        String createTableSql = "CREATE TABLE GameLanguagesAndAge (\n"
                + "	GameLanguagesAndAgeId INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	GameId INTEGER,\n"
                + "	NameDeutsch VARCHAR(255),\n"
                + "	NameEnglish VARCHAR(255), \n"
                + "	NameSpanish VARCHAR(255), \n"
                + "	MinAge DECIMAL,\n"
                + " FOREIGN KEY(GameId) REFERENCES Game(GameId));";

        try (Connection conn = DriverManager.getConnection(connectionString);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSql);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void save() {
        String insertSQL = "";
        insertSQL = "INSERT INTO GameLanguagesAndAge(GameId, NameDeutsch, NameEnglish, NameSpanish, MinAge) ";
        insertSQL = insertSQL + " Values(?,?,?,?,?)";

        String pragmaForeignKey="PRAGMA foreign_keys=on;";
        try {
            Connection conn = DriverManager.getConnection(connectionString);
            PreparedStatement stmt = conn.prepareStatement(insertSQL);
            PreparedStatement activateForeignKey = conn.prepareStatement(pragmaForeignKey);
            activateForeignKey.executeUpdate();
                    stmt.setInt(1, gameId);
                    stmt.setString(2, nameDeutsch);
                    stmt.setString(3, nameEnglish);
                    stmt.setString(4, nameSpanish);
                    stmt.setDouble(5, minAge);
                   stmt.executeUpdate();
                    String sqlText = "SELECT last_insert_rowid() as rowid;";
                    PreparedStatement stmtAutoincrement = conn.prepareStatement(sqlText);
                    ResultSet rs = stmtAutoincrement.executeQuery();
                    rs.next();
                    int autoincrementValue = rs.getInt("rowid");
                    setGameLanguagesAndAgeId(autoincrementValue);
                    stmtAutoincrement.close();
                    rs.close();
                } catch (SQLException ex) {
                }

    }

    public void printInfo(){
        String selectSQL = "";
        selectSQL = "SELECT * FROM GameLanguagesAndAge";

        try {
            Connection conn = DriverManager.getConnection(connectionString);
            PreparedStatement stmt = conn.prepareStatement(selectSQL);
            ResultSet rs = stmt.executeQuery();
            String nameTranslated="";
            rs.next();
            nameTranslated = rs.getString("NameDeutsch");
            if (rs.wasNull()){
                System.out.println("Die deutsche Bezeichnung war leer");
            } else
            {
                System.out.println("Deutsch: " + nameTranslated);
            }
            nameTranslated = rs.getString("NameEnglish");
            if (rs.wasNull()){
                System.out.println("Die englische Bezeichnung war leer");
            } else
            {
                System.out.println("English: " + nameTranslated);
            }
            nameTranslated = rs.getString("NameSpanish");
            if (rs.wasNull()){
                System.out.println("Die spanisch Bezeichnung war leer");
            } else
            {
                System.out.println("Spanisch: " + nameTranslated);
            }

            rs.close();
        } catch (SQLException ex) {
        }
    }
}
