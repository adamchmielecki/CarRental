package application;
import java.sql.*;

public class Database {
    Statement stmt;
    public Statement connect(){
        try{

            Class.forName("oracle.jdbc.OracleDriver");

            Connection con=DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","hr","rune");

            stmt=con.createStatement();
        }catch(Exception e){ System.out.println(e);}
        return stmt;
    }
}
