import java.sql.*;

public class Hello_World {


    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println();

        try {
            Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
            "root", "root");
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from test.adresa");
            stmt.executeUpdate("insert into test.adresa(mesto, ulica_cislo_domu, cislo_domu, psc) values ('Bratislava', 'Zochova', '124', '02901')");

//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("meno");
//                System.out.println(id + "   " + name + "    ");
//            }
        } catch(SQLException e) {
            System.out.println("SQL exception occured " + e);
        }

    }
}