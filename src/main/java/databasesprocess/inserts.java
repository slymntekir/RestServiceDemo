package databasesprocess;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import pojos.musteri;

public class inserts {

    baglanti b = new baglanti();
    PreparedStatement ps = null;
    ResultSet rs = null;

    public inserts() {
        b = new baglanti();
        b.baglan();
    }

    // kullanıcı ekleyen method
    // guncellendi 5/2/2017 21:38
    public String musteriEkle(musteri m) {
        String sorgu = "set IDENTITY_INSERT Kullanici ON\n"
                + "insert into Kullanici(kullanici_id,tc_no,adi,soyadi,arac_plaka,kullanici_adi,kullanici_sifre,"
                + "email,bakiye,tel_no,adres) "
                + "values(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = b.con.prepareStatement(sorgu);
            ps.setInt(1,new selects().getMaxMusteriId()+1);
            ps.setString(2,m.getTc_no());
            ps.setString(3,m.getAd());
            ps.setString(4,m.getSoyad());
            ps.setString(5,m.getArac_plaka());
            ps.setString(6,m.getKullanici_adi());
            ps.setString(7,m.getSifre());
            ps.setString(8,m.getE_mail());
            ps.setInt(9,m.getBakiye());
            ps.setString(10,m.getTel_no());
            ps.setString(11,m.getAdres());
            ps.executeUpdate();
            return "tamam";
        } catch (SQLException e) {
            return "" + e.getMessage();
        }
    }

    // kullanıcı bakiye yükleyen method
    // guncellendi 5/2/2017 21:45 çalışıyor
    public String tlYukle(int bakiye, int musteri_id) {
        String sorgu = "update Kullanici set bakiye=bakiye+? where kullanici_id=?";
        try {
            ps = b.con.prepareStatement(sorgu);
            ps.setInt(1, bakiye);
            ps.setInt(2, musteri_id);
            ps.executeUpdate();
            return "tamam";
        } catch (SQLException e) {
            return "" + e.getMessage();
        }
    }

    // kiralama işlemini veritabanından yapan method
    // guncellendi 5/2/2017 22:40 çalışıyor ancak android tarafı eksik
    public String kirala(int m_id, int konum_id, int lokasyon_id, Date tarih, Time bas_saat, Time bit_saat,int fiyat) {
        String sorgu = "set IDENTITY_INSERT Kiralanan ON\n"
                + "insert into Kiralanan(kiralanan_id,kullanici_id,konum_id,lokasyon_id,"
                + "tarih,baslangic_saat,bitis_saat,fiyat)"
                + "values(?,?,?,?,?,?,?,?)";
        try {
            ps = b.con.prepareStatement(sorgu);
            ps.setInt(1,new selects().getMaxKiralananId()+1);
            ps.setInt(2,m_id);
            ps.setInt(3,konum_id);
            ps.setInt(4,lokasyon_id);
            ps.setDate(5,tarih);
            ps.setTime(6,bas_saat);
            ps.setTime(7,bit_saat);
            ps.setInt(8,fiyat);
            ps.execute();
            return "tamam";
        } catch (SQLException e) {
            return "1 ." + e.getMessage();
        }
    }
    
    // kulllanıcı bilgilerini guncelleyen method
    public String kullaniciGuncelle(int kullanici_id,String tc_no,String adi,String soyadi,
            String arac_plaka,String kul_adi,String sifre,String email,String tel_no,String adres)
    {
        try
        {
            ps = b.con.prepareStatement("update Kullanici set tc_no=?,adi=?,soyadi=?,"
                    + "arac_plaka=?,kullanici_adi=?,"
                    + "kullanici_sifre=?,email=?,tel_no=?,"
                    + "adres=? where kullanici_id=?");
            ps.setString(1,tc_no);
            ps.setString(2,adi);
            ps.setString(3,soyadi);
            ps.setString(4,arac_plaka);
            ps.setString(5,kul_adi);
            ps.setString(6,sifre);
            ps.setString(7,email);
            ps.setString(8,tel_no);
            ps.setString(9,adres);
            ps.setInt(10,kullanici_id);
            ps.executeUpdate();
            return "tamam";
        }catch(SQLException e){
            return e.getMessage();
        }
    }
    
    public String tlYukle(String tl_kodu,int musteri_id)
    {
        int miktar = 0;
        if(miktarDon(tl_kodu) != -1 && miktarDon(tl_kodu)>0)
            miktar = miktarDon(tl_kodu);
        else if(miktarDon(tl_kodu)== -1)
            return "hata.1";
       
        String sonuc = tlYukle(miktar, musteri_id);
        if(sonuc.trim().contains("ama"))
        {
            boolean degisken = miktarSil(tl_kodu);
            if(degisken)
            {
                return "tamam";
            }
        }
        return "hata.all";
    }
    
    public boolean miktarSil(String tl_kodu)
    {
        String sorgu = "delete ParkKontor where sifre=?";
        try {
            ps = b.con.prepareStatement(sorgu);
            ps.setString(1,tl_kodu);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public int miktarDon(String tl_kodu)
    {
        int miktar;
        try {
            ps = b.con.prepareStatement("select kontor from ParkKontor where sifre=?");
            ps.setString(1, tl_kodu);
            rs = ps.executeQuery();
            rs.next();
            miktar = rs.getInt(1);
            return miktar;
        } catch(SQLException e) {
            return -1;
        }
    }
}