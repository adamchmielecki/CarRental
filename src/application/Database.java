package application;
import java.sql.*;

public class Database {
    Statement stmt;
    public Statement connect(){
        try{

            Class.forName("oracle.jdbc.OracleDriver");

            Connection con=DriverManager.getConnection(
                    "jdbc:oracle:thin:hr@localhost:1521/xe","hr","rune");

            if (con != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }

            stmt = con.createStatement();
        }catch(Exception e){ System.out.println(e);}
        return stmt;
    }
}
