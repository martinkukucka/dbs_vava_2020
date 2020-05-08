import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import orm.InvoiceEntity;
import javax.persistence.Query;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class printTables {


    public static void main(String[] args) throws DocumentException, FileNotFoundException {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Hello World", font);

        document.add(chunk);
        document.close();

//        try {
//            Connection con = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
//                    "root", "root");
//            Statement stmt = con.createStatement();
//
//            ResultSet rs = stmt.executeQuery("select name, phone_number, city, address_id from test.customer, test.address where customer.address_id = address.id;");
//
//            while (rs.next()) {
//                System.out.println(rs.getString("name") + " " +
//                        rs.getString("phone_number") + " " +
//                        rs.getString("city") + " " +
//                        rs.getString("address_id"));
//            }
//        } catch(SQLException e) {
//            System.out.println("SQL exception occured " + e);
//        }



//        InvoiceEntity invoice = new InvoiceEntity();
//        invoice.setCompanyname("sdga");
//        invoice.setAmount(54.24);
//        invoice.setBillto("jan");
//
//        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
//        Session session = sessionFactory.openSession();
//
//        session.beginTransaction();
//        session.save(invoice);
//        String hql = "SELECT max(id) FROM InvoiceEntity";
//        Query query = session.createQuery(hql);
//        int result = (int) query.getResultList().get(0);
//        System.out.println(result);
//        session.getTransaction().commit();
//
//        session.close();


    }

}
