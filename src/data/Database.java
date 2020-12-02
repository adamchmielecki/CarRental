package application;
import java.sql.*;

public class Database {
    public static void main(String args[]){
        try{

            Class.forName("oracle.jdbc.OracleDriver");

            Connection con=DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","hr","rune");

            Statement stmt=con.createStatement();

            ResultSet rs=stmt.executeQuery("select * from PERSONAL_DATA");
            while(rs.next()) {
                String firstName = rs.getString("FIRST_NAME");
                String lastName = rs.getString("LAST_NAME");
                int ID = rs.getInt("PERSONAL_DATA_ID");
                System.out.println(ID + ", " + firstName + ", " + lastName);
            }

            con.close();

        }catch(Exception e){ System.out.println(e);}

    }
}
