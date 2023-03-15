



/*=======================================================================
  ORNEK2: isciler adinda bir tablo olusturunuz id int,
  birim VARCHAR(10), maas int
========================================================================*/
import java.sql.*;

public class JDBC_Yeni {


    public static void main(String[] args) throws ClassNotFoundException, SQLException {

   /*
 	A) CREATE TABLE, DROP TABLE, ALTER TABLE gibi DDL ifadeleri icin sonuc kümesi (ResultSet)
 	   dondurmeyen metotlar kullanilmalidir. Bunun icin JDBC'de 2 alternatif bulunmaktadir.
 	    1) execute() metodu - boolean dondurur.
 	    2) executeUpdate() metodu - int deger dondurur.

 	B) - execute() metodu her tur SQL ifadesiyle kullanilabilen genel bir komuttur.
 	   - execute(), Boolean bir deger dondurur. DDL islemlerinde false dondururken,
 	     DML islemlerinde true deger dondurur.
 	   - Ozellikle, hangi tip SQL ifadesine hangi metodun uygun oldugunun bilinemedigi
 	     durumlarda tercih edilmektedir.

 	C) - executeUpdate() metodu ise INSERT, Update gibi DML islemlerinde yaygin kullanilir.
 	   - bu islemlerde islemden etkilenen satir sayisini dondurur.
 	   - Ayrıca, DDL islemlerinde de kullanilabilir ve bu islemlerde 0 dondurur.
    */

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "1234");
        Statement st = con.createStatement();

        /*======================================================================
		  ORNEK1: isciler tablosunu siliniz.
 	    ========================================================================*/


        String dropQuery = "DROP TABLE isciler";

// System.out.println(st.execute(dropQuery));

        if (!st.execute(dropQuery)){
            System.out.println("Isciler tablosu silindi!");
        }
        String createTable = "CREATE TABLE isciler" +
                "(id INT, " +
                "birim VARCHAR(10), " +
                "maas INT)";

        if (!st.execute(createTable)){
            System.out.println("Isciler tablosu olusturuldu!");
        }

        /*=======================================================================
		  ORNEK3: isciler tablosuna yeni bir kayit (80, 'ARGE', 4000)
		  ekleyelim.
		========================================================================*/

        String insertData = "INSERT INTO isciler VALUES(80, 'ARGE', 4000)";

        System.out.println("Islemden etkilenen satir sayisi : " + st.executeUpdate(insertData));


        /*=======================================================================
          ORNEK4: isciler tablosuna birden fazla yeni kayıt ekleyelim.
            INSERT INTO isciler VALUES(70, 'HR', 5000)
            INSERT INTO isciler VALUES(60, 'LAB', 3000)
            INSERT INTO isciler VALUES(50, 'ARGE', 4000)
         ========================================================================*/
        System.out.println( "********************1.YONTEM*******************");

        String [] queries ={"INSERT INTO isciler VALUES(70, 'HR', 5000)",
                "INSERT INTO isciler VALUES(60, 'LAB', 3000)",
                "INSERT INTO isciler VALUES(50, 'ARGE', 4000)"};
        int count=0;
        for (String each:queries){
            count+=     st.executeUpdate(each);

        }
        System.out.println(count + "satir eklendi");

// Ayri ayri sorgular ile veritabanina tekrar tekrar ulasmak islemlerin
        // yavas yapilmasina yol acar. 10000 tane veri kaydi yapildigi dusunuldugunde
        // bu kotu bir yaklasimdir.

        System.out.println("=============== 2. Yontem ==============");




        // 2.YONTEM (addBatch ve executeBatch() metotlari ile)
        // ----------------------------------------------------
        // addBatch metodu ile SQL ifadeleri gruplandirilabilir ve executeBatch()
        // metodu ile veritabanina bir kere gonderilebilir.
        // executeBatch() metodu bir int [] dizi dondurur. Bu dizi her bir ifade sonucunda
        // etkilenen satir sayisini gosterir.

        String [] queries2 ={"INSERT INTO isciler VALUES(10, 'teknik ', 3000)",
                "INSERT INTO isciler VALUES(20, 'kantin', 2000)",
                "INSERT INTO isciler VALUES(30, 'arge', 5000)"};

        for (String each:queries2) { // bu dongude her bir SQL komutunu torbaya atiyor
            st.addBatch(each);
        }

        st.executeBatch(); // burda  da tek seferde tum torbayi goturup databas  isliyor

        System.out.println(" satirlar eklendi");

        // ornek: isciler tablosunu goruntuleyin


        System.out.println("******************ISCILER TABLOSU*************************");

        String selectQuery = "select * from isciler";
        ResultSet iscilertablosu = st.executeQuery(selectQuery);

        while (iscilertablosu.next()){
            System.out.println(iscilertablosu.getInt(1) + " " +
                    iscilertablosu.getString(2) + " " +
                    iscilertablosu.getInt(3));


        }

            /*=======================================================================
  ORNEK6: isciler tablosundaki maasi 5000'den az olan iscilerin maasina
   %10 zam yapiniz
========================================================================*/


        String updateQuery ="UPDATE isciler SET maas=maas*1.1 WHERE maas<5000";
        int satir=  st.executeUpdate(updateQuery);

        System.out.println(satir + "satir guncellendi");

        System.out.println("******************ISCILER TABLOSU MAAS ZAMLARI*************************");


        ResultSet iscilertablosu2 = st.executeQuery(selectQuery);

        while (iscilertablosu2.next()){
            System.out.println(iscilertablosu2.getInt(1) + " " +
                    iscilertablosu2.getString(2) + " " +
                    iscilertablosu2.getInt(3));


        }

        /*=======================================================================
 ORNEK8: Isciler tablosundan birimi 'ARGE' olan iscileri siliniz.
========================================================================*/

        String deletQuery = "DELETE FROM isciler  WHERE birim='ARGE'";

        int silinenSatirSayisi =st.executeUpdate(deletQuery);
        System.out.println(silinenSatirSayisi + "satir silindi");

        System.out.println("******************ISCILER TABLOSU SON DURUM*************************");


        ResultSet iscilertablosu3 = st.executeQuery(selectQuery);

        while (iscilertablosu3.next()){
            System.out.println(iscilertablosu3.getInt(1) + " " +
                    iscilertablosu3.getString(2) + " " +
                    iscilertablosu3.getInt(3));


        }

        con.close();
        st.close();
        iscilertablosu.close();
        iscilertablosu2.close();
        iscilertablosu3.close();

    }{
    }
}



