import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import orm.CarserviceEntity;
import orm.InvoiceEntity;
import javax.persistence.Query;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;


public class printTables {


    public static void main(String[] args) throws DocumentException, FileNotFoundException {


        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = sessionFactory.openSession();
        CarserviceEntity a = session.load(CarserviceEntity.class, 1);
        System.out.println(a);

//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        Date inputString1 = Date.valueOf("2020-05-05");
//        Date inputString2 = Date.valueOf("2020-05-08");
//        long diff = inputString2.getTime() - inputString1.getTime();
//        System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

//        long diff = TimeUnit.DAYS.convert(inputString2.getTime() - inputString1.getTime(), TimeUnit.MILLISECONDS);
//        System.out.println ("Days: " + diff);

//        Document document = new Document();
//        PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));
//
//        document.open();
//        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
//        Chunk chunk = new Chunk("Hello World", font);
//
//        document.add(chunk);
//        document.close();

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
