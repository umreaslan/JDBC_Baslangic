import java.sql.*;

public class JDBC01_Query01 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        // !1- ilgili DRIVERI YUKLEMELIYIZ,Mysql kullandigimizi bildiriyoruz

        //Driveri bulammam ihtimaline karsi bizden forname methodu icin ClassNotFoundException
        // method signatura miza exception olarak firlatmamizi istiyor
        //"com.mysql.cj.jdbc.Driver":   mysql icin hep bunu yazicaz

        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2- baglantiyi kurabilmek icin username ve password unu girmeliyiz
        // burada da username pasworld un yanlis olma ihtimaline karsi bir
        // SQLException  firlatmamizi istiyor.

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "1234");

       // 3- SQL query leri icin bir statement objesi olusturup
        // java da sql  sorgularimiz icin bir alan acacagiz

        Statement st = con.createStatement();

        // 4-SQL query lerimizi yazip calistirabiliriz

       ResultSet veri= st.executeQuery("SELECT * FROM  personel");

       // 5- sonuclari gormek icin iteration ile set icerisinde ki elemanlari bir while dongusu ile yazdiriyoruz

        while (veri.next()){
            System.out.println(veri.getInt(1) + " " +  veri.getString(2) + " " +veri.getString(3) +
                     " " + veri.getInt(4) + " " + veri.getString(5));
        }

        // 6- olusturulan nesnelri kapatiyoruz ki bellekten  kaldirilsin

        con.close();
        st.close();
        veri.close();







    }




}
